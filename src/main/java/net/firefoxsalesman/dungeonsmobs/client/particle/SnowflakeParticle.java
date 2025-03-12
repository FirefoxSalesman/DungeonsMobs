package net.firefoxsalesman.dungeonsmobs.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class SnowflakeParticle extends TextureSheetParticle {

    protected SnowflakeParticle(ClientLevel level, double xCoord, double yCoord, double zCoord,
                                SpriteSet spriteSet, double xd, double yd, double zd) {
        super(level, xCoord, yCoord, zCoord, xd, yd, zd);

        quadSize *= 1.25F;
        lifetime = 5 + random.nextInt(10);
        hasPhysics = false;

        pickSprite(spriteSet);
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }


    public void tick() {
        xo = x;
        yo = y;
        zo = z;
        if (age++ >= lifetime) {
            remove();
        } else {
            yd += 0.004D;
            move(xd, yd, zd);
            if (y == yo) {
                xd *= 1.1D;
                zd *= 1.1D;
            }

            xd *= 0.75F;
            yd *= 0.75F;
            zd *= 0.75F;
            if (onGround) {
                xd *= 0.6F;
                zd *= 0.6F;
            }

        }
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
            return new SnowflakeParticle(level, x, y, z, sprites, dx, dy, dz);
        }
    }
}
