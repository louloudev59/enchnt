package fr.enchnt.enchantments;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import fr.enchnt.Enchnt;
import java.util.UUID;

public class StableEnchantment extends CustomEnchantment implements Listener {
    
    private static final UUID STABLE_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174002");
    
    public StableEnchantment() {
        super("Stable", 3, EnchantmentTarget.ARMOR);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        updatePlayerKnockback(event.getPlayer());
    }
    
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            updatePlayerKnockback((Player) event.getEntity());
        }
    }
    
    private void updatePlayerKnockback(Player player) {
        player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getModifiers().stream()
            .filter(mod -> mod.getUniqueId().equals(STABLE_UUID))
            .forEach(mod -> player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).removeModifier(mod));
        
        int totalLevel = 0;
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && armor.containsEnchantment(this)) {
                totalLevel += armor.getEnchantmentLevel(this);
            }
        }
        
        if (totalLevel > 0) {
            AttributeModifier knockbackModifier = new AttributeModifier(
                STABLE_UUID,
                "stable_knockback",
                totalLevel * 0.2,
                AttributeModifier.Operation.ADD_NUMBER
            );
            player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).addModifier(knockbackModifier);
        }
    }
} 