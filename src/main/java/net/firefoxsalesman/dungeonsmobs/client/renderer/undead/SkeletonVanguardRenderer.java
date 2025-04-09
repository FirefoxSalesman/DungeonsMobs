package net.firefoxsalesman.dungeonsmobs.client.renderer.undead;

import net.firefoxsalesman.dungeonsmobs.client.models.undead.SkeletonVanguardModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ArmourLayer;
import net.firefoxsalesman.dungeonsmobs.client.renderer.layers.ItemLayer;
import net.firefoxsalesman.dungeonsmobs.entity.undead.SkeletonVanguardEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SkeletonVanguardRenderer extends GeoEntityRenderer<SkeletonVanguardEntity> {
	public SkeletonVanguardRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new SkeletonVanguardModel());
		addRenderLayer(new ItemLayer<>(this) {
			@Override
			protected ItemStack getStackForBone(GeoBone bone, SkeletonVanguardEntity animatable) {
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
		addRenderLayer(new ArmourLayer<>(this) {
			@Override
			protected EquipmentSlot getEquipmentSlotForBone(GeoBone bone, ItemStack stack,
					SkeletonVanguardEntity animatable) {
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

		});
	}
}
