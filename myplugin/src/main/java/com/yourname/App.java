package com.yourname;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;


import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class App extends JavaPlugin implements Listener {

    private final Map<UUID, Map<Material, Integer>> blockStats = new HashMap<>();
    private final Map<UUID, Map<String, Integer>> userStats = new HashMap<>();

    private final File statsFile = new File(getDataFolder(), "stats.json");
    private final Gson gson = new Gson();

    // does not contain CONCRETE_POWDER since it need <Color>_CONCRETE_POWDER
    private static final Set<Material> SHOVEL_BLOCKS = EnumSet.of(
        Material.DIRT, 
        Material.GRASS_BLOCK, 
        Material.COARSE_DIRT, 
        Material.PODZOL,
        Material.ROOTED_DIRT, 
        Material.SAND, 
        Material.RED_SAND, 
        Material.GRAVEL,
        Material.MYCELIUM, 
        Material.SOUL_SAND, 
        Material.SOUL_SOIL, 
        Material.SNOW_BLOCK,
        Material.SNOW, 
        Material.DIRT_PATH, 
        Material.CLAY, 
        Material.MUD, 
        Material.FARMLAND,
        Material.MUDDY_MANGROVE_ROOTS, 
        Material.SUSPICIOUS_GRAVEL, 
        Material.SUSPICIOUS_SAND
    ); 

private static final Set<Material> PICKAXE_BLOCKS = EnumSet.of(
    // Material.AMETHYST_BUD,
    Material.AMETHYST_CLUSTER,
    Material.ANCIENT_DEBRIS,
    Material.ANDESITE,
    // Material.ANVIL,
    Material.BASALT,
    // Material.BELL,
    Material.BLACKSTONE,
    // Material.BLAST_FURNACE,
    Material.BLUE_ICE,
    // Material.BLOCK_OF_AMETHYST,
    // Material.BLOCK_OF_COAL,
    // Material.BLOCK_OF_COPPER,
    // Material.BLOCK_OF_DIAMOND,
    // Material.BLOCK_OF_EMERALD,
    // Material.BLOCK_OF_GOLD,
    // Material.BLOCK_OF_IRON,
    // Material.BLOCK_OF_LAPIS,
    // Material.BLOCK_OF_NETHERITE,
    // Material.BLOCK_OF_QUARTZ,
    // Material.BLOCK_OF_RAW_COPPER,
    // Material.BLOCK_OF_RAW_GOLD,
    // Material.BLOCK_OF_RAW_IRON,
    Material.BONE_BLOCK,
    // Material.BRICKS,
    // Material.BRICK_STAIRS,
    // Material.BREWING_STAND,
    Material.BUDDING_AMETHYST,
    Material.CALCITE,
    // Material.CAULDRON,
    // Material.CHAIN,
    // Material.CHISELED_DEEPSLATE,
    // Material.CHISELED_POLISHED_BLACKSTONE,
    Material.COBBLED_DEEPSLATE,
    Material.COBBLESTONE,
    // Material.COBBLESTONE_STAIRS,
    // Material.COBBLESTONE_WALL,
    Material.COAL_ORE,
    // Material.COMPOUND_CREATOR,
    // Material.CONCRETE,
    // Material.CONDUIT,
    // Material.COPPER_BLOCK,
    Material.COPPER_ORE,
    // Material.CRACKED_STONE_BRICKS, // if used
    Material.CRYING_OBSIDIAN,
    // Material.CUT_COPPER,
    // Material.CUT_COPPER_SLAB,
    // Material.CUT_COPPER_STAIRS,
    Material.DARK_PRISMARINE,
    Material.DEEPSLATE,
    // Material.DEEPSLATE_BRICKS,
    Material.DEEPSLATE_COAL_ORE,
    Material.DEEPSLATE_COPPER_ORE,
    Material.DEEPSLATE_DIAMOND_ORE,
    Material.DEEPSLATE_EMERALD_ORE,
    Material.DEEPSLATE_GOLD_ORE,
    Material.DEEPSLATE_IRON_ORE,
    Material.DEEPSLATE_LAPIS_ORE,
    Material.DEEPSLATE_REDSTONE_ORE,
    Material.DIAMOND_ORE,
    Material.DIORITE,
    // Material.DISPENSER,
    // Material.DRAGON_EGG,
    Material.DRIPSTONE_BLOCK,
    // Material.DROPPER,
    Material.EMERALD_ORE,
    // Material.ENCHANTING_TABLE,
    Material.END_STONE,
    // Material.ENDER_CHEST,
    // Material.FURNACE,
    Material.GILDED_BLACKSTONE,
    // Material.GLASS, // optional if used
    // Material.GLAZED_TERRACOTTA,
    Material.GOLD_ORE,
    Material.GRANITE,
    // Material.GRINDSTONE,
    // Material.HEAT_BLOCK,
    // Material.HOPPER,
    Material.ICE,
    // Material.IRON_BARS,
    // Material.IRON_DOOR,
    Material.IRON_ORE,
    // Material.IRON_TRAPDOOR,
    // Material.LANTERN,
    Material.LAPIS_ORE,
    // Material.LIGHTNING_ROD,
    Material.LODESTONE,
    Material.MAGMA_BLOCK,
    // Material.MONSTER_SPAWNER,
    Material.MOSSY_COBBLESTONE,
    Material.MUD_BRICKS,
    Material.NETHER_BRICK_FENCE,
    Material.NETHER_BRICK_STAIRS,
    Material.NETHER_BRICKS,
    Material.NETHER_GOLD_ORE,
    Material.NETHER_QUARTZ_ORE,
    Material.NETHERRACK,
    // Material.NYLIUM,
    // Material.OBSERVER,
    Material.OBSIDIAN,
    Material.PACKED_ICE,
    Material.PACKED_MUD,
    Material.POINTED_DRIPSTONE,
    Material.POLISHED_BASALT,
    Material.POLISHED_BLACKSTONE,
    Material.POLISHED_BLACKSTONE_BRICKS,
    Material.POLISHED_DEEPSLATE,
    Material.PRISMARINE,
    Material.PRISMARINE_BRICKS,
    Material.PURPUR_BLOCK,
    Material.PURPUR_PILLAR,
    Material.QUARTZ_STAIRS,
    // Material.RAIL,
    Material.RED_NETHER_BRICKS,
    Material.RED_SANDSTONE,
    Material.RED_SANDSTONE_STAIRS,
    Material.REDSTONE_ORE,
    Material.RESPAWN_ANCHOR,
    Material.SANDSTONE,
    Material.SANDSTONE_STAIRS,
    // Material.SHULKER_BOX,
    // Material.SMOKER,
    Material.SMOOTH_BASALT,
    Material.SMOOTH_STONE,
    Material.STONE,
    Material.STONE_BRICK_STAIRS,
    Material.STONE_BRICKS,
    // Material.STONE_BUTTON,
    // Material.STONE_PRESSURE_PLATE,
    Material.STONECUTTER,
    Material.TERRACOTTA,
    Material.TUFF
);



    @Override
    public void onEnable() {
        // Ensure plugin folder exists
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Load stats from file
        loadStats();

        // Register event listener
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Block leaderboard plugin enabled.");
    }

    @Override
    public void onDisable() {
        saveStats();
        getLogger().info("Block leaderboard plugin disabled.");
    }

    private String getToolTypeForMaterial(Material tool) {
        switch(tool) {
            case WOODEN_PICKAXE:
            case STONE_PICKAXE:
            case IRON_PICKAXE:
            case GOLDEN_PICKAXE:
            case DIAMOND_PICKAXE:
            case NETHERITE_PICKAXE:
                return "PICKAXE";
            case WOODEN_SHOVEL:
            case STONE_SHOVEL:
            case IRON_SHOVEL:
            case GOLDEN_SHOVEL:
            case DIAMOND_SHOVEL:
            case NETHERITE_SHOVEL:
                return "SHOVEL";
            case WOODEN_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLDEN_AXE:
            case DIAMOND_AXE:
            case NETHERITE_AXE:
                return "AXE";
            default:
                return "NOPE";
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Material block = event.getBlock().getType();


        // Get item in main hand
        Material tool = player.getInventory().getItemInMainHand().getType();
        String toolType = getToolTypeForMaterial(tool);

        // Print to player chat
        // player.sendMessage("You are holding     : " + tool);
        // player.sendMessage("tool type           : " + toolType);
        // player.sendMessage("Blast resistamce    : " + block.getBlastResistance());
        // player.sendMessage("Hardness            : " + block.getHardness());

        //blockStats.putIfAbsent(uuid, new HashMap<>());
        userStats.putIfAbsent(uuid, new HashMap<>());

        //Map<Material, Integer> playerBlockStats = blockStats.get(uuid);
        Map<String, Integer> playerStats = userStats.get(uuid);


        // Check if player is using a shovel
        if (toolType == "SHOVEL") {
            // Check if block is in shovel-appropriate list
            if (!SHOVEL_BLOCKS.contains(block)) return;

            //playerBlockStats.put(block, playerBlockStats.getOrDefault(block, 0) + 1);
            playerStats.put("SHOVEL", playerStats.getOrDefault("SHOVEL", 0) + 1);

            return;
        }
        if (toolType == "PICKAXE") {
            // Check if block is in shovel-appropriate list
            if (!PICKAXE_BLOCKS.contains(block)) return;

            // playerBlockStats.put(block, playerBlockStats.getOrDefault(block, 0) + 1);
            playerStats.put("PICKAXE", playerStats.getOrDefault("PICKAXE", 0) + 1);

            return;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("leaderboard")) {
            sender.sendMessage("§6§lTop Block Miners:");

            userStats.entrySet().stream()
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

        if (label.equalsIgnoreCase("mystats")) {
            if (!(sender instanceof Player)) return true;
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            Map<String, Integer> stats = userStats.get(uuid);
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
        return false;
    }

    private void saveStats() {
        try (Writer writer = new FileWriter(statsFile)) {
            gson.toJson(userStats, writer);
        } catch (IOException e) {
            getLogger().severe("Failed to save stats: " + e.getMessage());
        }
    }

    private void loadStats() {
        if (!statsFile.exists()) return;

        try (Reader reader = new FileReader(statsFile)) {
            Type type = new TypeToken<Map<String, Map<String, Integer>>>() {}.getType();
            Map<String, Map<String, Integer>> data = gson.fromJson(reader, type);

            for (Map.Entry<String, Map<String, Integer>> entry : data.entrySet()) {
                UUID uuid = UUID.fromString(entry.getKey());
                Map<String, Integer> stats = new HashMap<>();
                for (Map.Entry<String, Integer> blockEntry : entry.getValue().entrySet()) {
                    stats.put(blockEntry.getKey(), blockEntry.getValue());
                }
                userStats.put(uuid, stats);
            }
        } catch (IOException e) {
            getLogger().severe("Failed to load stats: " + e.getMessage());
        }
    }

}
