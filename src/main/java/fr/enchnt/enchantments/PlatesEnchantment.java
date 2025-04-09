package fr.enchnt.enchantments;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import fr.enchnt.Enchnt;
import java.util.UUID;

public class PlatesEnchantment extends CustomEnchantment implements Listener {
    
    private static final UUID PLATES_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174003");
    
    public PlatesEnchantment() {
        super("Plates", 3, EnchantmentTarget.ARMOR);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        updatePlayerArmor(event.getPlayer());
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            updatePlayerArmor((Player) event.getEntity());
        }
    }
    
    private void updatePlayerArmor(Player player) {
        
        player.getAttribute(Attribute.GENERIC_ARMOR).getModifiers().stream()
            .filter(mod -> mod.getUniqueId().equals(PLATES_UUID))
            .forEach(mod -> player.getAttribute(Attribute.GENERIC_ARMOR).removeModifier(mod));
        
        int totalLevel = 0;
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && armor.containsEnchantment(this)) {
                totalLevel += armor.getEnchantmentLevel(this);
            }
        }
        
        if (totalLevel > 0) {
            AttributeModifier armorModifier = new AttributeModifier(
                PLATES_UUID,
                "plates_armor",
                totalLevel * 1.0,
                AttributeModifier.Operation.ADD_NUMBER
            );
            player.getAttribute(Attribute.GENERIC_ARMOR).addModifier(armorModifier);
        }
    }
} 