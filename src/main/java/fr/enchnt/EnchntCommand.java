package fr.enchnt;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnchntCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cCette commande ne peut être utilisée que par un joueur !");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            player.sendMessage("§6=== Enchnt - Aide ===");
            player.sendMessage("§e/enchnt help §7- Affiche cette aide");
            player.sendMessage("§e/enchnt info §7- Affiche les informations sur le plugin");
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "help":
                player.sendMessage("§6=== Enchnt - Aide Détaillée ===");
                player.sendMessage("§e/enchnt help §7- Affiche cette aide");
                player.sendMessage("§e/enchnt info §7- Affiche les informations sur le plugin");
                break;
                
            case "info":
                player.sendMessage("§6=== Enchnt - Informations ===");
                player.sendMessage("§eVersion: §71.0");
                player.sendMessage("§eAuteur: §7VotreNom");
                player.sendMessage("§eDescription: §7Plugin ajoutant de nouveaux enchantements innovants");
                break;
                
            default:
                player.sendMessage("§cCommande inconnue. Utilisez §e/enchnt help §cpour voir les commandes disponibles.");
                break;
        }
        
        return true;
    }
} 