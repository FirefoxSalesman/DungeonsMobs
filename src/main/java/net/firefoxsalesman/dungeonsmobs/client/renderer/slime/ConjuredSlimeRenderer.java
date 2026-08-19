package net.firefoxsalesman.dungeonsmobs.client.renderer.slime;

import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.SlimeRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ConjuredSlimeRenderer extends SlimeRenderer {
	private static final ResourceLocation CONJURED_SLIME_TEXTURE = GeneralHelper.modLoc(
			"textures/entity/slime/conjured_slime.png");

	public ConjuredSlimeRenderer(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn);
	}

	@Override
	public ResourceLocation getTextureLocation(Slime entity) {
		return CONJURED_SLIME_TEXTURE;
	}
}
