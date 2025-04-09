package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Particle;
import fr.enchnt.Enchnt;

public class BarbarianEnchantment extends CustomEnchantment implements Listener {
    
    public BarbarianEnchantment() {
        super("Barbare", 5, EnchantmentTarget.WEAPON);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        if (!(event.getEntity() instanceof LivingEntity)) return;
        
        Player damager = (Player) event.getDamager();
        LivingEntity target = (LivingEntity) event.getEntity();
        
        ItemStack weapon = damager.getInventory().getItemInMainHand();
        if (weapon == null || !weapon.containsEnchantment(this)) return;
        
        int level = weapon.getEnchantmentLevel(this);
        
        double damageMultiplier = Enchnt.getInstance().getConfig().getDouble("barbarian.damage-multiplier", 1.2);
        
        double bonusDamage = event.getDamage() * (damageMultiplier * level - 1);
        
        event.setDamage(event.getDamage() + bonusDamage);
        
        target.getWorld().spawnParticle(Particle.CRIT, target.getLocation(), 5, 0.5, 0.5, 0.5, 0);
    }
} 