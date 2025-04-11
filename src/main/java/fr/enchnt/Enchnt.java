package fr.enchnt;

import org.bukkit.plugin.java.JavaPlugin;
import fr.enchnt.commands.AdminCommand;
import fr.enchnt.gui.AdminGUIListener;
import fr.enchnt.managers.*;

public class Enchnt extends JavaPlugin {
    
    private static Enchnt instance;
    private EnchantmentManager enchantmentManager;
    private LootManager lootManager;
    private VillagerManager villagerManager;
    private EnchantingManager enchantingManager;
    
    @Override
    public void onEnable() {
        instance = this;
        
        this.enchantmentManager = new EnchantmentManager();
        this.lootManager = new LootManager(this);
        this.villagerManager = new VillagerManager(this);
        this.enchantingManager = new EnchantingManager(this);
        
        getCommand("enchntadmin").setExecutor(new AdminCommand(this));
        
        getServer().getPluginManager().registerEvents(new AdminGUIListener(this), this);
        
        enchantmentManager.registerEnchantments();
        
        getCommand("enchnt").setExecutor(new EnchntCommand());
        
        getServer().getPluginManager().registerEvents(new EnchantmentListener(), this);
        
        getLogger().info("Enchnt a été activé avec succès !");
    }
    
    @Override
    public void onDisable() {
        enchantmentManager.unregisterEnchantments();
        
        getLogger().info("Enchnt a été désactivé avec succès !");
    }
    
    public static Enchnt getInstance() {
        return instance;
    }
    
    public EnchantmentManager getEnchantmentManager() {
        return enchantmentManager;
    }
    
    public LootManager getLootManager() {
        return lootManager;
    }
    
    public VillagerManager getVillagerManager() {
        return villagerManager;
    }
    
    public EnchantingManager getEnchantingManager() {
        return enchantingManager;
    }
} 