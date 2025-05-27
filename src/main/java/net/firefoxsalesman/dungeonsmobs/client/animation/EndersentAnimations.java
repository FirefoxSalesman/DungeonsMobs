package net.firefoxsalesman.dungeonsmobs.client.animation;

// Save this class in your mod and generate all required imports

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Made with Blockbench 4.12.4
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author Author
 */
public class EndersentAnimations {
	public static final AnimationDefinition IDLE = AnimationDefinition.Builder.withLength(4.72F).looping()
		.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("upperBody", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(60.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.04F, KeyframeAnimations.degreeVec(59.9057F, 0.0048F, -3.1713F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.08F, KeyframeAnimations.degreeVec(62.086F, -0.3412F, -3.6394F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.12F, KeyframeAnimations.degreeVec(66.6035F, -3.5925F, -3.2498F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(66.6035F, -3.5925F, -3.2498F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(68.9404F, -0.7649F, -1.6469F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.32F, KeyframeAnimations.degreeVec(69.0988F, -1.6593F, -3.9795F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.76F, KeyframeAnimations.degreeVec(62.1217F, -1.511F, -3.3492F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.52F, KeyframeAnimations.degreeVec(14.9981F, -2.3299F, -1.2936F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.0F, KeyframeAnimations.degreeVec(1.0F, 4.0F, 1.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.04F, KeyframeAnimations.degreeVec(-1.0F, 4.5F, 1.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.4F, KeyframeAnimations.degreeVec(1.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.48F, KeyframeAnimations.degreeVec(56.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.56F, KeyframeAnimations.degreeVec(61.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.64F, KeyframeAnimations.degreeVec(63.5961F, -0.8507F, 0.2017F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.68F, KeyframeAnimations.degreeVec(66.6477F, -0.1699F, -2.2614F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.84F, KeyframeAnimations.degreeVec(66.7953F, 0.8761F, 0.0192F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(3.32F, KeyframeAnimations.degreeVec(60.6673F, -2.6341F, 0.1031F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(4.16F, KeyframeAnimations.degreeVec(3.38F, -3.73F, 0.39F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(4.44F, KeyframeAnimations.degreeVec(1.25F, -4.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(4.6F, KeyframeAnimations.degreeVec(1.5F, -2.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(4.68F, KeyframeAnimations.degreeVec(23.0F, -0.83F, -1.67F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(4.72F, KeyframeAnimations.degreeVec(60.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("upperBody", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.08F, KeyframeAnimations.posVec(0.0F, -0.5F, -0.25F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.12F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.75F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.56F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.28F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.32F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.64F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.73F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.76F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.75F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.52F, KeyframeAnimations.posVec(0.0F, -0.25F, -2.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.4F, KeyframeAnimations.posVec(0.0F, -0.25F, -2.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.56F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.29F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(2.68F, KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(3.32F, KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(3.64F, KeyframeAnimations.posVec(0.0F, -0.25F, -1.71F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(3.88F, KeyframeAnimations.posVec(0.0F, -0.25F, -2.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(4.6F, KeyframeAnimations.posVec(0.0F, -0.25F, -2.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(4.72F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.5F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-30.0F, 2.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.08F, KeyframeAnimations.degreeVec(-42.5F, 2.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4F, KeyframeAnimations.degreeVec(-2.5F, 2.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0F, KeyframeAnimations.degreeVec(-20.0F, 2.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.32F, KeyframeAnimations.degreeVec(-8.95F, 2.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.76F, KeyframeAnimations.degreeVec(-65.0F, 2.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.96F, KeyframeAnimations.degreeVec(-12.5F, 2.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.72F, KeyframeAnimations.degreeVec(-30.0F, 2.0F, 0.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-150.0F, 2.5F, -6.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.08F, KeyframeAnimations.degreeVec(-37.5F, 12.5F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(-35.0F, 12.5F, -3.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(-30.0F, 12.5F, -3.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.24F, KeyframeAnimations.degreeVec(-30.0F, 10.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(-30.0F, 10.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.32F, KeyframeAnimations.degreeVec(-30.0F, 10.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-32.5F, 13.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.44F, KeyframeAnimations.degreeVec(-30.0F, 15.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.degreeVec(-27.5F, 3.0F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.16F, KeyframeAnimations.degreeVec(-25.0F, 3.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2F, KeyframeAnimations.degreeVec(-22.5F, 2.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.24F, KeyframeAnimations.degreeVec(-20.0F, 0.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.28F, KeyframeAnimations.degreeVec(-20.0F, 0.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.44F, KeyframeAnimations.degreeVec(-20.0F, -2.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.6F, KeyframeAnimations.degreeVec(-20.0F, -4.5F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.92F, KeyframeAnimations.degreeVec(-5.0F, -2.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4F, KeyframeAnimations.degreeVec(15.0F, -2.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.56F, KeyframeAnimations.degreeVec(26.0F, 3.0F, 4.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.64F, KeyframeAnimations.degreeVec(26.5F, -12.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.8F, KeyframeAnimations.degreeVec(26.5F, -12.0F, 1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.92F, KeyframeAnimations.degreeVec(27.5F, -10.25F, -0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.04F, KeyframeAnimations.degreeVec(27.5F, -10.25F, -0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.44F, KeyframeAnimations.degreeVec(-23.1505F, -7.0593F, 1.9039F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.04F, KeyframeAnimations.degreeVec(-102.5F, 0.0F, -7.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.44F, KeyframeAnimations.degreeVec(-102.5F, 4.75F, -3.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.6F, KeyframeAnimations.degreeVec(-101.5F, 4.75F, -3.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.72F, KeyframeAnimations.degreeVec(-150.0F, 2.5F, -6.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-105.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.08F, KeyframeAnimations.degreeVec(-74.9453F, -4.8292F, -1.2972F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(-52.3944F, -3.9649F, -3.0487F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(-57.4009F, -4.2154F, -2.6914F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.24F, KeyframeAnimations.degreeVec(-57.4533F, -3.8264F, -6.5305F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(-57.3382F, -5.9325F, -7.8825F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.32F, KeyframeAnimations.degreeVec(-57.5183F, -1.7182F, -5.1853F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-54.9569F, -3.6449F, -6.8023F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.44F, KeyframeAnimations.degreeVec(-57.3382F, -5.9325F, -7.8825F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.degreeVec(-17.5F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.16F, KeyframeAnimations.degreeVec(-17.3802F, 3.2494F, -0.1119F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.2F, KeyframeAnimations.degreeVec(-19.8798F, 3.3527F, -0.1468F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.24F, KeyframeAnimations.degreeVec(-22.3798F, 3.4544F, -0.1862F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.28F, KeyframeAnimations.degreeVec(-19.8798F, 3.3527F, -0.1468F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.44F, KeyframeAnimations.degreeVec(-9.7478F, 3.3579F, 2.4323F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.6F, KeyframeAnimations.degreeVec(-2.2726F, 2.7083F, 2.5008F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.92F, KeyframeAnimations.degreeVec(-14.8809F, 3.1446F, -0.0816F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4F, KeyframeAnimations.degreeVec(-17.5F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.56F, KeyframeAnimations.degreeVec(-45.0F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.64F, KeyframeAnimations.degreeVec(-40.0F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.8F, KeyframeAnimations.degreeVec(-42.5F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.92F, KeyframeAnimations.degreeVec(-42.5F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.04F, KeyframeAnimations.degreeVec(-42.5F, 2.5F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.44F, KeyframeAnimations.degreeVec(-32.2773F, 4.0216F, 6.3359F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.04F, KeyframeAnimations.degreeVec(-120.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.44F, KeyframeAnimations.degreeVec(-115.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.6F, KeyframeAnimations.degreeVec(-120.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.72F, KeyframeAnimations.degreeVec(-105.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightHand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.3309F, 9.1385F, -2.0052F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(37.3744F, 23.0928F, 8.7156F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.44F, KeyframeAnimations.degreeVec(38.1861F, 9.0446F, 2.2981F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(37.0F, 7.5F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.28F, KeyframeAnimations.degreeVec(-20.8105F, 7.118F, 2.1402F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(-30.5F, 8.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4F, KeyframeAnimations.degreeVec(-30.5F, 8.0F, 2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.84F, KeyframeAnimations.degreeVec(-90.5F, 0.5F, 8.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4F, KeyframeAnimations.degreeVec(-113.1667F, -0.9291F, 0.8009F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.44F, KeyframeAnimations.degreeVec(-124.1058F, 2.3653F, -0.9054F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.48F, KeyframeAnimations.degreeVec(-115.0F, 7.5F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.52F, KeyframeAnimations.degreeVec(-55.0F, 7.5F, 7.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.56F, KeyframeAnimations.degreeVec(-62.7517F, 0.8378F, 4.0443F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.64F, KeyframeAnimations.degreeVec(-35.0F, -2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.68F, KeyframeAnimations.degreeVec(-32.5F, -7.5F, 12.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.72F, KeyframeAnimations.degreeVec(-34.0652F, -6.9958F, 4.9323F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.8F, KeyframeAnimations.degreeVec(-30.0F, -7.5F, 5.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.88F, KeyframeAnimations.degreeVec(-30.0F, -7.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.12F, KeyframeAnimations.degreeVec(-30.0F, -5.0F, 3.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.28F, KeyframeAnimations.degreeVec(-30.0F, -5.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0F, KeyframeAnimations.degreeVec(-18.5F, 5.0F, -6.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.6F, KeyframeAnimations.degreeVec(24.0F, 5.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.72F, KeyframeAnimations.degreeVec(10.3309F, 9.1385F, -2.0052F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-45.993F, -10.5453F, 10.7286F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-55.789F, -21.8248F, 9.0003F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.44F, KeyframeAnimations.degreeVec(-55.6907F, -10.6053F, 10.955F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(-55.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.28F, KeyframeAnimations.degreeVec(-52.3944F, -3.9649F, -3.0487F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4F, KeyframeAnimations.degreeVec(-67.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.84F, KeyframeAnimations.degreeVec(-125.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4F, KeyframeAnimations.degreeVec(-117.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.44F, KeyframeAnimations.degreeVec(-115.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.48F, KeyframeAnimations.degreeVec(-100.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.52F, KeyframeAnimations.degreeVec(-110.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.56F, KeyframeAnimations.degreeVec(-57.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.64F, KeyframeAnimations.degreeVec(-62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.72F, KeyframeAnimations.degreeVec(-62.579F, 7.8054F, 1.2448F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.8F, KeyframeAnimations.degreeVec(-64.8109F, 6.7938F, 3.1846F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.88F, KeyframeAnimations.degreeVec(-64.8109F, 6.7938F, 3.1846F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.12F, KeyframeAnimations.degreeVec(-62.4776F, 2.2174F, 1.1549F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.28F, KeyframeAnimations.degreeVec(-59.9764F, 2.1649F, 1.2506F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.0F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.6F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.72F, KeyframeAnimations.degreeVec(-45.993F, -10.5453F, 10.7286F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("eye_of_ender", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("eye_of_ender", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.5F, 0.5F, 1.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(60.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.04F, KeyframeAnimations.degreeVec(59.9057F, 0.0048F, -3.1713F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.08F, KeyframeAnimations.degreeVec(62.086F, -0.3412F, -3.6394F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.12F, KeyframeAnimations.degreeVec(66.6035F, -3.5925F, -3.2498F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(66.6035F, -3.5925F, -3.2498F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(68.9404F, -0.7649F, -1.6469F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.32F, KeyframeAnimations.degreeVec(69.0988F, -1.6593F, -3.9795F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.76F, KeyframeAnimations.degreeVec(62.1217F, -1.511F, -3.3492F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.52F, KeyframeAnimations.degreeVec(14.9981F, -2.3299F, -1.2936F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.0F, KeyframeAnimations.degreeVec(1.0F, 4.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.04F, KeyframeAnimations.degreeVec(-1.0F, 4.5F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4F, KeyframeAnimations.degreeVec(1.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.48F, KeyframeAnimations.degreeVec(56.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.56F, KeyframeAnimations.degreeVec(61.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.64F, KeyframeAnimations.degreeVec(63.5961F, -0.8507F, 0.2017F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.68F, KeyframeAnimations.degreeVec(66.6477F, -0.1699F, -2.2614F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.84F, KeyframeAnimations.degreeVec(66.7953F, 0.8761F, 0.0192F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.32F, KeyframeAnimations.degreeVec(60.6673F, -2.6341F, 0.1031F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.16F, KeyframeAnimations.degreeVec(3.38F, -3.73F, 0.39F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.44F, KeyframeAnimations.degreeVec(1.25F, -4.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.6F, KeyframeAnimations.degreeVec(1.5F, -2.5F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.68F, KeyframeAnimations.degreeVec(23.0F, -0.83F, -1.67F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.72F, KeyframeAnimations.degreeVec(60.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.08F, KeyframeAnimations.posVec(0.0F, -0.5F, -0.25F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.12F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.56F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.32F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.64F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.73F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.76F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.75F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.52F, KeyframeAnimations.posVec(0.0F, -0.25F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.4F, KeyframeAnimations.posVec(0.0F, -0.25F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.56F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.29F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.68F, KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.32F, KeyframeAnimations.posVec(0.0F, -0.25F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.64F, KeyframeAnimations.posVec(0.0F, -0.25F, -1.71F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.88F, KeyframeAnimations.posVec(0.0F, -0.25F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.6F, KeyframeAnimations.posVec(0.0F, -0.25F, -2.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4.72F, KeyframeAnimations.posVec(0.0F, -0.25F, -0.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("eye", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.32F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.44F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.56F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.68F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.84F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.88F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.0F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.12F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.24F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.4F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3.52F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition WALK = AnimationDefinition.Builder.withLength(1.48F).looping()
		.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.72F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.48F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.36F, KeyframeAnimations.posVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.72F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.48F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.72F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.48F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.72F, KeyframeAnimations.posVec(0.0F, 0.0F, 3.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.posVec(0.0F, 10.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.48F, KeyframeAnimations.posVec(0.0F, 0.0F, -3.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("upperBody", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.76F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.44F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("upperBody", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.44F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-2.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.76F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.44F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.76F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.44F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.76F, KeyframeAnimations.degreeVec(37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.44F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.76F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.44F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("eye_of_ender", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("eye_of_ender", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.5F, 0.5F, 1.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.76F, KeyframeAnimations.degreeVec(7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.44F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.44F, KeyframeAnimations.posVec(0.0F, -1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("eye", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.64F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.76F, KeyframeAnimations.scaleVec(1.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.88F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition ATTACK = AnimationDefinition.Builder.withLength(1.32F)
		.addAnimation("upperBody", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(-14.9717F, 4.9571F, 0.6543F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-14.97F, 4.96F, 0.65F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.88F, KeyframeAnimations.degreeVec(55.3353F, -14.7313F, -2.8812F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(55.3353F, -14.7313F, -2.8812F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(9.9615F, -9.9904F, 0.4407F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(9.96F, -9.99F, 0.44F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-12.6578F, 4.7891F, 3.0147F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(-136.1441F, 32.4105F, -26.654F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-136.14F, 32.41F, -26.65F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.76F, KeyframeAnimations.degreeVec(-63.2251F, 20.5981F, -27.1509F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.88F, KeyframeAnimations.degreeVec(-75.5507F, 12.9254F, -12.611F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-75.5507F, 12.9254F, -12.611F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(-85.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-85.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.88F, KeyframeAnimations.degreeVec(-16.8516F, -10.2124F, -7.2468F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-16.8516F, -10.2124F, -7.2468F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5115F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(-20.0425F, 4.1574F, -4.1526F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-20.04F, 4.16F, -4.15F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.88F, KeyframeAnimations.degreeVec(52.6152F, -17.602F, -21.2304F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(52.6152F, -17.602F, -21.2304F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(7.5115F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(-22.1907F, 12.7398F, 0.3177F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-22.19F, 12.74F, 0.32F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.88F, KeyframeAnimations.degreeVec(-56.1401F, 25.2523F, -6.3998F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-56.1401F, 25.2523F, -6.3998F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("eye_of_ender", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("eye_of_ender", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.5F, 0.5F, 1.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(-12.4885F, 0.5409F, -2.5592F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.88F, KeyframeAnimations.degreeVec(-12.4885F, 0.5409F, -2.5592F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-12.4885F, 0.5409F, -2.5592F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.88F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.posVec(0.0F, 0.0F, -1.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(7.4929F, -0.3262F, 2.4786F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.88F, KeyframeAnimations.degreeVec(7.4929F, -0.3262F, 2.4786F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(7.4929F, -0.3262F, 2.4786F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.88F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.posVec(0.0F, 0.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(-14.9717F, 4.9571F, 0.6543F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-14.97F, 4.96F, 0.65F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.88F, KeyframeAnimations.degreeVec(55.3353F, -14.7313F, -2.8812F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(55.3353F, -14.7313F, -2.8812F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition SMASH_ATTACK = AnimationDefinition.Builder.withLength(1.84F)
		.addAnimation("upperBody", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.64F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.8F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.08F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.4F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.56F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.8F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.64F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.08F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.56F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.8F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-42.5F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.64F, KeyframeAnimations.degreeVec(-140.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8F, KeyframeAnimations.degreeVec(-140.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-157.5193F, 0.9565F, 0.1901F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.08F, KeyframeAnimations.degreeVec(-157.5193F, 0.9565F, 0.1901F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(-87.4976F, -2.4976F, 2.3909F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4F, KeyframeAnimations.degreeVec(-87.4976F, -2.4976F, 2.3909F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.56F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.8F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-85.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.64F, KeyframeAnimations.degreeVec(-85.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8F, KeyframeAnimations.degreeVec(-85.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.08F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.56F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.8F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5115F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(-24.9885F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-69.9885F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.64F, KeyframeAnimations.degreeVec(-137.4885F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8F, KeyframeAnimations.degreeVec(-137.4885F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-157.5296F, -1.4974F, -0.1306F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.08F, KeyframeAnimations.degreeVec(-157.5296F, -1.4974F, -0.1306F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(-87.4871F, 1.9567F, -2.3312F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4F, KeyframeAnimations.degreeVec(-87.4871F, 1.9567F, -2.3312F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.56F, KeyframeAnimations.degreeVec(-34.9885F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.8F, KeyframeAnimations.degreeVec(7.5115F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(-30.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-72.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.64F, KeyframeAnimations.degreeVec(-95.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8F, KeyframeAnimations.degreeVec(-95.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.08F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.56F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.8F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("eye_of_ender", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 2.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("eye_of_ender", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(0.5F, 0.5F, 1.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("rightHand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(1.12F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.36F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.52F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftHand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(1.12F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.36F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.52F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.64F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.08F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.32F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.56F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.8F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition TELEPORT = AnimationDefinition.Builder.withLength(1.44F)
		.addAnimation("humanoid", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.6F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.88F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.92F, KeyframeAnimations.scaleVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4F, KeyframeAnimations.scaleVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.44F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5115F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(7.5115F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.44F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-80.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.44F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.44F, KeyframeAnimations.degreeVec(-70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("eye", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition TELEPORT_SMASH_ATTACK = AnimationDefinition.Builder.withLength(2.36F)
		.addAnimation("humanoid", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.6F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.88F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.92F, KeyframeAnimations.scaleVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.72F, KeyframeAnimations.scaleVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.76F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.92F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.92F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.92F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.92F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.92F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.64F, KeyframeAnimations.degreeVec(-15.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.88F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.96F, KeyframeAnimations.degreeVec(62.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.12F, KeyframeAnimations.degreeVec(45.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.36F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.92F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.64F, KeyframeAnimations.degreeVec(12.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.88F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.96F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.12F, KeyframeAnimations.degreeVec(-37.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.36F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5115F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(7.5115F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.44F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.92F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.64F, KeyframeAnimations.degreeVec(-157.5296F, -1.4974F, -0.1306F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.88F, KeyframeAnimations.degreeVec(-87.4871F, 1.9567F, -2.3312F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.96F, KeyframeAnimations.degreeVec(-87.4871F, 1.9567F, -2.3312F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.12F, KeyframeAnimations.degreeVec(-34.9885F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.36F, KeyframeAnimations.degreeVec(7.5115F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-80.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.92F, KeyframeAnimations.degreeVec(-80.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.64F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.88F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.96F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.12F, KeyframeAnimations.degreeVec(-22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.36F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftHand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(1.68F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.92F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.08F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.44F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.92F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.64F, KeyframeAnimations.degreeVec(-157.5193F, 0.9565F, 0.1901F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.88F, KeyframeAnimations.degreeVec(-87.4976F, -2.4976F, 2.3909F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.96F, KeyframeAnimations.degreeVec(-87.4976F, -2.4976F, 2.3909F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.12F, KeyframeAnimations.degreeVec(-35.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.36F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.44F, KeyframeAnimations.degreeVec(-70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.92F, KeyframeAnimations.degreeVec(-70.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.64F, KeyframeAnimations.degreeVec(-32.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.88F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.96F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.12F, KeyframeAnimations.degreeVec(-25.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.36F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightHand", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(1.68F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.92F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.08F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("eye", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition SUMMON_WATCHLINGS = AnimationDefinition.Builder.withLength(1.12F)
		.addAnimation("humanoid", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.56F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.scaleVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.08F, KeyframeAnimations.scaleVec(0.0F, 1.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.POSITION, 
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.degreeVec(17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(5.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.degreeVec(-7.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(7.5115F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(4.595F, 18.8769F, -17.9816F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(4.595F, 18.8769F, -17.9816F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(-12.5F, 15.0F, -67.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-12.5F, 15.0F, -67.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.degreeVec(7.5115F, -0.5409F, -2.4408F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(-74.9453F, 4.8292F, 1.2972F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(-74.9453F, 4.8292F, 1.2972F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, -90.0F, -67.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(0.0F, -90.0F, -67.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(6.993F, -20.0489F, 17.2299F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(6.993F, -20.0489F, 17.2299F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(-12.5F, -15.0F, 67.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(-12.5F, -15.0F, 67.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(-74.9864F, -2.4148F, -0.6474F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(-74.9864F, -2.4148F, -0.6474F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, 90.0F, 65.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.degreeVec(0.0F, 90.0F, 65.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.degreeVec(-47.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("eye", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.0F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2F, KeyframeAnimations.scaleVec(0.65F, 0.65F, 1.05F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.scaleVec(0.65F, 0.65F, 1.05F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.scaleVec(1.1F, 1.1F, 1.1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6F, KeyframeAnimations.scaleVec(1.1F, 1.1F, 1.1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.12F, KeyframeAnimations.scaleVec(1.0F, 1.0F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition DEATH = AnimationDefinition.Builder.withLength(1.2F).looping()
		.addAnimation("rightLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.16F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftLeg", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.16F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -2.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("upperBody", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(22.3265F, 2.8631F, -6.9349F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(7.3265F, 2.8631F, -6.9349F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.68F, KeyframeAnimations.degreeVec(7.7694F, 1.2147F, 5.4643F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(22.3265F, -2.8631F, 6.9349F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(1.16F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(8.7828F, 11.0819F, -5.7692F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-11.0407F, 8.4849F, -5.7173F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.68F, KeyframeAnimations.degreeVec(-11.0407F, -8.485F, 9.2249F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(11.0407F, -8.485F, 9.2249F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.16F, KeyframeAnimations.degreeVec(10.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(-27.1423F, 4.599F, 8.8893F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(5.3577F, 4.599F, 8.8893F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.68F, KeyframeAnimations.degreeVec(5.7371F, 4.1154F, -1.6697F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-27.1423F, -4.599F, -8.8893F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.16F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("rightArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(-17.4844F, 0.7516F, 2.3844F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-17.4374F, 1.5018F, 4.7697F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.68F, KeyframeAnimations.degreeVec(-17.4374F, 1.5018F, 4.7697F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-17.4844F, 0.7516F, 2.3844F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.16F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArm", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(-27.2989F, 3.4553F, 6.6607F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(0.05F, 3.4608F, 4.1561F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.68F, KeyframeAnimations.degreeVec(0.251F, 4.5925F, -6.3812F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-27.2989F, -3.4553F, -6.6607F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.16F, KeyframeAnimations.degreeVec(-27.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("leftArmBone", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(-17.4844F, 0.7516F, 2.3844F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(-17.4374F, -1.5018F, -4.7697F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.68F, KeyframeAnimations.degreeVec(-17.4374F, -1.5018F, -4.7697F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(-17.4844F, 0.7516F, 2.3844F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.16F, KeyframeAnimations.degreeVec(-17.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION, 
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.2F, KeyframeAnimations.degreeVec(22.3265F, 2.8631F, -6.9349F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4F, KeyframeAnimations.degreeVec(7.3265F, 2.8631F, -6.9349F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.68F, KeyframeAnimations.degreeVec(7.7694F, 1.2147F, 5.4643F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.degreeVec(22.3265F, -2.8631F, 6.9349F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.16F, KeyframeAnimations.degreeVec(22.5F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("eye", new AnimationChannel(AnimationChannel.Targets.SCALE, 
			new Keyframe(0.28F, KeyframeAnimations.scaleVec(1.0F, 0.7F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.52F, KeyframeAnimations.scaleVec(1.0F, 0.1F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.72F, KeyframeAnimations.scaleVec(1.0F, 0.1F, 1.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.96F, KeyframeAnimations.scaleVec(1.0F, 0.7F, 1.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();
}
