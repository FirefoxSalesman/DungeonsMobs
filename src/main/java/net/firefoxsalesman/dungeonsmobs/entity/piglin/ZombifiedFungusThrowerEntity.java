package net.firefoxsalesman.dungeonsmobs.entity.piglin;

import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonsmobs.entity.piglin.ai.FungusThrowerAi;
import net.firefoxsalesman.dungeonsmobs.goals.SimpleRangedAttackGoal;
import net.firefoxsalesman.dungeonsmobs.items.BlueNethershroomItem;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class ZombifiedFungusThrowerEntity extends ZombifiedPiglin {
    public ZombifiedFungusThrowerEntity(EntityType<? extends ZombifiedFungusThrowerEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static boolean checkZombifiedFungusThrowerSpawnRules(EntityType<? extends ZombifiedPiglin> p_234351_0_, LevelAccessor p_234351_1_, MobSpawnType p_234351_2_, BlockPos p_234351_3_, RandomSource p_234351_4_) {
        return p_234351_1_.getDifficulty() != Difficulty.PEACEFUL && !p_234351_1_.getBlockState(p_234351_3_.below()).is(BlockTags.WART_BLOCKS);
    }

    @Override
    public boolean canFireProjectileWeapon(ProjectileWeaponItem shootableItem) {
        return super.canFireProjectileWeapon(shootableItem) || shootableItem instanceof BlueNethershroomItem;
    }

    @Override
    protected void addBehaviourGoals() {
        super.addBehaviourGoals();
        goalSelector.addGoal(2, new SimpleRangedAttackGoal<>(this, FungusThrowerAi.FUNGUS_ITEM_STACK_PREDICATE, FungusThrowerAi::performFungusThrow, 0.75D, 60, 6.0F));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficultyInstance) {
        SpawnEquipmentHelper.equipMainhand(ModItems.BLUE_NETHERSHROOM.get().getDefaultInstance(), this);
    }
}
