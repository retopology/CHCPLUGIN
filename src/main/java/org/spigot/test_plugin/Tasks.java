package org.spigot.test_plugin;

import org.bukkit.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Zombie;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.util.Vector;

public class Tasks {
    public void SpawnSheepColor(BlockBreakEvent event){
        // Информация о блоке
        BlockData block = event.getBlock().getBlockData();

        Player player = event.getPlayer();



        if(block.getMaterial().name().equals(Material.GRASS_BLOCK.name())|
                block.getMaterial().name().equals(Material.DIRT.name())){
            if(block.getMaterial().name().equals(Material.GRASS_BLOCK.name())){
                // Спавн овцы
                Sheep sheepSpawn = player.getWorld().spawn(event.getBlock().getLocation().add(0.5,0,0.5),Sheep.class);
                sheepSpawn.setColor(DyeColor.PURPLE);
                player.sendMessage(ChatColor.DARK_PURPLE + player.getName() + " сломал блок травы, и за это получает элитную, фиолетовую овцу'");
            }
            else{
                Sheep sheepSpawn = player.getWorld().spawn(event.getBlock().getLocation().add(0.5,0,0.5),Sheep.class);
                sheepSpawn.setColor(DyeColor.WHITE);
                player.sendMessage(ChatColor.BLUE + player.getName() + " сломал блок грязи, и за это получает обычную, белую овцу");
            }


        }

    }

    public void SpawnZombieWithArmor(BlockBreakEvent event){
        // Информация о блоке
        BlockData block = event.getBlock().getBlockData();

        Player player = event.getPlayer();



        if(block.getMaterial().name().equals(Material.GRASS_BLOCK.name())|
                block.getMaterial().name().equals(Material.DIRT.name())){
            if(block.getMaterial().name().equals(Material.GRASS_BLOCK.name())){
                // Спавн зомби
                Zombie zombie = player.getWorld().spawn(event.getBlock().getLocation().add(0.5,0,0.5),Zombie.class);
                zombie.getEquipment().clear();

                zombie.setCustomName(ChatColor.YELLOW + "Хайпбист");
                zombie.setCustomNameVisible(true);



                // Создание брони

                ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
                LeatherArmorMeta metaHelmet = (LeatherArmorMeta)helmet.getItemMeta();
                metaHelmet.setColor(Color.YELLOW);
                helmet.setItemMeta(metaHelmet);

                ItemStack chesplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
                chesplate.setItemMeta(metaHelmet);

                ItemStack leggins = new ItemStack(Material.LEATHER_LEGGINGS, 1);
                leggins.setItemMeta(metaHelmet);

                ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
                boots.setItemMeta(metaHelmet);



                // Выдача брони
                zombie.getEquipment().setHelmet(helmet);
                zombie.getEquipment().setChestplate(chesplate);
                zombie.getEquipment().setLeggings(leggins);
                zombie.getEquipment().setBoots(boots);

                Bukkit.getScheduler();

                // Пассивный зомби
                zombie.setAI(false);
                player.sendMessage(ChatColor.DARK_PURPLE + player.getName() + " сломал блок, и за это получает зомби в желтой броне броне'");
            }



        }
    }

    public void GoToSpawn(Player pLayer){
        Vector vector = new Vector(1,1,1);
        Location playerLoc = pLayer.getLocation();
        Location spawnPoint = pLayer.getWorld().getSpawnLocation();


    }
}
