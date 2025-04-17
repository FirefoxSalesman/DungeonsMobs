package net.firefoxsalesman.dungeonsmobs.client.renderer.jungle;

import com.mojang.blaze3d.vertex.PoseStack;

import net.firefoxsalesman.dungeonsmobs.client.models.jungle.AbstractVineModel;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.AbstractVineEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class AbstractVineRenderer<M extends AbstractVineModel> extends GeoEntityRenderer<AbstractVineEntity> {

	public AbstractVineRenderer(EntityRendererProvider.Context renderManager, M model) {
		super(renderManager, model);
	}

	public boolean isShaking(AbstractVineEntity p_230495_1_) {
		return p_230495_1_.isInWrongHabitat();
	}

	@Override
	protected void applyRotations(AbstractVineEntity entityLiving, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		if (isShaking(entityLiving)) {
			rotationYaw += (float) (Math.cos((double) entityLiving.tickCount * 3.25D) * Math.PI
					* (double) 0.4F);
		}
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);
	}

	@Override
	protected int getBlockLightLevel(AbstractVineEntity vine, BlockPos pos) {
		return vine.isOnFire() ? 15
				: vine.level().getBrightness(LightLayer.BLOCK,
						vine.getParts()[0].blockPosition());
	}

	@Override
	protected float getDeathMaxRotation(AbstractVineEntity entityLivingBaseIn) {
		return 0;
	}

	public ResourceLocation getTextureLocation(AbstractVineEntity entity) {
		return super.getTextureLocation(entity);
	}
}
