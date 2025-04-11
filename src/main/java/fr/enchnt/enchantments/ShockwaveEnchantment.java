package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShockwaveEnchantment extends CustomEnchantment implements Listener {
    private final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 5000;

    public ShockwaveEnchantment() {
        super("shockwave", 3, EnchantmentTarget.ARMOR);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return item.getType().name().equals("SHIELD") || 
               item.getType().name().endsWith("_HELMET") ||
               item.getType().name().endsWith("_CHESTPLATE") ||
               item.getType().name().endsWith("_LEGGINGS") ||
               item.getType().name().endsWith("_BOOTS");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        
        if (item == null || !item.containsEnchantment(this)) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        Long lastUse = cooldowns.get(player.getUniqueId());
        if (lastUse != null && currentTime - lastUse < COOLDOWN_TIME) {
            return;
        }

        int level = item.getEnchantmentLevel(this);
        double radius = 3.0 + (level * 0.5);
        double force = 1.0 + (level * 0.2);

        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (entity instanceof LivingEntity && entity != player) {
                Vector direction = entity.getLocation().subtract(player.getLocation()).toVector();
                direction.normalize();
                direction.multiply(force);
                entity.setVelocity(direction);
            }
        }

        cooldowns.put(player.getUniqueId(), currentTime);
    }
} 