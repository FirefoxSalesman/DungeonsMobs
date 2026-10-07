package net.firefoxsalesman.dungeonsmobs.network.message;

import java.util.Map;
import java.util.function.Supplier;

import net.firefoxsalesman.dungeonslibs.capabilities.builtinenchantments.BuiltInEnchantments;
import net.firefoxsalesman.dungeonslibs.capabilities.builtinenchantments.BuiltInEnchantmentsHelper;
import net.firefoxsalesman.dungeonsmobs.items.GildedItemHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

public class GildedItemMessage {
	private final int entityId;
	private final Map<String, Integer> enchantmentLocations;

	public GildedItemMessage(int entityId, Map<String, Integer> enchantmentLocations) {
		this.entityId = entityId;
		this.enchantmentLocations = enchantmentLocations;
	}

	public static boolean onPacketReceived(GildedItemMessage message,
			Supplier<NetworkEvent.Context> contextSupplier) {
		NetworkEvent.Context ctx = contextSupplier.get();
		if (ctx.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
			ctx.enqueueWork(() -> {
				Entity entity = Minecraft.getInstance().player.level().getEntity(message.entityId);
				if (entity instanceof ItemEntity itemEntity) {
					BuiltInEnchantments cap = BuiltInEnchantmentsHelper
							.getBuiltInEnchantmentsCapability(itemEntity.getItem());
					message.enchantmentLocations.forEach((loc, level) -> {
						cap.addBuiltInEnchantment(GildedItemHelper.GILDED_ITEM_RESOURCELOCATION,
								new EnchantmentInstance(
										ForgeRegistries.ENCHANTMENTS
												.getValue(new ResourceLocation(
														loc)),
										level));
					});
					System.out.println("These are the enchantments in the capibility: "
							+ cap.getAllBuiltInEnchantmentInstancesPerSource());
				}
			});
		}
		return true;
	}

	public void encode(FriendlyByteBuf buffer) {
		buffer.writeInt(entityId);
		buffer.writeMap(enchantmentLocations, (buf, string) -> buf.writeUtf(string),
				(buf, i) -> buffer.writeInt(i));
	}

	public static GildedItemMessage decode(FriendlyByteBuf buffer) {
		int entityId = buffer.readInt();
		Map<String, Integer> enchantmentLocations = buffer.readMap(buf -> buf.readUtf(), buf -> buf.readInt());
		return new GildedItemMessage(entityId, enchantmentLocations);
	}
}
