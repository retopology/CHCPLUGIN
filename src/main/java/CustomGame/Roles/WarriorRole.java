package CustomGame.Roles;

import CustomGame.GamePlayer;
import CustomGame.GameRules;
import CustomGame.NameKeys;
import CustomGame.Skills.SKILL_SPLASH;
import CustomGame.Skills.SkillRef;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.spigot.test_plugin.Test_plugin;

import java.util.ArrayList;
import java.util.List;

public class WarriorRole extends Roles {
    private String RoleName = "Воин";
    private ItemStack mainItem = new ItemStack(Material.DIAMOND_SWORD);
    private double damageUp = 30;

    private SkillRef mainskill;

    private double damageFromSkills = 5;

    public WarriorRole(){
        try {
        ItemMeta meta = mainItem.getItemMeta();

            meta.getPersistentDataContainer().set(NameKeys.SKILL_DAMAGE, PersistentDataType.DOUBLE, damageFromSkills);
            meta.getPersistentDataContainer().set(NameKeys.TYPE_DAMAGE, PersistentDataType.STRING, "SWORD");
            meta.setDisplayName("MAIN");
            mainItem.setItemMeta(meta);
        }
        catch (Exception ex){
            Test_plugin.gameChc.GetPlayersArray().get(0).getPlayer().sendMessage(ex.getMessage());
        }

    }


    public ItemStack GetMainItem(){
        return mainItem;
    }
    public void SetMainItem(ItemStack item){
        this.mainItem = item;
    }
    public String GetNameRole(){
        return RoleName;
    }

    public ItemStack GetMainWeapon(){ return mainItem;}

    public SkillRef GetMainSkill(){
        return mainskill = new SKILL_SPLASH();
    }


}
