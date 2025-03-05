package net.firefoxsalesman.dungeonsmobs.entity.renderer.undead;

import net.firefoxsalesman.dungeonsmobs.Dungeonsmobs;
import net.firefoxsalesman.dungeonsmobs.entity.entities.undead.JungleZombie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class CustomZombieRenderer extends ZombieRenderer {
	private static final ResourceLocation JUNGLE_ZOMBIE_TEXUTRE = new ResourceLocation(Dungeonsmobs.MOD_ID,
			"textures/entity/zombie/jungle_zombie.png");

	public CustomZombieRenderer(EntityRendererProvider.Context renderContext) {
		super(renderContext);
	}

	public ResourceLocation getTextureLocation(Zombie zombieEntity) {
		if (zombieEntity instanceof JungleZombie) {
			return JUNGLE_ZOMBIE_TEXUTRE;
		} else {
			return super.getTextureLocation(zombieEntity);
		}
	}
}
