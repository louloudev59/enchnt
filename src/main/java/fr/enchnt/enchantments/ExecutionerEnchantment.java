package fr.enchnt.enchantments;

import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import fr.enchnt.Enchnt;

public class ExecutionerEnchantment extends CustomEnchantment implements Listener {
    
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
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        
        if (entity instanceof Player) {
            meta.setOwningPlayer((Player) entity);
        } else {
            switch (entity.getType()) {
                case SKELETON:
                    meta.setOwningPlayer(Enchnt.getInstance().getServer().getOfflinePlayer("MHF_Skeleton"));
                    break;
                case ZOMBIE:
                    meta.setOwningPlayer(Enchnt.getInstance().getServer().getOfflinePlayer("MHF_Zombie"));
                    break;
                case CREEPER:
                    meta.setOwningPlayer(Enchnt.getInstance().getServer().getOfflinePlayer("MHF_Creeper"));
                    break;
                default:
                    return null;
            }
        }
        
        head.setItemMeta(meta);
        return head;
    }
} 