package khumps.firstmod.blocks;

import cpw.mods.fml.common.registry.GameRegistry;

public class BlockRegistry {

	public static BlockFurnace furnace;
	public static BlockFurnaceLit furnaceLit;
	public static BlockBasicMachine basicMachine;

	private static void create() {
		furnace = new BlockFurnace("furnace");
		furnaceLit = new BlockFurnaceLit("furnace_lit");
		basicMachine = new BlockBasicMachine("basicMachine");
	}

	private static void register() {
		GameRegistry.registerBlock(furnace, "furnace");
		GameRegistry.registerBlock(furnaceLit, "furnace_lit");
		//GameRegistry.registerBlock(basicMachine, "basicMachine");
	}

	public static void init() {
		create();
		register();
	}
}
