package net.firefoxsalesman.dungeonsmobs.datagen;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {

	public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, DungeonsMobs.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		ModEntities.SPAWN_EGGS.getEntries().forEach(item -> {
			getBuilder(item.getId().getPath())
					.parent(new ModelFile.UncheckedModelFile(mcLoc("item/template_spawn_egg")));
		});
	}
}
