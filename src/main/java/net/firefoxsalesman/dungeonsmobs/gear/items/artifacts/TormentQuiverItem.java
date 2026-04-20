package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.firefoxsalesman.dungeonsmobs.gear.capabilities.combo.Combo;
import net.firefoxsalesman.dungeonsmobs.gear.capabilities.combo.ComboHelper;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.soulcaster.SoulCasterHelper;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.firefoxsalesman.dungeonsmobs.lib.items.interfaces.ISoulConsumer;
import net.firefoxsalesman.dungeonsmobs.lib.network.BreakItemMessage;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

import java.util.UUID;

import static net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry.SOUL_GATHERING;

public class TormentQuiverItem extends ArtifactItem implements ISoulConsumer {
	public static final String TORMENT_ARROW = "TormentArrow";

	public TormentQuiverItem(Properties properties) {
		super(properties);
	}

	public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext c) {
		Player playerIn = c.getPlayer();
		ItemStack itemStack = c.getItemStack();

		if (SoulCasterHelper.consumeSouls(playerIn, this.getActivationCost(itemStack))) {

			Combo comboCap = ComboHelper.getComboCapability(playerIn);

			comboCap.setTormentArrowCount(3);

			itemStack.hurtAndBreak(1, playerIn,
					(entity) -> NetworkHandler.INSTANCE.send(
							PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity),
							new BreakItemMessage(entity.getId(), itemStack)));

			ArtifactItem.putArtifactOnCooldown(playerIn, itemStack.getItem());
		}
		return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemStack);
	}

	@Override
	public int getCooldownInSeconds() {
		return 1;
	}

	@Override
	public int getDurationInSeconds() {
		return 0;
	}

	@Override
	public float getActivationCost(ItemStack stack) {
		return 10;
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
