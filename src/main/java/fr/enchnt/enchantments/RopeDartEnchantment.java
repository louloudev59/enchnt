package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.util.Vector;
import fr.enchnt.Enchnt;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;

public class RopeDartEnchantment extends CustomEnchantment implements Listener {
    
    public RopeDartEnchantment() {
        super("Dart de Corde", 3, EnchantmentTarget.BOW);
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
        
        int level = bow.getEnchantmentLevel(this);
        Entity hitEntity = event.getHitEntity();
        
        if (hitEntity instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) hitEntity;
            
            int pullDelay = Enchnt.getInstance().getConfig().getInt("rope-dart.pull-delay", 20);
            double pullStrength = Enchnt.getInstance().getConfig().getDouble("rope-dart.pull-strength", 0.5);
            
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (target.isDead() || !target.isValid()) {
                        this.cancel();
                        return;
                    }
                    
                    Vector direction = shooter.getLocation().toVector().subtract(target.getLocation().toVector());
                    direction.normalize();
                    
                    target.setVelocity(direction.multiply(pullStrength * level));
                    
                    target.getWorld().spawnParticle(Particle.CRIT, target.getLocation(), 10, 0.5, 0.5, 0.5, 0);
                }
            }.runTaskLater(Enchnt.getInstance(), pullDelay);
        }
    }
} 