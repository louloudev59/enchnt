package fr.enchnt.gui;

import org.bukkit.Material;
import fr.enchnt.Enchnt;
import java.util.Arrays;

public class ShieldMenu extends SubMenu {
    
    public ShieldMenu(Enchnt plugin) {
        super(plugin, "§8Enchantements de Boucliers", 54);
    }
    
    @Override
    protected void initializeItems() {
        // Résistance
        addItem(10, Material.SHIELD, "§6Résistance", Arrays.asList(
            "§7Augmente la résistance aux dégâts",
            "§7+15% de résistance par niveau",
            "§aNiveau maximum: 3"
        ));
        
        // Réflexion
        addItem(11, Material.GLASS, "§6Réflexion", Arrays.asList(
            "§7Renvoie une partie des dégâts à l'attaquant",
            "§7+20% de dégâts renvoyés par niveau",
            "§aNiveau maximum: 3"
        ));
        
        // Absorption
        addItem(12, Material.GOLDEN_APPLE, "§6Absorption", Arrays.asList(
            "§7Chance de gagner des cœurs d'absorption en bloquant",
            "§7+1 cœur d'absorption par niveau",
            "§aNiveau maximum: 3"
        ));
        
        addBackButton();
    }
} 