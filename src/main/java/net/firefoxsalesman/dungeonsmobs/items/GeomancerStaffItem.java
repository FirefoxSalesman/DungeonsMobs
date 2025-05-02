package net.firefoxsalesman.dungeonsmobs.items;

import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.interfaces.IHasInventorySprite;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.firefoxsalesman.dungeonsmobs.lib.network.BreakItemMessage;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.firefoxsalesman.dungeonsmobs.utils.GeomancyHelper;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class GeomancerStaffItem extends ArtifactItem implements IHasInventorySprite {
    public GeomancerStaffItem(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext c) {
        Player playerIn = c.getPlayer();
        ItemStack itemstack = c.getItemStack();

        if (playerIn.getRandom().nextFloat() < 0.25F) {
            GeomancyHelper.summonOffensiveConstruct(playerIn, c.getClickedPos(), ModEntities.GEOMANCER_BOMB.get(), 0, 0, Direction.NORTH);
        } else {
            int[] rowToRemove = Util.getRandom(GeomancyHelper.CONFIG_1_ROWS, playerIn.getRandom());
            GeomancyHelper.summonAreaDenialTrap(playerIn, c.getClickedPos(), ModEntities.GEOMANCER_WALL.get(), rowToRemove);
        }
        itemstack.hurtAndBreak(1, playerIn, (entity) -> NetworkHandler.INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new BreakItemMessage(entity.getId(), itemstack)));
        ArtifactItem.putArtifactOnCooldown(playerIn, itemstack.getItem());
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, itemstack);
    }

    @Override
    public int getCooldownInSeconds() {
        return 20;
    }

    @Override
    public int getDurationInSeconds() {
        return 0;
    }
}
