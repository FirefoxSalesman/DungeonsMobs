package net.firefoxsalesman.dungeonsmobs.client.renderer.summonables;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.summonables.KelpTrapModel;
import net.firefoxsalesman.dungeonsmobs.client.renderer.projectile.ProjectileRenderer;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.KelpTrapEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.cache.object.BakedGeoModel;

public class KelpTrapRenderer extends ProjectileRenderer<KelpTrapEntity> {
	public KelpTrapRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new KelpTrapModel<KelpTrapEntity>());
	}

	@Override
	public void preRender(PoseStack poseStack, KelpTrapEntity animatable, BakedGeoModel model,
			MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick,
			int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		float scaleFactor = 2.0F;
		poseStack.scale(scaleFactor, scaleFactor, scaleFactor);
	}

}
