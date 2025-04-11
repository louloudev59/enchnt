package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import fr.enchnt.Enchnt;

public class RapidFireEnchantment extends CustomEnchantment implements Listener {
    
    public RapidFireEnchantment() {
        super("Tir Rapide", 3, EnchantmentTarget.BOW);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        
        ItemStack bow = event.getBow();
        if (bow == null || !bow.containsEnchantment(this)) return;
        
        final int level = bow.getEnchantmentLevel(this);
        final Player player = (Player) event.getEntity();
        
        new BukkitRunnable() {
            int shots = 0;
            
            @Override
            public void run() {
                if (shots >= level) {
                    this.cancel();
                    return;
                }
                
                Arrow arrow = player.launchProjectile(Arrow.class);
                arrow.setVelocity(arrow.getVelocity().multiply(0.8));
                
                shots++;
            }
        }.runTaskTimer(Enchnt.getInstance(), 2L, 2L);
    }
} 