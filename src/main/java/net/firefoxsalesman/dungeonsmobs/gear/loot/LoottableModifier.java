package net.firefoxsalesman.dungeonsmobs.gear.loot;

import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;

public class LoottableModifier extends LootModifier {
	public static final Supplier<Codec<LoottableModifier>> CODEC = Suppliers
			.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst)
					.and(Codec.STRING.fieldOf("table").forGetter(m -> m.table))
					.apply(inst, LoottableModifier::new)));
	public final String table;

	public LoottableModifier(LootItemCondition[] conditonsIn, String table) {
		super(conditonsIn);
		this.table = table;
	}

	@Override
	public Codec<? extends IGlobalLootModifier> codec() {
		return CODEC.get();
	}

	@Override
	protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot,
			LootContext context) {
		context.getLevel().getServer().getLootData().getLootTable(new ResourceLocation(table))
				.getRandomItemsRaw(context, generatedLoot::add);
		return generatedLoot;
	}
}
