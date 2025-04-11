package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class SiphonEnchantment extends CustomEnchantment implements Listener {
    public SiphonEnchantment() {
        super("siphon", 3, EnchantmentTarget.ALL);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();
        
        if (killer != null) {
            int totalLevel = 0;
            
            ItemStack weapon = killer.getInventory().getItemInMainHand();
            if (weapon != null && weapon.containsEnchantment(this)) {
                totalLevel += weapon.getEnchantmentLevel(this);
            }
            
            for (ItemStack armor : killer.getInventory().getArmorContents()) {
                if (armor != null && armor.containsEnchantment(this)) {
                    totalLevel += armor.getEnchantmentLevel(this);
                }
            }
            
            if (totalLevel > 0) {
                int originalExp = event.getDroppedExp();
                int bonusExp = (int) (originalExp * (0.2 * totalLevel));
                event.setDroppedExp(originalExp + bonusExp);
            }
        }
    }
} 