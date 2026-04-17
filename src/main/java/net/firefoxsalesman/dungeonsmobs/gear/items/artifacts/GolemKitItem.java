package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.firefoxsalesman.dungeonsmobs.gear.items.interfaces.SummoningArtifact;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.SoundHelper;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import static net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry.SUMMON_CAP;

public class GolemKitItem extends ArtifactItem implements SummoningArtifact<IronGolem> {
	public GolemKitItem(Properties itemProperties) {
		super(itemProperties);
		procOnItemUse = true;
	}

	public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
		return this.procSummoningArtifact(itemUseContext);
	}

	@Override
	public void onSummoned(Player player, Entity summonEntity) {
		if (summonEntity instanceof IronGolem ironGolem) {
			ironGolem.setPlayerCreated(true);
		}
		SoundHelper.playCreatureSound(player, SoundEvents.IRON_GOLEM_REPAIR);
	}

	@Override
	public EntityType<IronGolem> getSummonType() {
		return EntityType.IRON_GOLEM;
	}

	@Override
	public int getCooldownInSeconds() {
		return 30;
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(int slotIndex) {
		UUID slot_uuid = getUUIDForSlot(slotIndex);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(SUMMON_CAP.get(), new AttributeModifier(slot_uuid, "Artifact modifier", 3,
				AttributeModifier.Operation.ADDITION));
		return builder.build();
	}
}
