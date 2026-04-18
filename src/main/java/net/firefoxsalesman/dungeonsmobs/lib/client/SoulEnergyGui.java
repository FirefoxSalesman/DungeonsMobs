package net.firefoxsalesman.dungeonsmobs.lib.client;

import com.mojang.blaze3d.systems.RenderSystem;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.lib.capabilities.soulcaster.SoulCasterHelper;
import net.firefoxsalesman.dungeonsmobs.lib.config.DungeonsLibrariesConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.GameType;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.ModList;

/**
 * Borrowed from Goety. Many thanks to Polarice
 */
public class SoulEnergyGui {
	private static final String MOD_ID = DungeonsMobs.MOD_ID;
	public static final IGuiOverlay OVERLAY = SoulEnergyGui::drawHUD;
	private static final Minecraft minecraft = Minecraft.getInstance();

	public static boolean shouldDisplayBar() {
		return SoulCasterHelper.hasSouls(minecraft.player) && (minecraft.gameMode != null
				&& minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR)
				&& !ModList.get().isLoaded("goety");
	}

	public static Font getFont() {
		return minecraft.font;
	}

	public static void drawHUD(ForgeGui gui, GuiGraphics guiGraphics, float partialTicks, int screenWidth,
			int screenHeight) {
		if (!shouldDisplayBar()) {
			return;
		}

		int soulEnergy = (int) SoulCasterHelper.getSouls(minecraft.player);
		int soulEnergyTotal = (int) SoulCasterHelper.getSoulCap(minecraft.player);
		int i = (screenWidth / 2) - 200 + DungeonsLibrariesConfig.SOUL_BAR_HORIZONTAL_OFFSET.get();
		int energylength = (int) (60 * (soulEnergy / (double) soulEnergyTotal));
		int maxenergy = 71;

		int height = screenHeight - 5 + DungeonsLibrariesConfig.SOUL_BAR_VERTICAL_OFFSET.get();

		int offset = (int) ((minecraft.player.tickCount + partialTicks) % 234);

		guiGraphics.blit(new ResourceLocation(MOD_ID, "textures/gui/soul_energy.png"), i, height - 9, 0,
				0, maxenergy, 9, 128, 90);
		RenderSystem.setShaderTexture(0,
				new ResourceLocation(MOD_ID, "textures/gui/soul_energy_bar.png"));
		guiGraphics.blit(new ResourceLocation(MOD_ID, "textures/gui/soul_energy_bar.png"), i + 9, height - 7,
				offset, 0,
				energylength, 5, 128, 5);
	}
}
