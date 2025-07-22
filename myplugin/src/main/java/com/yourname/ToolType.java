package com.yourname;

public enum ToolType {
    PICKAXE, SHOVEL, AXE, NOPE;

    public static ToolType fromMaterial(org.bukkit.Material material) {
        switch (material) {
            case WOODEN_PICKAXE: 
            case STONE_PICKAXE: 
            case IRON_PICKAXE:
            case GOLDEN_PICKAXE: 
            case DIAMOND_PICKAXE: 
            case NETHERITE_PICKAXE:
                return PICKAXE;
            case WOODEN_SHOVEL: 
            case STONE_SHOVEL: 
            case IRON_SHOVEL:
            case GOLDEN_SHOVEL: 
            case DIAMOND_SHOVEL: 
            case NETHERITE_SHOVEL:
                return SHOVEL;
            case WOODEN_AXE: 
            case STONE_AXE: 
            case IRON_AXE:
            case GOLDEN_AXE: 
            case DIAMOND_AXE: 
            case NETHERITE_AXE:
                return AXE;
            default:
                return NOPE;
        }
    }
}
