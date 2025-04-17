package net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.lib.client.renderer.gearconfig.ArmorGearRenderer;
import net.firefoxsalesman.dungeonsmobs.lib.items.interfaces.IArmor;
import net.firefoxsalesman.dungeonsmobs.lib.items.interfaces.IReloadableGear;
import net.firefoxsalesman.dungeonsmobs.lib.items.interfaces.IUniqueGear;
import net.firefoxsalesman.dungeonsmobs.lib.utils.DescriptionHelper;
import net.firefoxsalesman.dungeonsmobs.mixin.ArmorItemAccessor;
import net.firefoxsalesman.dungeonsmobs.mixin.ItemAccessor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.Animation.LoopType;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static java.util.UUID.randomUUID;
import static net.minecraft.world.item.ArmorMaterials.CHAIN;
import static net.minecraftforge.registries.ForgeRegistries.ATTRIBUTES;

public class ArmorGear extends ArmorItem implements IReloadableGear, IArmor, IUniqueGear, GeoItem {
	private static final ResourceLocation DEFAULT_ARMOR_ANIMATIONS = new ResourceLocation(DungeonsMobs.MOD_ID,
			"animations/armor/armor_default.animation.json");
	private static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[] {
			UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"),
			UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"),
			UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"),
			UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150") };

	private Multimap<Attribute, AttributeModifier> defaultModifiers;
	private ArmorGearConfig armorGearConfig;
	private final ResourceLocation armorSet;
	private final ResourceLocation modelLocation;
	private final ResourceLocation textureLocation;
	private final ResourceLocation animationFileLocation;

	public ArmorGear(Type slotType, Properties properties, ResourceLocation armorSet,
			ResourceLocation modelLocation, ResourceLocation textureLocation,
			ResourceLocation animationFileLocation) {
		super(CHAIN, slotType, properties);
		this.armorSet = armorSet;
		this.modelLocation = modelLocation;
		this.textureLocation = textureLocation;
		this.animationFileLocation = animationFileLocation;
		reload();
	}

	@Override
	public void reload() {
		armorGearConfig = ArmorGearConfigRegistry.getConfig(armorSet);
		if (armorGearConfig == ArmorGearConfig.DEFAULT) {
			armorGearConfig = ArmorGearConfigRegistry.getConfig(ForgeRegistries.ITEMS.getKey(this));
		}
		ArmorMaterial material = armorGearConfig.getArmorMaterial();
		((ArmorItemAccessor) this).setMaterial(material);
		((ArmorItemAccessor) this).setDefense(material.getDefenseForType(getType()));
		((ArmorItemAccessor) this).setToughness(material.getToughness());
		((ArmorItemAccessor) this).setKnockbackResistance(material.getKnockbackResistance());
		((ItemAccessor) this).setMaxDamage(material.getDurabilityForType(getType()));
		ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
		UUID primaryUuid = ARMOR_MODIFIER_UUID_PER_SLOT[getType().getSlot().getIndex()];
		builder.put(Attributes.ARMOR, new AttributeModifier(primaryUuid, "Armor modifier",
				material.getDefenseForType(getType()), AttributeModifier.Operation.ADDITION));
		builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(primaryUuid, "Armor toughness",
				material.getToughness(), AttributeModifier.Operation.ADDITION));
		if (knockbackResistance > 0) {
			builder.put(Attributes.KNOCKBACK_RESISTANCE,
					new AttributeModifier(primaryUuid, "Armor knockback resistance",
							knockbackResistance,
							AttributeModifier.Operation.ADDITION));
		}
		armorGearConfig.getAttributes().forEach(attributeModifier -> {
			Attribute attribute = ATTRIBUTES.getValue(attributeModifier.getAttributeResourceLocation());
			if (attribute != null) {
				UUID uuid = randomUUID();
				builder.put(attribute, new AttributeModifier(uuid, "Armor modifier",
						attributeModifier.getAmount(), attributeModifier.getOperation()));
			}
		});
		defaultModifiers = builder.build();
	}

	public ArmorGearConfig getGearConfig() {
		return armorGearConfig;
	}

	@Override
	public boolean isUnique() {
		return armorGearConfig.isUnique();
	}

	@Override
	public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
		return pEquipmentSlot == getType().getSlot() ? defaultModifiers
				: super.getDefaultAttributeModifiers(pEquipmentSlot);
	}

	@Override
	public void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag) {
		super.appendHoverText(stack, level, list, flag);
		if (armorSet != null) {
			DescriptionHelper.addLoreDescription(list, armorSet);
		} else {
			DescriptionHelper.addLoreDescription(list, ForgeRegistries.ITEMS.getKey(this));
		}
	}

	@Override
	public Rarity getRarity(ItemStack pStack) {
		return getGearConfig().getRarity();
	}

	public ResourceLocation getArmorSet() {
		return armorSet;
	}

	protected AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	@Override
	public void registerControllers(ControllerRegistrar controllers) {
		controllers.add(new AnimationController<GeoAnimatable>(this, "controller", 20, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return factory;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().then("idle", LoopType.LOOP));
		return PlayState.CONTINUE;
	}

	public ResourceLocation getModelLocation() {
		return modelLocation;
	}

	public ResourceLocation getTextureLocation() {
		return textureLocation;
	}

	public ResourceLocation getAnimationFileLocation() {
		return animationFileLocation;
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		super.initializeClient(consumer);
		consumer.accept(new IClientItemExtensions() {
			private GeoArmorRenderer<?> renderer;

			@Override
			public HumanoidModel<?> getHumanoidArmorModel(LivingEntity entityLiving, ItemStack itemStack,
					EquipmentSlot armorSlot, HumanoidModel<?> _default) {
				// TODO: If your armour isn't rendering this is probably the issue
				if (renderer == null) {
					renderer = new ArmorGearRenderer<>();
				}
				renderer.prepForRender(entityLiving, itemStack, armorSlot, _default);
				return (HumanoidModel<?>) renderer;
			}
		});
	}
}
