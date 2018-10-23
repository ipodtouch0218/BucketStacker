package me.ipodtouch0218.bucketstacker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class BucketStacker extends JavaPlugin {
	
	@Override
	public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new EventBucketHandler(), this);
        getCommand("stackbuckets").setExecutor(new CommandStackBuckets());
	}
}