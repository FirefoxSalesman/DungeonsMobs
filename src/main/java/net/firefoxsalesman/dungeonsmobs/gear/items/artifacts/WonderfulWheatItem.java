package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.firefoxsalesman.dungeonsmobs.gear.items.interfaces.SummoningArtifact;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.SoundHelper;
import net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.firefoxsalesman.dungeonsmobs.mixin.AbstractHorseAccessor;
import net.firefoxsalesman.dungeonsmobs.mixin.LlamaInvoker;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class WonderfulWheatItem extends ArtifactItem implements SummoningArtifact<Llama> {
	public WonderfulWheatItem(Properties properties) {
		super(properties);
		procOnItemUse = true;
	}

	public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
		return this.procSummoningArtifact(itemUseContext);
	}

	@Override
	public void onSummoned(Player player, Entity summonEntity) {
		if (summonEntity instanceof Llama llama) {
			llama.tameWithName(player);
			llama.setVariant(Llama.Variant.BROWN);
			((LlamaInvoker) llama).invokeSetStrength(5);
			AttributeInstance maxHealth = llama.getAttribute(Attributes.MAX_HEALTH);
			if (maxHealth != null)
				maxHealth.setBaseValue(30.0D);
			((AbstractHorseAccessor) llama).getInventory().setItem(1,
					new ItemStack(Items.RED_CARPET.asItem()));
		}
		SoundHelper.playCreatureSound(player, SoundEvents.LLAMA_AMBIENT);
	}

	@Override
	public EntityType<Llama> getSummonType() {
		return EntityType.LLAMA;
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(int slotIndex) {
		UUID slot_uuid = getUUIDForSlot(slotIndex);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(AttributeRegistry.SUMMON_CAP.get(), new AttributeModifier(slot_uuid, "Artifact modifier", 3,
				AttributeModifier.Operation.ADDITION));
		return builder.build();
	}
}
