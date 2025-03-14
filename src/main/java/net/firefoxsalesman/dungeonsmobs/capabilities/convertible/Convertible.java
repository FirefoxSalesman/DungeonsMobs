package net.firefoxsalesman.dungeonsmobs.capabilities.convertible;

import net.firefoxsalesman.dungeonsmobs.capabilities.ModCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.function.BiConsumer;

public class Convertible implements INBTSerializable<CompoundTag> {

    boolean isConverting;
    private boolean canConvert;
    int conversionTime;
    int prepareConversionTime;

    public void tickConversionTime() {
        setConversionTime(getConversionTime() - 1);
    }

    public <T extends Mob> T doConversion(Mob original, EntityType<T> convertToType, BiConsumer<Mob, T> onConversion) {
        T convertedTo = original.convertTo(convertToType, true);
        onConversion.accept(original, convertedTo);
        return convertedTo;

    }

    public void startConversion(int conversionLength) {
        setConverting(true);
        setConversionTime(conversionLength);
    }

    public void tickPrepareConversionTime() {
        setPrepareConversionTime(getPrepareConversionTime() + 1);
    }

    public boolean isConverting() {
        return isConverting;
    }

    public void setConverting(boolean converting) {
        isConverting = converting;
    }

    public void setConversionTime(int conversionTime) {
        this.conversionTime = conversionTime;
    }

    public int getConversionTime() {
        return conversionTime;
    }

    public void setPrepareConversionTime(int prepareConversionTime) {
        this.prepareConversionTime = prepareConversionTime;
    }

    public int getPrepareConversionTime() {
        return prepareConversionTime;
    }

    public boolean canConvert() {
        return canConvert;
    }

    public void setCanConvert(boolean canConvert) {
        this.canConvert = canConvert;
    }

    @Override
    public CompoundTag serializeNBT() {
        if (ModCapabilities.CONVERTIBLE_CAPABILITY == null) {
            return new CompoundTag();
        }
        CompoundTag tag = new CompoundTag();
        tag.putInt("conversionTime", isConverting() ? getConversionTime() : -1);
        tag.putInt("prepareConversionTime", canConvert() ? getPrepareConversionTime() : -1);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        setPrepareConversionTime(tag.getInt("prepareConversionTime"));
        if (tag.contains("DrownedConversionTime", 99) && tag.getInt("conversionTime") > -1) {
            startConversion(tag.getInt("conversionTime"));
        }
    }
}
