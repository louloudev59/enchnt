package fr.enchnt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;
import fr.enchnt.Enchnt;
import fr.enchnt.gui.AdminGUI;
import org.bukkit.Material;

public class AdminCommand implements CommandExecutor {
    
    private final Enchnt plugin;
    
    public AdminCommand(Enchnt plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cCette commande ne peut être utilisée que par un joueur.");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (!player.hasPermission("enchnt.admin")) {
            player.sendMessage("§cVous n'avez pas la permission d'utiliser cette commande.");
            return true;
        }
        
        if (args.length == 0) {
            new AdminGUI(plugin).open(player);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "reload":
                plugin.reloadConfig();
                player.sendMessage("§aConfiguration rechargée avec succès.");
                break;
            case "give":
                if (args.length < 3) {
                    player.sendMessage("§cUsage: /enchntadmin give <joueur> <enchantement> [niveau]");
                    return true;
                }
                
                Player target = plugin.getServer().getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage("§cJoueur introuvable.");
                    return true;
                }
                
                String enchantName = args[2];
                int level = args.length > 3 ? Integer.parseInt(args[3]) : 1;
                
                ItemStack item = target.getInventory().getItemInMainHand();
                if (item == null || item.getType().equals(Material.AIR)) {
                    player.sendMessage("§cLe joueur doit tenir un item.");
                    return true;
                }
                
                Enchantment enchant = Enchantment.getByName(enchantName.toUpperCase());
                if (enchant == null) {
                    player.sendMessage("§cEnchantement inconnu.");
                    return true;
                }
                
                item.addUnsafeEnchantment(enchant, level);
                player.sendMessage("§aEnchantement " + enchantName + " niveau " + level + " donné à " + target.getName());
                break;
            default:
                player.sendMessage("§cCommande inconnue. Utilisez /enchntadmin pour voir les commandes disponibles.");
                break;
        }
        
        return true;
    }
} 