package khumps.firstmod;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import khumps.firstmod.blocks.BlockRegistry;
import khumps.firstmod.items.ItemRegistry;
import net.minecraft.init.Blocks;

@Mod(modid = Strings.MODID, version = Strings.VERSION)
public class FirstMod {
	@SidedProxy(modId = Strings.MODID, clientSide = "khumps.firstmod.ClientProxy", serverSide = "khumps.firstmod.ServerProxy")
	public static CommonProxy proxy;
	@Instance(Strings.MODID)
	public static FirstMod instance = new FirstMod();

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		proxy.preInit(e);
	}

	@EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
		System.out.println("DIRT BLOCK >> " + Blocks.dirt.getUnlocalizedName());
		ItemRegistry.init();
		BlockRegistry.init();
		NetworkRegistry.INSTANCE.registerGuiHandler(FirstMod.instance, new FirstModGuiHandler());
	}

	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
		Recipes.init();
	}
}
