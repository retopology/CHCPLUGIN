package org.spigot.test_plugin;

import Commands.*;
import Commands.Build.SetCommand;
import CustomGame.GameRules;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.generator.WorldInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.ArrayList;

public final class Test_plugin extends JavaPlugin implements Listener {

    public static GameRules gameChc;
    public static Tasks tasks = new Tasks();
    public static ArrayList<Player> playersGame = new ArrayList<>();

    public static SetCommand setCom = new SetCommand();
    public static Server server;
    public static BossBar bar = Bukkit.getServer().createBossBar("", BarColor.GREEN, BarStyle.SOLID);
    public static Plugin plugin;

    public static void StartGameChc(){
        if(playersGame.size() > 0)gameChc = new GameRules(playersGame, playersGame.get(0).getWorld());
    }
    @Override
    public void onEnable() {

        server = getServer();
        getServer().getLogger().info("Output");
        getServer().getPluginManager().registerEvents(new EventsClass(),this);
        this.getCommand("kazino").setExecutor(new KazinoCommand());
        this.getCommand("set").setExecutor(new SetCommand());
        this.getCommand("startgame").setExecutor(new PlayGameChc());
        this.getCommand("killmobs").setExecutor(new KillMobs());
        this.getCommand("rebild").setExecutor(new ReloadCommands());
        this.getCommand("gamemod").setExecutor(new GameModeCommand());
        this.getCommand("comms").setExecutor(new CommsCommand());
        this.getCommand("endgame").setExecutor(new EndGameCommand());
        this.getCommand("acceptchc").setExecutor(new YesCommand());
        plugin = Test_plugin.getPlugin(Test_plugin.class);


    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
