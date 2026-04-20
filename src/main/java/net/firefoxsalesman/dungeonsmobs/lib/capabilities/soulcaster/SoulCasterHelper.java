package net.firefoxsalesman.dungeonsmobs.lib.capabilities.soulcaster;

import static net.firefoxsalesman.dungeonsmobs.lib.capabilities.LibCapabilities.SOUL_CASTER_CAPABILITY;
import static net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry.SOUL_CAP;

import com.Polarice3.Goety.utils.SEHelper;
import net.firefoxsalesman.dungeonsmobs.lib.items.interfaces.ISoulConsumer;
import net.firefoxsalesman.dungeonsmobs.lib.network.UpdateSoulsMessage;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.PacketDistributor;

public class SoulCasterHelper {

	public static void addSouls(LivingEntity le, float amount) {
		SoulCaster soulCasterCapability = getSoulCasterCapability(le);
		float newAmount = getSouls(le) + amount + 1;
		soulCasterCapability.setSouls(newAmount, le);
		if (le instanceof ServerPlayer) {
			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) le),
					new UpdateSoulsMessage(getSouls(le)));
		}
	}

	public static void setSouls(LivingEntity le, float amount) {
		SoulCaster soulCasterCapability = getSoulCasterCapability(le);
		float newAmount = amount;
		soulCasterCapability.setSouls(newAmount, le);
		if (le instanceof ServerPlayer)
			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) le),
					new UpdateSoulsMessage(getSouls(le)));

	}

	public static boolean consumeSouls(LivingEntity le, float amount) {
		if (le instanceof Player && ((Player) le).isCreative())
			return true;

		float soulCount = getSouls(le);
		System.out.println("Attempting to consume " + amount + " souls. Current soul count: " + soulCount);
		if (soulCount < amount)
			return false;
		float newAmount = soulCount - amount;
		SoulCasterHelper.setSouls(le, newAmount);
		if (le instanceof ServerPlayer)
			NetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) le),
					new UpdateSoulsMessage(getSouls(le)));

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

	public static float getSouls(Entity le) {
		if (ModList.get().isLoaded("goety") && le instanceof Player) {
			return (float) SEHelper.getSoulAmountInt((Player) le);
		} else {
			SoulCaster soulCasterCapability = getSoulCasterCapability(le);
			return soulCasterCapability.getSouls();
		}
	}

	public static boolean hasSouls(Entity le) {
		return getSouls(le) > 0;
	}

	public static float getSoulCap(LivingEntity le) {
		return (float) le.getAttributeValue(SOUL_CAP.get());
	}
}
