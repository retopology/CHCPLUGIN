package Commands;

import CustomGame.GameRules;
import Objects.Inventories.Casino.CasinoMain;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.spigot.test_plugin.Test_plugin;

import java.util.ArrayList;

public class PlayGameChc implements CommandExecutor {
    private Plugin plugin = Test_plugin.getPlugin(Test_plugin.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(Test_plugin.playersGame.size() == 0)player.sendMessage("Если хотите начать игру, можете написать /acceptchc, или же подождать пока зайдут другие игроки");

            Test_plugin.playersGame.add(player);
            Bukkit.broadcastMessage("Игроков в арену - " + Test_plugin.playersGame.size());
        }

        return true;
    }
}
