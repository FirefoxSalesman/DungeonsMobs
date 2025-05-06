package net.firefoxsalesman.dungeonsmobs.client.models.geom;

import net.firefoxsalesman.dungeonsmobs.client.models.FungusSackModel;
import net.firefoxsalesman.dungeonsmobs.client.models.armor.VanguardShieldModel;
import net.firefoxsalesman.dungeonsmobs.client.models.ender.EndersentModel;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.IllagerBipedModel;
import net.firefoxsalesman.dungeonsmobs.client.models.projectile.SnarelingGlobModel;
import net.firefoxsalesman.dungeonsmobs.client.models.undead.SunkenSkeletonModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModModelLayers {
	public static ModelLayerLocation SUNKEN_SKELETON = makeModelLayerLocation("sunken_skeleton_model");
	public static ModelLayerLocation SNARELING_GLOB = makeModelLayerLocation("snareling_glob_model");

	public static ModelLayerLocation BIPED_ILLAGER_MODEL = makeModelLayerLocation("biped_illager_model");

	public static ModelLayerLocation VANGUARD_SHIELD = makeModelLayerLocation("vanguard_shield_model");
	public static ModelLayerLocation FUNGUS_SACK = makeModelLayerLocation("fungus_sack_model");
	public static final ModelLayerLocation ENDERSENT_BODY = makeModelLayerLocation("endersent");
	// public static final ModelLayerLocation ENDERSENT_BODY = new
	// ModelLayerLocation(
	// new ResourceLocation(MOD_ID, "endersent"), "main");

	private static ModelLayerLocation makeModelLayerLocation(String name) {
		return new ModelLayerLocation(new ResourceLocation(MOD_ID + name),
				name);
	}

	@SubscribeEvent
	public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(SUNKEN_SKELETON, SunkenSkeletonModel::createBodyLayer);
		event.registerLayerDefinition(FUNGUS_SACK, FungusSackModel::createBackpackLayer);
		event.registerLayerDefinition(SNARELING_GLOB, SnarelingGlobModel::createLayer);
		event.registerLayerDefinition(BIPED_ILLAGER_MODEL, IllagerBipedModel::createBodyLayer);
		event.registerLayerDefinition(VANGUARD_SHIELD, VanguardShieldModel::createLayer);
		event.registerLayerDefinition(ENDERSENT_BODY, EndersentModel::createBodyLayer);
	}
}
