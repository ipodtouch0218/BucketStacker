package me.ipodtouch0218.bucketstacker;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandStackBuckets implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) { 
			sender.sendMessage("[SB] Only players can use this command!");
			return true;
		}
		
		Player player = (Player) sender;
		int lava_bucket = 0;
		int water_bucket = 0;
		for (ItemStack item : player.getInventory()) {
			if (item != null) {
				if (item.getType() == Material.LAVA_BUCKET) {
					lava_bucket+= item.getAmount();
				} else if (item.getType() == Material.WATER_BUCKET) {
					water_bucket+= item.getAmount();
				}
			}
		}
		player.sendMessage(ChatColor.GREEN + "[SB] Compacted " + ChatColor.YELLOW + water_bucket + " water buckets" + ChatColor.GREEN + " and " + ChatColor.YELLOW + lava_bucket + " lava buckets" + ChatColor.GREEN + " into stacks!");
		player.getInventory().remove(Material.LAVA_BUCKET);
		player.getInventory().remove(Material.WATER_BUCKET);
		
		if (lava_bucket != 0) {
			while (lava_bucket > 16) {
				player.getInventory().addItem(new ItemStack(Material.LAVA_BUCKET, 16));
				lava_bucket-= -16;
			}
			player.getInventory().addItem(new ItemStack(Material.LAVA_BUCKET, lava_bucket));
			lava_bucket = 0;
		}
		
		if (water_bucket != 0) {
			while (water_bucket > 16) {
				player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET, 16));
				water_bucket-= 16;
			}
			player.getInventory().addItem(new ItemStack(Material.WATER_BUCKET, water_bucket));
			water_bucket = 0;
		}
		return true;
	}

}
