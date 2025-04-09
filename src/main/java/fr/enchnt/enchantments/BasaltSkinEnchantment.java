package fr.enchnt.enchantments;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.inventory.ItemStack;
import fr.enchnt.Enchnt;
import java.util.UUID;

public class BasaltSkinEnchantment extends CustomEnchantment implements Listener {
    
    private static final UUID BASALT_UUID = UUID.fromString("123e4567-e89b-12d3-a456-426614174004");
    
    public BasaltSkinEnchantment() {
        super("Peau de Basalte", 3, EnchantmentTarget.ARMOR);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onItemHeld(PlayerItemHeldEvent event) {
        updatePlayerFireResistance(event.getPlayer());
    }
    
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            updatePlayerFireResistance(player);
            
            if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || 
                event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
                event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                int totalLevel = getTotalEnchantmentLevel(player);
                if (totalLevel > 0) {
                    double reduction = 1.0 - (totalLevel * 0.2);
                    event.setDamage(event.getDamage() * reduction);
                }
            }
        }
    }
    
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (player.getFireTicks() > 0) {
            int totalLevel = getTotalEnchantmentLevel(player);
            if (totalLevel > 0) {
                player.addPotionEffect(new PotionEffect(
                    PotionEffectType.SLOW,
                    20, 
                    totalLevel - 1,     
                    false,
                    false
                ));
            }
        }
    }
    
    private void updatePlayerFireResistance(Player player) {
        player.getAttribute(Attribute.GENERIC_ARMOR).getModifiers().stream()
            .filter(mod -> mod.getUniqueId().equals(BASALT_UUID))
            .forEach(mod -> player.getAttribute(Attribute.GENERIC_ARMOR).removeModifier(mod));
        
        int totalLevel = getTotalEnchantmentLevel(player);
        
        if (totalLevel > 0) {
            AttributeModifier fireResistanceModifier = new AttributeModifier(
                BASALT_UUID,
                "basalt_fire_resistance",
                totalLevel * 0.1,
                AttributeModifier.Operation.MULTIPLY_SCALAR_1
            );
            player.getAttribute(Attribute.GENERIC_ARMOR).addModifier(fireResistanceModifier);
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