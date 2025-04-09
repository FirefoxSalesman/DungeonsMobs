package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.ItemArmorGeoLayer;

public class ArmourLayer<T extends LivingEntity & GeoAnimatable> extends ItemArmorGeoLayer<T> {
	public ArmourLayer(GeoRenderer<T> geoRenderer) {
		super(geoRenderer);
	}

	@Override
	protected ModelPart getModelPartForBone(GeoBone bone, EquipmentSlot slot, ItemStack stack, T animatable,
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
}
