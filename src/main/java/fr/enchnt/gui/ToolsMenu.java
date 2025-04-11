package fr.enchnt.gui;

import org.bukkit.Material;
import fr.enchnt.Enchnt;
import java.util.Arrays;

public class ToolsMenu extends SubMenu {
    
    public ToolsMenu(Enchnt plugin) {
        super(plugin, "§8Enchantements d'Outils", 54);
    }
    
    @Override
    protected void initializeItems() {
        // Excavation
        addItem(10, Material.DIAMOND_PICKAXE, "§6Excavation", Arrays.asList(
            "§7Permet de miner plusieurs blocs en même temps",
            "§7Formats: 1x2, +, 3x3, etc.",
            "§7Annulé en maintenant Shift",
            "§7Blocs configurables",
            "§aNiveau maximum: 3"
        ));
        
        // Déforestation
        addItem(11, Material.DIAMOND_AXE, "§6Déforestation", Arrays.asList(
            "§7Coupe un arbre entier en un seul clic",
            "§7Fonctionne sur tous les types d'arbres",
            "§aNiveau maximum: 1"
        ));
        
        // Récolte
        addItem(12, Material.DIAMOND_HOE, "§6Récolte", Arrays.asList(
            "§7Récolte et replante automatiquement les cultures",
            "§7Fonctionne sur toutes les cultures",
            "§aNiveau maximum: 1"
        ));
        
        // Affinité
        addItem(13, Material.DIAMOND, "§6Affinité", Arrays.asList(
            "§7Répare l'outil en minant des minerais correspondants",
            "§7Pourcentage de réparation basé sur le niveau",
            "§aNiveau maximum: 3"
        ));
        
        addBackButton();
    }
} 