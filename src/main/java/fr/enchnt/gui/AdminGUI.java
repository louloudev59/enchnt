package fr.enchnt.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import fr.enchnt.Enchnt;
import java.util.Arrays;

public class AdminGUI extends SubMenu {
    
    public static final String TITLE = "§8Administration des Enchantements";
    
    public AdminGUI(Enchnt plugin) {
        super(plugin, TITLE, 54);
    }
    
    @Override
    protected void initializeItems() {
        // Enchantements d'Armes
        addItem(10, Material.DIAMOND_SWORD, "§6Enchantements d'Armes", Arrays.asList(
            "§7Gérer les enchantements",
            "§7pour les armes"
        ));
        
        // Enchantements d'Outils
        addItem(11, Material.DIAMOND_PICKAXE, "§6Enchantements d'Outils", Arrays.asList(
            "§7Gérer les enchantements",
            "§7pour les outils"
        ));
        
        // Enchantements d'Armures
        addItem(12, Material.DIAMOND_CHESTPLATE, "§6Enchantements d'Armures", Arrays.asList(
            "§7Gérer les enchantements",
            "§7pour les armures"
        ));
        
        // Enchantements de Boucliers
        addItem(13, Material.SHIELD, "§6Enchantements de Boucliers", Arrays.asList(
            "§7Gérer les enchantements",
            "§7pour les boucliers"
        ));
        
        // Enchantements Généraux
        addItem(14, Material.ENCHANTED_BOOK, "§6Enchantements Généraux", Arrays.asList(
            "§7Gérer les enchantements",
            "§7généraux"
        ));
        
        // Configuration
        addItem(15, Material.REDSTONE, "§6Configuration", Arrays.asList(
            "§7Configurer les paramètres",
            "§7du plugin"
        ));
        
        // Statistiques
        addItem(16, Material.PAPER, "§6Statistiques", Arrays.asList(
            "§7Voir les statistiques",
            "§7d'utilisation"
        ));
    }
    
    public void open(Player player) {
        player.openInventory(inventory);
    }
} 