package com.yourname;

import com.yourname.commands.LeaderboardCommand;
import com.yourname.commands.MyStatsCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {

    private StatManager statManager;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) getDataFolder().mkdirs();

        this.statManager = new StatManager(this);
        statManager.loadStats();

        getServer().getPluginManager().registerEvents(new BlockBreakListener(statManager), this);

        getCommand("leaderboard").setExecutor(new LeaderboardCommand(statManager));
        getCommand("mystats").setExecutor(new MyStatsCommand(statManager));

        getLogger().info("Block leaderboard plugin enabled.");
    }

    @Override
    public void onDisable() {
        statManager.saveStats();
        getLogger().info("Block leaderboard plugin disabled.");
    }
}
