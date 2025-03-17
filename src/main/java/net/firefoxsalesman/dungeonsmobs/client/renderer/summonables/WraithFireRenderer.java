package net.firefoxsalesman.dungeonsmobs.client.renderer.summonables;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.summonables.WraithFireModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.ProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.WraithFireEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class WraithFireRenderer extends ProjectileRenderer<WraithFireEntity> {
	public WraithFireRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new WraithFireModel());
	}

	@Override
	public void preRender(PoseStack poseStack, WraithFireEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		float scaleFactor = 1.0F;
		poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
	}

	@Override
	protected int getBlockLightLevel(WraithFireEntity p_225624_1_, BlockPos p_225624_2_) {
		return 15;
	}

}
