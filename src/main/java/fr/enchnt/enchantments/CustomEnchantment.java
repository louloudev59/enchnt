package fr.enchnt.enchantments;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import fr.enchnt.Enchnt;

public abstract class CustomEnchantment extends Enchantment {
    
    private final String name;
    private final int maxLevel;
    private final EnchantmentTarget target;
    
    public CustomEnchantment(String name, int maxLevel, EnchantmentTarget target) {
        super(NamespacedKey.minecraft(name.toLowerCase().replace(" ", "_")));
        this.name = name;
        this.maxLevel = maxLevel;
        this.target = target;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public int getMaxLevel() {
        return maxLevel;
    }
    
    @Override
    public int getStartLevel() {
        return 1;
    }
    
    @Override
    public EnchantmentTarget getItemTarget() {
        return target;
    }
    
    @Override
    public boolean isTreasure() {
        return false;
    }
    
    @Override
    public boolean isCursed() {
        return false;
    }
    
    @Override
    public boolean conflictsWith(Enchantment other) {
        return false;
    }
    
    @Override
    public boolean canEnchantItem(ItemStack item) {
        return target.includes(item);
    }
} 