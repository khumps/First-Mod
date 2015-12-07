package khumps.firstmod.items;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemRegistry {

	public static FoodToast toast;

	private static void create() {
		toast = new FoodToast(8, 3f, false, "toast");
	}

	private static void register() {
		GameRegistry.registerItem(toast, "toast");
	}

	public static void init() {
		create();
		register();
	}

}
