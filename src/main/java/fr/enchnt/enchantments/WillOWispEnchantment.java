package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import fr.enchnt.Enchnt;
import org.bukkit.Particle;

public class WillOWispEnchantment extends CustomEnchantment implements Listener {
    
    public WillOWispEnchantment() {
        super("Feu Follet", 3, EnchantmentTarget.WEAPON);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Entity killer = entity.getKiller();
        
        if (!(killer instanceof Player)) return;
        
        Player player = (Player) killer;
        ItemStack weapon = player.getInventory().getItemInMainHand();
        
        if (weapon == null || !weapon.containsEnchantment(this)) return;
        
        int level = weapon.getEnchantmentLevel(this);
        
        float explosionPower = (float) Enchnt.getInstance().getConfig().getDouble("will-o-wisp.explosion-power", 2.0);
        int fireDuration = Enchnt.getInstance().getConfig().getInt("will-o-wisp.fire-duration", 100);
        
        entity.getWorld().createExplosion(
            entity.getLocation(),
            explosionPower * level,
            false,
            false
        );
        
        for (Entity nearby : entity.getNearbyEntities(3, 3, 3)) {
            if (nearby instanceof LivingEntity && nearby != player) {
                ((LivingEntity) nearby).setFireTicks(fireDuration * level);
            }
        }
        
        entity.getWorld().spawnParticle(Particle.FLAME, entity.getLocation(), 20, 1, 1, 1, 0.1);
        entity.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, entity.getLocation(), 1, 0, 0, 0, 0);
    }
} 