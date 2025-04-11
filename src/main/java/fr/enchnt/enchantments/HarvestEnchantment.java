package fr.enchnt.enchantments;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Crops;
import org.bukkit.material.MaterialData;

public class HarvestEnchantment extends CustomEnchantment implements Listener {
    public HarvestEnchantment() {
        super("harvest", 1, EnchantmentTarget.TOOL);
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return item.getType().name().endsWith("_HOE");
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();
        Block clickedBlock = event.getClickedBlock();

        if (tool == null || !tool.containsEnchantment(this) || clickedBlock == null) {
            return;
        }

        if (isCrop(clickedBlock.getType()) && isMature(clickedBlock)) {
            harvestAndReplant(player, clickedBlock);
            event.setCancelled(true);
        }
    }

    private boolean isCrop(Material material) {
        return material == Material.CROPS ||
               material == Material.CARROT ||
               material == Material.POTATO;
    }

    private boolean isMature(Block block) {
        MaterialData data = block.getState().getData();
        if (data instanceof Crops) {
            Crops crops = (Crops) data;
            return crops.getData() == 7;
        }
        return false;
    }

    private void harvestAndReplant(Player player, Block block) {
        Material cropType = block.getType();
        Material seedType = getSeedType(cropType);

        block.breakNaturally(player.getInventory().getItemInMainHand());

        if (seedType != null) {
            block.setType(cropType);
            BlockState state = block.getState();
            MaterialData data = state.getData();
            if (data instanceof Crops) {
                Crops crops = (Crops) data;
                crops.setData((byte) 0);
                state.setData(crops);
                state.update();
            }
        }
    }

    private Material getSeedType(Material cropType) {
        switch (cropType) {
            case CROPS:
                return Material.SEEDS;
            case CARROT:
                return Material.CARROT_ITEM;
            case POTATO:
                return Material.POTATO_ITEM;
            default:
                return null;
        }
    }
} 