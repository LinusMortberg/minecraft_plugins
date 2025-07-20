package com.yourname;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class App extends JavaPlugin implements Listener {

    private final Map<UUID, Integer> blockCounts = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Block leaderboard plugin enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Block leaderboard plugin disabled.");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        blockCounts.put(uuid, blockCounts.getOrDefault(uuid, 0) + 1);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("leaderboard")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            sender.sendMessage("§6§lTop Block Miners:");
            blockCounts.entrySet().stream()
                .sorted((a, b) -> b.getValue() - a.getValue())
                .limit(5)
                .forEach(entry -> {
                    String name = Bukkit.getOfflinePlayer(entry.getKey()).getName();
                    sender.sendMessage("§e" + name + ": §a" + entry.getValue() + " blocks");
                });
            return true;
        }
        return false;
    }
}

