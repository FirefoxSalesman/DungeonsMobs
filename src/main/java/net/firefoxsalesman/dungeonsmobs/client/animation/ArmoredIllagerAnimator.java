package net.firefoxsalesman.dungeonsmobs.client.animation;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.AbstractIllager;

public class ArmoredIllagerAnimator {
	public static void positionLimbs(AbstractIllager entity, boolean riding, ModelPart rightArm,
			ModelPart leftArm, ModelPart rightLeg, ModelPart leftLeg, ModelPart head, float limbSwing,
			float limbSwingAmount, float attackTime, float ageInTicks) {
		if (riding) {
			rightArm.xRot = (-(float) Math.PI / 5F);
			rightArm.yRot = 0.0F;
			rightArm.zRot = 0.0F;
			leftArm.xRot = (-(float) Math.PI / 5F);
			leftArm.yRot = 0.0F;
			leftArm.zRot = 0.0F;
			rightLeg.xRot = -1.4137167F;
			rightLeg.yRot = ((float) Math.PI / 10F);
			rightLeg.zRot = 0.07853982F;
			leftLeg.xRot = -1.4137167F;
			leftLeg.yRot = (-(float) Math.PI / 10F);
			leftLeg.zRot = -0.07853982F;
		} else {
			rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 2.0F * limbSwingAmount
					* 0.5F;
			rightArm.yRot = 0.0F;
			rightArm.zRot = 0.0F;
			leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
			leftArm.yRot = 0.0F;
			leftArm.zRot = 0.0F;
			rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
			rightLeg.yRot = 0.0F;
			rightLeg.zRot = 0.0F;
			leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount
					* 0.5F;
			leftLeg.yRot = 0.0F;
			leftLeg.zRot = 0.0F;
		}
		AbstractIllager.IllagerArmPose pose = entity.getArmPose();
		if (pose == AbstractIllager.IllagerArmPose.ATTACKING) {
			if (entity.getMainHandItem().isEmpty()) {
				AnimationUtils.animateZombieArms(leftArm, rightArm, true, attackTime,
						ageInTicks);
			} else {
				AnimationUtils.swingWeaponDown(rightArm, leftArm, entity, attackTime,
						ageInTicks);
			}
		} else if (pose == AbstractIllager.IllagerArmPose.SPELLCASTING) {
			rightArm.z = 0.0F;
			rightArm.x = -5.0F;
			leftArm.z = 0.0F;
			leftArm.x = 5.0F;
			rightArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
			leftArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.25F;
			rightArm.zRot = 2.3561945F;
			leftArm.zRot = -2.3561945F;
			rightArm.yRot = 0.0F;
			leftArm.yRot = 0.0F;
		} else if (pose == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
			rightArm.yRot = -0.1F + head.yRot;
			rightArm.xRot = (-(float) Math.PI / 2F) + head.xRot;
			leftArm.xRot = -0.9424779F + head.xRot;
			leftArm.yRot = head.yRot - 0.4F;
			leftArm.zRot = ((float) Math.PI / 2F);
		} else if (pose == AbstractIllager.IllagerArmPose.CROSSBOW_HOLD) {
			AnimationUtils.animateCrossbowHold(rightArm, leftArm, head, true);
		} else if (pose == AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE) {
			AnimationUtils.animateCrossbowCharge(rightArm, leftArm, entity, true);
		} else if (pose == AbstractIllager.IllagerArmPose.CELEBRATING) {
			rightArm.z = 0.0F;
			rightArm.x = -5.0F;
			rightArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.05F;
			rightArm.zRot = 2.670354F;
			rightArm.yRot = 0.0F;
			leftArm.z = 0.0F;
			leftArm.x = 5.0F;
			leftArm.xRot = Mth.cos(ageInTicks * 0.6662F) * 0.05F;
			leftArm.zRot = -2.3561945F;
			leftArm.yRot = 0.0F;
		}
	}
}
