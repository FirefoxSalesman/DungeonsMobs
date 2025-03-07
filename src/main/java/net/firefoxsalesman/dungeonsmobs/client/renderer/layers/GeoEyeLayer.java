package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

@OnlyIn(Dist.CLIENT)
public class GeoEyeLayer<T extends LivingEntity & GeoEntity> extends AutoGlowingGeoLayer<T> {

	public ResourceLocation textureLocation;

	public GeoEyeLayer(GeoRenderer<T> endermanReplacementRenderer, ResourceLocation textureLocation) {
		super(endermanReplacementRenderer);
		this.textureLocation = textureLocation;
	}

	@Override
	protected ResourceLocation getTextureResource(T animatable) {
		return textureLocation;
	}

	@Override
	protected RenderType getRenderType(T animatable) {
		return RenderType.eyes(textureLocation);
	}
}
