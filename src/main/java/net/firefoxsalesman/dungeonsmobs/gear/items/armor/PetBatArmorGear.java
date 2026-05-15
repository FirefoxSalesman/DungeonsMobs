package net.firefoxsalesman.dungeonsmobs.gear.items.armor;

import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorGear;
import net.minecraft.resources.ResourceLocation;

public class PetBatArmorGear extends ArmorGear {

	public PetBatArmorGear(Type slotType, Properties properties, ResourceLocation armorSet,
			ResourceLocation modelLocation, ResourceLocation textureLocation,
			ResourceLocation animationFileLocation) {
		super(slotType, properties, armorSet, modelLocation, textureLocation, animationFileLocation);
	}

	public boolean doGivesYouAPetBat() {
		return true;
	}
}
