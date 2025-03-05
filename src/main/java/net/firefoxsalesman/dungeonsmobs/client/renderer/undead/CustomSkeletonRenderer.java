package net.firefoxsalesman.dungeonsmobs.client.renderer.undead;

import net.firefoxsalesman.dungeonsmobs.Dungeonsmobs;
import net.firefoxsalesman.dungeonsmobs.entity.entities.undead.MossySkeleton;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class CustomSkeletonRenderer extends SkeletonRenderer {
	private static final ResourceLocation MOSSY_SKELETON_TEXTURE = new ResourceLocation(Dungeonsmobs.MOD_ID,
			"textures/entity/skeleton/mossy_skeleton.png");

	public CustomSkeletonRenderer(EntityRendererProvider.Context renderContext) {
		super(renderContext);
	}

	public ResourceLocation getTextureLocation(AbstractSkeleton abstractSkeletonEntity) {
		if (abstractSkeletonEntity instanceof MossySkeleton) {
			return MOSSY_SKELETON_TEXTURE;
		} else {
			return super.getTextureLocation(abstractSkeletonEntity);
		}
	}
}
