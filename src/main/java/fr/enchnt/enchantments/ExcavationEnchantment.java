package fr.enchnt.enchantments;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Particle;
import fr.enchnt.Enchnt;

import java.util.ArrayList;
import java.util.List;

public class ExcavationEnchantment extends CustomEnchantment implements Listener {
    
    public ExcavationEnchantment() {
        super("Excavation", 3, EnchantmentTarget.TOOL);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        
        if (tool == null || !tool.containsEnchantment(this)) return;
        if (player.isSneaking()) return;
        
        int level = tool.getEnchantmentLevel(this);
        Block centerBlock = event.getBlock();
        
        String pattern = Enchnt.getInstance().getConfig().getStringList("excavation.patterns").get(level - 1);
        
        List<Block> blocksToBreak = getBlocksInPattern(centerBlock, pattern);
        
        for (Block block : blocksToBreak) {
            if (canBreakBlock(block, tool)) {
                block.breakNaturally(tool);
                
                block.getWorld().spawnParticle(Particle.BLOCK_CRACK, 
                    block.getLocation().add(0.5, 0.5, 0.5),
                    5, 0.2, 0.2, 0.2, 0,
                    block.getType());
            }
        }
    }
    
    private List<Block> getBlocksInPattern(Block center, String pattern) {
        List<Block> blocks = new ArrayList<>();
        
        switch (pattern) {
            case "1x2":
                blocks.add(center);
                blocks.add(center.getRelative(0, 1, 0));
                break;
                
            case "+":
                blocks.add(center);
                blocks.add(center.getRelative(1, 0, 0));
                blocks.add(center.getRelative(-1, 0, 0));
                blocks.add(center.getRelative(0, 0, 1));
                blocks.add(center.getRelative(0, 0, -1));
                break;
                
            case "3x3":
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        blocks.add(center.getRelative(x, 0, z));
                    }
                }
                break;
        }
        
        return blocks;
    }
    
    private boolean canBreakBlock(Block block, ItemStack tool) {
        Material toolType = tool.getType();
        Material blockType = block.getType();
        
        switch (toolType) {
            case DIAMOND_PICKAXE:
            case IRON_PICKAXE:
            case STONE_PICKAXE:
            case WOOD_PICKAXE:
                return blockType.name().contains("ORE") || 
                       blockType.name().contains("STONE") ||
                       blockType == Material.DIAMOND_BLOCK ||
                       blockType == Material.IRON_BLOCK ||
                       blockType == Material.GOLD_BLOCK;
                       
            case DIAMOND_SPADE:
            case IRON_SPADE:
            case STONE_SPADE:
            case WOOD_SPADE:
                return blockType.name().contains("DIRT") ||
                       blockType.name().contains("SAND") ||
                       blockType == Material.GRAVEL ||
                       blockType == Material.CLAY;
                       
            case DIAMOND_AXE:
            case IRON_AXE:
            case STONE_AXE:
            case WOOD_AXE:
                return blockType.name().contains("LOG") ||
                       blockType.name().contains("WOOD") ||
                       blockType == Material.PUMPKIN ||
                       blockType == Material.MELON_BLOCK;
                       
            default:
                return false;
        }
    }
} 