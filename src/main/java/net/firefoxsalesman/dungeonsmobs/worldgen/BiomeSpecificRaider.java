package net.firefoxsalesman.dungeonsmobs.worldgen;

import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.biome.Biome;

public enum BiomeSpecificRaider {
	SQUALL_GOLEM(
			ModEntities.SQUALL_GOLEM.get(),
			EntityType.RAVAGER,
			BiomeTags.SQUALL_GOLEM_RAIDS_IN),
	MOUNTAINEER(
			ModEntities.MOUNTAINEER.get(),
			EntityType.VINDICATOR,
			BiomeTags.MOUNTAINEEER_RAIDS_IN),
	ICEOLOGER(
			ModEntities.ICEOLOGER.get(),
			EntityType.EVOKER,
			BiomeTags.ICEOLOGER_RAIDS_IN),
	WINDCALLER(
			ModEntities.WINDCALLER.get(),
			EntityType.EVOKER,
			BiomeTags.WINDCALLER_RAIDS_IN)
	/*
	 * ,
	 * ILLUSIONER(
	 * EntityType.ILLUSIONER,
	 * EntityType.ILLUSIONER,
	 * DungeonsMobsConfig.COMMON.ILLUSIONER_BIOME_TYPES.get())
	 */;

	private final EntityType<? extends Raider> entityType;
	private final EntityType<? extends Raider> equivalentType;
	private final TagKey<Biome> biomeTag;

	BiomeSpecificRaider(EntityType<? extends Raider> entityTypeIn, EntityType<? extends Raider> equivalentTypeIn,
			TagKey<Biome> biomeTag) {
		this.entityType = entityTypeIn;
		this.equivalentType = equivalentTypeIn;
		this.biomeTag = biomeTag;
	}

	public EntityType<? extends Raider> getType() {
		return entityType;
	}

	public EntityType<? extends Raider> getEquivalentType() {
		return equivalentType;
	}

	public TagKey<Biome> getBiomeTag() {
		return biomeTag;
	}
}
