package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.Particle;
import fr.enchnt.Enchnt;

public class ToxicEnchantment extends CustomEnchantment implements Listener {
    
    public ToxicEnchantment() {
        super("Toxique", 3, EnchantmentTarget.WEAPON);
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
        
        int duration = Enchnt.getInstance().getConfig().getInt("toxic.duration", 100);
        double regenerationReduction = Enchnt.getInstance().getConfig().getDouble("toxic.regeneration-reduction", 0.5);
        
        target.addPotionEffect(new PotionEffect(
            PotionEffectType.POISON,
            duration * level,
            level - 1
        ));
        
        if (target instanceof Player) {
            Player targetPlayer = (Player) target;
            targetPlayer.addPotionEffect(new PotionEffect(
                PotionEffectType.SLOW_DIGGING,
                duration * level,
                0
            ));
        }
        
        target.getWorld().spawnParticle(Particle.SLIME, target.getLocation(), 10, 0.5, 0.5, 0.5, 0);
    }
} 