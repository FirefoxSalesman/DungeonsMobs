package net.firefoxsalesman.dungeonsmobs.capabilities.ancient;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.util.INBTSerializable;

import static net.firefoxsalesman.dungeonsmobs.capabilities.ModCapabilities.ANCIENT_CAPABILITY;

import net.firefoxsalesman.dungeonsmobs.network.DungeonsBossInfo;

public class Ancient implements INBTSerializable<CompoundTag> {
	private boolean ancient = false;
	private DungeonsBossInfo bossInfo = null;
	private Component displayName = null;

	public boolean isAncient() {
		return ancient;
	}

	public void setAncient(boolean ancient) {
		this.ancient = ancient;
	}

	public boolean initiateBossBar(Mob boss, Component displayName) {
		this.displayName = displayName;
		bossInfo = new DungeonsBossInfo(displayName, boss, BossEvent.BossBarOverlay.PROGRESS);
		return true;
	}

	public DungeonsBossInfo getBossInfo() {
		return bossInfo;
	}

	public Component getDisplayName() {
		return displayName;
	}

	@Override
	public CompoundTag serializeNBT() {
		if (ANCIENT_CAPABILITY == null) {
			return new CompoundTag();
		}
		CompoundTag tag = new CompoundTag();
		tag.putBoolean("ancient", this.isAncient());
		if (this.getBossInfo() != null) {
			tag.putString("displayName", this.getBossInfo().getName().getString());
		}
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.setAncient(tag.getBoolean("ancient"));
		if (tag.contains("displayName")) {
			this.displayName = Component.literal(tag.getString("displayName"));
			// this.initiateBossBar(boss, Component.literal(tag.getString("displayName")));
		}
	}
}
