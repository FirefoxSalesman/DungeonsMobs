package net.firefoxsalesman.dungeonsmobs.client.models.armor;

import net.firefoxsalesman.dungeonsmobs.lib.client.renderer.gearconfig.ArmorGearModel;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorGear;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.molang.MolangParser;

public class IceologerArmorGearModel<T extends ArmorGear> extends ArmorGearModel<T> {

	LivingEntity wearer;

	public LivingEntity getWearer() {
		return wearer;
	}

	public void setWearer(LivingEntity wearer) {
		this.wearer = wearer;
	}

	@Override
	public void setCustomAnimations(T entity, long uniqueID, AnimationState<T> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		CoreGeoBone cloak = this.getAnimationProcessor().getBone("armorCloak");

		// TODO: uncomment this after adding the iceologer
		// cloak.setHidden(this.getWearer() != null && this.getWearer() instanceof
		// IceologerEntity);
	}

	@Override
	public void applyMolangQueries(T animatable, double animTime) {
		if (wearer != null) {
			Vec3 velocity = wearer.getDeltaMovement();
			float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
			MolangParser.INSTANCE.setValue("query.ground_speed", () -> groundSpeed * 13);
		}
	}
}
