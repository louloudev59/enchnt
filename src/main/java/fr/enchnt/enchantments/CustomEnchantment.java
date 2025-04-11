package fr.enchnt.enchantments;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import fr.enchnt.Enchnt;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Random;

public abstract class CustomEnchantment extends Enchantment {
    
    private final String name;
    private final int maxLevel;
    private final EnchantmentTarget target;
    private final Random random = new Random();
    private static final CustomEnchantment[] VALUES = new CustomEnchantment[0]; // Sera initialisé par les sous-classes
    
    public CustomEnchantment(String name, int maxLevel, EnchantmentTarget target) {
        super(Enchnt.getInstance().getEnchantmentManager().getNextId());
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
        return true; // Permet l'apparition dans les coffres
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

    public int getRandomLevel() {
        FileConfiguration config = Enchnt.getInstance().getConfig();
        int minLevel = config.getInt("enchantments.spawn.chest.min_level", 1);
        int maxLevel = config.getInt("enchantments.spawn.chest.max_level", 3);
        return random.nextInt(maxLevel - minLevel + 1) + minLevel;
    }

    public boolean shouldSpawnInChest() {
        FileConfiguration config = Enchnt.getInstance().getConfig();
        double probability = config.getDouble("enchantments.spawn.chest.probability", 0.1);
        int weight = config.getInt("enchantments.specific." + getName().toLowerCase() + ".chest_weight", 3);
        return random.nextDouble() < (probability * weight / 5.0);
    }

    public boolean shouldSpawnInVillager() {
        FileConfiguration config = Enchnt.getInstance().getConfig();
        double probability = config.getDouble("enchantments.spawn.villager.probability", 0.15);
        int weight = config.getInt("enchantments.specific." + getName().toLowerCase() + ".villager_weight", 3);
        return random.nextDouble() < (probability * weight / 5.0);
    }

    public boolean shouldSpawnInEnchantingTable() {
        FileConfiguration config = Enchnt.getInstance().getConfig();
        double probability = config.getDouble("enchantments.spawn.enchanting_table.probability", 0.2);
        int weight = config.getInt("enchantments.specific." + getName().toLowerCase() + ".enchanting_table_weight", 3);
        return random.nextDouble() < (probability * weight / 5.0);
    }

    public static CustomEnchantment[] values() {
        return VALUES;
    }

    public Enchantment getEnchantment() {
        return this;
    }
} 