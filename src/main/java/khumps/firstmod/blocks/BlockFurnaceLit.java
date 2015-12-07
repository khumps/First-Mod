package khumps.firstmod.blocks;

import khumps.firstmod.FirstMod;
import khumps.firstmod.FirstModGuiHandler;
import khumps.firstmod.Strings;
import khumps.firstmod.tile.Broken;
import khumps.firstmod.utils.BlockUtils;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockFurnaceLit extends BlockContainer {
	public IIcon[] icons = new IIcon[6];
	private int direction; // 0 = N, 1 = E, 2 = S, 3 = W
	protected IIcon front;
	protected IIcon top;

	public BlockFurnaceLit(String name) {
		super(Material.rock);
		setBlockName(Strings.MODID + "_" + name).setCreativeTab(CreativeTabs.tabMisc).setHardness(2f).setResistance(6f)
				.setHarvestLevel("pickaxe", 2);
		setBlockTextureName(Strings.MODID + ":" + "furnace");
	}

	public void registerBlockIcons(IIconRegister reg) {
		/*
		 * for (int i = 1; i < 7; i++) { icons[i - 1] =
		 * reg.registerIcon(textureName + "/furnace_" + (i));
		 */
		top = reg.registerIcon(textureName + "/furnace_top");
		front = reg.registerIcon(textureName + "/furnace_burning");
	}

	public IIcon getIcon(int side, int meta) { // 0 Bot, 1 top, 2 N, 3 S, 4 W, 5
												// E
		if (meta <= 1) {
			if (side == 3)
				return front;
			else
				return top;
		}
		if (side == 0 || side == 1)
			return top;
		if (side == meta)
			return front;

		return top;
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
		int playerDirection = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, BlockUtils.getOppositeDirection(playerDirection), 2);

		if (stack.hasDisplayName()) {
			((TileEntityFurnace) world.getTileEntity(x, y, z)).func_145951_a(stack.getDisplayName());
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new Broken();
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_,
			float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		System.out.println("OPENED");
		if (!world.isRemote) {
			System.out.println("Dfsd");
			player.openGui(FirstMod.instance, FirstModGuiHandler.FURNACE_ID, world, x, y, z);
		}
		return true;
	}

	public static void updateFurnaceBlockState(boolean burning, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (!burning) {
			world.setBlock(x, y, z, BlockRegistry.furnace, te.getBlockMetadata(), 3);
			te.validate();
			world.setTileEntity(x, y, z, te);
		}
	}

}