package fr.enchnt;

import org.bukkit.plugin.java.JavaPlugin;
import fr.enchnt.commands.AdminCommand;
import fr.enchnt.gui.AdminGUIListener;

public class Enchnt extends JavaPlugin {
    
    private static Enchnt instance;
    
    @Override
    public void onEnable() {
        instance = this;
        
        getCommand("enchntadmin").setExecutor(new AdminCommand(this));
        
        getServer().getPluginManager().registerEvents(new AdminGUIListener(this), this);
        
        
        EnchantmentManager.registerEnchantments();
        
        getCommand("enchnt").setExecutor(new EnchntCommand());
        
        getServer().getPluginManager().registerEvents(new EnchantmentListener(), this);
        
        getLogger().info("Enchnt a été activé avec succès !");
    }
    
    @Override
    public void onDisable() {
        EnchantmentManager.unregisterEnchantments();
        
        getLogger().info("Enchnt a été désactivé avec succès !");
    }
    
    public static Enchnt getInstance() {
        return instance;
    }
} 