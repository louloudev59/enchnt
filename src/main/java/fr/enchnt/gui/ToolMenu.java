package fr.enchnt.gui;

import org.bukkit.Material;
import fr.enchnt.Enchnt;
import java.util.Arrays;

public class ToolMenu extends SubMenu {
    
    public ToolMenu(Enchnt plugin) {
        super(plugin, "§8Enchantements d'Outils", 54);
    }
    
    @Override
    protected void initializeItems() {
        // Efficacité
        addItem(10, Material.DIAMOND_PICKAXE, "§6Efficacité", Arrays.asList(
            "§7Augmente la vitesse de minage",
            "§7+30% de vitesse par niveau",
            "§aNiveau maximum: 5"
        ));
        
        // Fortune
        addItem(11, Material.LAPIS_BLOCK, "§6Fortune", Arrays.asList(
            "§7Augmente les chances de récolter plus de ressources",
            "§7+20% de chance par niveau",
            "§aNiveau maximum: 3"
        ));
        
        // Réparation
        addItem(12, Material.ANVIL, "§6Réparation", Arrays.asList(
            "§7Répare lentement l'outil pendant l'utilisation",
            "§71 point de durabilité toutes les 5 secondes",
            "§aNiveau maximum: 3"
        ));
        
        // Expérience
        addItem(13, Material.GLASS_BOTTLE, "§6Expérience", Arrays.asList(
            "§7Génère de l'expérience en minant",
            "§71 point d'expérience par bloc miné",
            "§aNiveau maximum: 3"
        ));
        
        addBackButton();
    }
} 