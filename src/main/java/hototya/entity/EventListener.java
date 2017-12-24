package hototya.entity;

import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.level.Location;

public class EventListener implements Listener {

	private GravityBlock plugin;

	public EventListener(GravityBlock plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		Location location = block.getLocation().add(0.5, 0, 0.5);
		if (block.isSolid() && block.level.getBlock(location.subtract(0, 1)).getId() == 0) {
			event.setCancelled();
			plugin.createGravityBlock(location, block.getId(), block.getDamage()).spawnToAll();
		}
	}
}
