package tallestegg.guardvillagers.entities;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class GuardContainer extends Container {
    private final IInventory guardInventory;
    private final GuardEntity guard;

    public GuardContainer(int id, PlayerInventory playerInventory, IInventory guardInventory, final GuardEntity guard) {
        super((ContainerType<?>) null, id);
        this.guardInventory = guardInventory;
        this.guard = guard;
        guardInventory.openInventory(playerInventory.player);
        this.addSlot(new Slot(guardInventory, 0, 8, 18) {
            public boolean isItemValid(ItemStack stack) {
                return true;
            }

            public boolean isEnabled() {
                return true;
            }
        });
        this.addSlot(new Slot(guardInventory, 1, 8, 36) {
            /**
             * Check if the stack is allowed to be placed in this slot, used for armor slots
             * as well as furnace fuel.
             */
            public boolean isItemValid(ItemStack stack) {
                return true;
            }

            public boolean isEnabled() {
                return true;
            }

            public int getSlotStackLimit() {
                return 1;
            }
        });
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.guardInventory.isUsableByPlayer(playerIn) && this.guard.isAlive() && this.guard.getDistance(playerIn) < 8.0F;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            int i = this.guardInventory.getSizeInventory();
            if (index < i) {
                if (!this.mergeItemStack(itemstack1, i, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).isItemValid(itemstack1) && !this.getSlot(1).getHasStack()) {
                if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).isItemValid(itemstack1)) {
                if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (i <= 2 || !this.mergeItemStack(itemstack1, 2, i, false)) {
                int j = i + 27;
                int k = j + 9;
                if (index >= j && index < k) {
                    if (!this.mergeItemStack(itemstack1, i, j, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= i && index < j) {
                    if (!this.mergeItemStack(itemstack1, j, k, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.mergeItemStack(itemstack1, j, j, false)) {
                    return ItemStack.EMPTY;
                }

                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        this.guardInventory.closeInventory(playerIn);
    }
}