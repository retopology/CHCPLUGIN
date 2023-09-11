package CustomGame.InventoriesGame;

import CustomGame.NameKeys;
import CustomGame.Skills.SKILL_SPLASH;
import CustomGame.Skills.SkillRef;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.spigot.test_plugin.Test_plugin;

import java.util.ArrayList;
import java.util.Random;

public class MagazineInventory {
    private Inventory inventory;
    private Player player;
    private static String NameInventory = ChatColor.RED + "МАГАЗИН";
    private Plugin plugin = Test_plugin.getPlugin(Test_plugin.class);
    ArrayList<ItemStack> itemsShop = new ArrayList<>();

    public void GenerateNewShop(){
        try {


            itemsShop.clear();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    int num = new Random().nextInt(0, Material.values().length);
                    int count = 1;
                    //if(Material.values()[num] instanceof Enum.EnumDesc.){

                    // }
                    ItemStack randomItem = new ItemStack(Material.values()[num], 1);
                    ItemMeta meta = randomItem.getItemMeta();
                    meta.getPersistentDataContainer().set(NameKeys.SHOP_ITEM, PersistentDataType.STRING, player.getName());
                    meta.getPersistentDataContainer().set(NameKeys.SHOP_ITEM_PRICE, PersistentDataType.INTEGER, 50);
                    randomItem.setItemMeta(meta);
                    //
                    itemsShop.add(randomItem);
                }
            }

        }
        catch (Exception ex){
            player.sendMessage("Ошибка в GnerateNewShop - " + ex.getMessage());
        }
    }
    public Inventory GetInventory(Player playe){
        if(playe != null) player = playe;
        int coint = 9 * 6;
        inventory = plugin.getServer().createInventory(null,coint,NameInventory);
        GenerateNewShop();


        return inventory;
    }

    public void OpenMagazine(){
        // 0  1  2  3  4  5  6  7  8
        // 9 10 11 12 13 14 15 16 17
        //18 19 20 21 22 23 24 25 26
        //27 28 29 30 31 32 33 34 35
        //36 37 38 39 40 41 42 43 44

        player.openInventory(inventory);
        int i = 0;
        int indexItem = 0;
        int j = 0;
        int duoJ = 0;
        while(i < 5){
            if(duoJ == 5){
                j += 4;
                duoJ = 0;
                i++;
            }
            inventory.setItem(j, itemsShop.get(indexItem));
            duoJ++;
            j++;
            indexItem++;

        }
        //SkillRef itm = new SKILL_SPLASH();
        //inventory.setItem(7,itm.GetItemSkills());



    }
    public void RemoveItemFromMagazine(ItemStack item){
        itemsShop.remove(item);
    }
    public static String GetInventoryName(){
        return NameInventory;
    }

}
