package fr.enchnt.managers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import fr.enchnt.Enchnt;
import fr.enchnt.enchantments.CustomEnchantment;

import java.util.Random;

public class LootManager implements Listener {
    private final Random random = new Random();

    public LootManager(Enchnt plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        // 10% de chance d'ajouter un livre enchanté personnalisé
        if (random.nextDouble() < 0.10) {
            ItemStack enchantedBook = new ItemStack(Material.ENCHANTED_BOOK);
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) enchantedBook.getItemMeta();

            // Sélectionner un enchantement personnalisé aléatoire
            CustomEnchantment[] customEnchants = CustomEnchantment.values();
            CustomEnchantment randomEnchant = customEnchants[random.nextInt(customEnchants.length)];
            
            // Niveau aléatoire entre 1 et le niveau maximum de l'enchantement
            int level = random.nextInt(randomEnchant.getMaxLevel()) + 1;
            
            meta.addStoredEnchant(randomEnchant.getEnchantment(), level, true);
            enchantedBook.setItemMeta(meta);
            
            // Ajouter le livre enchanté dans un coffre aléatoire du chunk
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    for (int y = 0; y < 256; y++) {
                        org.bukkit.block.Block block = event.getChunk().getBlock(x, y, z);
                        if (block.getType() == Material.CHEST) {
                            org.bukkit.block.Chest chest = (org.bukkit.block.Chest) block.getState();
                            chest.getInventory().addItem(enchantedBook);
                            return;
                        }
                    }
                }
            }
        }
    }
} 