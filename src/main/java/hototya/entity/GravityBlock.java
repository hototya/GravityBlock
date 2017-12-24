package hototya.entity;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityFallingBlock;
import cn.nukkit.level.Location;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.DoubleTag;
import cn.nukkit.nbt.tag.FloatTag;
import cn.nukkit.nbt.tag.ListTag;
import cn.nukkit.plugin.PluginBase;

public class GravityBlock extends PluginBase {

	@Override
	public void onLoad() {
		Entity.registerEntity(EntityFallingBlock.class.getSimpleName(), EntityFallingBlock.class);
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new EventListener(this), this);
	}

	public Entity createGravityBlock(Position pos, int blockId, int damage) {
		FullChunk chunk = pos.level.getChunk((int) pos.x >> 4, (int) pos.z >> 4);
		if (!chunk.isGenerated()) {
			chunk.setGenerated();
		}
		if (!chunk.isPopulated()) {
			chunk.setPopulated();
		}
		CompoundTag nbt = new CompoundTag()
				.putList(new ListTag<DoubleTag>("Pos")
						.add(new DoubleTag("", pos.x))
						.add(new DoubleTag("", pos.y))
						.add(new DoubleTag("", pos.z)))
				.putList(new ListTag<DoubleTag>("Motion")
						.add(new DoubleTag("", 0))
						.add(new DoubleTag("", 0))
						.add(new DoubleTag("", 0)))
				.putList(new ListTag<FloatTag>("Rotation")
						.add(new FloatTag("", pos instanceof Location ? (float) ((Location) pos).yaw : 0))
						.add(new FloatTag("", pos instanceof Location ? (float) ((Location) pos).pitch : 0)))
				.putInt("TileID", blockId)
				.putByte("Data", damage);
		return Entity.createEntity("EntityFallingBlock", chunk, nbt);
	}
}
