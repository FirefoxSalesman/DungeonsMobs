package net.firefoxsalesman.dungeonsmobs.items.shield.bewlr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import static net.firefoxsalesman.dungeonsmobs.items.shield.ShieldTextures.LOCATION_ROYAL_GUARD_SHIELD_BASE;
import static net.firefoxsalesman.dungeonsmobs.items.shield.ShieldTextures.LOCATION_ROYAL_GUARD_SHIELD_NO_PATTERN;
import static net.minecraft.client.model.geom.ModelLayers.SHIELD;

@OnlyIn(Dist.CLIENT)
public class RoyalGuardShieldBEWLR extends BlockEntityWithoutLevelRenderer {

	private final ShieldModel royalGuardShieldModel;

	public RoyalGuardShieldBEWLR(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
		super(p_172550_, p_172551_);
		royalGuardShieldModel = new ShieldModel(p_172551_.bakeLayer(SHIELD));
	}

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext pDisplayContext, PoseStack matrixStack,
			MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		boolean flag = BlockItem.getBlockEntityData(stack) != null;
		matrixStack.pushPose();
		matrixStack.scale(1.0F, -1.0F, -1.0F);

		Material material = flag ? LOCATION_ROYAL_GUARD_SHIELD_BASE : LOCATION_ROYAL_GUARD_SHIELD_NO_PATTERN;
		VertexConsumer vertexconsumer = material.sprite()
				.wrap(ItemRenderer.getFoilBufferDirect(buffer,
						royalGuardShieldModel.renderType(material.atlasLocation()), true,
						stack.hasFoil()));
		royalGuardShieldModel.handle().render(matrixStack, vertexconsumer, combinedLight, combinedOverlay,
				1.0F, 1.0F, 1.0F, 1.0F);
		if (flag) {
			List<Pair<Holder<BannerPattern>, DyeColor>> list = BannerBlockEntity.createPatterns(
					ShieldItem.getColor(stack), BannerBlockEntity.getItemPatterns(stack));
			BannerRenderer.renderPatterns(matrixStack, buffer, combinedLight, combinedOverlay,
					royalGuardShieldModel.plate(), material, false, list, stack.hasFoil());
		} else {
			royalGuardShieldModel.plate().render(matrixStack, vertexconsumer, combinedLight,
					combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
		}

		matrixStack.popPose();
	}
}
