package net.firefoxsalesman.dungeonsmobs.client.renderer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.mojang.blaze3d.systems.RenderSystem;

import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.client.event.CustomizeGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Borrowed from Goety
 */
public class BossBarRenderer {
	private static final ResourceLocation TEXTURE = GeneralHelper.modLoc("textures/gui/boss_bar.png");

	public static Map<UUID, Mob> BOSS_BARS = new HashMap<>();

	@SubscribeEvent
	public static void renderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
		Minecraft minecraft = Minecraft.getInstance();

		int i = minecraft.getWindow().getGuiScaledWidth();
		if (BOSS_BARS.containsKey(event.getBossEvent().getId())) {
			Mob boss = BOSS_BARS.get(event.getBossEvent().getId());
			event.setCanceled(true);
			int k = i / 2 - 100;
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			drawBar(event.getGuiGraphics(), k, event.getY(), boss);
			Component itextcomponent = boss.getDisplayName();
			int l = minecraft.font.width(itextcomponent);
			int i1 = i / 2 - l / 2;
			event.getGuiGraphics().drawString(minecraft.font, itextcomponent, i1, event.getY() - 9,
					16777215);
			if (event.getY() >= minecraft.getWindow().getGuiScaledHeight() / 3) {
				return;
			}
			event.setIncrement(12 + minecraft.font.lineHeight);
		}
	}

	private static void drawBar(GuiGraphics guiGraphics, int pX, int pY, Mob pEntity) {
		float percent = pEntity.getHealth() / pEntity.getMaxHealth();
		int i = (int) (percent * 182.0F);
		int pX2 = pX + 9;
		int pY2 = pY + 4;
		guiGraphics.blit(TEXTURE, pX2, pY2, 0, 9, i, 4, 182, 128);
		if (i > 0)
			guiGraphics.blit(TEXTURE, pX2, pY2, 0, 1, 182, 4, 182, 128);

	}

	public static void addBossBar(UUID id, Mob mob) {
		BOSS_BARS.put(id, mob);
	}

	public static void removeBossBar(UUID id, Mob mob) {
		BOSS_BARS.remove(id, mob);
	}
}
