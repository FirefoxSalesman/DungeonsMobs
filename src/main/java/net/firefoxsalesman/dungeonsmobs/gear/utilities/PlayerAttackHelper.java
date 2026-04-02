package net.firefoxsalesman.dungeonsmobs.gear.utilities;

import net.firefoxsalesman.dungeonsmobs.gear.capabilities.combo.Combo;
import net.firefoxsalesman.dungeonsmobs.gear.capabilities.combo.ComboHelper;
import net.firefoxsalesman.dungeonsmobs.mixin.LivingEntityAccessor;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import static net.firefoxsalesman.dungeonsmobs.gear.utilities.DamageSourceHelper.isSource;

import java.util.Optional;

public class PlayerAttackHelper {

	public static void swingArm(ServerPlayer playerEntity, InteractionHand hand) {
		ItemStack stack = playerEntity.getItemInHand(hand);
		if (stack.isEmpty() || !stack.onEntitySwing(playerEntity)) {
			if (!playerEntity.swinging
					|| playerEntity.swingTime >= getArmSwingAnimationEnd(playerEntity) / 2
					|| playerEntity.swingTime < 0) {
				playerEntity.swingTime = -1;
				playerEntity.swinging = true;
				playerEntity.swingingArm = hand;
				if (playerEntity.level() instanceof ServerLevel) {
					ClientboundAnimatePacket sanimatehandpacket = new ClientboundAnimatePacket(
							playerEntity, hand == InteractionHand.MAIN_HAND ? 0 : 3);
					ServerChunkCache serverchunkprovider = ((ServerLevel) playerEntity.level())
							.getChunkSource();

					serverchunkprovider.broadcast(playerEntity, sanimatehandpacket);
				}
			}

		}
	}

	private static int getArmSwingAnimationEnd(LivingEntity livingEntity) {
		if (MobEffectUtil.hasDigSpeed(livingEntity)) {
			return 6 - (1 + MobEffectUtil.getDigSpeedAmplification(livingEntity));
		} else {
			return livingEntity.hasEffect(MobEffects.DIG_SLOWDOWN)
					? 6 + (1 + livingEntity.getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) * 2
					: 6;
		}
	}

	public static void attackTargetEntityWithCurrentOffhandItem(ServerPlayer serverPlayerEntity, Entity target) {
		if (serverPlayerEntity.gameMode.getGameModeForPlayer() == GameType.SPECTATOR) {
			serverPlayerEntity.setCamera(target);
		} else {
			swapHeldItems(serverPlayerEntity);
			serverPlayerEntity.attack(target);
			swapHeldItems(serverPlayerEntity);
		}

	}

	public static void swapHeldItems(LivingEntity e) {
		// attributes = new ArrayList<>();
		ItemStack main = e.getMainHandItem(), off = e.getOffhandItem();
		int tssl = ((LivingEntityAccessor) e).getAttackStrengthTicker();
		boolean silent = e.isSilent();
		e.setSilent(true);
		Combo cap = ComboHelper.getComboCapability(e);
		e.setItemInHand(InteractionHand.MAIN_HAND, e.getOffhandItem());
		e.setItemInHand(InteractionHand.OFF_HAND, main);
		main.getAttributeModifiers(EquipmentSlot.MAINHAND).forEach((att, mod) -> {
			Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {
				mai.removeModifier(mod);
			});
		});
		off.getAttributeModifiers(EquipmentSlot.OFFHAND).forEach((att, mod) -> {
			Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {
				mai.removeModifier(mod);
			});
		});
		main.getAttributeModifiers(EquipmentSlot.OFFHAND).forEach((att, mod) -> {
			Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {
				mai.addTransientModifier(mod);
			});
		});
		off.getAttributeModifiers(EquipmentSlot.MAINHAND).forEach((att, mod) -> {
			Optional.ofNullable(e.getAttribute(att)).ifPresent((mai) -> {
				mai.addTransientModifier(mod);
			});
		});
		((LivingEntityAccessor) e).setAttackStrengthTicker(cap.getOffhandCooldown());
		cap.setOffhandCooldown(tssl);
		e.setSilent(silent);
	}

	public static boolean isProbablyNotMeleeDamage(DamageSource damageSource) {
		Entity e = damageSource.getEntity();
		if (e == null) {
			return false;
		}
		DamageSources ds = e.damageSources();
		return isSource(damageSource, ds.onFire())
				|| isSource(damageSource, ds.inFire())
				|| isSource(damageSource, ds.explosion(damageSource.getEntity(), null))
				|| isSource(damageSource, ds.magic())
				|| isSource(damageSource, ds.mobProjectile(damageSource.getDirectEntity(),
						(LivingEntity) damageSource.getEntity()))
				|| !isDirectDamage(damageSource);
	}

	private static boolean isDirectDamage(DamageSource damageSource) {
		return damageSource.getMsgId().equals("mob")
				|| damageSource.getMsgId().equals("player");
	}
}
