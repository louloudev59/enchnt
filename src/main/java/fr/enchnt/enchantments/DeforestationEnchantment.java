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

import java.util.HashSet;
import java.util.Set;

public class DeforestationEnchantment extends CustomEnchantment implements Listener {
    
    private final Set<Material> LOG_TYPES = new HashSet<>();
    private final Set<Material> LEAF_TYPES = new HashSet<>();
    
    public DeforestationEnchantment() {
        super("Déforestation", 1, EnchantmentTarget.TOOL);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
        
        for (Material material : Material.values()) {
            if (material.name().contains("LOG")) {
                LOG_TYPES.add(material);
            } else if (material.name().contains("LEAVES")) {
                LEAF_TYPES.add(material);
            }
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        
        if (tool == null || !tool.containsEnchantment(this)) return;
        if (!isAxe(tool.getType())) return;
        
        Block block = event.getBlock();
        if (!LOG_TYPES.contains(block.getType())) return;
        
        Set<Block> treeBlocks = findTreeBlocks(block);
        
        for (Block treeBlock : treeBlocks) {
            if (canBreakBlock(treeBlock, tool)) {
                treeBlock.breakNaturally(tool);
                
                treeBlock.getWorld().spawnParticle(Particle.BLOCK_CRACK, 
                    treeBlock.getLocation().add(0.5, 0.5, 0.5),
                    5, 0.2, 0.2, 0.2, 0,
                    treeBlock.getType());
            }
        }
    }
    
    private Set<Block> findTreeBlocks(Block startBlock) {
        Set<Block> blocks = new HashSet<>();
        findConnectedBlocks(startBlock, blocks);
        return blocks;
    }
    
    private void findConnectedBlocks(Block block, Set<Block> foundBlocks) {
        if (foundBlocks.contains(block)) return;
        if (!LOG_TYPES.contains(block.getType()) && !LEAF_TYPES.contains(block.getType())) return;
        
        foundBlocks.add(block);
        
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    
                    Block adjacent = block.getRelative(x, y, z);
                    findConnectedBlocks(adjacent, foundBlocks);
                }
            }
        }
    }
    
    private boolean isAxe(Material material) {
        return material.name().contains("_AXE");
    }
    
    private boolean canBreakBlock(Block block, ItemStack tool) {
        Material blockType = block.getType();
        return LOG_TYPES.contains(blockType) || LEAF_TYPES.contains(blockType);
    }
} 