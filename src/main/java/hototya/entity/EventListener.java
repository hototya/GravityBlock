package hototya.entity;

import cn.nukkit.block.Block;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;

public class EventListener implements Listener {

	private GravityBlock plugin;

	public EventListener(GravityBlock plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlock();
		Location location = block.getLocation();
		if (block.isSolid() && block.level.getBlock(location.subtract(0, 1)).getId() == 0) {
			event.setCancelled();
			plugin.createGravityBlock(location.add(0.5, 0, 0.5), block.getId(), block.getDamage()).spawnToAll();
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Location location = event.getBlock().getLocation();
		Level level = location.level;
		for (int i = 1; level.getBlock(location.add(0, i)).getId() != 0; i++) {
			Location pos = location.add(0, i);
			Block block = level.getBlock(pos);
			if (block.isSolid()) {
				level.setBlock(pos, Block.get(0));
				plugin.createGravityBlock(pos.add(0.5, 0, 0.5), block.getId(), block.getDamage()).spawnToAll();
			} else {
				return;
			}
		}
	}
}
