package net.firefoxsalesman.dungeonsmobs.mod;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.effects.WarpedEffect;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.BlueNethershroomEntity;
import net.firefoxsalesman.dungeonsmobs.effects.EnsnaredEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,
			DungeonsMobs.MOD_ID);

	public static final RegistryObject<MobEffect> ENSNARED = EFFECTS.register("ensnared",
			() -> new EnsnaredEffect(MobEffectCategory.HARMFUL, 0xdbe64e).addAttributeModifier(
					Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -5.0D,
					AttributeModifier.Operation.MULTIPLY_TOTAL));

	public static final RegistryObject<MobEffect> WARPED = EFFECTS.register("warped",
			() -> new WarpedEffect(MobEffectCategory.HARMFUL,
					BlueNethershroomEntity.LIGHT_BLUE_HEX_COLOR_CODE));

	public static void register(IEventBus eventBus) {
		EFFECTS.register(eventBus);
	}
}
