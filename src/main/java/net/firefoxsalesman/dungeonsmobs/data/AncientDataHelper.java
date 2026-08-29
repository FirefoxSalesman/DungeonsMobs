package net.firefoxsalesman.dungeonsmobs.data;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import baguchan.enchantwithmob.api.IEnchantCap;
import baguchan.enchantwithmob.capability.MobEnchantCapability;
import baguchan.enchantwithmob.registry.MobEnchants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.firefoxsalesman.dungeonslibs.data.util.MergeableCodecDataManager;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AncientDataHelper {

	private static final MergeableCodecDataManager<MobAncientData, MobAncientData> MOB_ANCIENT_DATA = new MergeableCodecDataManager<>(
			"ancient/mob_ancient_data", MobAncientData.CODEC, AncientDataHelper::mobMerger);
	private static final MergeableCodecDataManager<MobEnchantmentAncientData, MobEnchantmentAncientData> MOB_ENCHANTMENT_ANCIENT_DATA = new MergeableCodecDataManager<>(
			"ancient/mob_enchantment_ancient_data", MobEnchantmentAncientData.CODEC,
			AncientDataHelper::mobEnchantmentMerger);

	private static MobAncientData mobMerger(List<MobAncientData> raws) {
		List<String> adjectives = new ObjectArrayList<>();
		List<String> nouns = new ObjectArrayList<>();
		List<ResourceLocation> minions = new ObjectArrayList<>();
		List<UniqueAncientData> uniques = new ObjectArrayList<>();
		raws.forEach(raw -> {
			adjectives.addAll(raw.getAdjectives());
			nouns.addAll(raw.getNouns());
			minions.addAll(raw.getMinions());
			uniques.addAll(raw.getUniques());
		});
		return new MobAncientData(adjectives, nouns, minions, uniques);
	}

	private static MobEnchantmentAncientData mobEnchantmentMerger(List<MobEnchantmentAncientData> raws) {
		List<String> adjectives = new ObjectArrayList<>();
		List<String> nouns = new ObjectArrayList<>();
		raws.forEach(raw -> {
			adjectives.addAll(raw.getAdjectives());
			nouns.addAll(raw.getNouns());
		});
		return new MobEnchantmentAncientData(adjectives, nouns);
	}

	private static MobAncientData getMobAncientData(ResourceLocation mobResourceLocation) {

		return MOB_ANCIENT_DATA.getData().getOrDefault(mobResourceLocation, MobAncientData.DEFAULT);
	}

	private static MobEnchantmentAncientData getMobEnchantmentAncientData(
			ResourceLocation mobEnchantmentResourceLocation) {
		return MOB_ENCHANTMENT_ANCIENT_DATA.getData().getOrDefault(mobEnchantmentResourceLocation,
				MobEnchantmentAncientData.DEFAULT);
	}

	private static Optional<String> doUniques(LivingEntity entity, MobAncientData mobAncientData) {
		List<UniqueAncientData> uniques = mobAncientData.getUniques();
		UniqueAncientData unique = uniques.get(entity.getRandom().nextInt(0, uniques.size()));
		unique.getMobEnchantments().forEach(enchant -> {
			MobEnchantCapability enchantCap = entity instanceof IEnchantCap enchantedEntity
					? enchantedEntity.getEnchantCap()
					: new MobEnchantCapability();
			enchantCap.addMobEnchant(entity, MobEnchants.getRegistry().get().getValue(enchant), 1, true);
		});
		return Optional.of(unique.getName());
	}

	public static String getAncientName(LivingEntity entity) {
		Set<String> adjectives = new HashSet<>();
		Set<String> nouns = new HashSet<>();
		MobEnchantCapability enchantCap = entity instanceof IEnchantCap enchantedEntity
				? enchantedEntity.getEnchantCap()
				: new MobEnchantCapability();
		enchantCap.getMobEnchants().forEach(mobEnchantment -> {
			MobEnchantmentAncientData mobEnchantmentAncientData = getMobEnchantmentAncientData(
					MobEnchants.getRegistry().get().getKey(mobEnchantment.getMobEnchant()));
			adjectives.addAll(mobEnchantmentAncientData.getAdjectives());
			nouns.addAll(mobEnchantmentAncientData.getNouns());
		});
		MobAncientData mobAncientData = getMobAncientData(
				ForgeRegistries.ENTITY_TYPES.getKey(entity.getType()));
		Optional<String> uniqueName = doUniques(entity, mobAncientData);
		adjectives.addAll(mobAncientData.getAdjectives());
		nouns.addAll(mobAncientData.getNouns());
		return uniqueName.isPresent() ? uniqueName.get()
				: new ObjectArrayList<>(adjectives).get(entity.getRandom().nextInt(adjectives.size()))
						+ " " + new ObjectArrayList<>(nouns)
								.get(entity.getRandom().nextInt(nouns.size()));
	}

	@SubscribeEvent
	public static void onAddReloadListeners(AddReloadListenerEvent event) {
		event.addListener(MOB_ANCIENT_DATA);
		event.addListener(MOB_ENCHANTMENT_ANCIENT_DATA);
	}
}
