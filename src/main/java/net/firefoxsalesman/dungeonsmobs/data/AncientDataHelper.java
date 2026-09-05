package net.firefoxsalesman.dungeonsmobs.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import baguchan.enchantwithmob.api.IEnchantCap;
import baguchan.enchantwithmob.capability.MobEnchantCapability;
import baguchan.enchantwithmob.mobenchant.MobEnchant;
import baguchan.enchantwithmob.registry.MobEnchants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.firefoxsalesman.dungeonslibs.attribute.AttributeRegistry;
import net.firefoxsalesman.dungeonslibs.data.util.MergeableCodecDataManager;
import net.firefoxsalesman.dungeonslibs.summon.SummonHelper;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

	private static void addEnchant(LivingEntity entity, ResourceLocation enchant) {
		MobEnchantCapability enchantCap = entity instanceof IEnchantCap enchantedEntity
				? enchantedEntity.getEnchantCap()
				: new MobEnchantCapability();
		MobEnchant enchantment = MobEnchants.getRegistry().get().getValue(enchant);
		enchantCap.addMobEnchant(entity, enchantment, enchantment.getMaxLevel());

	}

	private static <T> T getRandomElement(RandomSource random, Collection<T> collection) {
		return (T) collection.toArray()[random.nextInt(0, collection.size())];
	}

	private static void ancientHelper(LivingEntity entity, MobAncientData mobAncientData,
			List<ResourceLocation> mobEnchants, int minionCount,
			EntityType<?> minion, List<ResourceLocation> minionEnchants) {
		RandomSource random = entity.getRandom();
		MobEnchantCapability enchantCap = entity instanceof IEnchantCap enchantedEntity
				? enchantedEntity.getEnchantCap()
				: new MobEnchantCapability();
		enchantCap.setEnchantType(entity, MobEnchantCapability.EnchantType.ANCIENT);
		mobEnchants.forEach(enchant -> addEnchant(entity, enchant));
		AttributeInstance attributeInstance = entity.getAttribute(AttributeRegistry.SUMMON_CAP.get());
		if (attributeInstance != null) {
			attributeInstance.addTransientModifier(new AttributeModifier(
					UUID.fromString("3960f897-17c1-4169-b516-07d2b03d41dd"),
					"AncientMob", minionCount,
					AttributeModifier.Operation.ADDITION));
		}
		for (int i = 0; i < minionCount; i++) {
			BlockPos pos = entity.blockPosition().offset(random.nextInt(5), 0, random.nextInt(5));
			Entity summon = SummonHelper.summonEntity(entity, pos, minion);
			if (summon != null && summon instanceof LivingEntity) {
				minionEnchants.forEach(enchant -> addEnchant((LivingEntity) summon, enchant));
				if (summon instanceof Mob mob) {
					mob.finalizeSpawn((ServerLevel) mob.level(),
							mob.level().getCurrentDifficultyAt(pos),
							MobSpawnType.MOB_SUMMONED, null, null);
				}
			}
		}

	}

	private static void doNonUniques(LivingEntity entity, MobAncientData mobAncientData) {
		RandomSource random = entity.getRandom();
		Collection<ResourceLocation> enchants = MobEnchants.getRegistry().get().getKeys();
		List<ResourceLocation> mobEnchants = new ArrayList<>();
		for (int i = 0; i < 3; i++)
			mobEnchants.add(getRandomElement(random, enchants));
		ancientHelper(entity, mobAncientData, mobEnchants, 7, ForgeRegistries.ENTITY_TYPES
				.getValue(getRandomElement(random, mobAncientData.getMinions())),
				List.of(getRandomElement(random, enchants)));
	}

	private static Optional<String> doUniques(LivingEntity entity, MobAncientData mobAncientData) {
		List<UniqueAncientData> uniques = mobAncientData.getUniques();
		if (uniques.size() > 0) {
			RandomSource random = entity.getRandom();
			UniqueAncientData unique = getRandomElement(random, uniques);
			ancientHelper(entity, mobAncientData, unique.getMobEnchantments(), unique.getMinionCount(),
					ForgeRegistries.ENTITY_TYPES.getValue(unique.getMinion()),
					unique.getMinionMobEnchantments());
			return Optional.of(unique.getName());

		} else
			doNonUniques(entity, mobAncientData);
		return Optional.empty();
	}

	public static String getAncientName(LivingEntity entity, boolean unique) {
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
		Optional<String> uniqueName = unique ? doUniques(entity, mobAncientData) : Optional.empty();
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
