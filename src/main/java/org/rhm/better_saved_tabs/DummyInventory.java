package org.rhm.better_saved_tabs;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class DummyInventory extends Inventory {
    NonNullList<ItemStack> itemStacks;

    public DummyInventory() {
        super(null);
        itemStacks = NonNullList.withSize(Inventory.getSelectionSize(), ItemStack.EMPTY);
    }

    @Override
    public void setItem(int i, ItemStack itemStack) {
        itemStacks.set(i, itemStack);
    }

    @Override
    public ItemStack getItem(int i) {
        return itemStacks.get(i);
    }
}
