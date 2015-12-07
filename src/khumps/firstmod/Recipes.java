package khumps.firstmod;

import cpw.mods.fml.common.registry.GameRegistry;
import khumps.firstmod.handlers.MachineRecipes;
import khumps.firstmod.items.ItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class Recipes {

	private static void initFurnaceRecipies() {
		GameRegistry.addSmelting(Items.bread, new ItemStack(ItemRegistry.toast), 3f);
	}

	private static void initCraftingRecipies() {

	}

	private static void initSmeltingRecipes() {
		MachineRecipes.addSmeltingRecipe(null, null);
		MachineRecipes.getSmelterRecipes().putAll(FurnaceRecipes.smelting().getSmeltingList());
	}

	private static void initCrusherRecipes() {

	}

	public static void init() {
		initFurnaceRecipies();
		initCraftingRecipies();
		//initSmeltingRecipes();
		initCrusherRecipes();
	}
}
