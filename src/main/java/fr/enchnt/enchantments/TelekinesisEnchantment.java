package fr.enchnt.enchantments;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TelekinesisEnchantment extends CustomEnchantment implements Listener {
    public TelekinesisEnchantment() {
        super("telekinesis", 1, EnchantmentTarget.ALL);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return true;
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        Item item = event.getEntity();
        if (item.getPickupDelay() == 0) {
            Player nearestPlayer = null;
            double nearestDistance = Double.MAX_VALUE;

            for (Player player : item.getWorld().getPlayers()) {
                if (player.getInventory().getItemInMainHand().containsEnchantment(this) ||
                    player.getInventory().getItemInOffHand().containsEnchantment(this)) {
                    double distance = player.getLocation().distance(item.getLocation());
                    if (distance < nearestDistance) {
                        nearestDistance = distance;
                        nearestPlayer = player;
                    }
                }
            }

            if (nearestPlayer != null && nearestDistance <= 5.0) {
                ItemStack itemStack = event.getEntity().getItemStack();
                List<ItemStack> drops = new ArrayList<>();
                
                // Gérer les quantités
                int amount = itemStack.getAmount();
                while (amount > 0) {
                    ItemStack drop = itemStack.clone();
                    drop.setAmount(Math.min(amount, 64));
                    drops.add(drop);
                    amount -= 64;
                }

                for (ItemStack drop : drops) {
                    if (nearestPlayer.getInventory().addItem(drop).isEmpty()) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() != null) {
            Player killer = event.getEntity().getKiller();
            if (killer.getInventory().getItemInMainHand().containsEnchantment(this) ||
                killer.getInventory().getItemInOffHand().containsEnchantment(this)) {
                // Les drops seront automatiquement collectés par onItemSpawn
            }
        }
    }
} 