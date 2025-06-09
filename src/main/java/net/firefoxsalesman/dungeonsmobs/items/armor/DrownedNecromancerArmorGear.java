package net.firefoxsalesman.dungeonsmobs.items.armor;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import net.firefoxsalesman.dungeonsmobs.client.renderer.armor.DrownedNecromancerArmorGearRenderer;
import net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig.ArmorGear;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DrownedNecromancerArmorGear extends ArmorGear {

	public DrownedNecromancerArmorGear(Type slotType, Properties properties, ResourceLocation armorSet,
			ResourceLocation modelLocation, ResourceLocation textureLocation,
			ResourceLocation animationFileLocation) {
		super(slotType, properties, armorSet, modelLocation, textureLocation, animationFileLocation);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			private GeoArmorRenderer<?> renderer;

			@Override
			public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity,
					ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
				if (this.renderer == null)
					this.renderer = new DrownedNecromancerArmorGearRenderer();

				// This prepares our GeoArmorRenderer for the current render frame.
				// These parameters may be null however, so we don't do anything further with
				// them
				this.renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);

				return this.renderer;
			}
		});
	}
}
