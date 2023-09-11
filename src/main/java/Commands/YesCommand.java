package Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.spigot.test_plugin.HelpModule;
import org.spigot.test_plugin.Test_plugin;

public class YesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(player == Test_plugin.playersGame.get(0)){
                Test_plugin.StartGameChc();
            }

        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
