package khumps.firstmod.utils;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class BlockUtils {
	public static Random random = new Random();

	public static int getOppositeDirection(int playerDirection) {
		System.out.println(playerDirection);
		if (playerDirection == 0) {
			return 2;
		}

		if (playerDirection == 1) {
			return 5;
		}

		if (playerDirection == 2) {
			return 3;
		}

		if (playerDirection == 3) {
			return 4;
		}
		return 0;
	}

	public static void dropInventory(World world, int x, int y, int z, Block block, IInventory te) {

		if (te != null) {
			for (int i = 0; i < te.getSizeInventory(); ++i) {
				ItemStack itemstack = te.getStackInSlot(i);

				if (itemstack != null) {
					float f = random.nextFloat() * 0.8F + 0.1F;
					float f1 = random.nextFloat() * 0.8F + 0.1F;
					EntityItem entityitem;

					for (float f2 = random.nextFloat() * 0.8F + 0.1F; itemstack.stackSize > 0; world
							.spawnEntityInWorld(entityitem)) {
						int j1 = random.nextInt(21) + 10;

						if (j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1),
								(double) ((float) z + f2),
								new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
						float f3 = 0.05F;
						entityitem.motionX = (double) ((float) random.nextGaussian() * f3);
						entityitem.motionY = (double) ((float) random.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double) ((float) random.nextGaussian() * f3);

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem()
									.setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
					}
				}
			}

			world.func_147453_f(x, y, z, block);
		}
	}
}
