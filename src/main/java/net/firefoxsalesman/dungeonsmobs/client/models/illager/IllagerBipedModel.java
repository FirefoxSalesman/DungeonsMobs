package net.firefoxsalesman.dungeonsmobs.client.models.illager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.MountaineerEntity;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;

import static net.firefoxsalesman.dungeonsmobs.entity.illagers.IllagerArmsUtil.armorHasCrossedArms;

public class IllagerBipedModel<T extends AbstractIllager> extends HumanoidModel<T> {
    public ModelPart nose = head.getChild("nose");
    public ModelPart jacket = body.getChild("jacket");
    public ModelPart arms;

    public IllagerBipedModel(ModelPart part) {
        super(part);
        arms = part.getChild("arms");
        hat.visible = false;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition partdefinition1 = partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition1.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -10.0F, -4.0F,
                8.0F, 12.0F, 8.0F, new CubeDeformation(0.45F)), PartPose.ZERO);
        partdefinition1.addOrReplaceChild("nose",
                CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F),
                PartPose.offset(0.0F, -2.0F, 0.0F));
        PartDefinition body = partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("jacket", CubeListBuilder.create().texOffs(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F,
                6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition partdefinition2 = partdefinition.addOrReplaceChild("arms",
                CubeListBuilder.create().texOffs(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).texOffs(40, 38)
                        .addBox(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F),
                PartPose.offsetAndRotation(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        partdefinition2.addOrReplaceChild("left_shoulder",
                CubeListBuilder.create().texOffs(44, 22).mirror().addBox(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F),
                PartPose.ZERO);
        partdefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(0, 22).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-2.0F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(2.0F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_arm",
                CubeListBuilder.create().texOffs(40, 46).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm",
                CubeListBuilder.create().texOffs(40, 46).mirror().addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F),
                PartPose.offset(5.0F, 2.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return Iterables.concat(super.bodyParts(), ImmutableList.of(arms, jacket));
    }

    @Override
    public void prepareMobModel(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        rightArmPose = HumanoidModel.ArmPose.EMPTY;
        leftArmPose = HumanoidModel.ArmPose.EMPTY;
        if (entityIn.getMainArm() == HumanoidArm.RIGHT) {
            giveModelRightArmPoses(InteractionHand.MAIN_HAND, entityIn);
            giveModelLeftArmPoses(InteractionHand.OFF_HAND, entityIn);
        } else {
            giveModelRightArmPoses(InteractionHand.OFF_HAND, entityIn);
            giveModelLeftArmPoses(InteractionHand.MAIN_HAND, entityIn);
        }
        super.prepareMobModel(entityIn, limbSwing, limbSwingAmount, partialTick);
    }

    private void giveModelRightArmPoses(InteractionHand hand, T entityIn) {
        ItemStack itemstack = entityIn.getItemInHand(hand);
        UseAnim useaction = itemstack.getUseAnimation();
        if (entityIn.getArmPose() != AbstractIllager.IllagerArmPose.CROSSED || !armorHasCrossedArms(entityIn, entityIn.getItemBySlot(EquipmentSlot.CHEST))) {
            switch (useaction) {
                case BLOCK:
                    if (entityIn.isBlocking()) {
                        rightArmPose = HumanoidModel.ArmPose.BLOCK;
                    } else {
                        rightArmPose = HumanoidModel.ArmPose.ITEM;
                    }
                    break;
                case CROSSBOW:
                    rightArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
                    if (entityIn.isUsingItem()) {
                        rightArmPose = HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                    }
                    break;
                case BOW:
                    rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
                    break;
                case SPEAR:
                    leftArmPose = ArmPose.THROW_SPEAR;
                    break;
                default:
                    rightArmPose = HumanoidModel.ArmPose.EMPTY;
                    if (!itemstack.isEmpty()) {
                        rightArmPose = HumanoidModel.ArmPose.ITEM;
                    }
                    break;
            }
        }
    }

    private void giveModelLeftArmPoses(InteractionHand hand, T entityIn) {
        ItemStack itemstack = entityIn.getItemInHand(hand);
        UseAnim useaction = itemstack.getUseAnimation();
        if (entityIn.getArmPose() != AbstractIllager.IllagerArmPose.CROSSED || !armorHasCrossedArms(entityIn, entityIn.getItemBySlot(EquipmentSlot.CHEST))) {
            switch (useaction) {
                case BLOCK:
                    if (entityIn.isBlocking()) {
                        leftArmPose = HumanoidModel.ArmPose.BLOCK;
                    } else {
                        leftArmPose = HumanoidModel.ArmPose.ITEM;
                    }
                    break;
                case CROSSBOW:
                    leftArmPose = HumanoidModel.ArmPose.CROSSBOW_HOLD;
                    if (entityIn.isUsingItem()) {
                        leftArmPose = HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                    }
                    break;
                case BOW:
                    leftArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
                    break;
                case SPEAR:
                    leftArmPose = ArmPose.THROW_SPEAR;
                    break;
                default:
                    leftArmPose = HumanoidModel.ArmPose.EMPTY;
                    if (!itemstack.isEmpty()) {
                        leftArmPose = HumanoidModel.ArmPose.ITEM;
                    }
                    break;
            }
        }
    }

    @Override
    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        AbstractIllager.IllagerArmPose armpose = entityIn.getArmPose();
        arms.y = 3.0F;
        arms.z = -1.0F;
        arms.xRot = -0.75F;
        jacket.copyFrom(body);
        boolean isWearingChestplateOrLeggings = entityIn.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof ArmorItem || entityIn.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof ArmorItem;
        jacket.visible = !isWearingChestplateOrLeggings;
        boolean flag = armpose == AbstractIllager.IllagerArmPose.CROSSED && armorHasCrossedArms(entityIn, entityIn.getItemBySlot(EquipmentSlot.CHEST));
        arms.visible = flag;
        leftArm.visible = !flag;
        rightArm.visible = !flag;

        float f = 1.0F;
        if (flag) {
            f = (float) entityIn.getDeltaMovement().lengthSqr();
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F) {
            f = 1.0F;
        }

        if (flag) {
            leftArm.y = 3.0F;
            leftArm.z = -1.0F;
            leftArm.xRot = -0.75F;
            rightArm.y = 3.0F;
            rightArm.z = -1.0F;
            rightArm.xRot = -0.75F;
        }

        if (riding) {
            rightArm.xRot = (-(float) Math.PI / 5F);
            rightArm.yRot = 0.0F;
            rightArm.zRot = 0.0F;
            leftArm.xRot = (-(float) Math.PI / 5F);
            leftArm.yRot = 0.0F;
            leftArm.zRot = 0.0F;
            leftLeg.xRot = -1.4137167F;
            leftLeg.yRot = ((float) Math.PI / 10F);
            leftLeg.zRot = 0.07853982F;
            rightLeg.xRot = -1.4137167F;
            rightLeg.yRot = (-(float) Math.PI / 10F);
            rightLeg.zRot = -0.07853982F;
        } else {
            leftLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
            leftLeg.yRot = 0.0F;
            leftLeg.zRot = 0.0F;
            rightLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount * 0.5F;
            rightLeg.yRot = 0.0F;
            rightLeg.zRot = 0.0F;
        }
        if (entityIn instanceof MountaineerEntity && ((MountaineerEntity) entityIn).isClimbing()) {
            rightArm.xRot = -1.8849558F + Mth.sin(ageInTicks * 0.35F) * 0.5F;
            leftArm.xRot = -1.8849558F - Mth.sin(ageInTicks * 0.35F) * 0.5F;
        } else {
            switch (armpose) {
                case ATTACKING:
                    if (!entityIn.getMainHandItem().isEmpty()
                            && !(entityIn.getMainHandItem().getItem() instanceof ProjectileWeaponItem)
                            && !(entityIn.isBlocking())) {
                        // raises arm with weapon, moves left arm back and forth while attacking
                        AnimationUtils.swingWeaponDown(rightArm, leftArm, entityIn, attackTime, ageInTicks);
                    }
                    break;
                case CELEBRATING:
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
                    break;
                case SPELLCASTING:
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
                    break;
                default:
                    break;
            }
        }
    }

    public void copyPropertiesTo(IllagerBipedModel<T> model) {
        super.copyPropertiesTo(model);
        model.arms.copyFrom(arms);
        model.jacket.copyFrom(jacket);
    }
}
