package net.firefoxsalesman.dungeonsmobs.mobenchants;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.PROXY;
import static net.firefoxsalesman.dungeonsmobs.mobenchants.NewMobEnchantUtils.executeIfPresentWithLevel;

import baguchan.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class RegenerationMobEnchant extends MobEnchant {

	public RegenerationMobEnchant(Properties properties) {
		super(properties);
	}

	@SubscribeEvent
	public static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
		LivingEntity entity = event.getEntity();
		executeIfPresentWithLevel(entity, ModMobEnchants.REGENERATION.get(), (level) -> {
			if (entity.getHealth() < entity.getMaxHealth()
					&& entity.tickCount % getTickCountForLevel(level) == 0) {
				entity.heal(1.0F);
				PROXY.spawnParticles(entity, ParticleTypes.HEART);
			}
		});
	}

	private static int getTickCountForLevel(Integer level) {
		return 62 - level * 12;
	}
}
