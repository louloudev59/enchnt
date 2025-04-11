package fr.enchnt.enchantments;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import fr.enchnt.Enchnt;
import java.util.Map;
import java.util.HashMap;

public class EarthShakerEnchantment extends CustomEnchantment implements Listener {
    
    private final Map<Player, Double> fallDistances = new HashMap<>();
    
    public EarthShakerEnchantment() {
        super("Secoueur de Terre", 3, EnchantmentTarget.ARMOR);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getBoots() == null || 
            !player.getInventory().getBoots().containsEnchantment(this)) {
            return;
        }
        
        if (event.getTo().getY() < event.getFrom().getY()) {
            double fallDistance = fallDistances.getOrDefault(player, 0.0) + 
                                (event.getFrom().getY() - event.getTo().getY());
            fallDistances.put(player, fallDistance);
        } else {
            double fallDistance = fallDistances.getOrDefault(player, 0.0);
            if (fallDistance > 3.0) {
                int enchantLevel = player.getInventory().getBoots().getEnchantmentLevel(this);
                createLandingEffect(player, fallDistance, enchantLevel);
            }
            fallDistances.put(player, 0.0);
        }
    }
    
    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player && 
            event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            Player player = (Player) event.getEntity();
            if (player.getInventory().getBoots() != null && 
                player.getInventory().getBoots().containsEnchantment(this)) {
                event.setCancelled(true);
            }
        }
    }
    
    private void createLandingEffect(Player player, double fallDistance, int enchantLevel) {
        Location location = player.getLocation();
        
        player.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, 
            location, 1, 0, 0, 0, 0);
        player.getWorld().spawnParticle(Particle.BLOCK_CRACK, 
            location, 50, 1, 0, 1, 0.1, Material.DIRT);
        
        player.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 0.5f);
        
        double radius = Math.min(fallDistance * 0.5, enchantLevel * 2.0);
        player.getNearbyEntities(radius, radius, radius).forEach(entity -> {
            if (entity instanceof Player) {
                Player nearbyPlayer = (Player) entity;
                if (nearbyPlayer != player) {
                    double damage = Math.min(fallDistance * 0.5, enchantLevel * 2.0);
                    nearbyPlayer.damage(damage, player);
                }
            }
        });
        
        for (int x = -(int)radius; x <= radius; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -(int)radius; z <= radius; z++) {
                    Block block = location.getBlock().getRelative(x, y, z);
                    if (block.getType().isSolid() && 
                        block.getType() != Material.BEDROCK && 
                        block.getLocation().distanceSquared(location) <= radius * radius) {
                        block.breakNaturally();
                    }
                }
            }
        }
    }
} 