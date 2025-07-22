package com.yourname.commands;

import com.yourname.StatManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Map;
import java.util.UUID;

public class LeaderboardCommand implements CommandExecutor {
    private final StatManager statManager;

    public LeaderboardCommand(StatManager statManager) {
        this.statManager = statManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§6§lTop Block Miners:");

        statManager.getUserStats().entrySet().stream()
            .sorted((a, b) -> {
                double sumA = a.getValue().values().stream().mapToDouble(Double::doubleValue).sum();
                double sumB = b.getValue().values().stream().mapToDouble(Double::doubleValue).sum();
                return Double.compare(sumB, sumA);
            })
            .limit(5)
            .forEach(entry -> {
                String name = Bukkit.getOfflinePlayer(entry.getKey()).getName();
                double total = entry.getValue().values().stream().mapToDouble(Double::doubleValue).sum();
                sender.sendMessage("§e" + name + ": §a" + String.format("%.2f", total) + " blocks");
            });

        return true;
    }
}
