package net.firefoxsalesman.dungeonsmobs.mod;

import baguchan.enchantwithmob.EnchantWithMob;
import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.mobenchants.BurningMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.ChillingMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.EchoMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.GravityPulseMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.HealsAlliesMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.RadianceMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.RegenerationMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.RushMobEnchant;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraft.resources.ResourceLocation;

public class ModMobEnchants {
	private static final DeferredRegister<MobEnchant> MOB_ENCHANTS_DEFERRED = DeferredRegister
			.create(new ResourceLocation(EnchantWithMob.MODID, "mob_enchant"), DungeonsMobs.MOD_ID);

	public static final RegistryObject<RushMobEnchant> RUSH = MOB_ENCHANTS_DEFERRED.register("rush",
			() -> new RushMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.COMMON, 3)));
	public static final RegistryObject<BurningMobEnchant> BURNING = MOB_ENCHANTS_DEFERRED.register("burning",
			() -> new BurningMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.UNCOMMON, 3)));
	public static final RegistryObject<ChillingMobEnchant> CHILLING = MOB_ENCHANTS_DEFERRED.register("chilling",
			() -> new ChillingMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.UNCOMMON, 3)));
	public static final RegistryObject<EchoMobEnchant> ECHO = MOB_ENCHANTS_DEFERRED.register("echo",
			() -> new EchoMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.VERY_RARE, 3)));
	public static final RegistryObject<GravityPulseMobEnchant> GRAVITY_PULSE = MOB_ENCHANTS_DEFERRED.register(
			"gravity_pulse",
			() -> new GravityPulseMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.RARE, 3)));
	public static final RegistryObject<HealsAlliesMobEnchant> HEALS_ALLIES = MOB_ENCHANTS_DEFERRED.register(
			"heals_allies",
			() -> new HealsAlliesMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.RARE, 3)));
	public static final RegistryObject<RadianceMobEnchant> RADIANCE = MOB_ENCHANTS_DEFERRED.register("radiance",
			() -> new RadianceMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.RARE, 3)));
	public static final RegistryObject<RegenerationMobEnchant> REGENERATION = MOB_ENCHANTS_DEFERRED.register(
			"regeneration",
			() -> new RegenerationMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.COMMON, 3)));

	public static void register(IEventBus eventBus) {
		MOB_ENCHANTS_DEFERRED.register(eventBus);
	}
}
