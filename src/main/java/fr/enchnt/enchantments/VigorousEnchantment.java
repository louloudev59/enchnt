package fr.enchnt.enchantments;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import fr.enchnt.Enchnt;
import java.util.UUID;

public class VigorousEnchantment extends CustomEnchantment implements Listener {
    
    private static final UUID VIGOROUS_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174001");
    
    public VigorousEnchantment() {
        super("Vigoureux", 3, EnchantmentTarget.ARMOR);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        updatePlayerRegeneration(event.getPlayer());
    }
    
    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            int totalLevel = getTotalEnchantmentLevel(player);
            
            if (totalLevel > 0) {
                double multiplier = 1.0 + (totalLevel * 0.2);
                event.setAmount(event.getAmount() * multiplier);
            }
        }
    }
    
    private void updatePlayerRegeneration(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getModifiers().stream()
            .filter(mod -> mod.getUniqueId().equals(VIGOROUS_UUID))
            .forEach(mod -> player.getAttribute(Attribute.GENERIC_MAX_HEALTH).removeModifier(mod));
        
        int totalLevel = getTotalEnchantmentLevel(player);
        
        if (totalLevel > 0) {
            AttributeModifier regenModifier = new AttributeModifier(
                VIGOROUS_UUID,
                "vigorous_regen",
                totalLevel * 0.1,
                AttributeModifier.Operation.MULTIPLY_SCALAR_1
            );
            player.getAttribute(Attribute.GENERIC_MAX_HEALTH).addModifier(regenModifier);
        }
    }
    
    private int getTotalEnchantmentLevel(Player player) {
        int totalLevel = 0;
        for (ItemStack armor : player.getInventory().getArmorContents()) {
            if (armor != null && armor.containsEnchantment(this)) {
                totalLevel += armor.getEnchantmentLevel(this);
            }
        }
        return totalLevel;
    }
} 