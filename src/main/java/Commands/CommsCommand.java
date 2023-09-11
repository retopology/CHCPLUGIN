package Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.spigot.test_plugin.Test_plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommsCommand implements CommandExecutor {
    private Plugin plugin = Test_plugin.getPlugin(Test_plugin.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(ChatColor.AQUA + "Список команд, который позволит управлять игрой:");
            player.sendMessage(ChatColor.YELLOW + "/startgame" + ChatColor.WHITE + " - начать игру");
            player.sendMessage(ChatColor.YELLOW + "/gamemod" + ChatColor.WHITE + " - меняет гейммод, с креатива на выжиывание и наоборот");
            player.sendMessage(ChatColor.YELLOW + "/rebild" + ChatColor.WHITE + " - перезагрузить сервер");
            player.sendMessage(ChatColor.YELLOW + "/comms" + ChatColor.WHITE + " - команды");
            player.sendMessage(ChatColor.YELLOW + "/killmobs" + ChatColor.WHITE + " - убивает всех врагов в радиусе");
            player.sendMessage(ChatColor.YELLOW + "/endgame" + ChatColor.WHITE + " - завершить игру");


            player.sendMessage(ChatColor.AQUA + "Рекомендую проверять не в одиночку, например запустив второй лаунчер, и зайти на сервер с 2 аккаунтов");

        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
