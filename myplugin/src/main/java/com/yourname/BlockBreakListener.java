package com.yourname;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.Material;

public class BlockBreakListener implements Listener {
    private final StatManager statManager;

    public BlockBreakListener(StatManager statManager) {
        this.statManager = statManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material block = event.getBlock().getType();
        Material tool = player.getInventory().getItemInMainHand().getType();

        ToolType toolType = ToolType.fromMaterial(tool);

        switch (toolType) {
            case SHOVEL:
                if (!BlockGroups.SHOVEL_BLOCKS.contains(block)) return;
                break;
            case PICKAXE:
                if (!BlockGroups.PICKAXE_BLOCKS.contains(block)) return;
                break;
            default:
                return;
        }

        statManager.incrementStat(player.getUniqueId(), toolType.name());
    }
}
