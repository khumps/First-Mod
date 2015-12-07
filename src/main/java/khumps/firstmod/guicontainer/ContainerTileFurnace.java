package khumps.firstmod.guicontainer;

import khumps.firstmod.gui.MachineSlot;
import khumps.firstmod.tile.FurnaceSlotSetBroken;
import khumps.firstmod.tile.TileFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTileFurnace extends Container {

	private TileFurnace te;

	public ContainerTileFurnace(IInventory playerInv, TileFurnace te) {
		this.te = te;
		initSlots(playerInv);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return te.isUseableByPlayer(player);
	}

	private void initSlots(IInventory playerInv) {
		addSlotToContainer(new MachineSlot(te, 0, 8, 59, FurnaceSlotSetBroken.FUELSLOT));
		for (int i = 0; i < TileFurnace.NUMSLOTS; i++) {
			for (int j = 1; j < FurnaceSlotSetBroken.NUMSLOTS; j++) {
				int slotX = 52 + (i * 24);
				int slotY = 15 + ((j - 1) * 44);
				int slotNum = i * (FurnaceSlotSetBroken.NUMSLOTS - 1) + j;
				// System.out.println(slotNum);
				addSlotToContainer(new MachineSlot(te, slotNum, slotX, slotY, j));
			}
		}

		// Player Inventory, Slot 9-35, Slot IDs 9-35
		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 9; ++x) {
				this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
			}
		}

		// Player Inventory, Slot 0-8, Slot IDs 36-44
		for (int x = 0; x < 9; ++x) {
			this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
		}
	}

	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		return null;
	}

}
