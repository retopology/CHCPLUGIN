package Mobs.ReferenseMobs;

import CustomGame.GamePlayer;
import Mobs.MobRef;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;

public class ZombieRef extends MobRef {
    String main = "Zombie";
    private double health = 20;
    private double damage = 2;
    private double upgrade = 2;
    private String unicode = "";
    private GamePlayer player;

    public String GetEntity(){

        return main;
    }
    public double GetUpgradePropetry(){
        return upgrade;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public void setPlayer(GamePlayer player) {
        this.player = player;
    }
}
