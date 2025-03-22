package net.firefoxsalesman.dungeonsmobs.client.renderer.undead;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.undead.WraithModel;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.PulsatingGlowLayer;
import net.firefoxsalesman.dungeonsmobs.entity.undead.WraithEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class WraithRenderer extends GeoEntityRenderer<WraithEntity> {

	public WraithRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new WraithModel());
		addRenderLayer(new PulsatingGlowLayer<>(this,
				new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/wraith/wraith_glow.png"),
				0.1F, 1.0F, 0.25F));
	}

	@Override
	protected void applyRotations(WraithEntity entityLiving, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		float scaleFactor = 1.0F;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

	}

	@Override
	public void renderRecursively(PoseStack poseStack, WraithEntity animatable, GeoBone bone, RenderType renderType,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		if (isArmorBone(bone)) {
			bone.setChildrenHidden(true);
		}
		super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender,
				partialTick,
				packedLight, packedOverlay, red, green, blue, alpha);
	}

	protected boolean isArmorBone(CoreGeoBone bone) {
		return bone.getName().startsWith("armor");
	}

	// @Nullable
	// @Override
	// protected ResourceLocation getTextureForBone(String s, WraithEntity
	// windcallerEntity) {
	// return null;
	// }

	// @Override
	// protected ItemStack getHeldItemForBone(String boneName, WraithEntity
	// currentEntity) {
	// switch (boneName) {
	// case DefaultBipedBoneIdents.LEFT_HAND_BONE_IDENT:
	// return currentEntity.isLeftHanded() ? mainHand : offHand;
	// case DefaultBipedBoneIdents.RIGHT_HAND_BONE_IDENT:
	// return currentEntity.isLeftHanded() ? offHand : mainHand;
	// case DefaultBipedBoneIdents.POTION_BONE_IDENT:
	// break;
	// }
	// return null;
	// }

	// @Override
	// protected TransformType getCameraTransformForItemAtBone(ItemStack boneItem,
	// String boneName) {
	// switch (boneName) {
	// case DefaultBipedBoneIdents.LEFT_HAND_BONE_IDENT:
	// return TransformType.THIRD_PERSON_RIGHT_HAND;
	// case DefaultBipedBoneIdents.RIGHT_HAND_BONE_IDENT:
	// return TransformType.THIRD_PERSON_RIGHT_HAND;
	// default:
	// return TransformType.NONE;
	// }
	// }

	// @Override
	// protected void preRenderItem(PoseStack stack, ItemStack item, String
	// boneName, WraithEntity currentEntity,
	// IBone bone) {
	// if (item == this.mainHand) {
	// stack.mulPose(Axis.XP.rotationDegrees(-90f));

	// if (item.getItem() instanceof ShieldItem)
	// stack.translate(0, 0.125, -0.25);
	// } else if (item == this.offHand) {
	// stack.mulPose(Axis.XP.rotationDegrees(-90f));

	// if (item.getItem() instanceof ShieldItem) {
	// stack.translate(0, 0.125, 0.25);
	// stack.mulPose(Axis.YP.rotationDegrees(180));
	// }
	// }
	// }

    // @Nullable
	// @Override
	// protected ItemStack getArmorForBone(String boneName, WraithEntity currentEntity) {
	// 	switch (boneName) {
	// 		case "armorBipedLeftFoot":
	// 		case "armorBipedRightFoot":
	// 			return boots;
	// 		case "armorBipedLeftLeg":
	// 		case "armorBipedRightLeg":
	// 			return leggings;
	// 		case "armorBipedBody":
	// 		case "armorBipedRightArm":
	// 		case "armorBipedLeftArm":
	// 		case "armorIllagerRightArm":
	// 		case "armorIllagerLeftArm":
	// 			return chestplate;
	// 		case "armorBipedHead":
	// 			return helmet;
	// 		default:
	// 			return null;
	// 	}
	// }

    // @Override
	// protected EquipmentSlot getEquipmentSlotForArmorBone(String boneName, WraithEntity currentEntity) {
	// 	switch (boneName) {
	// 		case "armorBipedLeftFoot":
	// 		case "armorBipedRightFoot":
	// 			return EquipmentSlot.FEET;
	// 		case "armorBipedLeftLeg":
	// 		case "armorBipedRightLeg":
	// 			return EquipmentSlot.LEGS;
	// 		case "armorBipedRightHand":
	// 			return !currentEntity.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
	// 		case "armorBipedLeftHand":
	// 			return currentEntity.isLeftHanded() ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
	// 		case "armorBipedRightArm":
	// 		case "armorBipedLeftArm":
	// 		case "armorIllagerRightArm":
	// 		case "armorIllagerLeftArm":
	// 		case "armorBipedBody":
	// 			return EquipmentSlot.CHEST;
	// 		case "armorBipedHead":
	// 			return EquipmentSlot.HEAD;
	// 		default:
	// 			return null;
	// 	}
	// }

    // @Override
	// protected ModelPart getArmorPartForBone(String name, HumanoidModel<?> armorBipedModel) {
	// 	switch (name) {
	// 		case "armorBipedLeftFoot":
	// 		case "armorBipedLeftLeg":
	// 			return armorBipedModel.leftLeg;
	// 		case "armorBipedRightFoot":
	// 		case "armorBipedRightLeg":
	// 			return armorBipedModel.rightLeg;
	// 		case "armorBipedRightArm":
	// 		case "armorIllagerRightArm":
	// 			return armorBipedModel.rightArm;
	// 		case "armorBipedLeftArm":
	// 		case "armorIllagerLeftArm":
	// 			return armorBipedModel.leftArm;
	// 		case "armorBipedBody":
	// 			return armorBipedModel.body;
	// 		case "armorBipedHead":
	// 			return armorBipedModel.head;
	// 		default:
	// 			return null;
	// 	}
	// }
}
