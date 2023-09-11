package CustomGame;

import CustomGame.InventoriesGame.MagazineInventory;
import CustomGame.Roles.Roles;
import CustomGame.Skills.SKILL_SPLASH;
import CustomGame.Skills.SkillRef;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.*;
import org.spigot.test_plugin.Test_plugin;

import java.util.ArrayList;

public class GamePlayer {
    // Player Info //
    //----------------------------------------------------------------------
    private  Player player;
    private  int Health = 2;
    private  int Money = 0;
    private  Roles role;
    private Arena arena;
    private boolean checker = false;
    private boolean Dueling = false;
    private MagazineInventory magazine;

    //----------------------------------------------------------------------
    // Score Info //
    //----------------------------------------------------------------------
    private Objective scoreBar;
    private Score currentWave;
    private Score emptyScore1;
    private Score scoreGold;
    private Score scoreRole;
    private Score scoreLives;
    private Scoreboard board;

    private Score emptyScore2;

    private Score needKills;
    private ArrayList<SkillRef> skills = new ArrayList<>();

    //----------------------------------------------------------------------
    // Cooldown Info //
    //----------------------------------------------------------------------

    private Objective scoreBarCooldowns;
    private Scoreboard boardCooldowns;

    //----------------------------------------------------------------------
    public void RegisterScoreBarCoolDowns(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        boardCooldowns = manager.getNewScoreboard();

        scoreBarCooldowns = boardCooldowns.registerNewObjective(this.player == null ? "None" : this.player.getName(), this.player.getName() + "1", ChatColor.RED + "ПЕРЕЗАРЯДКИ");
        scoreBarCooldowns.setDisplaySlot(DisplaySlot.BELOW_NAME);
    }
    public void RegisterScoreBar(){
        try {


            ScoreboardManager manager = Bukkit.getScoreboardManager();
            board = manager.getNewScoreboard();

            scoreBar = board.registerNewObjective(this.player == null ? "None" : this.player.getName(), this.player.getName() + "1", ChatColor.RED + "Профиль");
            scoreBar.setDisplaySlot(DisplaySlot.SIDEBAR);

            currentWave = scoreBar.getScore(Test_plugin.gameChc == null ? "Волна №0" : "Волна №" + Test_plugin.gameChc.GetCurrentWave());
            emptyScore1 = scoreBar.getScore("");
            scoreGold = scoreBar.getScore("Деньги: " + Money);
            scoreRole = scoreBar.getScore( role == null ? "Роль: None" : "Роль: " + role.GetNameRole());
            scoreLives = scoreBar.getScore("Жизни: " + Health);
            emptyScore2 = scoreBar.getScore("");
            needKills = scoreBar.getScore(Test_plugin.gameChc == null ?"Мобов: 0" : "Мобов: " + Test_plugin.gameChc.GetCountMobsInWavePlayer(player));

            currentWave.setScore(1);
            emptyScore1.setScore(2);
            scoreGold.setScore(3);
            scoreRole.setScore(4);
            scoreLives.setScore(5);
            emptyScore2.setScore(6);
            needKills.setScore(7);

            player.setScoreboard(board);
        }
        catch (Exception ex){
            player.sendMessage("НЕ ПОЛУЧИЛОСЬ АХАХАХАХАХАХАХАХА" + ex.getMessage());
        }
    }
    public GamePlayer(){
       // role = new MagicRole();
    }
    public void UseSpellItem(ItemStack item){
        try {

            for (SkillRef skill : skills) {
                if (skill.GetItemSkills().getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName())) {
                    skill.UseSpell();
                    break;
                }
            }
        }
        catch (Exception ex){
            player.sendMessage(ex.getMessage());
        }
    }
    public void AddMainSkill(SkillRef ref){
        if(ref != null){
            SkillRef newSkill = ref;
            newSkill.INITSKILL(player);
            skills.add(newSkill);
            player.getInventory().addItem(newSkill.GetItemSkills());
        }
    }
    public void setRole(Roles role) {
        this.role = role;
    }

    public Roles getRole() {
        return role;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        this.Money = money;
    }

    public int getHealth() {
        return Health;
    }

    public void setHealth(int health) {
        this.Health = health;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public boolean isChecker() {
        return checker;
    }

    public void setChecker(boolean checker) {
        this.checker = checker;
    }


    public Objective getScoreBar() {
        return scoreBar;
    }

    public void setScoreBar(Objective scoreBar) {
        this.scoreBar = scoreBar;
    }

    public ArrayList<SkillRef> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<SkillRef> skills) {
        this.skills = skills;
    }

    public MagazineInventory getMagazine() {
        return magazine;
    }

    public void setMagazine(MagazineInventory magazine) {
        this.magazine = magazine;
    }

    public boolean isDueling() {
        return Dueling;
    }

    public void setDueling(boolean dueling) {
        Dueling = dueling;
    }


   /* public void RefreshScoreBoard() {
        if(scoreBar != null) {


            try {



                currentWave = scoreBar.getScore("Волна №" + Test_plugin.gameChc.GetCurrentWave());
                emptyScore1 = scoreBar.getScore("");
                scoreGold = scoreBar.getScore("Деньги: " + Money);
                scoreRole = scoreBar.getScore(role == null ? "Роль: None" : "Роль: " + role.GetNameRole());
                scoreLives = scoreBar.getScore("Жизни: " + Health);
                emptyScore2 = scoreBar.getScore("");
                needKills = scoreBar.getScore("Мобов: " + Test_plugin.gameChc.GetCountMobsInWavePlayer(player));

            }
            catch (Exception ex){
                player.sendMessage("ЛОШАРА - " + ex.getMessage());
            }

        }
    }
    private Objective scoreBar;
    private Score currentWave;
    private Score emptyScore1;
    private Score scoreGold;
    private Score scoreRole;
    private Score scoreLives;
    private Scoreboard board;

    private Score emptyScore2;

    private Score needKills;*/


}
