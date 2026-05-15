package net.firefoxsalesman.dungeonsmobs.lib.items;

import static net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry.LIFE_STEAL;
import static net.firefoxsalesman.dungeonsmobs.lib.attribute.AttributeRegistry.MAGIC_DAMAGE_MULTIPLIER;

import java.util.UUID;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.lib.utils.DamageSourceHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class ItemEvents {
	protected static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
	protected static final UUID BASE_ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

	@SubscribeEvent
	public static void onMagicDamage(LivingDamageEvent event) {
		DamageSource source = event.getSource();
		if (source.isIndirect()
				&& DamageSourceHelper.isSource(source,
						event.getEntity().damageSources().magic())
				&&
				source.getEntity() instanceof LivingEntity) {

			float originalDamage = event.getAmount();

			LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
			AttributeInstance magicDamageMultiplierAttribute = attacker
					.getAttribute(MAGIC_DAMAGE_MULTIPLIER.get());
			double attributeModifier = magicDamageMultiplierAttribute != null
					? magicDamageMultiplierAttribute.getValue()
					: 1.0D;
			double additionalDamage = originalDamage * attributeModifier;

			if (additionalDamage > 0)
				event.setAmount(originalDamage + (float) additionalDamage);
		}
	}

	@SubscribeEvent
	public static void onEntityKilled(LivingDeathEvent event) {
		DamageSource source = event.getSource();
		if (source.getEntity() instanceof LivingEntity) {
			LivingEntity attacker = (LivingEntity) source.getEntity();
			AttributeInstance attribute = attacker.getAttribute(LIFE_STEAL.get());
			if (attribute != null) {
				double lifeStealAmount = attribute.getValue() - 1.0D;
				float victimMaxHealth = event.getEntity().getMaxHealth();
				if (attacker.getHealth() < attacker.getMaxHealth()) {
					attacker.heal(victimMaxHealth * (float) lifeStealAmount);
				}
			}
		}
	}
}
