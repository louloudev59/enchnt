package fr.enchnt.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import fr.enchnt.Enchnt;
import java.util.Arrays;
import java.util.List;

public abstract class SubMenu {
    
    protected final Enchnt plugin;
    protected final Inventory inventory;
    protected final String title;
    
    public SubMenu(Enchnt plugin, String title, int size) {
        this.plugin = plugin;
        this.title = title;
        this.inventory = Bukkit.createInventory(null, size, title);
        initializeItems();
    }
    
    protected abstract void initializeItems();
    
    protected void addItem(int slot, Material material, String name, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        inventory.setItem(slot, item);
    }
    
    protected void addBackButton() {
        addItem(inventory.getSize() - 1, Material.BARRIER, "§cRetour", Arrays.asList(
            "§7Retourner au menu principal"
        ));
    }
    
    public void open(Player player) {
        player.openInventory(inventory);
    }
    
    public String getTitle() {
        return title;
    }
} 