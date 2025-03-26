package net.firefoxsalesman.dungeonsmobs.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class CorruptedMagicParticle extends TextureSheetParticle {

    protected CorruptedMagicParticle(ClientLevel level, double xCoord, double yCoord, double zCoord,
                                     SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        quadSize *= 1.25F;
        lifetime = 15;
        hasPhysics = false;

        pickSprite(spriteSet);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        if (age++ >= lifetime) {
            remove();
        }
        fadeOut();
    }

    @Override
    protected int getLightColor(float p_189214_1_) {
        return 240;
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
            return new CorruptedMagicParticle(level, x, y, z, sprites, dx, dy, dz);
        }
    }
}
