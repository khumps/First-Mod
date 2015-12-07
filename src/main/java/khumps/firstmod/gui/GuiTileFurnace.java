package khumps.firstmod.gui;

import khumps.firstmod.Strings;
import khumps.firstmod.guicontainer.ContainerTileFurnace;
import khumps.firstmod.tile.FurnaceSlotSetBroken;
import khumps.firstmod.tile.TileFurnace;
import khumps.firstmod.tile.Broken;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiTileFurnace extends GuiContainer {
	TileFurnace te;

	public GuiTileFurnace(IInventory playerInv, TileFurnace te) {
		super(new ContainerTileFurnace(playerInv, te));
		this.xSize = 176;
		this.ySize = 166;
		this.te = te;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		/*
		 * mc.getTextureManager().bindTexture(new ResourceLocation(Strings.MODID
		 * + ":textures/gui/furnace.png")); drawTexturedModalRect(152, 44, 0, 0,
		 * 176, 165); renderFlames();
		 */
		mc.getTextureManager().bindTexture(new ResourceLocation(Strings.MODID + ":textures/gui/furnace.png"));
		drawTexturedModalRect(152, 44, 0, 0, 176, 165);
		renderFlames();
		renderProgress();
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

	}

	private void renderFlames() {
		/* 126,60,14,14 */
		int drawX = 278;
		int flameHeight = te.getBurnTimeRemainingScaled(7) * 2;
		// flameHeight = 0;
		int drawY = 118 - flameHeight;
		drawTexturedModalRect(drawX, drawY, 176, 14 - flameHeight, 14, flameHeight);
	}

	private void renderProgress() {
		for (int i = 0; i < te.NUMSLOTS; i++) {
			int drawX = 203 + (23 * i);
			int drawY = 78;
			int progress = te.getCookProgressScaled(i, 24);
			// System.out.println("" + progress);
			int tX = 176;
			int tY = 14;
			drawTexturedModalRect(drawX, drawY, tX, tY, 18, te.TOTALCOOKSCALED - progress);
		}
	}

}
