package net.firefoxsalesman.dungeonsmobs.interfaces;

import net.minecraft.client.model.geom.ModelPart;

/**
 * An interface for entities that can wear armor.
 * The methods provided allow us to get the various body parts armor can be on.
 */
public interface IArmorWearer {
	void setAllVisible(boolean visible);

	ModelPart getBody();

	ModelPart getLeftArm();

	ModelPart getRightArm();

	ModelPart getLeftLeg();

	ModelPart getRightLeg();
}
