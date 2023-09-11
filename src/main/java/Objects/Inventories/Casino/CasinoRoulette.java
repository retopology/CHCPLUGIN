package Objects.Inventories.Casino;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.spigot.test_plugin.Test_plugin;

import java.util.ArrayList;
import java.util.Random;

public class CasinoRoulette {
    private static Inventory inventory;
    private static String NameInventory = ChatColor.RED + "РУЛЕТКА";
    private static Plugin plugin = Test_plugin.getPlugin(Test_plugin.class);


    public static Inventory GetInventory(Player player){
        inventory = plugin.getServer().createInventory(null,27,NameInventory);
        ItemStack diamond = new ItemStack(Material.DIAMOND,1);
        ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE,1);
        ItemStack gold = new ItemStack(Material.GOLD_INGOT,1);
        ItemStack iron = new ItemStack(Material.IRON_INGOT,1);
        ItemStack coal = new ItemStack(Material.COAL,1);
        ItemStack dirt = new ItemStack(Material.DIRT,1);

        ItemStack winningPlace = new ItemStack(Material.LIME_STAINED_GLASS_PANE);


        for(int i = 0; i < 27; i++){
            if(i < 9 || i > 17) inventory.setItem(i, empty);
        }
        inventory.setItem(0, winningPlace);
        inventory.setItem(18, winningPlace);
        return inventory;


    }
    // 58 - 62
    public static String GetInventoryName(){
        return NameInventory;
    }
    private static ArrayList<ItemStack> itemsRoulette = new ArrayList<ItemStack>();
    private static int winIndex = 0;
    private static ItemStack WinningItem;
    private static void FillRulesRoulette(){

        // Выигрышный индекс
        Random rnd = new Random();
        winIndex = rnd.nextInt(71);

        // Определение приза

        if(winIndex == 70) WinningItem = new ItemStack(Material.DIAMOND,1);
        else{
            if(winIndex <= 30) WinningItem = new ItemStack(Material.DIRT,1);
            else{
                if(winIndex > 30 && winIndex <= 50) WinningItem = new ItemStack(Material.COAL,1);
                else{
                    if(winIndex > 50 && winIndex <= 60) WinningItem = new ItemStack(Material.IRON_INGOT,1);
                    else{
                        if(winIndex > 60 && winIndex <= 69) WinningItem = new ItemStack(Material.GOLD_INGOT,1);
                    }
                }
            }
        }
        winIndex = 62;
        // Заполнение списка
        for(int i =0; i < 71; i++){

            if(i == winIndex) itemsRoulette.add(WinningItem);
            else{
                Random rand = new Random();
                int randNum = rand.nextInt(5);
                switch (randNum){
                    case 0:itemsRoulette.add(new ItemStack(Material.DIRT,1)); break;
                    case 1: itemsRoulette.add(new ItemStack(Material.COAL,1)); break;
                    case 2: itemsRoulette.add(new ItemStack(Material.IRON_INGOT,1)); break;
                    case 3: itemsRoulette.add(new ItemStack(Material.GOLD_INGOT,1)); break;
                    case 4: itemsRoulette.add(new ItemStack(Material.DIAMOND,1)); break;
                }

            }

        }

    }
    static int loger = 0;
    static int boundsItems = 0;
    public static void StartRoulette(final Player player){
        try {

            itemsRoulette.clear();
            FillRulesRoulette();

            Long leg = 100L;
            Long speed = 2L;
            Long slower = 2L;
            Long iterationSlow = 0L;


            for (int i = 0; i < winIndex + 1; i++) {
                if (i > leg / 2) {
                    speed += slower;
                    slower = iterationSlow * 2;
                    iterationSlow++;
                } else speed += 1;


                new BukkitRunnable() {
                    @Override
                    public void run() {
                        int ramka = 9;
                        for(int i = boundsItems; i < boundsItems + 9;i++){
                            inventory.setItem(ramka, itemsRoulette.get(i));
                            ramka++;
                        }
                        boundsItems++;

                        player.sendMessage(ChatColor.WHITE + "ВРЕМЯ - " + loger);
                        if(loger == winIndex) {
                            EndRoullette(player);
                        }
                        else loger++;
                        cancel();


                    }

                }.runTaskTimer(plugin, speed, 20L);



            }

        }
        catch (Exception ex){
            player.sendMessage(ChatColor.RED + ex.getMessage());
        }
    }

    public static void EndRoullette(Player player){
        //player.openInventory()
        player.getInventory().addItem(WinningItem);
        boundsItems = 0;
        loger = 0;
        winIndex = 0;

        Firework fw = (Firework) player.getWorld().spawn(player.getEyeLocation(), Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();
        //use meta to customize the firework or add parameters to the method
        fw.setVelocity(player.getLocation().getDirection().multiply(200));
    }

    //
}
