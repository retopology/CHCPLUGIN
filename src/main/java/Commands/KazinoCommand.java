package Commands;

import Objects.Inventories.Casino.CasinoMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.spigot.test_plugin.Test_plugin;

public class KazinoCommand implements CommandExecutor {
    private Plugin plugin = Test_plugin.getPlugin(Test_plugin.class);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            //Inventory inv = CasinoMain.inventory;
            Inventory inventory = CasinoMain.GetInventory();
            player.openInventory(inventory);
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}

