package fr.enchnt.enchantments;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import fr.enchnt.Enchnt;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LavaWalkerEnchantment extends CustomEnchantment implements Listener {
    
    private static final UUID LAVA_WALKER_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174005");
    private final Map<Block, Integer> magmaBlocks = new HashMap<>();
    
    public LavaWalkerEnchantment() {
        super("Marcheur de Lave", 2, EnchantmentTarget.ARMOR);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getBoots() == null || 
            !player.getInventory().getBoots().containsEnchantment(this)) {
            return;
        }
        
        int enchantLevel = player.getInventory().getBoots().getEnchantmentLevel(this);
        Block block = player.getLocation().getBlock();
        
        for (int x = -enchantLevel; x <= enchantLevel; x++) {
            for (int z = -enchantLevel; z <= enchantLevel; z++) {
                Block targetBlock = block.getRelative(x, -1, z);
                if (targetBlock.getType() == Material.LAVA) {
                    createMagmaBlock(targetBlock);
                }
            }
        }
    }
    
    private void createMagmaBlock(Block block) {
        Material originalType = block.getType();
        block.setType(Material.MAGMA_BLOCK);
        
        magmaBlocks.put(block, 100);
        
        new BukkitRunnable() {
            @Override
            public void run() {
                if (magmaBlocks.containsKey(block)) {
                    int ticks = magmaBlocks.get(block) - 1;
                    if (ticks <= 0) {
                        block.setType(originalType);
                        magmaBlocks.remove(block);
                        cancel();
                    } else {
                        magmaBlocks.put(block, ticks);
                    }
                } else {
                    cancel();
                }
            }
        }.runTaskTimer(Enchnt.getInstance(), 1L, 1L);
    }
} 