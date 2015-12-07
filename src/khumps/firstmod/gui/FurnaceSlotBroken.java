package khumps.firstmod.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class FurnaceSlotBroken extends net.minecraft.inventory.Slot {

	public FurnaceSlotBroken(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
		// TODO Auto-generated constructor stub
	}

	public boolean isItemValid(ItemStack p_75214_1_) {
		return true;
	}
}
