package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts.beacon;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.firefoxsalesman.dungeonsmobs.lib.capabilities.soulcaster.SoulCasterHelper;
import net.firefoxsalesman.dungeonsmobs.lib.items.interfaces.ISoulConsumer;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

import static net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry.SOUL_GATHERING;

public abstract class SoulBeaconItem extends AbstractBeaconItem implements ISoulConsumer {

	public SoulBeaconItem(Properties properties) {
		super(properties);
	}

	public boolean canFire(Player playerEntity, ItemStack stack) {
		return SoulCasterHelper.canConsumeSouls(playerEntity, stack);
	}

	@Override
	protected boolean consumeTick(Player playerEntity, ItemStack itemStack) {
		return SoulCasterHelper.consumeSouls(playerEntity, this.getActivationCost(itemStack));
	}

	@Override
	public float getActivationCost(ItemStack stack) {
		return AbstractBeaconItem.SOUL_COST_PER_TICK;
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(int slotIndex) {
		return getAttributeModifiersForSlot(getUUIDForSlot(slotIndex));
	}

	private ImmutableMultimap<Attribute, AttributeModifier> getAttributeModifiersForSlot(UUID slot_uuid) {
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(SOUL_GATHERING.get(), new AttributeModifier(slot_uuid, "Artifact modifier", 1,
				AttributeModifier.Operation.ADDITION));
		return builder.build();
	}
}
