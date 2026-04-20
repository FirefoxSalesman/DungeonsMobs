package net.firefoxsalesman.dungeonsmobs.lib.capabilities.soulcaster;

import com.Polarice3.Goety.utils.SEHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.ModList;

import javax.annotation.Nullable;

import static net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry.SOUL_CAP;

public class SoulCaster implements INBTSerializable<CompoundTag> {

	private float souls;

	public SoulCaster() {
		this.souls = 0;
	}

	/**
	 * Do not call this method directly or you will be fired!
	 * Use {@link SoulCasterHelper#getSouls(Entity)} instead, because it supports
	 * Goety.
	 */
	public float getSouls() {
		return souls;
	}

	public void addSouls(float amount, LivingEntity living) {
		if (ModList.get().isLoaded("goety") && living instanceof Player) {
			SEHelper.increaseSouls((Player) living, (int) amount);
		} else {
			setSouls(this.getSouls() + amount, living);
		}
	}

	public void setSouls(float amount, @Nullable LivingEntity living) {
		if (ModList.get().isLoaded("goety") && living instanceof Player) {
			SEHelper.setSoulsAmount((Player) living, (int) amount);
		} else if (living != null) {
			this.souls = Mth.clamp(amount, 0, (float) living.getAttributeValue(SOUL_CAP.get()));
		} else {
			this.souls = Math.max(amount, 0);
		}
	}

	@Nullable
	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		tag.putFloat("souls", this.getSouls());
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag tag) {
		this.setSouls(tag.getFloat("souls"), null);
	}
}
