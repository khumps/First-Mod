package khumps.firstmod.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class MachineUtils {

	public static boolean isFuel(ItemStack item) {
		return TileEntityFurnace.isItemFuel(item);
	}

	public static int getBurnTime(ItemStack item) {
		return TileEntityFurnace.getItemBurnTime(item);
	}

/*	public static void decStackSize(ItemStack stack, int amt) {
		if (stack != null) {
			stack.stackSize -= amt;
			if (stack.stackSize <= 0)
				stack = null;
		}
	}*/
	
	public static boolean canBeSmelted(ItemStack stack) {
		if (stack == null)
			return false;
		return FurnaceRecipes.smelting().getSmeltingResult(stack) != null;
	}

}
