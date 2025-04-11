package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NeutralizerEnchantment extends CustomEnchantment implements Listener {
    public NeutralizerEnchantment() {
        super("neutralizer", 3, EnchantmentTarget.ARMOR);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return item.getType().name().equals("SHIELD") || 
               item.getType().name().endsWith("_HELMET") ||
               item.getType().name().endsWith("_CHESTPLATE") ||
               item.getType().name().endsWith("_LEGGINGS") ||
               item.getType().name().endsWith("_BOOTS");
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        if (!event.getPotion().getEffects().isEmpty()) {
            for (PotionEffect effect : event.getPotion().getEffects()) {
                if (isNegativeEffect(effect.getType())) {
                    for (Entity entity : event.getAffectedEntities()) {
                        if (entity instanceof Player) {
                            Player player = (Player) entity;
                            for (ItemStack armor : player.getInventory().getArmorContents()) {
                                if (armor != null && armor.containsEnchantment(this)) {
                                    int level = armor.getEnchantmentLevel(this);
                                    if (Math.random() < (0.2 * level)) {
                                        event.setIntensity(player, 0);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean isNegativeEffect(PotionEffectType type) {
        return type.equals(PotionEffectType.POISON) ||
               type.equals(PotionEffectType.WEAKNESS) ||
               type.equals(PotionEffectType.SLOW) ||
               type.equals(PotionEffectType.BLINDNESS) ||
               type.equals(PotionEffectType.CONFUSION) ||
               type.equals(PotionEffectType.HARM) ||
               type.equals(PotionEffectType.HUNGER) ||
               type.equals(PotionEffectType.WITHER);
    }
} 