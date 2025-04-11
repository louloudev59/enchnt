package fr.enchnt.gui;

import org.bukkit.Material;
import fr.enchnt.Enchnt;
import java.util.Arrays;

public class ArmorMenu extends SubMenu {
    
    public ArmorMenu(Enchnt plugin) {
        super(plugin, "§8Enchantements d'Armures", 54);
    }
    
    @Override
    protected void initializeItems() {
        // Vitalité
        addItem(10, Material.GOLDEN_APPLE, "§6Vitalité", Arrays.asList(
            "§7Améliore les soins reçus",
            "§7+20% de soins par niveau",
            "§aNiveau maximum: 3"
        ));
        
        // Vigoureux
        addItem(11, Material.GOLDEN_CARROT, "§6Vigoureux", Arrays.asList(
            "§7Augmente les points de vie maximum",
            "§7+2 cœurs par niveau",
            "§aNiveau maximum: 3"
        ));
        
        // Stable
        addItem(12, Material.ANVIL, "§6Stable", Arrays.asList(
            "§7Réduit les effets de recul",
            "§7+20% de résistance au recul par niveau",
            "§aNiveau maximum: 3"
        ));
        
        // Plates
        addItem(13, Material.IRON_CHESTPLATE, "§6Plates", Arrays.asList(
            "§7Accorde une valeur d'armure supplémentaire",
            "§7+1 point d'armure par niveau",
            "§aNiveau maximum: 3"
        ));
        
        // Peau de Basalte
        addItem(14, Material.MAGMA_CREAM, "§6Peau de Basalte", Arrays.asList(
            "§7Confère une résistance au feu",
            "§7Ralentit lorsque le joueur est en feu",
            "§7-20% de dégâts de feu par niveau",
            "§aNiveau maximum: 3"
        ));
        
        // Marcheur de Lave
        addItem(15, Material.NETHERRACK, "§6Marcheur de Lave", Arrays.asList(
            "§7Transforme la lave en blocs de magma temporaires",
            "§7Zone d'effet basée sur le niveau",
            "§aNiveau maximum: 2"
        ));
        
        // Secoueur de Terre
        addItem(16, Material.DIAMOND_BOOTS, "§6Secoueur de Terre", Arrays.asList(
            "§7Immunité aux dégâts de chute",
            "§7Crée une explosion en tombant d'une grande hauteur",
            "§7Dégâts et destruction des blocs basés sur la hauteur",
            "§aNiveau maximum: 3"
        ));
        
        // Protection
        addItem(20, Material.SHIELD, "§6Protection", Arrays.asList(
            "§7Chance de dévier les projectiles",
            "§715% de chance par niveau",
            "§7Les projectiles déviés sont renvoyés",
            "§aNiveau maximum: 3"
        ));
        
        // Métabolisme
        addItem(21, Material.COOKED_BEEF, "§6Métabolisme", Arrays.asList(
            "§7Régénération lente des points de faim",
            "§7+1 point de faim toutes les 5 secondes",
            "§aNiveau maximum: 3"
        ));
        
        addBackButton();
    }
} 