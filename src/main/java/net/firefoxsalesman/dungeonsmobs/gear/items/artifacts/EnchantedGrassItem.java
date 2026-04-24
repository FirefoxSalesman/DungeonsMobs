package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts;

import java.util.UUID;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.firefoxsalesman.dungeonsmobs.gear.items.interfaces.SummoningArtifact;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.SoundHelper;
import net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class EnchantedGrassItem extends ArtifactItem implements SummoningArtifact<Sheep> {

	public EnchantedGrassItem(Properties properties) {
		super(properties);
		procOnItemUse = true;
	}

	public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
		return this.procSummoningArtifact(itemUseContext);
	}

	@Override
	public void onSummoned(Player player, Entity summonEntity) {
		if (summonEntity instanceof Sheep sheep) {
			int sheepEnchantment = sheep.getRandom().nextInt(3);
			if (sheepEnchantment == 0) {
				sheep.setColor(DyeColor.RED);
				sheep.addTag("Fire");
			} else if (sheepEnchantment == 1) {
				sheep.setColor(DyeColor.GREEN);
				sheep.addTag("Poison");
			} else {
				sheep.setColor(DyeColor.BLUE);
				sheep.addTag("Speed");
			}
		}
		SoundHelper.playCreatureSound(player, SoundEvents.SHEEP_AMBIENT);
	}

	@Override
	public EntityType<Sheep> getSummonType() {
		return EntityType.SHEEP;
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
