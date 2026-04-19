package net.firefoxsalesman.dungeonsmobs.client.animation;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

// Save this class in your mod and generate all required imports

/**
 * Made with Blockbench 5.0.7
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * 
 * @author Author
 */
public class RedstoneCubeAnimations {
	public static final AnimationDefinition GENERAL = AnimationDefinition.Builder.withLength(4.04F).looping()
			.addAnimation("redstonecube", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.36F, KeyframeAnimations.degreeVec(0.44F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.56F, KeyframeAnimations.degreeVec(0.8848F, 4.0F, 0.0771F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.92F, KeyframeAnimations.degreeVec(1.2849F, 4.0F, 0.1162F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.12F, KeyframeAnimations.degreeVec(1.5016F, -5.0F, -0.0963F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.48F, KeyframeAnimations.degreeVec(1.89F, -5.0F, -0.06F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.72F, KeyframeAnimations.degreeVec(2.1488F, 0.0F, 0.0595F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.04F, KeyframeAnimations.degreeVec(2.5F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(4.04F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR)))
			.build();

	public static final AnimationDefinition SPAWN = AnimationDefinition.Builder.withLength(2.08F)
			.addAnimation("redstonecube", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(1.76F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.88F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.08F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("redstonecube", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -35.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(1.84F, KeyframeAnimations.posVec(0.0F, -35.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.0F, KeyframeAnimations.posVec(0.0F, 6.33F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(2.08F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR)))
			.build();

	public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(0.9667F).looping()
			.addAnimation("redstonecube", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2333F, KeyframeAnimations.degreeVec(90.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4833F, KeyframeAnimations.degreeVec(180.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7167F, KeyframeAnimations.degreeVec(270.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9667F, KeyframeAnimations.degreeVec(360.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("redstonecube", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1167F, KeyframeAnimations.posVec(0.0F, 12.33F, 20.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2333F, KeyframeAnimations.posVec(0.0F, 16.0F, 16.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3667F, KeyframeAnimations.posVec(0.0F, 35.0F, 20.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4833F, KeyframeAnimations.posVec(0.0F, 32.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, 35.0F, -3.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7167F, KeyframeAnimations.posVec(0.0F, 16.0F, -16.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.8333F, KeyframeAnimations.posVec(-1.0F, 13.14F, -2.14F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.9667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR)))
			.build();

	public static final AnimationDefinition ATTACK = AnimationDefinition.Builder.withLength(0.72F)
			.addAnimation("redstonecube", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1167F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(30.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3667F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4833F, KeyframeAnimations.degreeVec(-5.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7167F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR)))
			.addAnimation("redstonecube", new AnimationChannel(AnimationChannel.Targets.POSITION,
					new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.1167F, KeyframeAnimations.posVec(0.0F, 8.33F, -3.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, 8.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.3667F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.4833F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.6F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.7167F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F),
							AnimationChannel.Interpolations.LINEAR)))
			.build();
}
