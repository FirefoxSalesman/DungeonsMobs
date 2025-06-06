package net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster;

import net.firefoxsalesman.dungeonsmobs.lib.summon.SummonConfigRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.*;
import java.util.stream.Collectors;

public class Leader implements INBTSerializable<CompoundTag>, Master {

    private Set<Entity> summonedMobs;
    private List<UUID> summonedMobsUUID = new ArrayList<>();
    private ResourceLocation levelOnLoad;
    private Set<Entity> otherFollowers;
    private final List<UUID> otherFollowersUUID = new ArrayList<>();

    public void copyFrom(Leader leader) {
        this.setSummonedMobs(leader.getSummonedMobs());
    }

    public List<Entity> getAllFollowers() {
        List<Entity> minions = new ArrayList<>();
        minions.addAll(this.getSummonedMobs());
        minions.addAll(this.getOtherFollowers());
        return minions;
    }

    public List<Entity> getSummonedMobs() {
        summonedMobs = initEntities(this.summonedMobs, this.summonedMobsUUID);
        return new ArrayList<>(this.summonedMobs);
    }

    public int getSummonedMobsCost() {
        return this.getSummonedMobs().stream().map(entity -> SummonConfigRegistry.getConfig(ForgeRegistries.ENTITY_TYPES.getKey(entity.getType())).getCost()).reduce(0, Integer::sum);
    }

    public boolean addSummonedMob(Entity entity) {
        summonedMobs = initEntities(this.summonedMobs, this.summonedMobsUUID);
        return this.summonedMobs.add(entity);
    }

    public void setSummonedMobs(List<Entity> summonedMobs) {
        this.summonedMobs = new HashSet<>(summonedMobs);
    }

    public void setSummonedMobsUUID(List<UUID> summonedMobsUUID) {
        this.summonedMobsUUID = summonedMobsUUID;
    }

    public void setLevelOnLoad(ResourceLocation levelOnLoad) {
        this.levelOnLoad = levelOnLoad;
    }

    public boolean addFollower(Entity entity) {
        otherFollowers = initEntities(this.otherFollowers, this.otherFollowersUUID);
        return otherFollowers.add(entity);
    }

    public List<Entity> getOtherFollowers() {
        otherFollowers = initEntities(this.otherFollowers, this.otherFollowersUUID);
        return new ArrayList<>(this.otherFollowers);
    }

    public void setOtherFollowers(List<Entity> otherFollowers) {
        this.otherFollowers = new HashSet<>(otherFollowers);
    }

    private Set<Entity> initEntities(Set<Entity> entities, List<UUID> entityUUIDs) {
        if (entities != null) return entities;
        if (entityUUIDs != null && this.levelOnLoad != null) {
            if (entityUUIDs.isEmpty()) return new HashSet<>();
            ResourceKey<Level> registrykey1 = ResourceKey.create(Registries.DIMENSION, this.levelOnLoad);
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            ServerLevel world = server.getLevel(registrykey1);
            if (world != null) {
                entities = entityUUIDs.stream().map(world::getEntity).filter(Objects::nonNull).collect(Collectors.toSet());
            }
        } else {
            return new HashSet<>();
        }
        return new HashSet<>(entities);
    }

    public void removeMinion(LivingEntity entityLiving) {
        this.getOtherFollowers().remove(entityLiving);
    }

    public static final String LEVEL_KEY = "level";

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        ListTag summoned = new ListTag();
        this.getSummonedMobs().forEach(entity -> {
            CompoundTag mob = new CompoundTag();
            mob.putUUID("uuid", entity.getUUID());
            summoned.add(mob);
        });
        nbt.put("summoned", summoned);
        ListTag minions = new ListTag();
        this.getOtherFollowers().forEach(entity -> {
            CompoundTag minion = new CompoundTag();
            minion.putUUID("uuid", entity.getUUID());
            minions.add(minion);
        });
        nbt.put("followers", minions);
        if (!this.getSummonedMobs().isEmpty()) {
            ResourceLocation location = this.getSummonedMobs().get(0).level().dimension().location();
            nbt.putString(LEVEL_KEY, location.toString());
        } else if (!this.getOtherFollowers().isEmpty()) {
            ResourceLocation location = this.getOtherFollowers().get(0).level().dimension().location();
            nbt.putString(LEVEL_KEY, location.toString());
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        ListTag listNBT = tag.getList("summoned", 10);
        List<UUID> summonedUUIDs = new ArrayList<>();
        for (int i = 0; i < listNBT.size(); ++i) {
            CompoundTag compoundnbt = listNBT.getCompound(i);
            summonedUUIDs.add(compoundnbt.getUUID("uuid"));
        }
        this.setSummonedMobsUUID(summonedUUIDs);
        ListTag minionsNBT = tag.getList("followers", 10);
        List<UUID> minionUUIDs = new ArrayList<>();
        for (int i = 0; i < minionsNBT.size(); ++i) {
            CompoundTag compoundnbt = minionsNBT.getCompound(i);
            minionUUIDs.add(compoundnbt.getUUID("uuid"));
        }
        if (tag.contains(LEVEL_KEY)) {
            this.setLevelOnLoad(new ResourceLocation(tag.getString(LEVEL_KEY)));
        }
    }

    // Following methods to be removed in 1.20.0

    @Override
    public void copyFrom(Master summoner) {
        this.copyFrom((Leader) summoner);
    }

    @Override
    public List<Entity> getAllMinions() {
        return getAllFollowers();
    }

    @Override
    public boolean addMinion(Entity entity) {
        return addFollower(entity);
    }

    @Override
    public List<Entity> getOtherMinions() {
        return getOtherFollowers();
    }

    @Override
    public void setOtherMinions(List<Entity> otherMinions) {
        setOtherFollowers(otherMinions);
    }
}
