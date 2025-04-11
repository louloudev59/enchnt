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

public class MetabolismEnchantment extends CustomEnchantment implements Listener {
    private final Map<UUID, BukkitRunnable> activeTasks = new HashMap<>();
    private static final int TICK_INTERVAL = 100;

    public MetabolismEnchantment() {
        super("metabolism", 3, EnchantmentTarget.ARMOR);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return item.getType().name().endsWith("_HELMET") ||
               item.getType().name().endsWith("_CHESTPLATE") ||
               item.getType().name().endsWith("_LEGGINGS") ||
               item.getType().name().endsWith("_BOOTS");
    }

    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        
        int totalLevel = 0;
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && armor.containsEnchantment(this)) {
                totalLevel += armor.getEnchantmentLevel(this);
            }
        }
        
        BukkitRunnable oldTask = activeTasks.remove(player.getUniqueId());
        if (oldTask != null) {
            oldTask.cancel();
        }
        
        if (totalLevel > 0) {
            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (!player.isOnline()) {
                        this.cancel();
                        return;
                    }
                    
                    int currentLevel = 0;
                    for (ItemStack armor : player.getInventory().getArmorContents()) {
                        if (armor != null && armor.containsEnchantment(MetabolismEnchantment.this)) {
                            currentLevel += armor.getEnchantmentLevel(MetabolismEnchantment.this);
                        }
                    }
                    
                    if (currentLevel == 0) {
                        this.cancel();
                        return;
                    }
                    
                    int foodLevel = player.getFoodLevel();
                    if (foodLevel < 20) {
                        player.setFoodLevel(Math.min(20, foodLevel + currentLevel));
                    }
                }
            };
            
            task.runTaskTimer(Enchnt.getInstance(), TICK_INTERVAL, TICK_INTERVAL);
            activeTasks.put(player.getUniqueId(), task);
        }
    }
} 