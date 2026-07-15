package net.firefoxsalesman.dungeonsmobs.gear.items.armor;

import net.firefoxsalesman.dungeonslibs.items.gearconfig.ArmorGear;
import net.minecraft.resources.ResourceLocation;

public class FreezingResistanceArmorGear extends ArmorGear {

	public FreezingResistanceArmorGear(Type slotType, Properties properties, ResourceLocation armorSet,
			ResourceLocation modelLocation, ResourceLocation textureLocation,
			ResourceLocation animationFileLocation) {
		super(slotType, properties, armorSet, modelLocation, textureLocation, animationFileLocation);
	}

	public double getFreezingResistance() {
		return 25;
	}
}
