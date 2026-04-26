package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.firefoxsalesman.dungeonsmobs.gear.entities.SoulWizardEntity;
import net.firefoxsalesman.dungeonsmobs.gear.items.interfaces.SummoningArtifact;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EntityTypeInit;
import net.firefoxsalesman.dungeonsmobs.gear.registry.SoundEventInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.SoundHelper;
import net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SoulLanternItem extends ArtifactItem implements SummoningArtifact<SoulWizardEntity> {
	public SoulLanternItem(Properties properties) {
		super(properties);
		procOnItemUse = true;
	}

	public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
		return this.procSummoningArtifact(itemUseContext);
	}

	@Override
	public void onSummoned(Player player, Entity summonEntity) {
		SoundHelper.playCreatureSound(player, SoundEventInit.SOUL_WIZARD_APPEAR.get());
	}

	@Override
	public EntityType<SoulWizardEntity> getSummonType() {
		return EntityTypeInit.SOUL_WIZARD.get();
	}

	@Override
	public int getCooldownInSeconds() {
		return 30;
	}

	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(int slotIndex) {
		UUID slot_uuid = getUUIDForSlot(slotIndex);
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(AttributeRegistry.SUMMON_CAP.get(), new AttributeModifier(slot_uuid, "Artifact modifier", 3,
				AttributeModifier.Operation.ADDITION));
		return builder.build();
	}
}
