package net.firefoxsalesman.dungeonsmobs.client.renderer.redstone;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers.REDSTONE_CUBE;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.firefoxsalesman.dungeonsmobs.client.models.redstone.RedstoneCubeModel;
import net.firefoxsalesman.dungeonsmobs.entity.redstone.RedstoneCubeEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RedstoneCubeRenderer extends MobRenderer<RedstoneCubeEntity, RedstoneCubeModel<RedstoneCubeEntity>> {
	private static final ResourceLocation REDSTONE_CUBE_TEXTURE = new ResourceLocation(MOD_ID,
			"textures/entity/redstone/redstone_cube.png");

	public RedstoneCubeRenderer(EntityRendererProvider.Context renderContext) {
		super(renderContext, new RedstoneCubeModel<>(renderContext.bakeLayer(REDSTONE_CUBE)), 0.25F);
	}

	public void render(RedstoneCubeEntity entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn,
			MultiBufferSource bufferIn, int packedLightIn) {
		this.shadowRadius = 0.5F;
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	protected void setupRotations(RedstoneCubeEntity redstoneCubeEntity, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		super.setupRotations(redstoneCubeEntity, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
		if (redstoneCubeEntity.isRolling()) {
			float rotationPerTick = 360.0F / 20.0F;
			float rotationAmount = ((float) redstoneCubeEntity.tickCount + partialTicks) * -rotationPerTick;
			this.rollCube(matrixStackIn, rotationAmount);
		}
	}

	private void rollCube(PoseStack matrixStackIn, float rotationAmount) {
		Vec3 offset = new Vec3(0.0, 0.5, 0);
		matrixStackIn.translate(offset.x, offset.y, offset.z);
		matrixStackIn.mulPose(Axis.XP.rotationDegrees(rotationAmount)); // Forward roll
		matrixStackIn.translate(-offset.x, -offset.y, -offset.z);
	}

	protected void scale(RedstoneCubeEntity redstoneCubeEntity, PoseStack matrixStackIn, float partialTickTime) {
		matrixStackIn.translate(0.0D, 0.001F, 0.0D);
		float sizeScaleFactor = 0.5F; // Big slimes have a size of 2
		matrixStackIn.scale(sizeScaleFactor, sizeScaleFactor, sizeScaleFactor);
	}

	/**
	 * Returns the location of an entity's texture.
	 */
	@Override
	public ResourceLocation getTextureLocation(RedstoneCubeEntity entity) {
		return REDSTONE_CUBE_TEXTURE;
	}
}
