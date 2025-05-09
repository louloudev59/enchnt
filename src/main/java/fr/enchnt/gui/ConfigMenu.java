package fr.enchnt.gui;

import org.bukkit.Material;
import fr.enchnt.Enchnt;
import java.util.Arrays;

public class ConfigMenu extends SubMenu {
    
    public ConfigMenu(Enchnt plugin) {
        super(plugin, "§8Configuration", 54);
    }
    
    @Override
    protected void initializeItems() {
        addItem(10, Material.GLASS_BOTTLE, "§6Niveaux Maximum", Arrays.asList(
            "§7Configurer les niveaux maximum",
            "§7des enchantements"
        ));
        
        addItem(11, Material.EMERALD, "§6Coûts", Arrays.asList(
            "§7Configurer les coûts",
            "§7des enchantements"
        ));
        
        addItem(12, Material.COMPASS, "§6Probabilités", Arrays.asList(
            "§7Configurer les probabilités",
            "§7des enchantements"
        ));
        
        addBackButton();
    }
} 