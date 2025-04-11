package fr.enchnt.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import fr.enchnt.Enchnt;

public class HelpCommand implements CommandExecutor {
    
    public HelpCommand(Enchnt plugin) {
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage("§6=== §eEnchnt - Aide des Enchantements §6===");
        sender.sendMessage("");
        
        // Armes
        sender.sendMessage("§c§lARMES");
        sender.sendMessage("§6Tir Rapide §7- Tire plusieurs flèches en succession rapide");
        sender.sendMessage("§6Dart de Corde §7- Attache une corde invisible à l'ennemi");
        sender.sendMessage("§6Tir Éparpillé §7- Endommage les entités proches de la cible");
        sender.sendMessage("§6Feu Follet §7- Les entités tuées explosent");
        sender.sendMessage("§6Exécuteur §7- Chance de faire tomber la tête des mobs");
        sender.sendMessage("§6Toxique §7- Réduit la régénération des cibles");
        sender.sendMessage("§6Barbare §7- Augmente les dégâts d'attaque");
        sender.sendMessage("§6Écrasant §7- Dégâts bonus contre les armures lourdes");
        sender.sendMessage("§6Perforant §7- Dégâts bonus contre les armures légères");
        sender.sendMessage("");
        
        // Outils
        sender.sendMessage("§e§lOUTILS");
        sender.sendMessage("§6Excavation §7- Mine plusieurs blocs simultanément");
        sender.sendMessage("§6Déforestation §7- Coupe un arbre entier en un clic");
        sender.sendMessage("§6Récolte §7- Récolte et replante automatiquement");
        sender.sendMessage("§6Affinité §7- Réparation avec les minerais correspondants");
        sender.sendMessage("");
        
        // Armures
        sender.sendMessage("§b§lARMURES");
        sender.sendMessage("§6Vitalité §7- Améliore les soins reçus");
        sender.sendMessage("§6Vigoureux §7- Augmente les points de vie maximum");
        sender.sendMessage("§6Stable §7- Réduit le recul");
        sender.sendMessage("§6Plates §7- Valeur d'armure supplémentaire");
        sender.sendMessage("§6Peau de Basalte §7- Résistance au feu");
        sender.sendMessage("§6Marcheur de Lave §7- Marche sur la lave");
        sender.sendMessage("§6Secoueur de Terre §7- Immunité aux chutes");
        sender.sendMessage("§6Protection §7- Chance de dévier les projectiles");
        sender.sendMessage("§6Métabolisme §7- Régénération de la faim");
        sender.sendMessage("");
        
        // Boucliers
        sender.sendMessage("§d§lBOUCLIERS");
        sender.sendMessage("§6Neutraliseur §7- Bloque les effets des potions");
        sender.sendMessage("§6Onde de Choc §7- Repousse les mobs environnants");
        sender.sendMessage("");
        
        // Général
        sender.sendMessage("§a§lGÉNÉRAL");
        sender.sendMessage("§6Télékinésie §7- Les objets vont directement dans l'inventaire");
        sender.sendMessage("§6Lié à l'Âme §7- Les objets ne sont pas perdus à la mort");
        sender.sendMessage("§6Rajeunissement §7- Régénération de la durabilité");
        sender.sendMessage("§6Siphon §7- Plus d'EXP des entités tuées");
        sender.sendMessage("");
        
        sender.sendMessage("§6Utilisez §e/enchntadmin §6pour gérer les enchantements");
        
        return true;
    }
} 