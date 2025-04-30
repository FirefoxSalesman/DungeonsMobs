package net.firefoxsalesman.dungeonsmobs.lib.items;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static net.firefoxsalesman.dungeonsmobs.lib.utils.ResourceLocationHelper.modLoc;

public class ItemTagWrappers {

    public static final TagKey<Item> CURIOS_ARTIFACTS = tag(new ResourceLocation("curios", "artifact"));
    public static final TagKey<Item> ARTIFACT_REPAIR_ITEMS = tag(modLoc("artifact_repair_items"));

        private static TagKey<Item> tag(ResourceLocation resourceLocation) {
        return TagKey.create(Registries.ITEM, resourceLocation);
    }

    public static void init() {
    }
}
