package org.spigot.test_plugin;

import CustomGame.GamePlayer;
import CustomGame.InventoriesGame.RoleInventory;
import CustomGame.NameKeys;
import CustomGame.Roles.BowmanRole;
import CustomGame.Roles.WarriorRole;
import CustomGame.Skills.SkillRef;
import Objects.Inventories.Casino.CasinoMain;
import Objects.Inventories.Casino.CasinoRoulette;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class EventsClass implements Listener {

    @EventHandler
    public void onEntityDrop(EntityDropItemEvent event){
            event.setCancelled(true);

    }

    @EventHandler
    public void onEntityTarget(EntityTargetLivingEntityEvent event){
        if (event.getTarget().getCustomName() == "МАГАЗИН"){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void PlayerRightClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if(Test_plugin.gameChc != null && event.getRightClicked().getCustomName().equals("МАГАЗИН")){
            ArrayList<GamePlayer> players = Test_plugin.gameChc.GetPlayersArray();
            for (GamePlayer play: players) {
                if(play.getPlayer() == player){
                    play.getMagazine().OpenMagazine();
                    break;
                }
            }
        }

    }


    @EventHandler
    public void onItemDrop (PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if(p != null) e.setCancelled(true);
    }
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event){

        Player p = event.getPlayer();
        // Builds
        if(p.getItemInHand().getType() == Material.IRON_SWORD){
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                Test_plugin.setCom.SetLocalOne(event.getClickedBlock().getLocation());
                p.sendMessage(ChatColor.AQUA + "RIGHT CLICK - " + event.getClickedBlock().getX() + " " +
                        event.getClickedBlock().getY() + " " + event.getClickedBlock().getZ());
            }
            else {
                if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    Test_plugin.setCom.SetLocalSecond(event.getClickedBlock().getLocation());
                    p.sendMessage(ChatColor.GREEN + "LEFT CLICK - " + event.getClickedBlock().getX() + " " +
                            event.getClickedBlock().getY() + " " + event.getClickedBlock().getZ());
                }
            }

        }


        if(p.getItemInHand().getItemMeta().getDisplayName().contains("SKILL - ")
        && event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            ArrayList<GamePlayer> players = Test_plugin.gameChc.GetPlayersArray();
            for (GamePlayer play: players) {
                if(play.getPlayer() == p){
                    play.UseSpellItem(p.getItemInHand());

                    break;
                }
            }
        }

    }
    @EventHandler
    public void TakeDamage(EntityDamageByEntityEvent event){
            try {


                if (event.getEntity().getCustomName() != null && event.getEntity().getCustomName().equals("МАГАЗИН")) event.setCancelled(true);

                    if (event.getEntity() instanceof Player) {
                        if (Test_plugin.gameChc.getDuelList().size() > 1) {
                            if (event.getDamager() instanceof Player) {
                                if (Test_plugin.gameChc.getDuelList().contains(event.getDamager()) &&
                                        Test_plugin.gameChc.getDuelList().contains(event.getEntity())) {
                                    event.setCancelled(false);
                                } else event.setCancelled(true);
                            }
                        }


                        Player player = (Player) event.getEntity();
                        if (player.getHealth() - event.getDamage() <= 0) {
                            event.setCancelled(true);
                            player.setHealth(20);
                            ArrayList<GamePlayer> players = Test_plugin.gameChc.GetPlayersArray();
                            for (GamePlayer play : players) {
                                if (play.getPlayer() == player) {
                                    play.getPlayer().sendMessage("Я ТУТ");
                                    if (event.getDamager() instanceof Player) {
                                        event.setCancelled(true);
                                        Test_plugin.gameChc.EndDuel((Player) event.getDamager());
                                        break;
                                    } else {

                                        if (play.getHealth() == 0) {
                                            ArrayList<GamePlayer> oldPLayers = Test_plugin.gameChc.GetPlayersArray();
                                            if (oldPLayers.size() == 1) {

                                                play.getPlayer().sendMessage("Одиночная игра. Конец. Волн - " + Test_plugin.gameChc.GetCurrentWave());
                                                Test_plugin.gameChc.TeleportToSpawn();
                                                Test_plugin.gameChc.EndGame();
                                                event.setCancelled(true);
                                            } else {
                                                oldPLayers.remove(play);
                                                if (oldPLayers.size() == 1) {
                                                    Bukkit.broadcastMessage("ПОБЕДИТЕЛЬ - " + oldPLayers.get(0).getPlayer().getName());

                                                    Test_plugin.gameChc.TeleportToSpawn();
                                                    Test_plugin.gameChc.EndGame();
                                                    event.setCancelled(true);
                                                }
                                                Test_plugin.gameChc.SetPlayersArray(oldPLayers);
                                            }
                                            break;
                                        } else {
                                            event.setCancelled(true);
                                            player.getWorld().playEffect(player.getLocation(), Effect.BLAZE_SHOOT, 0);
                                            play.setHealth(play.getHealth() - 1);
                                            Test_plugin.gameChc.RefreshScore(play);

                                        }

                                    }
                                }
                                break;
                            }
                        }
                    }

            }
            catch (Exception ex){
                Bukkit.broadcastMessage(ex.getMessage());
            }
    }



    @EventHandler
    public void KillMob(EntityDeathEvent event){

        GamePlayer playerTp = HelpModule.CalculateKills(event.getEntity().getKiller(), event.getEntity());
        if (playerTp != null) {

            playerTp.setChecker(true);
            Test_plugin.gameChc.TeleportToSpawn(playerTp);

            boolean allReady = Test_plugin.gameChc.isAllReady();
            if (!allReady) playerTp.getPlayer().setGameMode(GameMode.SPECTATOR);
            else Test_plugin.gameChc.NextWave();
        }

    }

    public void SendKommandsMsg(Player player){
        player.sendMessage(ChatColor.AQUA + "Список команд, который позволит управлять игрой:");
        player.sendMessage(ChatColor.YELLOW + "/startgame" + ChatColor.WHITE + " - начать игру");
        player.sendMessage(ChatColor.YELLOW + "/gamemod" + ChatColor.WHITE + " - меняет гейммод, с креатива на выжиывание и наоборот");
        player.sendMessage(ChatColor.YELLOW + "/rebild" + ChatColor.WHITE + " - перезагрузить сервер");
        player.sendMessage(ChatColor.YELLOW + "/comms" + ChatColor.WHITE + " - команды");
        player.sendMessage(ChatColor.YELLOW + "/killmobs" + ChatColor.WHITE + " - убивает всех врагов в радиусе");
        player.sendMessage(ChatColor.YELLOW + "/endgame" + ChatColor.WHITE + " - завершить игру");


        player.sendMessage(ChatColor.AQUA + "Рекомендую проверять не в одиночку, например запустив второй лаунчер, и зайти на сервер с 2 аккаунтов");
    }
    @EventHandler
    public void handleJoinEvent(PlayerJoinEvent event) throws ClassNotFoundException {


        // Данные о игроке
        Player player = event.getPlayer();
        if(!player.getInventory().contains(Material.IRON_SWORD))
            player.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
        player.sendMessage(ChatColor.AQUA + "Привет, " + ChatColor.YELLOW + player.getName() + ChatColor.AQUA +", время протестировать работу плагина.");
        player.setGameMode(GameMode.CREATIVE);
        SendKommandsMsg(player);


        //ItemStack skull = new ItemStack(Material.LEGACY_SKULL, 1, (short) SkullType.PLAYER.ordinal());
        //SkullMeta meta = (SkullMeta) skull.getItemMeta();
        //meta.setOwningPlayer(player);
        //skull.setItemMeta(meta);




        /*try {
            List<Class<?>> classes = ReflactionMain.find("CustomGame.Skills");
            for (Class clas: classes) {
                player.sendMessage(clas.getName());
            }

        }
        catch (Exception ex){
            player.sendMessage(ex.getMessage());
        }*/


    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent event){
        //tasks.SpawnSheepColor(event);
        Test_plugin.tasks.SpawnZombieWithArmor(event);


    }


    @EventHandler
    public void InventClick(InventoryClickEvent event){


            if (Test_plugin.gameChc != null) {
                ItemStack item = event.getCurrentItem();
                Player player = (Player) event.getWhoClicked();
                ItemMeta meta = item.getItemMeta();
                event.setCancelled(HelpModule.isClickInventory(event.getView().getTitle()));

                // Выбор лучника
                if (item.getItemMeta().getDisplayName().equals(RoleInventory.GetBowerName())) {

                    ArrayList<GamePlayer> gamers = Test_plugin.gameChc.GetPlayersArray();
                    for (GamePlayer playerGame : gamers) {
                        if (playerGame.getPlayer() == player) {
                            playerGame.setChecker(true);
                            playerGame.setRole(new BowmanRole());

                            Test_plugin.gameChc.RefreshScore(playerGame);
                            playerGame.getPlayer().sendMessage("Вы выбрали роль - " + RoleInventory.GetBowerName());


                            break;

                        }
                    }
                    Test_plugin.gameChc.SetPlayersArray(gamers);
                    player.closeInventory();
                }

                // Выбор мечника
                if (item.getItemMeta().getDisplayName().equals(RoleInventory.GetWarriorName())) {
                    ArrayList<GamePlayer> gamers = Test_plugin.gameChc.GetPlayersArray();
                    for (GamePlayer playerGame : gamers) {
                        if (playerGame.getPlayer() == player) {
                            playerGame.setChecker(true);
                            playerGame.setRole(new WarriorRole());

                            Test_plugin.gameChc.RefreshScore(playerGame);
                            playerGame.getPlayer().sendMessage("Вы выбрали роль - " + RoleInventory.GetWarriorName());
                            break;

                        }
                    }
                    Test_plugin.gameChc.SetPlayersArray(gamers);
                    player.closeInventory();
                }


                if (event.getView().getTitle().equals(CasinoMain.GetInventoryName())) {

                    if (item.getItemMeta().getDisplayName().equals(CasinoMain.GetStartMeta())) {
                        player.openInventory(CasinoRoulette.GetInventory(player));

                        CasinoRoulette.StartRoulette(player);
                    }
                }
                try {


                    String shopItem = meta.getPersistentDataContainer().get(NameKeys.SHOP_ITEM, PersistentDataType.STRING);
                    if (shopItem != "") {
                        if (shopItem.equals(player.getName())) {
                            ArrayList<GamePlayer> players = Test_plugin.gameChc.GetPlayersArray();
                            for (GamePlayer play : players) {
                                if (play.getPlayer() == player) {
                                    int price = meta.getPersistentDataContainer().get(NameKeys.SHOP_ITEM_PRICE, PersistentDataType.INTEGER);
                                    if (play.getMoney() >= price) {
                                        player.getInventory().addItem(item);
                                        play.setMoney(play.getMoney() - price);
                                        play.RegisterScoreBar();
                                        play.getMagazine().RemoveItemFromMagazine(item);
                                        play.getMagazine().OpenMagazine();


                                    } else player.sendMessage(ChatColor.RED + "Недостаточно средств");
                                    break;
                                }
                            }
                        }
                    }


                }
                catch (Exception ex){

                }
            }




    }
}
