package net.firefoxsalesman.dungeonsmobs.mod;

import dev.obscuria.fragmentum.api.registry.Deferred;
import kotlin.jvm.functions.Function0;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.effects.EnsnaredEffect;
import net.firefoxsalesman.dungeonsmobs.effects.WarpedEffect;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.BlueNethershroomEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ModEffects {
	public static final Deferred<MobEffect> ENSNARED = registerMobEffects("ensnared",
			() -> new EnsnaredEffect(MobEffectCategory.HARMFUL, 0xdbe64e).addAttributeModifier(
					Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -5.0D,
					AttributeModifier.Operation.MULTIPLY_TOTAL));

	public static final Deferred<MobEffect> WARPED = registerMobEffects("warped",
			() -> new WarpedEffect(MobEffectCategory.HARMFUL,
					BlueNethershroomEntity.LIGHT_BLUE_HEX_COLOR_CODE));

	private static Deferred<MobEffect> registerMobEffects(String name, Function0<? extends MobEffect> fn) {
		return DungeonsMobs.REGISTRAR.register(Registries.MOB_EFFECT, GeneralHelper.modLoc(name), fn);
	}

	public static void init() {

	}
}
