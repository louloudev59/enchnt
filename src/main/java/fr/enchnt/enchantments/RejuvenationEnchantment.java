package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import fr.enchnt.Enchnt;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RejuvenationEnchantment extends CustomEnchantment implements Listener {
    private final Map<UUID, BukkitRunnable> activeTasks = new HashMap<>();
    private static final int TICK_INTERVAL = 100;

    public RejuvenationEnchantment() {
        super("rejuvenation", 3, EnchantmentTarget.ALL);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = player.getInventory().getItem(event.getNewSlot());
        
        BukkitRunnable oldTask = activeTasks.remove(player.getUniqueId());
        if (oldTask != null) {
            oldTask.cancel();
        }
        
        if (newItem != null && newItem.containsEnchantment(this)) {
            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!player.isOnline()) {
                        this.cancel();
                        return;
                    }
                    
                    ItemStack currentItem = player.getInventory().getItem(event.getNewSlot());
                    if (currentItem == null || !currentItem.containsEnchantment(RejuvenationEnchantment.this)) {
                        this.cancel();
                        return;
                    }
                    
                    int level = currentItem.getEnchantmentLevel(RejuvenationEnchantment.this);
                    short currentDurability = currentItem.getDurability();
                    
                    if (currentDurability > 0) {
                        short newDurability = (short) Math.max(0, currentDurability - level);
                        currentItem.setDurability(newDurability);
                    }
                }
            };
            
            task.runTaskTimer(Enchnt.getInstance(), TICK_INTERVAL, TICK_INTERVAL);
            activeTasks.put(player.getUniqueId(), task);
        }
    }
} 