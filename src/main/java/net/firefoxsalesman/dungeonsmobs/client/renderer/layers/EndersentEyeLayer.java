package net.firefoxsalesman.dungeonsmobs.client.renderer.layers;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.client.models.ender.EndersentModel;
import net.firefoxsalesman.dungeonsmobs.entity.ender.EndersentEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EndersentEyeLayer<T extends EndersentEntity> extends EyesLayer<T, EndersentModel<T>> {
    private static final RenderType ENDERMAN_EYES = RenderType.eyes(new ResourceLocation(DungeonsMobs.MOD_ID, "textures/entity/ender/endersent_eyes.png"));

   public EndersentEyeLayer(RenderLayerParent<T, EndersentModel<T>> parent) {
      super(parent);
   }

   public RenderType renderType() {
      return ENDERMAN_EYES;
   }
}
