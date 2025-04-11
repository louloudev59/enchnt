package fr.enchnt.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import fr.enchnt.Enchnt;
import fr.enchnt.enchantments.CustomEnchantment;

import java.util.Random;

public class EnchantingManager implements Listener {
    private final Random random = new Random();
    private final Enchnt plugin;

    public EnchantingManager(Enchnt plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
        if (event.getItem().getType() == Material.BOOK) {
            int[] levels = event.getExpLevelCostsOffered();
            for (int i = 0; i < levels.length; i++) {
                levels[i] = Math.min(30, levels[i] + 5);
            }
 
        }
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        if (event.getItem().getType() == Material.BOOK) {
            if (random.nextDouble() < 0.20) {
                event.getEnchantsToAdd().clear();

                ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();

                CustomEnchantment[] customEnchants = CustomEnchantment.values();
                CustomEnchantment randomEnchant = customEnchants[random.nextInt(customEnchants.length)];
                
                int maxLevel = randomEnchant.getMaxLevel();
                int level = Math.min(maxLevel, (event.getExpLevelCost() / 10) + 1);
                
                meta.addStoredEnchant(randomEnchant.getEnchantment(), level, true);
                enchantedBook.setItemMeta(meta);
                
                event.getInventory().setItem(0, enchantedBook);
            }
        }
    }
} 