package net.firefoxsalesman.dungeonsmobs.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class DustParticle extends TextureSheetParticle {

    protected DustParticle(ClientLevel level, double xCoord, double yCoord, double zCoord,
                           SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        quadSize *= 2.5F;
        lifetime = 20 + random.nextInt(40);
        hasPhysics = true;

        pickSprite(spriteSet);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        xd = xd * 0.95F;
        yd = yd * 0.75F;
        zd = zd * 0.95F;
        fadeOut();
    }

    private void fadeOut() {
        alpha = (-(1 / (float) lifetime) * age + 1);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Factory(SpriteSet spriteSet) {
            sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new DustParticle(level, x, y, z, sprites, dx, dy, dz);
        }
    }
}
