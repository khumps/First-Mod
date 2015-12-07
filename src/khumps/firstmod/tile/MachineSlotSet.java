package khumps.firstmod.tile;

import java.util.HashMap;

import khumps.firstmod.handlers.MachineRecipes;
import khumps.firstmod.utils.MachineUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

public class MachineSlotSet {
	public final int SLOTNUM;
	public static final int FUELSLOT = 0;
	public static final int INSLOT = 1;
	public static final int OUTSLOT = 2;
	private int fuel;
	private int in;
	private int out;
	protected TileBasicMachine machine;
	protected boolean isProcessing;
	private int TIMETOPROCESS;
	private int processTime;
	public static final int NUMSLOTS = 3;

	public MachineSlotSet(int fuel, int in, int out, TileBasicMachine machine, int processTime, int slotNum) {
		this.fuel = fuel;
		this.in = in;
		this.out = out;
		this.machine = machine;
		TIMETOPROCESS = processTime;
		SLOTNUM = slotNum;
	}

	public void update() {
		if (canSmelt()) {
			if (isProcessing) {
				if (getSmeltTime() == 0) {
					cookItem();
					isProcessing = false;
					processTime = TIMETOPROCESS;
				} else
					setSmeltTime(getSmeltTime() - 1);
			} else {
				if (canBeSmelted()) {
					if (machine.isBurning)
						processTime = TIMETOPROCESS;
					isProcessing = true;
				}
			}
		}
	}

	public boolean stuffToSmelt() {
		return machine.inventory[in] != null;
	}

	private void cookItem() {
		if (canBeSmelted()) {

			if (addToStack(out, getProcessedItem(machine.inventory[in], 1), 1))
				machine.decrStackSize(in, 1);
		}
	}

	public boolean isValidForSlot(int slot, ItemStack stack) {
		if (slot == fuel) {
			return MachineUtils.isFuel(stack);
		}
		if (slot == in) {
			return FurnaceRecipes.smelting().getSmeltingResult(stack) != null;
		}
		if (slot == out) {
			return false;
		}
		return true;
	}

	public boolean isSmelting() {
		return isProcessing;
	}

	public boolean canSmelt() {
		if (isProcessing && machine.inventory[in] == null) {
			processTime = TIMETOPROCESS;
			return false;
		}
		if (machine.isBurning) {
			if (machine.inventory[in] != null && machine.inventory[in].stackSize != 0) {
				if (machine.inventory[out] == null
						|| sameItem(getSmeltingResult(machine.inventory[in]), machine.inventory[out])) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean canBeSmelted() {
		return machine.inventory[in] != null && getSmeltingResult(machine.inventory[in]) != null;
	}

	private void moveStack(int from, int to, int numItems) {
		if (machine.inventory[to] == null) {
			machine.inventory[to] = new ItemStack(machine.inventory[from].getItem());
			machine.inventory[to].stackSize = numItems;
			machine.decrStackSize(from, numItems);
		}
	}

	private static ItemStack getSmeltingResult(ItemStack item) {
		return FurnaceRecipes.smelting().getSmeltingResult(item);
	}

	private ItemStack getProcessedItem(ItemStack item, int numItems) {
		ItemStack temp = MachineRecipes.getProcessingResult(item, machine.type);
		temp.stackSize = numItems;
		return temp;
	}

	public boolean hasSlot(int slot) {
		return (slot == fuel || slot == in || slot == out);
	}

	private boolean addToStack(int slot, ItemStack item, int numItems) {
		if (machine.inventory[slot] == null) {
			machine.inventory[slot] = new ItemStack(item.getItem());
			machine.inventory[slot].stackSize = numItems;
			return true;
		} else if (machine.inventory[slot].isItemEqual(item)) {
			machine.inventory[slot].stackSize += numItems;
			return true;
		}
		return false;
	}

	public static boolean sameItem(ItemStack a, ItemStack b) {
		if (a == null) {
			return b == null;
		}
		if (b != null) {
			return a.getItem() == b.getItem();
		}
		return false;
	}

	public int getSmeltTime() {
		return processTime;
	}

	public void setSmeltTime(int smeltTime) {
		this.processTime = smeltTime;
	}

	public void writeToNBT(NBTTagCompound compound) {
		NBTTagCompound set = new NBTTagCompound();
		set.setInteger("fuel", fuel);
		set.setInteger("in", in);
		set.setInteger("out", out);
		// set.setTag("furnace", compound);
		set.setBoolean("isSmelting", isProcessing);
		set.setInteger("TIMETOSMELT", TIMETOPROCESS);
		set.setInteger("smeltTime", processTime);
		compound.setTag(SLOTNUM + "", set);

	}

	public void readFromNBT(NBTTagCompound compound, int slotNum) {
		NBTTagCompound set = compound.getCompoundTag(slotNum + "");
		fuel = set.getInteger("fuel");
		in = set.getInteger("in");
		out = set.getInteger("out");
		isProcessing = set.getBoolean("isSmelting");
		TIMETOPROCESS = set.getInteger("TIMETOSMELT");
		processTime = set.getInteger("smeltTime");
	}
}