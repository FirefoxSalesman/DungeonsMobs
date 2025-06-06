package net.firefoxsalesman.dungeonsmobs.lib.capabilities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.lib.capabilities.artifact.ArtifactUsage;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.artifact.AttacherArtifactUsage;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.builtinenchantments.AttacherBuiltInEnchantments;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.builtinenchantments.BuiltInEnchantments;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.AttacherFollower;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.AttacherLeader;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.Follower;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.minionmaster.Leader;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LibCapabilities {

	public static final Capability<BuiltInEnchantments> BUILT_IN_ENCHANTMENTS_CAPABILITY = CapabilityManager
			.get(new CapabilityToken<>() {
			});
	public static final Capability<ArtifactUsage> ARTIFACT_USAGE_CAPABILITY = CapabilityManager
			.get(new CapabilityToken<>() {
			});
	public static final Capability<Follower> FOLLOWER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static final Capability<Leader> LEADER_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
	});

	public static void setupCapabilities() {
		IEventBus forgeBus = MinecraftForge.EVENT_BUS;
		forgeBus.addGenericListener(ItemStack.class, AttacherBuiltInEnchantments::attach);
		forgeBus.addGenericListener(Entity.class, AttacherArtifactUsage::attach);
		forgeBus.addGenericListener(Entity.class, AttacherLeader::attach);
		forgeBus.addGenericListener(Entity.class, AttacherFollower::attach);
	}

	@SubscribeEvent
	public static void registerCaps(RegisterCapabilitiesEvent event) {
		event.register(BuiltInEnchantments.class);
		event.register(ArtifactUsage.class);
		event.register(Leader.class);
		event.register(Follower.class);
	}
}
