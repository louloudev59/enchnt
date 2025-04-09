package fr.enchnt.enchantments;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import fr.enchnt.Enchnt;
import java.util.UUID;

public class VitalityEnchantment extends CustomEnchantment implements Listener {
    
    private static final UUID VITALITY_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174000");
    
    public VitalityEnchantment() {
        super("Vitalité", 3, EnchantmentTarget.ARMOR);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        updatePlayerHealth(event.getPlayer());
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            updatePlayerHealth((Player) event.getEntity());
        }
    }
    
    private void updatePlayerHealth(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers().stream()
            .filter(mod -> mod.getUniqueId().equals(VITALITY_UUID))
            .forEach(mod -> player.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(mod));
        
        int totalLevel = 0;
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && armor.containsEnchantment(this)) {
                totalLevel += armor.getEnchantmentLevel(this);
            }
        }
        
        if (totalLevel > 0) {
            AttributeModifier healthModifier = new AttributeModifier(
                VITALITY_UUID,
                "vitality_health",
                totalLevel * 2.0,
                AttributeModifier.Operation.ADD_NUMBER
            );
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(healthModifier);
        }
    }
} 