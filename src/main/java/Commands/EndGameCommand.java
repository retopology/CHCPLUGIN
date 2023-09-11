package Commands;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.spigot.test_plugin.Test_plugin;

public class EndGameCommand implements CommandExecutor {
    private Plugin plugin = Test_plugin.getPlugin(Test_plugin.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            if(Test_plugin.gameChc != null) Test_plugin.gameChc.EndGame();

        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
