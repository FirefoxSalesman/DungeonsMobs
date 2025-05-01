package net.firefoxsalesman.dungeonsmobs.client.models.armor;

import net.firefoxsalesman.dungeonsmobs.entity.illagers.WindcallerEntity;
import net.firefoxsalesman.dungeonsmobs.lib.client.renderer.gearconfig.ArmorGearModel;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorGear;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;

public class WindcallerArmorGearModel<T extends ArmorGear> extends ArmorGearModel<T> {

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

		cloak.setHidden(this.getWearer() != null && this.getWearer() instanceof WindcallerEntity);
	}
}
