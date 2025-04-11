package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SoulboundEnchantment extends CustomEnchantment implements Listener {
    private final Map<UUID, ItemStack[]> soulboundItems = new HashMap<>();

    public SoulboundEnchantment() {
        super("soulbound", 1, EnchantmentTarget.ALL);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ItemStack[] inventory = player.getInventory().getContents();
        ItemStack[] armor = player.getInventory().getArmorContents();
        
        ItemStack[] soulboundInventory = new ItemStack[inventory.length];
        ItemStack[] soulboundArmor = new ItemStack[armor.length];
        
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].containsEnchantment(this)) {
                soulboundInventory[i] = inventory[i];
                inventory[i] = null;
            }
        }
        
        for (int i = 0; i < armor.length; i++) {
            if (armor[i] != null && armor[i].containsEnchantment(this)) {
                soulboundArmor[i] = armor[i];
                armor[i] = null;
            }
        }
        
        player.getInventory().setContents(inventory);
        player.getInventory().setArmorContents(armor);
        
        ItemStack[] allSoulbound = new ItemStack[soulboundInventory.length + soulboundArmor.length];
        System.arraycopy(soulboundInventory, 0, allSoulbound, 0, soulboundInventory.length);
        System.arraycopy(soulboundArmor, 0, allSoulbound, soulboundInventory.length, soulboundArmor.length);
        
        soulboundItems.put(player.getUniqueId(), allSoulbound);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        ItemStack[] soulbound = soulboundItems.remove(player.getUniqueId());
        
        if (soulbound != null) {
            for (ItemStack item : soulbound) {
                if (item != null) {
                    player.getInventory().addItem(item);
                }
            }
        }
    }
} 