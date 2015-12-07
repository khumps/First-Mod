package khumps.firstmod.guicontainer;

import khumps.firstmod.tile.TileBasicMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;

public class ContainerTileBasicMachine extends Container {
	TileBasicMachine te;
	IInventory playerInv;

	public ContainerTileBasicMachine(IInventory playerInv, TileBasicMachine te) {
		this.te = te;
		this.playerInv = playerInv;
	}

	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		// TODO Auto-generated method stub
		return false;
	}

}
