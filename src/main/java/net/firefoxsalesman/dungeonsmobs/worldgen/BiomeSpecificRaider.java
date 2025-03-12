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
        BiomeTags.SQUALL_GOLEM_RAIDS_IN) ,

    /*,
    ILLUSIONER(
            EntityType.ILLUSIONER,
            EntityType.ILLUSIONER,
            DungeonsMobsConfig.COMMON.ILLUSIONER_BIOME_TYPES.get())
     */;

    private final EntityType<? extends Raider> entityType;
    private final EntityType<? extends Raider> equivalentType;
    private final TagKey<Biome> biomeTag;

    BiomeSpecificRaider(EntityType<? extends Raider> entityTypeIn, EntityType<? extends Raider> equivalentTypeIn, TagKey<Biome> biomeTag) {
        this.entityType = entityTypeIn;
        this.equivalentType = equivalentTypeIn;
        this.biomeTag = biomeTag;
    }

    public EntityType<? extends Raider> getType() {
        return this.entityType;
    }

    public EntityType<? extends Raider> getEquivalentType() {
        return this.equivalentType;
    }

    public TagKey<Biome> getBiomeTag() {
        return biomeTag;
    }
}
