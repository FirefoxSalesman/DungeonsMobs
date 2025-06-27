package net.firefoxsalesman.dungeonsmobs.client.models.armor;

import net.firefoxsalesman.dungeonsmobs.entity.undead.NecromancerEntity;
import net.firefoxsalesman.dungeonsmobs.items.armor.NecromancerArmorGear;
import net.firefoxsalesman.dungeonsmobs.lib.client.renderer.gearconfig.ArmorGearModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.molang.MolangParser;

public class NecromancerArmorGearModel<T extends NecromancerArmorGear> extends ArmorGearModel<T> {

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
		cloak.setHidden(this.getWearer() != null && this.getWearer() instanceof NecromancerEntity);
		
		this.getAnimationProcessor().getBone("armorHood").setHidden(true);
	}

	@Override
	public void applyMolangQueries(T animatable, double animTime) {
		super.applyMolangQueries(animatable, animTime);
		if (wearer != null) {
			Vec3 velocity = wearer.getDeltaMovement();
			float groundSpeed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
			MolangParser.INSTANCE.setValue("query.ground_speed", () -> groundSpeed * 13);
		}
	}
}
