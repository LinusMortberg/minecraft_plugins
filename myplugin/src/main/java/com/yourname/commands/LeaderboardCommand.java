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
                int sumA = a.getValue().values().stream().mapToInt(Integer::intValue).sum();
                int sumB = b.getValue().values().stream().mapToInt(Integer::intValue).sum();
                return Integer.compare(sumB, sumA);
            })
            .limit(5)
            .forEach(entry -> {
                String name = Bukkit.getOfflinePlayer(entry.getKey()).getName();
                int total = entry.getValue().values().stream().mapToInt(Integer::intValue).sum();
                sender.sendMessage("§e" + name + ": §a" + total + " blocks");
            });

        return true;
    }
}
