package CustomGame.InventoriesGame;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.spigot.test_plugin.Test_plugin;

public class RoleInventory {
    private static Inventory inventory;
    private static String NameInventory = ChatColor.RED + "ВЫБЕРИ РОЛЬ";
    private static String selectWarrior = ChatColor.WHITE + "МЕЧНИК";
    private static String selectBower = ChatColor.WHITE + "ЛУЧНИК";
    private static Plugin plugin = Test_plugin.getPlugin(Test_plugin.class);

    public static Inventory GetInventory(){
        inventory = plugin.getServer().createInventory(null,9,NameInventory);

        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD,1);
        ItemMeta swordMeta = diamondSword.getItemMeta();
        swordMeta.setDisplayName(selectWarrior);
        diamondSword.setItemMeta(swordMeta);

        ItemStack bow = new ItemStack(Material.BOW,1);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.setDisplayName(selectBower);
        bow.setItemMeta(bowMeta);
        // 0 1 2 3 4 5 6 7 8
        inventory.setItem(3, diamondSword);
        inventory.setItem(5, bow);

        return inventory;
    }
    public static String GetInventoryName(){
        return NameInventory;
    }

    public static String GetWarriorName(){
        return selectWarrior;
    }

    public static String GetBowerName(){
        return selectBower;
    }
}
