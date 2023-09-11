package CustomGame.Roles;

import CustomGame.Skills.SkillRef;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class BowmanRole extends Roles{
    private String RoleName = "Лучник";
    private ItemStack mainItem = new ItemStack(Material.BOW);
    private double damageUp = 25;
    private SkillRef dopItems = null;

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
    public SkillRef GetMainSkill(Player player){
        return dopItems;
    }
}
