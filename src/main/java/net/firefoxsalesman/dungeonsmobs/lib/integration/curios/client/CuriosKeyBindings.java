package net.firefoxsalesman.dungeonsmobs.lib.integration.curios.client;

import net.firefoxsalesman.dungeonsmobs.lib.client.message.CuriosArtifactStartMessage;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonsmobs.lib.items.artifacts.ArtifactUseContext;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.Optional;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, value = Dist.CLIENT)
public class CuriosKeyBindings {

	public static final KeyMapping activateArtifact1 = new KeyMapping(
			"key.dungeons_libraries.curiosintegration.description_slot1", GLFW.GLFW_KEY_V,
			"key.dungeons_libraries.curiosintegration.category");
	public static final KeyMapping activateArtifact2 = new KeyMapping(
			"key.dungeons_libraries.curiosintegration.description_slot2", GLFW.GLFW_KEY_B,
			"key.dungeons_libraries.curiosintegration.category");
	public static final KeyMapping activateArtifact3 = new KeyMapping(
			"key.dungeons_libraries.curiosintegration.description_slot3", GLFW.GLFW_KEY_N,
			"key.dungeons_libraries.curiosintegration.category");

	@SubscribeEvent
	public static void onClientTick(InputEvent.Key event) {
		if (activateArtifact1.consumeClick()) {
			sendCuriosStartMessageToServer(0);
		}
		if (activateArtifact2.consumeClick()) {
			sendCuriosStartMessageToServer(1);
		}
		if (activateArtifact3.consumeClick()) {
			sendCuriosStartMessageToServer(2);
		}
	}

	private static void sendCuriosStartMessageToServer(int slot) {
		HitResult hitResult = Minecraft.getInstance().hitResult;
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null) {
			if (hitResult == null || hitResult.getType() == HitResult.Type.MISS) {
				BlockHitResult blockHitResult = new BlockHitResult(player.position(), Direction.UP,
						player.blockPosition(), false);
				curiosStartMessage(slot, blockHitResult, player);
			} else if (hitResult.getType() == HitResult.Type.BLOCK) {
				curiosStartMessage(slot, (BlockHitResult) hitResult, player);
			} else if (hitResult.getType() == HitResult.Type.ENTITY) {
				EntityHitResult entityHitResult = (EntityHitResult) hitResult;
				BlockHitResult blockHitResult = new BlockHitResult(
						entityHitResult.getEntity().position(), Direction.UP,
						entityHitResult.getEntity().blockPosition(), false);
				curiosStartMessage(slot, blockHitResult, player);
			}
		}
	}

	private static void curiosStartMessage(int slot, BlockHitResult blockHitResult, LocalPlayer player) {
		NetworkHandler.INSTANCE.sendToServer(new CuriosArtifactStartMessage(slot, blockHitResult));
		CuriosApi.getCuriosHelper().getCuriosHandler(player).ifPresent(iCuriosItemHandler -> {
			Optional<ICurioStacksHandler> artifactStackHandler = iCuriosItemHandler
					.getStacksHandler("artifact");
			if (artifactStackHandler.isPresent()) {
				ItemStack artifact = artifactStackHandler.get().getStacks().getStackInSlot(slot);
				if (!artifact.isEmpty() && artifact.getItem() instanceof ArtifactItem) {
					ArtifactUseContext iuc = new ArtifactUseContext(player.level(), player,
							artifact, blockHitResult);
					((ArtifactItem) artifact.getItem()).activateArtifact(iuc);
				}
			}
		});
	}

}
