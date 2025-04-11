package fr.enchnt.enchantments;

import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class AffinityEnchantment extends CustomEnchantment implements Listener {
    public AffinityEnchantment() {
        super("affinity", 3, EnchantmentTarget.TOOL);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return item.getType().name().endsWith("_PICKAXE") ||
               item.getType().name().endsWith("_AXE") ||
               item.getType().name().endsWith("_SHOVEL");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        
        if (tool == null || !tool.containsEnchantment(this)) return;

        Material blockType = event.getBlock().getType();
        int level = tool.getEnchantmentLevel(this);
        
        if (isMatchingOre(blockType, tool.getType())) {
            int repairAmount = level * 2;
            short newDurability = (short) (tool.getDurability() - repairAmount);
            if (newDurability < 0) newDurability = 0;
            tool.setDurability(newDurability);
        }
    }

    private boolean isMatchingOre(Material ore, Material tool) {
        String toolType = tool.name().split("_")[0];
        
        switch (toolType) {
            case "DIAMOND":
                return ore.name().contains("DIAMOND_ORE");
            case "IRON":
                return ore.name().contains("IRON_ORE");
            case "GOLD":
                return ore.name().contains("GOLD_ORE");
            case "STONE":
                return ore.name().contains("COAL_ORE") || 
                       ore.name().contains("LAPIS_ORE") || 
                       ore.name().contains("REDSTONE_ORE");
            default:
                return false;
        }
    }
} 