package Commands.Build;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigot.test_plugin.Test_plugin;

import java.util.ArrayList;

public class SetCommand  implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            boolean checer = CheckCoordinates();
            if(checer)
                SetWorld(player);
            else player.sendMessage(ChatColor.RED + "Вы не назначили область");
        }
        return true;

    }
    //private static JavaPlugin plugin = Test_plugin.getPlugin(Test_plugin.class);
    static ArrayList<Location> locationsSet = new ArrayList<Location>(); // Координаты нижнего слоя
    static Location locationSetOne; // Координаты первой точки
    static Location locationSetTwo; // Координаты второй точки
    static double MinY;
    static double MaxY;
    public boolean CheckCoordinates(){
        if(locationSetOne != null && locationSetTwo != null){
            return true;
        }
        else return false;
    }
    public void SetLocalOne(Location location){
        locationSetOne = location;
    }
    public void SetLocalSecond(Location location){
        locationSetTwo = location;
    }
    void SetWorld(Player player){
        try {



            player.sendMessage(ChatColor.YELLOW + "Начал работу ");
            Location LocMax = locationSetOne.getX() > locationSetTwo.getX() ? locationSetOne : locationSetTwo;
            Location LocMin = locationSetOne.getX() > locationSetTwo.getX() ? locationSetTwo : locationSetOne;

            MinY = Math.min(LocMax.getY(), LocMin.getY());
            MaxY = Math.max(LocMax.getY(), LocMin.getY());


            Location firstAngle = new Location(player.getWorld(), LocMin.getX(), MinY, LocMax.getZ());
            for (double i = firstAngle.getX(); i <= LocMax.getX(); i++) {
                for (double j = firstAngle.getZ(); j <= LocMin.getZ(); j++) {
                    locationsSet.add(new Location(player.getWorld(), i, MinY, j));
                }
            }
            boolean result = ReplaceBlockAsync(player);
            if(result)player.sendMessage(ChatColor.GREEN + "Успешно!");
            else player.sendMessage(ChatColor.RED + "Не получилось!");
            locationSetTwo = null;
            locationSetOne = null;
            locationsSet.clear();


        }
        catch (Exception ex){
            locationSetTwo = null;
            locationSetOne = null;
            locationsSet.clear();
            player.sendMessage(ChatColor.RED + ex.getMessage().toString());
            locationsSet.clear();
        }
    }
    boolean ReplaceBlockAsync(Player player){
        /*try {
            Plugin plugin = Test_plugin.getPlugin(Test_plugin.class);
            new BukkitRunnable() {
                public void run() {
                    while (MinY != MaxY + 1) {
                        for (Location loc : locationsSet) {
                            loc.setY(MinY);
                            loc.getBlock().setType(Material.GLASS);
                        }
                        MinY++;
                    }
                    locationsSet.clear();

                }
            }.runTaskTimer(plugin, 20L, 20L);
            return true;
        }
        catch (Exception ex){
            player.sendMessage(ChatColor.RED + ex.getMessage().toString());
            return false;
        }*/
        return false;

    }
}
