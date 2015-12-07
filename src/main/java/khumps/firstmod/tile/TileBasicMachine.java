package khumps.firstmod.tile;

import khumps.firstmod.blocks.BlockFurnace;
import khumps.firstmod.blocks.BlockFurnaceLit;
import khumps.firstmod.utils.MachineConfig;
import khumps.firstmod.utils.MachineConfig.MachineTier;
import khumps.firstmod.utils.MachineConfig.MachineType;
import khumps.firstmod.utils.MachineUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class TileBasicMachine extends TileEntity implements IInventory {
	public final MachineType type;
	public MachineTier tier;
	protected ItemStack[] inventory;
	private MachineSlotSet[] slots;
	public int NUMSETS; // sets per machine
	public int NUMSLOTS; // slots per set
	public int EXTRASLOTS; // extra slot for the machine
	private String customName;
	protected boolean isBurning; // is consuming fuel
	private int burnTime; // time left before it needs to consume more fuel
	private int totalBurnTime; // burn time at the last fuel consumption, used
								// to scale the flames in the gui
	public int TOTALPROCESSTIME = 40; // time required to process an item
	public final int TOTALPROCESSTIMESCALED; // process time scaled to fit the
												// arrow in the gui

	public TileBasicMachine(MachineType type, MachineTier tier) {
		this.type = type;
		this.tier = tier;
		MachineConfig.setMachineType(this, type);
		MachineConfig.setMachineTier(this, tier);
		slots = new MachineSlotSet[NUMSETS];
		inventory = new ItemStack[NUMSETS * NUMSLOTS + EXTRASLOTS];
		initSlotSets();
		TOTALPROCESSTIMESCALED = 24;
	}

	public TileBasicMachine() {
		this(MachineType.SMELTING, MachineTier.BASIC);
	}

	private void initSlotSets() {
		inventory[0] = null;
		int j = 0;
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new MachineSlotSet(0, i * (MachineSlotSet.NUMSLOTS - 1) + 1,
					i * (MachineSlotSet.NUMSLOTS - 1) + 2, this, TOTALPROCESSTIME, i);
		}
	}

	@Override
	public void updateEntity() {
		if (burnTime == 0)
			setBurning(false);
		if (isBurning) {
			process();
			burnTime--;
		}
		if (!isProcessing()) {
			if (needsProcessing()) {
				consumeFuel();
			}
		}
	}

	private void process() {
		for (MachineSlotSet s : slots) {
			s.update();
		}
	}

	private boolean isProcessing() {
		for (MachineSlotSet s : slots) {
			if (s.isProcessing)
				return true;
		}
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		NBTTagList inventory = new NBTTagList();
		for (int i = 0; i < this.getSizeInventory(); ++i) {
			if (this.getStackInSlot(i) != null) {
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setByte("Slot", (byte) i);
				this.getStackInSlot(i).writeToNBT(stackTag);
				inventory.appendTag(stackTag);
			}
		}
		nbt.setTag("Items", inventory);
		nbt.setBoolean("isBurning", isBurning);
		nbt.setInteger("burnTime", burnTime);
		nbt.setInteger("totalBurnTime", totalBurnTime);

		for (int i = 0; i < slots.length; i++) {
			slots[i].writeToNBT(nbt);
		}

		if (this.hasCustomInventoryName()) {
			nbt.setString("CustomName", getInventoryName());
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		NBTTagList list = nbt.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); ++i) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
		}

		nbt.getBoolean("isBurning");
		nbt.getInteger("burnTime");
		nbt.getInteger("totalBurnTime");

		for (int i = 0; i < slots.length; i++) {
			slots[i].readFromNBT(nbt, i);
		}

		if (nbt.hasKey("CustomName", 8)) {
			this.customName = nbt.getString("CustomName");
		}
	}

	public void setBurning(boolean burning) {
		if (isBurning != burning) {
			if (isBurning) {
				isBurning = burning;
				BlockFurnaceLit.updateFurnaceBlockState(isBurning, worldObj, xCoord, yCoord, zCoord);
			} else {
				isBurning = burning;
				BlockFurnace.updateFurnaceBlockState(isBurning, worldObj, xCoord, yCoord, zCoord);
			}
		}

	}

	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory())
			return null;
		return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.getStackInSlot(index) != null) {
			ItemStack itemstack;

			if (this.getStackInSlot(index).stackSize <= count) {
				itemstack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, null);
				this.markDirty();
				return itemstack;
			} else {
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).stackSize <= 0) {
					this.setInventorySlotContents(index, null);
				} else {
					// Just to show that changes happened
					this.setInventorySlotContents(index, this.getStackInSlot(index));
				}

				this.markDirty();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		ItemStack stack = this.getStackInSlot(index);
		this.setInventorySlotContents(index, null);
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

		if (index < 0 || index >= this.getSizeInventory())
			return;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
			stack.stackSize = this.getInventoryStackLimit();

		if (stack != null && stack.stackSize == 0)
			stack = null;

		this.inventory[index] = stack;
		this.markDirty();
	}

	public void consumeFuel() {
		if (needsProcessing() && inventory[0] != null) {
			setBurning(true);
			burnTime = MachineUtils.getBurnTime(inventory[0]);
			totalBurnTime = burnTime;
			decrStackSize(0, 1);
		}
	}

	private boolean needsProcessing() {
		if (!isBurning) {
			for (MachineSlotSet s : slots) {
				if (s.stuffToSmelt()) {
					return true;
				}
			}
		}
		return false;
	}

	public int getCookProgressScaled(int slot, int maxScaledValue) {
		int progress = slots[slot].getSmeltTime();
		System.out.println(progress);
		if (!slots[slot].isProcessing)
			return maxScaledValue;
		double max = maxScaledValue;
		double val = (double) max / TOTALPROCESSTIME * progress;
		return Math.toIntExact(Math.round(val));
	}

	public int getBurnTimeRemainingScaled(int maxScaledValue) {
		if (totalBurnTime == 0)
			return 0;
		double temp = (double) maxScaledValue / totalBurnTime;
		double val = temp * burnTime;
		return Math.toIntExact(Math.round(val));
	}

	@Override
	public String getInventoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasCustomInventoryName() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	public MachineSlotSet[] getSlots() {
		return slots;
	}

}
