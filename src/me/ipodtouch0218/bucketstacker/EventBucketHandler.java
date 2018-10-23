package me.ipodtouch0218.bucketstacker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EventBucketHandler implements Listener {

	private List<Material> transMaterials = new ArrayList<Material>(Arrays.asList(new Material[] {Material.LONG_GRASS, Material.YELLOW_FLOWER, 
			Material.RED_MUSHROOM, Material.RED_ROSE, Material.DOUBLE_PLANT, Material.BROWN_MUSHROOM, Material.NETHER_WARTS, Material.NETHER_STALK, 
			Material.CROPS, Material.CARROT, Material.POTATO, Material.BEETROOT_BLOCK, Material.AIR}));
	
	@SuppressWarnings("deprecation")
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onUse(PlayerInteractEvent e) {
		if (e.isCancelled()) return;
		if (e.getItem() == null) return;
		if (e.getItem().getType() != Material.WATER_BUCKET && e.getItem().getType() != Material.LAVA_BUCKET) return;
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (e.getItem().getAmount() <= 1) return;
		
		e.setCancelled(true);
		
		Block block;
		if (transMaterials.contains(e.getClickedBlock().getType())) {
			block = e.getClickedBlock();
		} else {
			block = e.getClickedBlock().getRelative(e.getBlockFace());
		}
		if (!(transMaterials.contains(block.getType()))) return;
		boolean isLava = (e.getItem().getType() == Material.LAVA_BUCKET);
		
		if (isLava) {
			if (block.getType() == Material.LAVA || block.getType() == Material.STATIONARY_LAVA) {
				if (block.getData() == 0) return;
			}
			block.setType(Material.LAVA);
		} else {
			if (block.getType() == Material.WATER || block.getType() == Material.STATIONARY_WATER) {
				if (block.getData() == 0) return;
			}
			block.setType(Material.WATER);
		}
		
		e.getItem().setAmount(e.getItem().getAmount()-1);
		
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			boolean itemBucket = true;
			for (ItemStack item : e.getPlayer().getInventory()) {
				if (item == null || (item.getType() == Material.BUCKET && item.getAmount() < 16)) {
					e.getPlayer().getInventory().addItem(new ItemStack(Material.BUCKET, 1));
					itemBucket = false;
					break;
				}
			} 
			if (itemBucket) {
				e.getPlayer().getWorld().dropItemNaturally(e.getPlayer().getLocation(), new ItemStack(Material.BUCKET, 1));		
			}
		}
	}
}