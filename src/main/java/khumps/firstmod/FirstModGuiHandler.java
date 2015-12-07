package khumps.firstmod;

import cpw.mods.fml.common.network.IGuiHandler;
import khumps.firstmod.gui.GuiTileFurnace;
import khumps.firstmod.gui.GuiTileMachine;
import khumps.firstmod.guicontainer.ContainerTileFurnace;
import khumps.firstmod.tile.TileBasicMachine;
import khumps.firstmod.tile.TileFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class FirstModGuiHandler implements IGuiHandler {
	public static final int FURNACE_ID = 0;
	public static final int BASIC_MACHINE_ID = 1;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == FURNACE_ID)
			return new ContainerTileFurnace(player.inventory, (TileFurnace) world.getTileEntity(x, y, z));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == FURNACE_ID)
			return new GuiTileFurnace(player.inventory, (TileFurnace) world.getTileEntity(x, y, z));
		if (ID == BASIC_MACHINE_ID)
			return new GuiTileMachine(player.inventory, (TileBasicMachine) world.getTileEntity(x, y, z));
		return null;
	}

}
