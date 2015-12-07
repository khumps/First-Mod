package khumps.firstmod.gui;

import khumps.firstmod.tile.FurnaceSlotSetBroken;
import khumps.firstmod.utils.MachineUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class MachineSlot extends net.minecraft.inventory.Slot {
	public int slotType;

	public MachineSlot(IInventory inventory, int slot, int x, int y, int type) {
		super(inventory, slot, x, y);
		slotType = type;
	}

	public boolean isItemValid(ItemStack stack) {
		//System.out.println("aa" + super.getSlotIndex());
		//System.out.println(slotType);
		switch (slotType) {
		case FurnaceSlotSetBroken.FUELSLOT: {
			return MachineUtils.isFuel(stack);
		}
		case FurnaceSlotSetBroken.INSLOT: {
			return MachineUtils.canBeSmelted(stack);
		}
		case FurnaceSlotSetBroken.OUTSLOT: {
			return true;
		}
		}
		return false;
	}
}