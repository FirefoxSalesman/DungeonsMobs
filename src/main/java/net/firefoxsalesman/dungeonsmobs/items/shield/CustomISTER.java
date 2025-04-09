package net.firefoxsalesman.dungeonsmobs.items.shield;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;

import net.firefoxsalesman.dungeonsmobs.client.models.armor.VanguardShieldModel;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import static net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers.VANGUARD_SHIELD;
import static net.firefoxsalesman.dungeonsmobs.items.shield.ShieldTextures.*;
import static net.minecraft.client.model.geom.ModelLayers.SHIELD;

@OnlyIn(Dist.CLIENT)
public class CustomISTER extends BlockEntityWithoutLevelRenderer {

	private final ShieldModel royalGuardShieldModel;
	private final VanguardShieldModel modelVanguardShield;

	public CustomISTER(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
		super(p_172550_, p_172551_);
		modelVanguardShield = new VanguardShieldModel(p_172551_.bakeLayer(VANGUARD_SHIELD));
		royalGuardShieldModel = new ShieldModel(p_172551_.bakeLayer(SHIELD));
	}

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext pDisplayContext, PoseStack matrixStack,
			MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		Item item = stack.getItem();
		if (item instanceof RoyalGuardShieldItem) {
			boolean flag = stack.getTagElement("BlockEntityTag") != null;
			matrixStack.pushPose();
			matrixStack.scale(1.0F, -1.0F, -1.0F);
			Material rendermaterial = flag ? LOCATION_ROYAL_GUARD_SHIELD_BASE
					: LOCATION_ROYAL_GUARD_SHIELD_NO_PATTERN;
			VertexConsumer ivertexbuilder = rendermaterial.sprite()
					.wrap(ItemRenderer.getFoilBufferDirect(buffer,
							this.royalGuardShieldModel
									.renderType(rendermaterial.atlasLocation()),
							true, stack.hasFoil()));
			this.royalGuardShieldModel.handle().render(matrixStack, ivertexbuilder, combinedLight,
					combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
			if (flag) {
				List<Pair<Holder<BannerPattern>, DyeColor>> list = BannerBlockEntity.createPatterns(
						ShieldItem.getColor(stack), BannerBlockEntity.getItemPatterns(stack));
				BannerRenderer.renderPatterns(matrixStack, buffer, combinedLight, combinedOverlay,
						this.royalGuardShieldModel.plate(), rendermaterial, false, list,
						stack.hasFoil());
			} else {
				this.royalGuardShieldModel.plate().render(matrixStack, ivertexbuilder, combinedLight,
						combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
			}

			matrixStack.popPose();
		} else if (item instanceof VanguardShieldItem) {
			matrixStack.pushPose();
			matrixStack.scale(1.0F, -1.0F, -1.0F);
			Material rendermaterial = LOCATION_VANGUARD_SHIELD;
			VertexConsumer ivertexbuilder = rendermaterial.sprite()
					.wrap(ItemRenderer.getFoilBufferDirect(buffer,
							this.royalGuardShieldModel
									.renderType(rendermaterial.atlasLocation()),
							true, stack.hasFoil()));
			// this.modelVanguardShield.getHandle().render(matrixStack, ivertexbuilder,
			// combinedLight, combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
			this.modelVanguardShield.getRoot().render(matrixStack, ivertexbuilder, combinedLight,
					combinedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);

			matrixStack.popPose();
		}
	}
}
