package net.firefoxsalesman.dungeonsmobs.mixin;

import org.spongepowered.asm.mixin.Mixin;

import net.firefoxsalesman.dungeonsmobs.interfaces.NametagHaver;
import net.minecraft.world.entity.Entity;

@Mixin(Entity.class)
public class EntityMixin implements NametagHaver {
	private boolean showNametag = true;

	@Override
	public void setShowNametag(boolean showNametag) {
		this.showNametag = showNametag;
	}

	@Override
	public boolean getShowNametag() {
		return showNametag;
	}
}
