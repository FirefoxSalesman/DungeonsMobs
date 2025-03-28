package net.firefoxsalesman.dungeonsmobs.client.renderer.illager;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.DynamicGeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

public class DefaultIllagerRenderer<T extends Mob & GeoAnimatable> extends DynamicGeoEntityRenderer<T> {
	private float scaleFactor = 0.9375F;

	public DefaultIllagerRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider) {
		super(renderManager, modelProvider);
		addRenderLayer(new BlockAndItemGeoLayer<>(this) {
			@Override
			protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
					T animatable, MultiBufferSource bufferSource, float partialTick,
					int packedLight, int packedOverlay) {

				if (stack == animatable.getMainHandItem()) {
					poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

					if (stack.getItem() instanceof ShieldItem)
						poseStack.translate(0, 0.125, -0.25);
				} else if (stack == animatable.getOffhandItem()) {
					poseStack.mulPose(Axis.XP.rotationDegrees(-90f));

					if (stack.getItem() instanceof ShieldItem) {
						poseStack.translate(0, 0.125, 0.25);
						poseStack.mulPose(Axis.YP.rotationDegrees(180));
					}
				}
				super.renderStackForBone(poseStack, bone, stack, animatable, bufferSource, partialTick,
						packedLight, packedOverlay);
			}

			@Override
			protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack,
					T animatable) {
				switch (bone.getName()) {
					case "bipedHandLeft":
						return ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
					case "bipedHandRight":
						return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
					default:
						return ItemDisplayContext.NONE;
				}
			}

			@Override
			protected ItemStack getStackForBone(GeoBone bone, T animatable) {
				switch (bone.getName()) {
					case "bipedHandLeft":
						return animatable.isLeftHanded() ? animatable.getMainHandItem()
								: animatable.getOffhandItem();
					case "bipedHandRight":
						return animatable.isLeftHanded() ? animatable.getOffhandItem()
								: animatable.getMainHandItem();
					default:
						return null;
				}
			}
		});
		addRenderLayer(new ItemArmorGeoLayer<>(this) {
			@Override
			protected void prepModelPartForRender(PoseStack poseStack, GeoBone bone, ModelPart sourcePart) {
				super.prepModelPartForRender(poseStack, bone, sourcePart);
				// TODO: If you're having issues with helmet rendering, this is why.
				if (bone.getName().equals("armorBipedHead")) {
					poseStack.translate(0, 0.125, 0); // 1y is 1 cube up, we want 2/16
				}
				if (bone.getName().equals("armorBipedBody")) {
					poseStack.scale(1.0F, 1.0F, 0.8F); 
				}
			}

			@Override
			protected ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack,
					T animatable,
					HumanoidModel<?> baseModel) {
				switch (bone.getName()) {
					case "armorBipedLeftFoot":
					case "armorBipedLeftLeg":
						return baseModel.leftLeg;
					case "armorBipedRightFoot":
					case "armorBipedRightLeg":
						return baseModel.rightLeg;
					case "armorBipedRightArm":
					case "armorIllagerRightArm":
						return baseModel.rightArm;
					case "armorBipedLeftArm":
					case "armorIllagerLeftArm":
						return baseModel.leftArm;
					case "armorBipedBody":
						return baseModel.body;
					case "armorBipedHead":
						return baseModel.head;
					default:
						return null;
				}
			}

			@Override
			protected EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack, T animatable) {
				switch (bone.getName()) {
					case "armorBipedLeftFoot":
					case "armorBipedRightFoot":
						return EquipmentSlot.FEET;
					case "armorBipedLeftLeg":
					case "armorBipedRightLeg":
						return EquipmentSlot.LEGS;
					case "armorBipedRightHand":
						return !animatable.isLeftHanded() ? EquipmentSlot.MAINHAND
								: EquipmentSlot.OFFHAND;
					case "armorBipedLeftHand":
						return animatable.isLeftHanded() ? EquipmentSlot.MAINHAND
								: EquipmentSlot.OFFHAND;
					case "armorBipedRightArm":
					case "armorBipedLeftArm":
					case "armorIllagerRightArm":
					case "armorIllagerLeftArm":
					case "armorBipedBody":
						return EquipmentSlot.CHEST;
					case "armorBipedHead":
						return EquipmentSlot.HEAD;
					default:
						return null;
				}
			}

			@Override
			protected ItemStack getArmorItemForBone(GeoBone bone, T animatable) {
				switch (bone.getName()) {
					case "armorBipedLeftFoot":
					case "armorBipedRightFoot":
						return bootsStack;
					case "armorBipedLeftLeg":
					case "armorBipedRightLeg":
						return leggingsStack;
					case "armorBipedBody":
					case "armorBipedRightArm":
					case "armorBipedLeftArm":
					case "armorIllagerRightArm":
					case "armorIllagerLeftArm":
						return chestplateStack;
					case "armorBipedHead":
						return helmetStack;
					default:
						return null;
				}
			}
		});
	}

	public DefaultIllagerRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider,
			float scaleFactor) {
		this(renderManager, modelProvider);
		this.scaleFactor = scaleFactor;
	}

	@Override
	protected void applyRotations(T entityLiving, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

	}

	@Override
	public void renderRecursively(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		// if (this.isArmorBone(bone)) {
		// bone.setCubesHidden(true);
		// }
		super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender,
				partialTick, packedLight, packedOverlay, red, green, blue, alpha);
	}

	// protected boolean isArmorBone(GeoBone bone) {
	// return bone.getName().startsWith("armor");
	// }
}
