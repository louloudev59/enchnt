package fr.enchnt.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import fr.enchnt.Enchnt;
import fr.enchnt.enchantments.CustomEnchantment;

import java.util.Random;

public class VillagerManager implements Listener {
    private final Random random = new Random();

    public VillagerManager(Enchnt plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        if (event.getEntity() instanceof Villager) {
            Villager villager = (Villager) event.getEntity();
            
            if (villager.getProfession() == Villager.Profession.LIBRARIAN) {
                if (random.nextDouble() < 0.15) {
                    ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();

                    CustomEnchantment[] customEnchants = CustomEnchantment.values();
                    CustomEnchantment randomEnchant = customEnchants[random.nextInt(customEnchants.length)];
                    
                    int level = random.nextInt(randomEnchant.getMaxLevel()) + 1;
                    
                    meta.addStoredEnchant(randomEnchant.getEnchantment(), level, true);
                    enchantedBook.setItemMeta(meta);

                    MerchantRecipe recipe = new MerchantRecipe(enchantedBook, 0, 12, true);
                    recipe.addIngredient(new ItemStack(Material.EMERALD, 5 * level));
                    recipe.addIngredient(new ItemStack(Material.BOOK, 1));
                    
                    event.setRecipe(recipe);
                }
            }
        }
    }
} 