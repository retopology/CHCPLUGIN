package CustomGame;


import CustomGame.InventoriesGame.MagazineInventory;
import CustomGame.InventoriesGame.RoleInventory;
import CustomGame.Roles.WarriorRole;
import CustomGame.Skills.SkillRef;
import Mobs.MobRef;
import Mobs.WaveRef;
import Mobs.Waves;
import Mobs.WavesObj;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.spigot.test_plugin.Test_plugin;

import java.util.*;

public class GameRules {


    private Plugin plugin = Test_plugin.getPlugin(Test_plugin.class); // Плагин
    private Location startPoint; // Точка спавна ОСНОВНОЙ АРЕНЫ

    private World world; // Мир, в котором идет игра
    private ArrayList<GamePlayer> players = new ArrayList<>(); // Список игроков
    private ArrayList<Arena> arenas = new ArrayList<>(); // Список арен
    private int currentWave; // Номер текущей волны
    private ArrayList<MobRef> wave = new ArrayList<>(); // Список всех мобов на волне (у всех игроков)
    private ArrayList<GamePlayer> duelList = new ArrayList<>();
    Creeper magazine;



    // Инициализация
    public GameRules(ArrayList<Player> _players, World _world){
        world = _world;
        currentWave = 0;
        arenas.clear();
        players.clear();
        Location locMag = new Location(world, -357, 63,616);
        magazine = world.spawn(locMag, Creeper.class);
        magazine.setCustomName("МАГАЗИН");
        magazine.setSilent(true);
        magazine.setAI(false);
        magazine.setRotation(180,0);


        FillArenaArray();
        SetPlayers(_players);

        //players.get(0).getPlayer().sendMessage("Мир создан");
        startPoint = new Location(world,-355,63,612);
        TeleportToSpawn();
        SelectRole();
    }

    // Смена цвета босс бара
    public void SetColorBossBar(BarColor color){
        Test_plugin.bar.setColor(color);
    }

    // Значение босс бара
    public void SetBarValue(double m, String Reason){
        Test_plugin.bar.setProgress(m/100);
        Test_plugin.bar.setTitle(Reason);
    }

    // Получить текущую волну
    public int GetCurrentWave(){
        return currentWave;
    }

    // Получить спискок волны врагов
    public ArrayList<MobRef> GetWaveList(){
        return wave;
    }

    // Изменить спискок волны врагов
    public void SetWaveList(ArrayList<MobRef> waves){
        wave = waves;
    }


    // Изменить спискок игроков
    public void SetPlayersArray(ArrayList<GamePlayer> plaus){
        players = plaus;
    }

    // Получить спискок игроков
    public ArrayList<GamePlayer> GetPlayersArray(){
        return players;
    }

    // Рандномый выбор следующей волны
    public void SelectWaveForm(){
            WaveRef refer = new WaveRef();
            int num = new Random().nextInt(0,3);
            //int num = 1;
            switch (num){
                case 0: refer.setMobsWave(Waves.Wave_Only_Skeletons()); break;
                case 1: refer.setMobsWave(Waves.Wave_Only_Zombies()); break;
                case 2: refer.setMobsWave(Waves.Wave_Zombie_And_Skeleton()); break;
            }
            ArrayList<WavesObj> map = refer.getMobsWave();
            int inex = 0;
            for (int i = 0; i < players.size(); i++) {
                if (!players.get(i).isDueling()) {
                    for (WavesObj obj: map) {
                        for (int j = 0; j < obj.count; j++) {
                            MobRef ref = obj.mob;
                            ref.setUnicode(players.get(i).getPlayer().getName() + obj.mob.GetEntity() + inex);
                            ref.setPlayer(players.get(i));
                            inex++;
                            wave.add(ref);


                        }
                    }
                    map = refer.getMobsWave();


                }
            }





    }

    // Генерация волны
    public void SpawnWave(){
        SelectWaveForm();


       //
        try {

            for (MobRef ref: wave) {
                for (GamePlayer play: players) {

                    if(ref.getPlayer() == play) {
                        int numLeft = new Random().nextInt(1, 4);
                        int minusOrPlus = new Random().nextInt(0, 2);
                        numLeft = minusOrPlus == 0 ? numLeft * -1 : numLeft;


                        int numTop = new Random().nextInt(1, 4);
                        minusOrPlus = new Random().nextInt(0, 2);
                        numTop = minusOrPlus == 0 ? numTop * -1 : numTop;
                        Location newLoc = new Location(world, numLeft, 0, numTop);
                        Location loc = ref.getPlayer().getArena().getStartLocation();
                        newLoc = newLoc.add(loc);


                        Entity entMob = world.spawnEntity(newLoc, EntityType.fromName(ref.GetEntity()));


                        LivingEntity enemy = (LivingEntity) entMob;

                        double manyDmg = ref.getDamage() + currentWave + ref.GetUpgradePropetry();

                        ItemStack weapon = new ItemStack(Material.BONE);
                        AttributeModifier mod = new AttributeModifier(UUID.randomUUID(), "generic.attackDamage", manyDmg,
                                AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND);
                        ItemMeta metaEnemy = weapon.getItemMeta();
                        metaEnemy.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, mod);
                        weapon.setItemMeta(metaEnemy);
                        enemy.getEquipment().setItemInHand(weapon);

                        double manyHp = ref.getHealth() + currentWave + ref.GetUpgradePropetry();
                        enemy.setMaxHealth(manyHp);
                        enemy.setHealth(manyHp);
                        enemy.setCustomName(ref.getUnicode());
                    }

                }

            }

        }
        catch (Exception ex){
            players.get(0).getPlayer().sendMessage(ex.getMessage());
        }


    }

    // Генерация игроков
    public void SetPlayers(ArrayList<Player> _players){
        for(int i = 0; i < _players.size(); i++){
            GamePlayer player = new GamePlayer();
            player.setPlayer(_players.get(i));
            player.setMagazine(new MagazineInventory());
            player.getMagazine().GetInventory(_players.get(i));
            player.setHealth(2);
            player.setMoney(500);
            player.setArena(arenas.get(i));
            player.setChecker(false);
            player.RegisterScoreBar();


            Test_plugin.bar.addPlayer(_players.get(i));


            players.add(player);
        }

    }
    BukkitTask runnable = null;

    // Таймер
    public void StartTimer(TimerCause cause){

        switch (cause.toString()){
            case "SKILLS":{

                   runnable = new BukkitRunnable() {
                        int countTick = 10;

                        @Override
                        public void run() {

                            if(countTick > -1) {

                                double setValue = ((countTick) * 100d) / 10;
                                SetBarValue(setValue, "Выбор роли - " + countTick + " сек.");
                                for (GamePlayer player : players) {
                                    if (!player.isChecker())
                                        player.getPlayer().openInventory(RoleInventory.GetInventory());
                                }
                                if(isAllReady()){
                                    StartGame();
                                }


                            }
                            else{
                                StartGame();
                            }
                            countTick--;

                        }

                    }.runTaskTimer(plugin, 0, 20);

            } break;

            case "CHILL":{
                runnable = new BukkitRunnable() {
                    int countTick = 20;
                    @Override
                    public void run() {
                        double setValue = ((countTick) * 100d) / 20;
                        SetBarValue(setValue, "До волны " + currentWave + " - " + countTick + " сек.");
                        if(countTick != 0) {
                            countTick--;
                            if(countTick == 3){
                                for (GamePlayer player : players) {
                                    try {
                                        if(!player.isDueling())player.getPlayer().teleport(player.getArena().getStartLocation());
                                    }
                                    catch (Exception ex){
                                        player.getPlayer().sendMessage(ex.getMessage());
                                    }


                                }
                            }


                        }
                        else{

                            SpawnWave();
                            RefreshScore();
                            TeleportToDuelArena();
                            StartTimer(TimerCause.WAVE);
                            this.cancel();
                        }
                    }

                }.runTaskTimer(plugin, 0, 20);
            } break;
            case "WAVE":{
                runnable = new BukkitRunnable() {
                    int countTick = 45;
                    boolean berserkMode = false;
                    @Override
                    public void run() {
                        if(!berserkMode) {
                            double setValue = ((countTick) * 100d) / 45;
                            SetBarValue(setValue, "Волна №" + currentWave + " - " + countTick + " сек.");
                        }
                        if(countTick == 0){
                            SetColorBossBar(BarColor.RED);
                            SetBarValue(1, "ВЫ ПОЛУЧАЕТЕ УРОН");
                            SetBerserkMode();
                        }
                        else countTick--;

                        if(isAllReady() && !CheckDuels())this.cancel();
                    }

                }.runTaskTimer(plugin, 0, 20);
            }
            break;
        }

    }

    // Проверка на игроков в дуэли
    public boolean CheckDuels(){
        for (GamePlayer play: players) {
            if(play.isDueling())return true;
        }
        return false;
    }

    // Завершить дуэль
    public void EndDuel(Player winner){
        for (GamePlayer player: duelList){
            player.setDueling(false);
            player.setChecker(true);
            TeleportToSpawn(player);
            if(player.getPlayer() == winner){
                player.setMoney(player.getMoney() + 50 * currentWave / 2);
            }
            else{
                if(players.size() < 4) {
                    player.setHealth(player.getHealth()-1);
                    if(player.getHealth() < 0){
                        player.getPlayer().setMaxHealth(player.getPlayer().getMaxHealth() - 2);
                    }
                }
            }
            player.RegisterScoreBar();
        }
        duelList.clear();
        if(isAllReady()) NextWave();
    }

    // Берсерк
    public void SetBerserkMode(){
        for (GamePlayer ply: players) {
            if(!ply.isChecker()){
                ply.getPlayer().getWorld().playEffect(ply.getPlayer().getLocation(), Effect.END_GATEWAY_SPAWN, 0);
                if(ply.getPlayer().getHealth() -1 <= 0){
                    ply.getPlayer().setHealth(20);
                    ply.setHealth(ply.getHealth()-1);
                    if(ply.getHealth() < 0){
                        players.remove(ply);
                    }
                }
                ply.getPlayer().setHealth(ply.getPlayer().getHealth() - 1);
            }
        }
    }

    // Проверка на готовность
    public boolean isAllReady(){
        for (GamePlayer player: players) {

            if(!player.isChecker()) return false;


        }
        return true;
    }

    // Убрать готовность у всех
    public void OffReadyPlayers(){
        for (GamePlayer player: players) {
            player.setChecker(false);
        }
    }

    // Выбрать 2 случайных челов на дуэль
    public void GetTwoPlayersForDuel(){
        Random rnd = new Random();
        GamePlayer duel = players.get(rnd.nextInt(0,players.size()));
        if(duelList.get(0) == duel) GetTwoPlayersForDuel();
        else{
            duel.setDueling(true);
            duelList.add(duel);
            if(duelList.size() != 2) GetTwoPlayersForDuel();
        }
    }

    // Телепортировать их на арену дуэли
    public void TeleportToDuelArena(){
        if(duelList != null && duelList.size() == 2) {
            Location spawnfirst = new Location(world, 0, 0, 0);
            Location spawnsecond = new Location(world, 0, 0, 0);
            duelList.get(0).getPlayer().teleport(spawnfirst);
            duelList.get(1).getPlayer().teleport(spawnsecond);
        }

    }

    // Начать следующую волну
    public void NextWave(){
        if(runnable != null) runnable.cancel();
        SetColorBossBar(BarColor.GREEN);
        wave.clear();
        OffReadyPlayers();
        TeleportToSpawn();
        currentWave++;
        RefreshScore();
        for (GamePlayer play: players) {
            play.getPlayer().setHealth(20);
            play.getPlayer().setGameMode(GameMode.CREATIVE);
        }
        if(currentWave > 3 && players.size() > 1){
            duelList.clear();
            GetTwoPlayersForDuel();
            Bukkit.broadcastMessage(ChatColor.BLACK + "ДУЭЛЬ! " + ChatColor.YELLOW + duelList.get(0) + "(" + duelList.get(0).getRole().GetNameRole() + ")" + " VS " +duelList.get(1)
                    + "(" + duelList.get(1).getRole().GetNameRole() + ")");
        }
        OffReadyPlayers();
        StartTimer(TimerCause.CHILL);
    }

    // Начало игры
    public void StartGame(){
        runnable.cancel();
        for (GamePlayer player : players) {

            player.getPlayer().closeInventory();
            player.getPlayer().setGameMode(GameMode.CREATIVE);
            try {
            if (player.getRole() == null) player.setRole(new WarriorRole());

                player.getPlayer().sendMessage("Все роли распеделены, вы играете за " + player.getRole().GetNameRole());
                player.getPlayer().getInventory().addItem(player.getRole().GetMainWeapon());
                SkillRef mainSkill = player.getRole().GetMainSkill();
                if (mainSkill != null) {
                    player.AddMainSkill(mainSkill);
                }
            }
            catch (Exception ex){
                player.getPlayer().sendMessage(ex.getMessage());
            }


        }
        NextWave();
    }

    // Конец игры
    public void EndGame(){
        players.clear();
        wave.clear();
        magazine.remove();
        runnable.cancel();
        Test_plugin.bar.removeAll();
    }

    // Получить координаты арен (в Location должен быть центр арены, место спавна игрока если быть точнее)
    public void FillArenaArray(){
        Arena arena1 = new Arena();
        arena1.setStartLocation(new Location(world,-355,63,629));
        arenas.add(arena1);

        Arena arena2 = new Arena();
        arena2.setStartLocation(new Location(world,-354,63,595));
        arenas.add(arena2);
    }

    // Выбор роли на игру
    public void SelectRole(){
        for(int i = 0; i < players.size(); i++){
            players.get(i).getPlayer().openInventory(RoleInventory.GetInventory());
        }
        StartTimer(TimerCause.SKILLS);
    }

    // Телепортировать игрока на его арену
    public void TeleportToArena(GamePlayer player){
        player.getPlayer().teleport(player.getArena().getStartLocation());
    }

    // Телепорт одного игрока на спавн
    public void TeleportToSpawn(GamePlayer player){
        player.getPlayer().teleport(startPoint);
    }

    // Тп всех игроков на спавн
    public void TeleportToSpawn(){
        for(int i = 0; i < players.size(); i++){
            players.get(i).getPlayer().teleport(startPoint);
        }
    }

    // Обновить счет игрока
    public void RefreshScore(GamePlayer player){
        player.RegisterScoreBar();
    }

    // Обновить счет игроков
    public void RefreshScore(){
        for (GamePlayer play: players) {
            play.RegisterScoreBar();
        }

    }

    // Сколько осталось мобов в волне у игрока
    public int GetCountMobsInWavePlayer(GamePlayer playerWhoDef){
        int count = 0;
        for (MobRef wav: wave) {

            if(wav.getPlayer() == playerWhoDef) {
                count++;
            }
        }
        return count;
    }
    public int GetCountMobsInWavePlayer(Player play){
        GamePlayer currPLayer = null;
        for (GamePlayer pla: players) {
            if(play == pla.getPlayer()){
                currPLayer = pla;
                break;
            }
        }
        int count = 0;
        for (MobRef wav: wave) {

            if(wav.getPlayer() == currPLayer) {
                count++;
            }
        }
        return count;
    }

    public ArrayList<GamePlayer> getDuelList() {
        return duelList;
    }

    public void setDuelList(ArrayList<GamePlayer> duelList) {
        this.duelList = duelList;
    }


    // #1
    // -354 63 629
    // left right 7 // 6
    // front back 4 // 3

    // #2
    //-354 63 595
}
