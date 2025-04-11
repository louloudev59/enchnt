package fr.enchnt.enchantments;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import fr.enchnt.Enchnt;
import java.util.UUID;

public class ExecutionerEnchantment extends CustomEnchantment implements Listener {
    
    private static final UUID SKELETON_UUID = UUID.fromString("00000000-0000-0000-0000-000000000001");
    private static final UUID ZOMBIE_UUID = UUID.fromString("00000000-0000-0000-0000-000000000002");
    private static final UUID CREEPER_UUID = UUID.fromString("00000000-0000-0000-0000-000000000003");
    
    public ExecutionerEnchantment() {
        super("Exécuteur", 3, EnchantmentTarget.WEAPON);
        Enchnt.getInstance().getServer().getPluginManager().registerEvents(this, Enchnt.getInstance());
    }
    
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Player killer = entity.getKiller();
        
        if (killer == null) return;
        
        ItemStack weapon = killer.getInventory().getItemInMainHand();
        if (weapon == null || !weapon.containsEnchantment(this)) return;
        
        int level = weapon.getEnchantmentLevel(this);
        
        double dropChance = Enchnt.getInstance().getConfig().getDouble("executioner.drop-chance", 0.1);
        dropChance *= level;
        
        if (Math.random() > dropChance) return;
        
        ItemStack head = createHeadForEntity(entity);
        if (head != null) {
            entity.getWorld().dropItemNaturally(entity.getLocation(), head);
        }
    }
    
    private ItemStack createHeadForEntity(LivingEntity entity) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        
        if (entity instanceof Player) {
            meta.setOwningPlayer((Player) entity);
        } else {
            switch (entity.getType()) {
                case SKELETON:
                    meta.setOwningPlayer(Bukkit.getOfflinePlayer(SKELETON_UUID));
                    break;
                case ZOMBIE:
                    meta.setOwningPlayer(Bukkit.getOfflinePlayer(ZOMBIE_UUID));
                    break;
                case CREEPER:
                    meta.setOwningPlayer(Bukkit.getOfflinePlayer(CREEPER_UUID));
                    break;
                default:
                    return null;
            }
        }
        
        head.setItemMeta(meta);
        return head;
    }
} 