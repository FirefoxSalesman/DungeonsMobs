package net.firefoxsalesman.dungeonsmobs.lib.attribute;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

public class AttributeRegistry {

	private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES,
			MOD_ID);

	/**
	 * @deprecated To be renamed FOLLOWER_COST_LIMIT in 1.20.0
	 */
	@Deprecated
	public static final RegistryObject<Attribute> SUMMON_CAP = ATTRIBUTES.register("summon_cap",
			() -> new RangedAttribute(
					"attribute.name.generic.dungeons_libraries.summon_cap",
					0.0D,
					0.0D,
					1024.0D)
					.setSyncable(true));

	public static final RegistryObject<Attribute> FOLLOWER_COST_LIMIT = ATTRIBUTES.register("follower_cost_limit",
			() -> new RangedAttribute(
					"attribute.name.generic.dungeons_libraries.follower_cost_limit",
					24.0D,
					0.0D,
					1024.0D)
					.setSyncable(true));

	public static final RegistryObject<Attribute> SOUL_GATHERING = ATTRIBUTES.register("soul_gathering",
			() -> new RangedAttribute(
					"attribute.name.generic.dungeons_libraries.soul_gathering",
					0.0D,
					0.0D,
					1024.0D)
					.setSyncable(true));

	public static final RegistryObject<Attribute> SOUL_CAP = ATTRIBUTES.register("soul_cap",
			() -> new RangedAttribute(
					"attribute.name.generic.dungeons_libraries.soul_cap",
					300.0D,
					0.0D,
					1024.0D)
					.setSyncable(true));

	public static final RegistryObject<Attribute> LIFE_STEAL = ATTRIBUTES.register("life_steal",
			() -> new RangedAttribute(
					"attribute.name.generic.dungeons_libraries.life_steal",
					1.0D,
					0.0D,
					1024.0D)
					.setSyncable(true));

	public static final RegistryObject<Attribute> RANGED_DAMAGE_MULTIPLIER = ATTRIBUTES
			.register("ranged_damage_multiplier", () -> new RangedAttribute(
					"attribute.name.generic.dungeons_libraries.ranged_damage_multiplier",
					1.0D,
					0.0D,
					1024.0D)
					.setSyncable(true));

	public static final RegistryObject<Attribute> MAGIC_DAMAGE_MULTIPLIER = ATTRIBUTES
			.register("magic_damage_multiplier", () -> new RangedAttribute(
					"attribute.name.generic.dungeons_libraries.magic_damage_multiplier",
					0.0D,
					0.0D,
					1024.0D)
					.setSyncable(true));

	public static final RegistryObject<Attribute> ARTIFACT_COOLDOWN_MULTIPLIER = ATTRIBUTES
			.register("artifact_cooldown_multiplier", () -> new RangedAttribute(
					"attribute.name.generic.dungeons_libraries.artifact_cooldown_multiplier",
					1.0D,
					0.0D,
					1024.0D)
					.setSyncable(true));

	public static void register(IEventBus bus) {
		ATTRIBUTES.register(bus);
	}
}
