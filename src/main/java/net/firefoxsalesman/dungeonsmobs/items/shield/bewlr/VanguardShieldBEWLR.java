package net.firefoxsalesman.dungeonsmobs.items.shield.bewlr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.armor.VanguardShieldModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers.VANGUARD_SHIELD;
import static net.firefoxsalesman.dungeonsmobs.items.shield.ShieldTextures.LOCATION_VANGUARD_SHIELD;

@OnlyIn(Dist.CLIENT)
public class VanguardShieldBEWLR extends BlockEntityWithoutLevelRenderer {

	private final VanguardShieldModel modelVanguardShield;

	public VanguardShieldBEWLR(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
		super(p_172550_, p_172551_);
		modelVanguardShield = new VanguardShieldModel(p_172551_.bakeLayer(VANGUARD_SHIELD));
	}

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext pDisplayContext, PoseStack matrixStack,
			MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		matrixStack.pushPose();
		matrixStack.scale(1.0F, -1.0F, -1.0F);
		Material rendermaterial = LOCATION_VANGUARD_SHIELD;
		VertexConsumer ivertexbuilder = rendermaterial.sprite()
				.wrap(ItemRenderer.getFoilBufferDirect(buffer,
						modelVanguardShield.renderType(rendermaterial.atlasLocation()),
						true, stack.hasFoil()));
		modelVanguardShield.getRoot().render(matrixStack, ivertexbuilder, combinedLight, combinedOverlay,
				1.0F, 1.0F, 1.0F, 1.0F);

		matrixStack.popPose();
	}
}
