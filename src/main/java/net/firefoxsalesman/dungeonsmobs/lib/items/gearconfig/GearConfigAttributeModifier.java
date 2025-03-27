package net.firefoxsalesman.dungeonsmobs.lib.items.gearconfig;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public class GearConfigAttributeModifier {

    public static final Codec<AttributeModifier.Operation> ATTRIBUTE_MODIFIER_OPERATION_CODEC = Codec.INT.flatComapMap(AttributeModifier.Operation::fromValue, d -> DataResult.success(d.toValue()));

    public static final Codec<GearConfigAttributeModifier> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("attribute").forGetter(GearConfigAttributeModifier::getAttributeResourceLocation),
            Codec.DOUBLE.fieldOf("amount").forGetter(GearConfigAttributeModifier::getAmount),
            ATTRIBUTE_MODIFIER_OPERATION_CODEC.fieldOf("operation").forGetter(GearConfigAttributeModifier::getOperation)
    ).apply(instance, GearConfigAttributeModifier::new));

    private final ResourceLocation attributeResourceLocation;
    private final double amount;
    private final AttributeModifier.Operation operation;

    public GearConfigAttributeModifier(ResourceLocation attributeResourceLocation, double amount, AttributeModifier.Operation operation) {
        this.attributeResourceLocation = attributeResourceLocation;
        this.amount = amount;
        this.operation = operation;
    }

    public ResourceLocation getAttributeResourceLocation() {
        return attributeResourceLocation;
    }

    public double getAmount() {
        return amount;
    }

    public AttributeModifier.Operation getOperation() {
        return operation;
    }

    public AttributeModifier toAttributeModifier(UUID uuid, String name){
        return new AttributeModifier(uuid, name, amount, operation);
    }

    public Attribute getAttribute(){
        return ForgeRegistries.ATTRIBUTES.getValue(attributeResourceLocation);
    }
}
