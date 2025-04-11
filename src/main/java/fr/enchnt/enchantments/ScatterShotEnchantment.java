package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Particle;
import fr.enchnt.Enchnt;

public class ScatterShotEnchantment extends CustomEnchantment implements Listener {
    
    public ScatterShotEnchantment() {
        super("Tir Éparpillé", 3, EnchantmentTarget.BOW);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow)) return;
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        
        Arrow arrow = (Arrow) event.getEntity();
        Player shooter = (Player) arrow.getShooter();
        
        ItemStack bow = shooter.getInventory().getItemInMainHand();
        if (bow == null || !bow.containsEnchantment(this)) return;
        
        Entity hitEntity = event.getHitEntity();
        
        if (hitEntity instanceof LivingEntity) {
            LivingEntity mainTarget = (LivingEntity) hitEntity;
            
            double radius = Enchnt.getInstance().getConfig().getDouble("scatter-shot.radius", 3.0);
            double damageMultiplier = Enchnt.getInstance().getConfig().getDouble("scatter-shot.damage-multiplier", 0.5);
            
            double baseDamage = 6.0;
            
            for (Entity nearby : mainTarget.getNearbyEntities(radius, radius, radius)) {
                if (nearby instanceof LivingEntity && nearby != mainTarget) {
                    LivingEntity target = (LivingEntity) nearby;
                    
                    double distance = mainTarget.getLocation().distance(target.getLocation());
                    double damage = baseDamage * damageMultiplier * (1 - (distance / radius));
                    
                    target.damage(damage, shooter);
                    
                    target.getWorld().spawnParticle(Particle.CRIT, target.getLocation(), 5, 0.5, 0.5, 0.5, 0);
                }
            }
        }
    }
} 