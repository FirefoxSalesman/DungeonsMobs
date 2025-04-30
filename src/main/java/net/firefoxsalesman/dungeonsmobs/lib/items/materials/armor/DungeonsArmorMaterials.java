package net.firefoxsalesman.dungeonsmobs.lib.items.materials.armor;

import net.firefoxsalesman.dungeonsmobs.lib.data.util.DefaultsCodecJsonDataManager;
import net.firefoxsalesman.dungeonsmobs.lib.network.materials.ArmorMaterialSyncPacket;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static net.minecraft.world.item.ArmorMaterials.*;

public class DungeonsArmorMaterials {

	public static final DefaultsCodecJsonDataManager<ArmorMaterial> ARMOR_MATERIALS = new DefaultsCodecJsonDataManager<>(
			"material/armor", DungeonsArmorMaterial.CODEC);
	public static final Map<ArmorMaterial, ArmorMaterialBaseType> baseArmorMaterials = new HashMap<>();

	public static void setupVanillaMaterials() {
		addDefaultArmorMaterial(LEATHER, ArmorMaterialBaseType.LEATHER,
				new ResourceLocation("minecraft:leather"));
		addDefaultArmorMaterial(CHAIN, ArmorMaterialBaseType.METAL,
				new ResourceLocation("minecraft:chainmail"));
		addDefaultArmorMaterial(IRON, ArmorMaterialBaseType.METAL, new ResourceLocation("minecraft:iron"));
		addDefaultArmorMaterial(GOLD, ArmorMaterialBaseType.METAL, new ResourceLocation("minecraft:gold"));
		addDefaultArmorMaterial(DIAMOND, ArmorMaterialBaseType.GEM, new ResourceLocation("minecraft:diamond"));
		addDefaultArmorMaterial(TURTLE, ArmorMaterialBaseType.LEATHER,
				new ResourceLocation("minecraft:turtle"));
		addDefaultArmorMaterial(NETHERITE, ArmorMaterialBaseType.METAL,
				new ResourceLocation("minecraft:netherite"));
	}

	public static void addDefaultArmorMaterial(ArmorMaterials material, ArmorMaterialBaseType baseType,
			ResourceLocation resourceLocation) {
		ARMOR_MATERIALS.addDefault(resourceLocation, material);
		baseArmorMaterials.put(material, baseType);
	}

	public static ArmorMaterial getArmorMaterial(ResourceLocation resourceLocation) {
		return ARMOR_MATERIALS.getData().getOrDefault(resourceLocation, IRON);
	}

	public static boolean ArmorMaterialExists(ResourceLocation resourceLocation) {
		return ARMOR_MATERIALS.getData().containsKey(resourceLocation);
	}

	public static Collection<ResourceLocation> armorMaterialsKeys() {
		return ARMOR_MATERIALS.getData().keySet();
	}

	public static Collection<ArmorMaterial> getArmorMaterials(ArmorMaterialBaseType baseType) {
		return ARMOR_MATERIALS.getData().values().stream().filter(iArmorMaterial -> {
			if (iArmorMaterial instanceof DungeonsArmorMaterial) {
				return ((DungeonsArmorMaterial) iArmorMaterial).getBaseType() == baseType;
			} else if (baseArmorMaterials.containsKey(iArmorMaterial)) {
				return baseArmorMaterials.get(iArmorMaterial) == baseType;
			} else {
				return ArmorMaterialBaseType.UNKNOWN == baseType;
			}
		}).collect(Collectors.toList());
	}

	public static ArmorMaterialSyncPacket toPacket(Map<ResourceLocation, ArmorMaterial> map) {
		return new ArmorMaterialSyncPacket(map);
	}

	public static void subscribe() {
		ARMOR_MATERIALS.subscribeAsSyncable(NetworkHandler.INSTANCE, DungeonsArmorMaterials::toPacket);
	}
}
