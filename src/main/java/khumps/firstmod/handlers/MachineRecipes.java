package khumps.firstmod.handlers;

import java.util.HashMap;

import khumps.firstmod.utils.MachineConfig.MachineType;
import net.minecraft.item.ItemStack;

public class MachineRecipes {
	private static HashMap<ItemStack, ItemStack> smelterRecipes;
	private static HashMap<ItemStack, ItemStack> crusherRecipes;

	public static void addSmeltingRecipe(ItemStack in, ItemStack out) {
		smelterRecipes.put(in, out);
	}

	public static void addCrusherRecipe(ItemStack in, ItemStack out) {
		crusherRecipes.put(in, out);
	}

	public static boolean canBeSmelted(ItemStack in) {
		return smelterRecipes.containsKey(in);
	}

	public static boolean canBeCrushed(ItemStack in) {
		return crusherRecipes.containsKey(in);
	}

	public static HashMap getSmelterRecipes() {
		return smelterRecipes;
	}

	public static HashMap getCrusherRecipes() {
		return crusherRecipes;
	}

	public static ItemStack getProcessingResult(ItemStack stack, MachineType type) {
		switch (type) {
		case SMELTING: {
			return smelterRecipes.get(stack);
		}
		case CRUSHING: {
			return crusherRecipes.get(stack);
		}
		}
		return null;
	}

}
