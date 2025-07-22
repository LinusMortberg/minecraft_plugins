package com.yourname.commands;

import com.yourname.StatManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class MyStatsCommand implements CommandExecutor {
    private final StatManager statManager;

    public MyStatsCommand(StatManager statManager) {
        this.statManager = statManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        Map<String, Integer> stats = statManager.getUserStats().get(player.getUniqueId());

        if (stats == null || stats.isEmpty()) {
            player.sendMessage("§cYou haven’t mined any blocks yet.");
            return true;
        }

        player.sendMessage("§6§lYour Mined Blocks:");
        stats.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(entry -> player.sendMessage("§e" + entry.getKey() + ": §a" + entry.getValue()));

        return true;
    }
}
