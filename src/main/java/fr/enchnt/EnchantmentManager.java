package fr.enchnt;

import fr.enchnt.enchantments.RapidFireEnchantment;
import fr.enchnt.enchantments.RopeDartEnchantment;
import fr.enchnt.enchantments.ScatterShotEnchantment;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EnchantmentManager {
    
    private static final Map<String, Enchantment> enchantments = new HashMap<>();
    
    public static void registerEnchantments() {
        registerEnchantment(new RapidFireEnchantment());
        registerEnchantment(new RopeDartEnchantment());
        registerEnchantment(new ScatterShotEnchantment());
        
        try {
            Field field = Enchantment.class.getDeclaredField("acceptingNew");
            field.setAccessible(true);
            field.set(null, true);
            
            for (Enchantment enchantment : enchantments.values()) {
                try {
                    Enchantment.registerEnchantment(enchantment);
                } catch (IllegalArgumentException ignored) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void registerEnchantment(Enchantment enchantment) {
        enchantments.put(enchantment.getName().toLowerCase().replace(" ", "_"), enchantment);
    }
    
    public static void unregisterEnchantments() {
        try {
            Field field = Enchantment.class.getDeclaredField("byKey");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, Enchantment> byKey = (Map<String, Enchantment>) field.get(null);
            
            for (Enchantment enchantment : enchantments.values()) {
                byKey.remove(enchantment.getName().toLowerCase().replace(" ", "_"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} 