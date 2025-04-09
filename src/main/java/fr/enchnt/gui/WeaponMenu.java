package fr.enchnt.gui;

import org.bukkit.Material;
import fr.enchnt.Enchnt;
import java.util.Arrays;
import java.util.List;

public class WeaponMenu extends SubMenu {
    
    public WeaponMenu(Enchnt plugin) {
        super(plugin, "§8Enchantements d'Armes", 54);
    }
    
    @Override
    protected void initializeItems() {
        // Tranchant
        addItem(10, Material.DIAMOND_SWORD, "§6Tranchant", Arrays.asList(
            "§7Augmente les dégâts de l'arme",
            "§7+1 dégât par niveau",
            "§aNiveau maximum: 5"
        ));
        
        // Critique
        addItem(11, Material.IRON_AXE, "§6Critique", Arrays.asList(
            "§7Chance d'infliger des dégâts critiques",
            "§715% de chance par niveau",
            "§7+50% de dégâts en critique",
            "§aNiveau maximum: 3"
        ));
        
        // Vampirisme
        addItem(12, Material.REDSTONE, "§6Vampirisme", Arrays.asList(
            "§7Récupère des points de vie en infligeant des dégâts",
            "§710% des dégâts infligés par niveau",
            "§aNiveau maximum: 3"
        ));
        
        // Poing de Fer
        addItem(13, Material.IRON_SWORD, "§6Poing de Fer", Arrays.asList(
            "§7Augmente les dégâts des coups critiques",
            "§7+25% de dégâts critiques par niveau",
            "§aNiveau maximum: 3"
        ));
        
        // Coup de Grâce
        addItem(14, Material.DIAMOND_AXE, "§6Coup de Grâce", Arrays.asList(
            "§7Chance d'instakill les mobs faibles",
            "§710% de chance par niveau",
            "§7Ne fonctionne que sur les mobs avec moins de 20% de vie",
            "§aNiveau maximum: 3"
        ));
        
        addBackButton();
    }
} 