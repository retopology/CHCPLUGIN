package Objects.Inventories.Casino;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.spigot.test_plugin.Test_plugin;

public class CasinoResult {
    private static Inventory inventory;
    private static String NameInventory = ChatColor.RED + "ПОЗДРАВЛЯЕМ";
    private static Plugin plugin = Test_plugin.getPlugin(Test_plugin.class);


    public static Inventory GetInventory(Player player, ItemStack winItem){
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
    public static String GetInventoryName(){
        return NameInventory;
    }
}
