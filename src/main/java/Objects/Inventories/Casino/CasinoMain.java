package Objects.Inventories.Casino;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.spigot.test_plugin.Test_plugin;

public class CasinoMain {
    private static Inventory inventory;
    private static String NameInventory = ChatColor.RED + "AZINO77";
    private static String StartName = ChatColor.WHITE + "СТАРТ";
    private static Plugin plugin = Test_plugin.getPlugin(Test_plugin.class);

    public static Inventory GetInventory(){
        inventory = plugin.getServer().createInventory(null,18,NameInventory);
        ItemStack diamond = new ItemStack(Material.DIAMOND,1);
        ItemStack gold = new ItemStack(Material.GOLD_INGOT,1);
        ItemStack iron = new ItemStack(Material.IRON_INGOT,1);
        ItemStack coal = new ItemStack(Material.COAL,1);
        ItemStack dirt = new ItemStack(Material.DIRT,1);

        ItemStack lime = new ItemStack(Material.LIME_WOOL,1);
        ItemMeta metaLime = lime.getItemMeta();
        metaLime.setDisplayName(StartName);
        lime.setItemMeta(metaLime);

        inventory.setItem(0, lime);
        inventory.setItem(11, dirt);
        inventory.setItem(12, coal);
        inventory.setItem(13, iron);
        inventory.setItem(14, gold);
        inventory.setItem(15, diamond);

        return inventory;
    }
    public static String GetInventoryName(){
        return NameInventory;
    }
    public static String GetStartMeta(){
        return StartName;
    }
}
