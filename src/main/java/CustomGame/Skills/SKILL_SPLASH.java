package CustomGame.Skills;

import CustomGame.GamePlayer;
import CustomGame.NameKeys;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
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

import java.util.Arrays;
import java.util.List;

public class SKILL_SPLASH extends SkillRef{
    private int cooldown = 10;
    private int nowCooldown = 0;
    private Player player;
    private boolean isCooldown = false;
    private String name = "SKILL - УДАР НА 360";
    private ItemStack texture = new ItemStack(Material.WOODEN_SWORD);
    private List<String> description = Arrays.asList("Наносит всем врагам в радиусе 3 блока", "урон вашего предмета в левой руке", "а так же отталкивает их.", "Способость не может добавить врагов");

    public void INITSKILL(Player p_layer){
        player = p_layer;
        SetDataItem();
    }
    public void SetDataItem(){
        ItemMeta meta = texture.getItemMeta();
        meta.setLore(description);
        meta.setDisplayName(name);
        meta.addEnchant(Enchantment.LUCK,2,true);
        meta.getPersistentDataContainer().set(NameKeys.SHOP_ITEM, PersistentDataType.STRING, player.getPlayer().getName());
        meta.getPersistentDataContainer().set(NameKeys.SKILL_ITEM, PersistentDataType.STRING, "SKILL_SPLASH");
        meta.getPersistentDataContainer().set(NameKeys.SHOP_ITEM_PRICE, PersistentDataType.INTEGER, 10);
        texture.setItemMeta(meta);
    }
    public void UseSpell(){

            if(!isCooldown) {
                Inventory inv = player.getInventory();
                ItemStack itemLeftHand = inv.getItem(40) == null ? null : inv.getItem(40);
                double dmg = 5;
                if (itemLeftHand != null) {
                    ItemMeta meta = itemLeftHand.getItemMeta();
                    dmg = meta.getPersistentDataContainer().get(NameKeys.SKILL_DAMAGE, PersistentDataType.DOUBLE);
                }


                List<Entity> enemies = player.getNearbyEntities(3, 3, 3);
                for (Entity ent : enemies) {
                    if (ent instanceof LivingEntity) {
                        LivingEntity entity = (LivingEntity) ent;

                        Vector from = new Vector(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());
                        Vector to = new Vector(entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ());

                        Vector vector = to.subtract(from);


                        entity.setVelocity(vector);
                        entity.setHealth(entity.getHealth() - dmg);


                    }
                }
                player.getWorld().playEffect(player.getLocation(), Effect.LAVA_INTERACT, 0);
                isCooldown = true;
                StartCooldownSkill();
            }
            else player.sendMessage("Кулдаун способности - " + nowCooldown + " сек.");



    }
    public void StartCooldownSkill(){
        nowCooldown = cooldown;
        new BukkitRunnable(){
            @Override
            public void run(){
                if(nowCooldown == 0){
                    isCooldown = false;
                    this.cancel();
                }
                else nowCooldown--;
            }
        }.runTaskTimer(Test_plugin.plugin,0,20);
    }

    public int GetNowCooldown() {
        return nowCooldown;
    }

    public void SetNowCooldown(int nowCooldown) {
        this.nowCooldown = nowCooldown;
    }
    public boolean IsCooldown(){
        return this.isCooldown;
    }
    public ItemStack GetItemSkills(){
        return this.texture;
    }
}
