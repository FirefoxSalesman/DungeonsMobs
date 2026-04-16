package net.firefoxsalesman.dungeonsmobs.lib.capabilities.soulcaster;

import static net.firefoxsalesman.dungeonsmobs.lib.capabilities.LibCapabilities.SOUL_CASTER_CAPABILITY;
import static net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry.SOUL_CAP;

import net.firefoxsalesman.dungeonsmobs.lib.items.interfaces.ISoulConsumer;
import net.firefoxsalesman.dungeonsmobs.lib.network.UpdateSoulsMessage;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class SoulCasterHelper {

	public static void addSouls(LivingEntity le, float amount) {
		SoulCaster soulCasterCapability = getSoulCasterCapability(le);
		float newAmount = soulCasterCapability.getSouls() + amount + 1;
		soulCasterCapability.setSouls(newAmount, le);
		if (le instanceof ServerPlayer) {
			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) le),
					new UpdateSoulsMessage(soulCasterCapability.getSouls()));
		}
	}

	public static void setSouls(LivingEntity le, float amount) {
		SoulCaster soulCasterCapability = getSoulCasterCapability(le);
		float newAmount = amount;
		soulCasterCapability.setSouls(newAmount, le);
		if (le instanceof ServerPlayer) {
			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) le),
					new UpdateSoulsMessage(soulCasterCapability.getSouls()));
		}
	}

	public static boolean consumeSouls(LivingEntity le, float amount) {
		if (le instanceof Player && ((Player) le).isCreative())
			return true;
		SoulCaster soulCasterCapability = getSoulCasterCapability(le);

		if (soulCasterCapability.getSouls() < amount)
			return false;
		float newAmount = soulCasterCapability.getSouls() - amount;
		soulCasterCapability.setSouls(newAmount, le);
		if (le instanceof ServerPlayer) {
			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) le),
					new UpdateSoulsMessage(soulCasterCapability.getSouls()));
		}
		return true;
	}

	public static boolean canConsumeSouls(LivingEntity le, ItemStack itemStack) {
		if (le instanceof Player && ((Player) le).isCreative())
			return true;

		Item item = itemStack.getItem();
		if (item instanceof ISoulConsumer) {
			ISoulConsumer soulConsumer = (ISoulConsumer) item;
			return getSouls(le) > soulConsumer.getActivationCost(itemStack);
		}
		return false;
	}

	public static SoulCaster getSoulCasterCapability(Entity entity) {
		return entity.getCapability(SOUL_CASTER_CAPABILITY).orElse(new SoulCaster());
	}

	public static float getSouls(LivingEntity le) {
		SoulCaster soulCasterCapability = getSoulCasterCapability(le);
		return soulCasterCapability.getSouls();
	}

	public static boolean hasSouls(LivingEntity le) {
		return getSouls(le) > 0;
	}

	public static float getSoulCap(LivingEntity le) {
		return (float) le.getAttributeValue(SOUL_CAP.get());
	}
}
