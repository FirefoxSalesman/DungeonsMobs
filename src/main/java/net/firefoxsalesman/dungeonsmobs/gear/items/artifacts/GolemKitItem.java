package net.firefoxsalesman.dungeonsmobs.gear.items.artifacts;

import net.firefoxsalesman.dungeonsmobs.gear.items.interfaces.SummoningArtifact;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.SoundHelper;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

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

}
