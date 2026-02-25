package net.firefoxsalesman.dungeonsmobs.gear.enchantments.melee;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

import net.firefoxsalesman.dungeonsmobs.gear.config.DungeonsGearConfig;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.ModEnchantmentTypes;
import net.firefoxsalesman.dungeonsmobs.gear.enchantments.types.DropsEnchantment;
import net.firefoxsalesman.dungeonsmobs.gear.registry.EnchantmentInit;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.LootTableHelper;
import net.firefoxsalesman.dungeonsmobs.gear.utilities.ModEnchantmentHelper;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ProspectorEnchantment extends DropsEnchantment {

	public ProspectorEnchantment() {
		super(Enchantment.Rarity.RARE, ModEnchantmentTypes.MELEE, new EquipmentSlot[] {
				EquipmentSlot.MAINHAND });
	}

	public int getMaxLevel() {
		return 3;
	}

	@SubscribeEvent
	public static void onProspectiveKill(LivingDropsEvent event) {
		if (event.getSource().getDirectEntity() instanceof AbstractArrow)
			return;
		if (event.getSource().getEntity() instanceof LivingEntity) {
			LivingEntity attacker = (LivingEntity) event.getSource().getEntity();
			ItemStack mainhand = attacker.getMainHandItem();
			LivingEntity victim = event.getEntity();
			if (ModEnchantmentHelper.hasEnchantment(mainhand, EnchantmentInit.PROSPECTOR.get())) {
				int prospectorLevel = EnchantmentHelper
						.getItemEnchantmentLevel(EnchantmentInit.PROSPECTOR.get(), mainhand);
				float prospectorChance;
				prospectorChance = (float) (DungeonsGearConfig.PROSPECTOR_CHANCE_PER_LEVEL.get()
						* prospectorLevel);
				float prospectorRand = attacker.getRandom().nextFloat();
				if (prospectorRand <= prospectorChance) {
					if (victim instanceof Monster) {
						ItemEntity drop = getProspectorDrop(attacker, victim);
						event.getDrops().add(drop);
					}
				}
			}
		}
	}

	private static ItemEntity getProspectorDrop(LivingEntity attacker, LivingEntity victim) {
		ResourceLocation prospectorLootTable = getProspectorLootTable(victim.getCommandSenderWorld());
		ItemStack itemStack = LootTableHelper.generateItemStack((ServerLevel) victim.level(),
				victim.blockPosition(), prospectorLootTable, attacker.getRandom());
		return new ItemEntity(victim.level(), victim.getX(), victim.getY(), victim.getZ(), itemStack);
	}

	private static ResourceLocation getProspectorLootTable(Level world) {
		ResourceLocation resourceLocation = new ResourceLocation(MOD_ID,
				"enchantments/prospector/" + world.dimension().location().getPath());
		if (LootTableHelper.lootTableExists((ServerLevel) world, resourceLocation)) {
			return resourceLocation;
		} else {
			return new ResourceLocation(MOD_ID, "enchantments/prospector/overworld");
		}
	}

}
