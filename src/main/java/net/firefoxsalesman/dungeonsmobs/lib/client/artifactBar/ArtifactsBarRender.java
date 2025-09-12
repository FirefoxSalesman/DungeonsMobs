package net.firefoxsalesman.dungeonsmobs.lib.client.artifactBar;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;

import net.firefoxsalesman.dungeonsmobs.lib.client.CuriosKeyBindings;
import net.firefoxsalesman.dungeonsmobs.lib.client.gui.elementconfig.GuiElementConfig;
import net.firefoxsalesman.dungeonsmobs.lib.client.gui.elementconfig.GuiElementConfigRegistry;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Optional;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MOD_ID)
public class ArtifactsBarRender {
	private static final ResourceLocation ARTIFACT_BAR_RESOURCE = new ResourceLocation(MOD_ID,
			"textures/gui/artifact_bar.png");

	@SubscribeEvent
	public static void displayArtifactBar(RenderGuiOverlayEvent.Post event) {
		final Minecraft mc = Minecraft.getInstance();
		// if(mc != null &&
		// ForgeRegistries.ITEMS.tags().getTag(CURIOS_ARTIFACTS).isEmpty()) return;

		if (event.getOverlay().equals(VanillaGuiOverlay.HOTBAR.type())
				&& mc.getCameraEntity() instanceof Player renderPlayer) {
			if (renderPlayer == null)
				return;
			GuiElementConfig guiElementConfig = GuiElementConfigRegistry
					.getConfig(new ResourceLocation(MOD_ID, "artifact_bar"));
			if (guiElementConfig.isHidden())
				return;

			Window sr = event.getWindow();
			int scaledWidth = sr.getGuiScaledWidth();
			int scaledHeight = sr.getGuiScaledHeight();

			int x = guiElementConfig.getXPosition(scaledWidth);
			int y = guiElementConfig.getYPosition(scaledHeight);

			CuriosApi.getCuriosHelper().getCuriosHandler(renderPlayer).ifPresent(iCuriosItemHandler -> {
				renderBar(mc, renderPlayer, x, y, iCuriosItemHandler,
						event.getGuiGraphics());
			});

			// The resource location should point to wherever Gui.GUI_ICONS_LOCATION points
			// to. (We can't call it because it's protected)
			RenderSystem.setShaderTexture(0, new ResourceLocation("textures/gui/icons.png"));
		}

	}

	private static void renderBar(Minecraft mc, Player renderPlayer, int x, int y,
			ICuriosItemHandler iCuriosItemHandler, GuiGraphics graphics) {
		Optional<ICurioStacksHandler> artifactStackHandler = iCuriosItemHandler.getStacksHandler("artifact");
		if (artifactStackHandler.isPresent()) {
			IDynamicStackHandler stacks = artifactStackHandler.get().getStacks();
			if (noArtifactEquipped(stacks))
				return;
			int slots = stacks.getSlots();
			renderSlotBg(mc, x, y, slots, graphics);
			for (int slot = 0; slot < slots; slot++) {
				ItemStack artifact = stacks.getStackInSlot(slot);
				if (!artifact.isEmpty() && artifact.getItem() instanceof ArtifactItem) {
					int xPos = x + slot * 20 + 3;
					int yPos = y + 3;
					renderSlot(mc, xPos, yPos, renderPlayer, artifact, graphics.bufferSource(),
							graphics);
				}
				renderSlotKeybind(mc, x, y, slot, graphics);
			}
		}
	}

	private static boolean noArtifactEquipped(IDynamicStackHandler stacks) {
		int slots = stacks.getSlots();
		for (int slot = 0; slot < slots; slot++) {
			ItemStack artifact = stacks.getStackInSlot(slot);
			if (!artifact.isEmpty() && artifact.getItem() instanceof ArtifactItem) {
				return false;
			}
		}
		return true;
	}

	private static void renderSlot(Minecraft mc, int xPos, int yPos, Player renderPlayer, ItemStack artifactStack,
			MultiBufferSource bufferSource, GuiGraphics graphics) {
		if (!artifactStack.isEmpty()) {
			float f = (float) artifactStack.getPopTime() - 0;
			if (f > 0.0F) {
				float f1 = 1.0F + f / 5.0F;
				graphics.pose().pushPose();
				graphics.pose().translate((float) (xPos + 8), (float) (yPos + 12), 0.0F);
				graphics.pose().scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
				graphics.pose().translate((float) (-(xPos + 8)), (float) (-(yPos + 12)), 0.0F);
				RenderSystem.applyModelViewMatrix();
			}

			graphics.renderItem(artifactStack, xPos, yPos);
			BakedModel model = Minecraft.getInstance().getItemRenderer().getModel(artifactStack, mc.level,
					renderPlayer, 1);
			Minecraft.getInstance().getItemRenderer().render(artifactStack, ItemDisplayContext.GUI, false,
					graphics.pose(), bufferSource, 1, 1, model);
			if (f > 0.0F) {
				graphics.pose().popPose();
			}

			graphics.renderItemDecorations(Minecraft.getInstance().font, artifactStack, xPos, yPos);
		}
	}

	private static void renderSlotBg(Minecraft mc, int xPos, int yPos, int slots, GuiGraphics graphics) {
		RenderSystem.setShaderTexture(0, ARTIFACT_BAR_RESOURCE);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
		// GuiGraphics.blit(poseStack, xPos, yPos, 0, 0, 62, 22, 62, 22);
		RenderSystem.disableBlend();
	}

	private static void renderSlotKeybind(Minecraft mc, int x, int y, int slot,
			GuiGraphics graphics) {
		String keybind = "";
		if (slot == 0) {
			KeyMapping keyMapping = CuriosKeyBindings.activateArtifact1;
			keybind = getString(keyMapping);
		} else if (slot == 1) {
			KeyMapping keyMapping = CuriosKeyBindings.activateArtifact2;
			keybind = getString(keyMapping);
		} else if (slot == 2) {
			KeyMapping keyMapping = CuriosKeyBindings.activateArtifact3;
			keybind = getString(keyMapping);
		}
		int keybindWidth = mc.font.width(keybind);
		int xPosition = x + 1 + slot * 20 + 18 - keybindWidth;
		int yPosition = y + 3;
		graphics.drawString(mc.font, keybind, xPosition, yPosition, 0xFFFFFF);
	}

	private static String getString(KeyMapping keyMapping) {
		return keyMapping.getKey().getDisplayName().getString();
	}

}
