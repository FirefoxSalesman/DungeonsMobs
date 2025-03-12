package net.firefoxsalesman.dungeonsmobs.client.models.undead;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

public class SmartSkeletonModel<T extends AbstractSkeleton> extends SkeletonModel<T> {

    public SmartSkeletonModel(ModelPart p_170941_) {
        super(p_170941_);
    }

    @Override
    public void prepareMobModel(T skeleton, float p_212843_2_, float p_212843_3_, float p_212843_4_) {
        super.prepareMobModel(skeleton, p_212843_2_, p_212843_3_, p_212843_4_);
        rightArmPose = HumanoidModel.ArmPose.EMPTY;
        leftArmPose = HumanoidModel.ArmPose.EMPTY;
        if (skeleton.getMainArm() == HumanoidArm.RIGHT) {
            giveModelRightArmPoses(InteractionHand.MAIN_HAND, skeleton);
            giveModelLeftArmPoses(InteractionHand.OFF_HAND, skeleton);
        } else {
            giveModelRightArmPoses(InteractionHand.OFF_HAND, skeleton);
            giveModelLeftArmPoses(InteractionHand.MAIN_HAND, skeleton);
        }
    }

    @Override
    public void setupAnim(T skeleton, float p_225597_2_, float p_225597_3_, float p_225597_4_, float p_225597_5_, float p_225597_6_) {
        super.setupAnim(skeleton, p_225597_2_, p_225597_3_, p_225597_4_, p_225597_5_, p_225597_6_);
        if (swimAmount > 0.0F) {
            rightArm.xRot = rotlerpRad(swimAmount, rightArm.xRot, -2.5132742F) + swimAmount * 0.35F * Mth.sin(0.1F * p_225597_4_);
            leftArm.xRot = rotlerpRad(swimAmount, leftArm.xRot, -2.5132742F) - swimAmount * 0.35F * Mth.sin(0.1F * p_225597_4_);
            rightArm.zRot = rotlerpRad(swimAmount, rightArm.zRot, -0.15F);
            leftArm.zRot = rotlerpRad(swimAmount, leftArm.zRot, 0.15F);
            leftLeg.xRot -= swimAmount * 0.55F * Mth.sin(0.1F * p_225597_4_);
            rightLeg.xRot += swimAmount * 0.55F * Mth.sin(0.1F * p_225597_4_);
            head.xRot = 0.0F;
        }
    }

    private void giveModelRightArmPoses(InteractionHand hand, T skeleton) {
        ItemStack itemstack = skeleton.getItemInHand(hand);
        UseAnim useaction = itemstack.getUseAnimation();
        switch (useaction) {
            case BLOCK:
                if (skeleton.isBlocking()) {
                    rightArmPose = ArmPose.BLOCK;
                } else {
                    rightArmPose = ArmPose.ITEM;
                }
                break;
            case CROSSBOW:
                rightArmPose = ArmPose.CROSSBOW_HOLD;
                if (skeleton.isUsingItem()) {
                    rightArmPose = ArmPose.CROSSBOW_CHARGE;
                }
                break;
            case BOW:
                rightArmPose = ArmPose.BOW_AND_ARROW;
                break;
            case SPEAR:
                leftArmPose = ArmPose.THROW_SPEAR;
                break;
            default:
                rightArmPose = ArmPose.EMPTY;
                if (!itemstack.isEmpty()) {
                    rightArmPose = ArmPose.ITEM;
                }
                break;
        }
    }

    private void giveModelLeftArmPoses(InteractionHand hand, T entityIn) {
        ItemStack itemstack = entityIn.getItemInHand(hand);
        UseAnim useaction = itemstack.getUseAnimation();
        switch (useaction) {
            case BLOCK:
                if (entityIn.isBlocking()) {
                    leftArmPose = ArmPose.BLOCK;
                } else {
                    leftArmPose = ArmPose.ITEM;
                }
                break;
            case CROSSBOW:
                leftArmPose = ArmPose.CROSSBOW_HOLD;
                if (entityIn.isUsingItem()) {
                    leftArmPose = ArmPose.CROSSBOW_CHARGE;
                }
                break;
            case BOW:
                leftArmPose = ArmPose.BOW_AND_ARROW;
                break;
            case SPEAR:
                leftArmPose = ArmPose.THROW_SPEAR;
                break;
            default:
                leftArmPose = ArmPose.EMPTY;
                if (!itemstack.isEmpty()) {
                    leftArmPose = ArmPose.ITEM;
                }
                break;
        }
    }
}
