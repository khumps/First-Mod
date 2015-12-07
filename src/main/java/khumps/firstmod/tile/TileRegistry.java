package khumps.firstmod.tile;

import cpw.mods.fml.common.registry.GameRegistry;
import khumps.firstmod.Strings;

public class TileRegistry {

	private static void register() {
		GameRegistry.registerTileEntity(TileFurnaceBroken.class, Strings.MODID + "test");
		// GameRegistry.registerTileEntity(TileBasicMachine.class,
		// "tileBasicMachine");
	}

	public static void init() {
		register();
	}
}
