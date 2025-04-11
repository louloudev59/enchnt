package fr.enchnt.gui;

import org.bukkit.Material;
import fr.enchnt.Enchnt;
import java.util.Arrays;

public class StatsMenu extends SubMenu {
    
    public StatsMenu(Enchnt plugin) {
        super(plugin, "§8Statistiques", 54);
    }
    
    @Override
    protected void initializeItems() {
        // Enchantements populaires
        addItem(10, Material.DIAMOND_SWORD, "§6Enchantements Populaires", Arrays.asList(
            "§7Voir les enchantements",
            "§7les plus utilisés"
        ));
        
        // Statistiques des joueurs
        addItem(11, Material.ARMOR_STAND, "§6Statistiques des Joueurs", Arrays.asList(
            "§7Voir les statistiques",
            "§7par joueur"
        ));
        
        // Historique
        addItem(12, Material.BOOK, "§6Historique", Arrays.asList(
            "§7Voir l'historique des",
            "§7enchantements appliqués"
        ));
        
        addBackButton();
    }
} 