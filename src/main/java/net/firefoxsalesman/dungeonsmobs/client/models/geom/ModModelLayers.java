package net.firefoxsalesman.dungeonsmobs.client.models.geom;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.client.models.FungusSackModel;
import net.firefoxsalesman.dungeonsmobs.client.models.armor.VanguardShieldModel;
import net.firefoxsalesman.dungeonsmobs.client.models.blaze.WildfireModel;
import net.firefoxsalesman.dungeonsmobs.client.models.ender.EndersentModel;
import net.firefoxsalesman.dungeonsmobs.client.models.ender.EyelessEndersentModel;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.ArmoredPillagerModel;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.ArmoredVindicatorModel;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.GeomancerModel;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.IceologerModel;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.IllagerBipedModel;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.MageModel;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.MountaineerModel;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.RoyalGuardModel;
import net.firefoxsalesman.dungeonsmobs.client.models.illager.VindicatorChefModel;
import net.firefoxsalesman.dungeonsmobs.client.models.ocean.DrownedNecromancerModel;
import net.firefoxsalesman.dungeonsmobs.client.models.projectile.SnarelingGlobModel;
import net.firefoxsalesman.dungeonsmobs.client.models.redstone.RedstoneCubeModel;
import net.firefoxsalesman.dungeonsmobs.client.models.undead.NecromancerModel;
import net.firefoxsalesman.dungeonsmobs.client.models.undead.SkeletonVanguardModel;
import net.firefoxsalesman.dungeonsmobs.client.models.undead.SunkenSkeletonModel;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModModelLayers {
	public static ModelLayerLocation REDSTONE_CUBE = makeModelLayerLocation("redstone_cube_model");
	public static ModelLayerLocation SUNKEN_SKELETON = makeModelLayerLocation("sunken_skeleton_model");
	public static ModelLayerLocation SNARELING_GLOB = makeModelLayerLocation("snareling_glob_model");

	public static ModelLayerLocation BIPED_ILLAGER_MODEL = makeModelLayerLocation("biped_illager_model");

	public static ModelLayerLocation VANGUARD_SHIELD = makeModelLayerLocation("vanguard_shield_model");
	public static ModelLayerLocation FUNGUS_SACK = makeModelLayerLocation("fungus_sack_model");
	public static final ModelLayerLocation ENDERSENT_BODY = makeModelLayerLocation("endersent");
	public static final ModelLayerLocation EYELESS_ENDERSENT_BODY = makeModelLayerLocation("eyeless_endersent");
	public static final ModelLayerLocation WILDFIRE_BODY = makeModelLayerLocation("wildfire");
	public static final ModelLayerLocation VINDICATOR_CHEF_BODY = makeModelLayerLocation("vindicator_chef_model");
	public static final ModelLayerLocation ICEOLOGER_BODY = makeModelLayerLocation("iceologer_model");
	public static final ModelLayerLocation MAGE_BODY = makeModelLayerLocation("mage_model");
	public static final ModelLayerLocation MOUNTAINEER_BODY = makeModelLayerLocation("mountaineer_model");
	public static final ModelLayerLocation ROYAL_GUARD_BODY = makeModelLayerLocation("royal_guard_model");
	public static final ModelLayerLocation GEOMANCER_BODY = makeModelLayerLocation("geomancer_model");
	public static final ModelLayerLocation NECROMANCER_BODY = makeModelLayerLocation("necromancer_model");
	public static final ModelLayerLocation DROWNED_NECROMANCER_BODY = makeModelLayerLocation(
			"drowned_necromancer_model");
	public static final ModelLayerLocation SKELETON_VANGUARD_BODY = makeModelLayerLocation(
			"skeleton_vanguard_model");
	public static final ModelLayerLocation ARMORED_PILLAGER_BODY = makeModelLayerLocation(
			"armored_pillager_model");
	public static final ModelLayerLocation ARMORED_VINDICATOR_BODY = makeModelLayerLocation(
			"armored_vindicator_model");

	private static ModelLayerLocation makeModelLayerLocation(String name) {
		return new ModelLayerLocation(GeneralHelper.modLoc(name),
				"main");
	}

	@SubscribeEvent
	public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(REDSTONE_CUBE, RedstoneCubeModel::createBodyLayer);
		event.registerLayerDefinition(SUNKEN_SKELETON, SunkenSkeletonModel::createBodyLayer);
		event.registerLayerDefinition(FUNGUS_SACK, FungusSackModel::createBackpackLayer);
		event.registerLayerDefinition(SNARELING_GLOB, SnarelingGlobModel::createLayer);
		event.registerLayerDefinition(BIPED_ILLAGER_MODEL, IllagerBipedModel::createBodyLayer);
		event.registerLayerDefinition(VANGUARD_SHIELD, VanguardShieldModel::createLayer);
		event.registerLayerDefinition(DROWNED_NECROMANCER_BODY, DrownedNecromancerModel::createBodyLayer);
		event.registerLayerDefinition(NECROMANCER_BODY, NecromancerModel::createBodyLayer);
		event.registerLayerDefinition(ARMORED_PILLAGER_BODY, ArmoredPillagerModel::createBodyLayer);
		event.registerLayerDefinition(ARMORED_VINDICATOR_BODY, ArmoredVindicatorModel::createBodyLayer);
		event.registerLayerDefinition(ROYAL_GUARD_BODY, RoyalGuardModel::createBodyLayer);
		event.registerLayerDefinition(MOUNTAINEER_BODY, MountaineerModel::createBodyLayer);
		event.registerLayerDefinition(GEOMANCER_BODY, GeomancerModel::createBodyLayer);
		event.registerLayerDefinition(VINDICATOR_CHEF_BODY, VindicatorChefModel::createBodyLayer);
		event.registerLayerDefinition(MAGE_BODY, MageModel::createBodyLayer);
		event.registerLayerDefinition(ICEOLOGER_BODY, IceologerModel::createBodyLayer);
		event.registerLayerDefinition(EYELESS_ENDERSENT_BODY, EyelessEndersentModel::createBodyLayer);
		event.registerLayerDefinition(ENDERSENT_BODY, EndersentModel::createBodyLayer);
		event.registerLayerDefinition(WILDFIRE_BODY, WildfireModel::createBodyLayer);
		event.registerLayerDefinition(SKELETON_VANGUARD_BODY, SkeletonVanguardModel::createBodyLayer);
	}
}
