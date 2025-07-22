package com.yourname;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import org.bukkit.plugin.java.JavaPlugin;

public class StatManager {
    private final Map<UUID, Map<String, Integer>> userStats = new HashMap<>();
    private final File statsFile;
    private final Gson gson = new Gson();

    public StatManager(JavaPlugin plugin) {
        this.statsFile = new File(plugin.getDataFolder(), "stats.json");
    }

    public Map<UUID, Map<String, Integer>> getUserStats() {
        return userStats;
    }

    public void incrementStat(UUID uuid, String toolType) {
        userStats.putIfAbsent(uuid, new HashMap<>());
        Map<String, Integer> stats = userStats.get(uuid);
        stats.put(toolType, stats.getOrDefault(toolType, 0) + 1);
    }

    public void saveStats() {
        try (Writer writer = new FileWriter(statsFile)) {
            gson.toJson(userStats, writer);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to save stats: " + e.getMessage());
        }
    }

    public void loadStats() {
        if (!statsFile.exists()) return;

        try (Reader reader = new FileReader(statsFile)) {
            Type type = new TypeToken<Map<String, Map<String, Integer>>>() {}.getType();
            Map<String, Map<String, Integer>> data = gson.fromJson(reader, type);

            for (Map.Entry<String, Map<String, Integer>> entry : data.entrySet()) {
                UUID uuid = UUID.fromString(entry.getKey());
                userStats.put(uuid, new HashMap<>(entry.getValue()));
            }
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to load stats: " + e.getMessage());
        }
    }
}
