package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import fr.enchnt.Enchnt;
import java.util.Random;

public class ProtectionEnchantment extends CustomEnchantment implements Listener {
    
    private final Random random = new Random();
    
    public ProtectionEnchantment() {
        super("Protection", 3, EnchantmentTarget.ARMOR);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onProjectileHit(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDamager() instanceof Projectile)) return;
        
        Player player = (Player) event.getEntity();
        Projectile projectile = (Projectile) event.getDamager();
        
        int totalLevel = 0;
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && armor.containsEnchantment(this)) {
                totalLevel += armor.getEnchantmentLevel(this);
            }
        }
        
        if (totalLevel > 0) {
            double deflectionChance = totalLevel * 0.15;
            if (random.nextDouble() < deflectionChance) {
                event.setCancelled(true);
                
                Vector direction = projectile.getVelocity();
                direction.multiply(-0.5);
                projectile.setVelocity(direction);
                
                if (projectile instanceof Arrow) {
                    ((Arrow) projectile).setShooter(player);
                }
            }
        }
    }
} 