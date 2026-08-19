package net.firefoxsalesman.dungeonsmobs.mod;

import javax.annotation.Nullable;

import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

/**
 * This is utterly stolen from Goety.
 * Another thanks to Polarice for putting his mod under the MIT license
 */
public class ModDamageSources {
	public static ResourceKey<DamageType> ECHO = create("echo");
	public static ResourceKey<DamageType> BLASTLING = create("blastling");

	public static ResourceKey<DamageType> create(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, GeneralHelper.modLoc(name));
	}

	public static DamageSource source(Level level, ResourceKey<DamageType> type, @Nullable Entity attacker,
			@Nullable Entity indirectAttacker) {
		return new DamageSource(
				level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(type),
				attacker, indirectAttacker);
	}

}
