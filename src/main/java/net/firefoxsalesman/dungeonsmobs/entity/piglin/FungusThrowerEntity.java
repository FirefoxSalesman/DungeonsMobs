package net.firefoxsalesman.dungeonsmobs.entity.piglin;

import com.mojang.serialization.Dynamic;

import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.entity.piglin.ai.FungusThrowerAi;
import net.firefoxsalesman.dungeonsmobs.interfaces.ISmartCrossbowUser;
import net.firefoxsalesman.dungeonsmobs.items.BlueNethershroomItem;
import net.firefoxsalesman.dungeonsmobs.mixin.PiglinAccessor;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.firefoxsalesman.dungeonsmobs.utils.PiglinHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.monster.piglin.Piglin;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;

public class FungusThrowerEntity extends Piglin {

    public FungusThrowerEntity(EntityType<? extends FungusThrowerEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static boolean checkFungusThrowerSpawnRules(EntityType<? extends Piglin> pEntityType, LevelAccessor levelAccessor, MobSpawnType spawnType, BlockPos pos, RandomSource randomSource) {
        return !levelAccessor.getBlockState(pos.below()).is(BlockTags.WART_BLOCKS);
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem shootableItem) {
        return super.canFireProjectileWeapon(shootableItem) || shootableItem instanceof BlueNethershroomItem;
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        Brain<?> brain = super.makeBrain(dynamic);
        //noinspection unchecked
        FungusThrowerAi.addFungusThrowerTasks((Brain<FungusThrowerEntity>) brain);
        return brain;
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverWorld, DifficultyInstance difficultyInstance, MobSpawnType spawnReason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag compoundNBT) {
        SpawnGroupData spawnData = super.finalizeSpawn(serverWorld, difficultyInstance, spawnReason, spawnDataIn, compoundNBT);
        if (this instanceof ISmartCrossbowUser && ((ISmartCrossbowUser) this).isCrossbowUser()) {
            ((ISmartCrossbowUser) this).setCrossbowUser(false);
        }
        if (spawnReason != MobSpawnType.STRUCTURE) {
            if (isAdult()) {
                SpawnEquipmentHelper.equipMainhand(ModItems.BLUE_NETHERSHROOM.get().getDefaultInstance(), this);
            }
        }
        return spawnData;
    }

    @Override
    protected void finishConversion(ServerLevel serverWorld) {
        PiglinHelper.stopAdmiringItem(this);
        ((PiglinAccessor) this).getInventory().removeAllItems().forEach(this::spawnAtLocation);
        PiglinHelper.zombify(ModEntities.ZOMBIFIED_FUNGUS_THROWER.get(), this);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance p_180481_1_) {
        // NO-OP
    }

    @Override
    protected void populateDefaultEquipmentEnchantments(RandomSource randomSource, DifficultyInstance p_180483_1_) {
        // NO-OP
    }

    @Override
    protected boolean canReplaceCurrentItem(ItemStack replacement, ItemStack current) {
        boolean canReplaceCurrentItem = super.canReplaceCurrentItem(replacement, current);

        boolean takeReplacement =
                FungusThrowerAi.isBlueNethershroom(replacement);
        boolean keepCurrent =
                FungusThrowerAi.isBlueNethershroom(current);
        if (takeReplacement && !keepCurrent) {
            return true;
        } else if (!takeReplacement && keepCurrent) {
            return canReplaceCurrentItem;
        } else {
            return (!isAdult() || !FungusThrowerAi.isBlueNethershroom(replacement) || !(FungusThrowerAi.isBlueNethershroom(current))) && canReplaceCurrentItem;
        }
    }

}
