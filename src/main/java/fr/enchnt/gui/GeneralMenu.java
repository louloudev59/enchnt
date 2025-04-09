package fr.enchnt.gui;

import org.bukkit.Material;
import fr.enchnt.Enchnt;
import java.util.Arrays;
import java.util.List;

public class GeneralMenu extends SubMenu {
    
    public GeneralMenu(Enchnt plugin) {
        super(plugin, "§8Enchantements Généraux", 54);
    }
    
    @Override
    protected void initializeItems() {
        // Solidité
        addItem(10, Material.IRON_BLOCK, "§6Solidité", Arrays.asList(
            "§7Augmente la durabilité de l'item",
            "§7+50% de durabilité par niveau",
            "§aNiveau maximum: 3"
        ));
        
        // Indestructible
        addItem(11, Material.DIAMOND_BLOCK, "§6Indestructible", Arrays.asList(
            "§7Empêche l'item d'être détruit",
            "§7Niveau unique"
        ));
        
        // Malédiction
        addItem(12, Material.BONE, "§6Malédiction", Arrays.asList(
            "§7Empêche de retirer l'item",
            "§7Niveau unique"
        ));
        
        addBackButton();
    }
} 