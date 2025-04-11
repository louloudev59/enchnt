package fr.enchnt.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import fr.enchnt.Enchnt;

public class AdminGUIListener implements Listener {
    
    private final Enchnt plugin;
    
    public AdminGUIListener(Enchnt plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(AdminGUI.TITLE)) {
            return;
        }
        
        event.setCancelled(true);
        
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || !clickedItem.hasItemMeta()) {
            return;
        }
        
        String itemName = clickedItem.getItemMeta().getDisplayName();
        Player player = (Player) event.getWhoClicked();
        
        switch (itemName) {
            case "§6Enchantements d'Armes":
                new WeaponMenu(plugin).open(player);
                break;
            case "§6Enchantements d'Outils":
                new ToolMenu(plugin).open(player);
                break;
            case "§6Enchantements d'Armures":
                new ArmorMenu(plugin).open(player);
                break;
            case "§6Enchantements de Boucliers":
                new ShieldMenu(plugin).open(player);
                break;
            case "§6Enchantements Généraux":
                new GeneralMenu(plugin).open(player);
                break;
            case "§6Configuration":
                new ConfigMenu(plugin).open(player);
                break;
            case "§6Statistiques":
                new StatsMenu(plugin).open(player);
                break;
        }
    }
} 