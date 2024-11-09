package org.rhm.better_saved_tabs.mixin;

import net.minecraft.world.item.Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.player.inventory.Hotbar;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

import org.rhm.better_saved_tabs.DummyInventory;

@Debug(export = true)
@Mixin(CreativeModeInventoryScreen.class)
public abstract class CreativeModeInventoryScreenMixin extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu>  {
	@Unique
	private boolean better_saved_tabs$ctrlDown;

	@Shadow private static CreativeModeTab selectedTab;

	public CreativeModeInventoryScreenMixin(CreativeModeInventoryScreen.ItemPickerMenu abstractContainerMenu, Inventory inventory, Component component) {
		super(abstractContainerMenu, inventory, component);
	}


	@Shadow protected abstract boolean isCreativeSlot(Slot arg);
	@Shadow protected abstract void selectTab(CreativeModeTab arg);
	@Shadow protected abstract void slotClicked(Slot arg, int m, int n, ClickType arg2);

	@Inject(method = "slotClicked", at = @At("HEAD"), cancellable = true)
	private void slotClicked(Slot slot, int i, int j, ClickType clickType, CallbackInfo ci) {
		if (!this.isCreativeSlot(slot)
				|| selectedTab.getType() != CreativeModeTab.Type.HOTBAR
				|| Minecraft.getInstance().player == null) return;

		int row = i/9;
		int slotId = (i+1)%9-1;
		if (slotId == -1) slotId = 8;
		LocalPlayer player = Minecraft.getInstance().player;
		ItemStack cursor = player.containerMenu.getCarried();
		Hotbar hotbar = Minecraft.getInstance().getHotbarManager().get(row);
		//? if <1.20.6 {
		/*List<ItemStack> items = new ArrayList<>(hotbar);
		*///?} else
		List<ItemStack> items = new ArrayList<>(hotbar.load(player.level().registryAccess()));

		if (better_saved_tabs$ctrlDown && cursor.isEmpty()) {
			items.set(slotId, ItemStack.EMPTY);
			better_saved_tabs$saveHotbar(hotbar, items, player.level().registryAccess());
			ci.cancel();
			return;
		}
		if (((!slot.getItem().isEmpty() && (slotId == row && slot.getItem().is(Items.PAPER))) && !better_saved_tabs$ctrlDown) || cursor.isEmpty()) return;


		items.set(slotId,cursor);
		better_saved_tabs$saveHotbar(hotbar, items, player.level().registryAccess());
		ci.cancel();
	}

	@Unique
	private void better_saved_tabs$saveHotbar(Hotbar hotbar, List<ItemStack> items, RegistryAccess access) {
		//? if <1.20.6 {
		/*for(int k = 0; k < Inventory.getSelectionSize(); ++k) {
			hotbar.set(k, items.get(k));
		}
		*///?} else {
		DummyInventory dummy = new DummyInventory();
		for(int k = 0; k < Inventory.getSelectionSize(); ++k) {
			dummy.setItem(k, items.get(k));
		}
		/*hotbar.storeFrom(dummy, access);*/
		//?}
		Minecraft.getInstance().getHotbarManager().save();
		selectTab(selectedTab);
	}

	@Inject(method = "keyPressed", at = @At("HEAD"))
	public void keyPressed(int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
		if (j == 29) { //left ctrl key
			better_saved_tabs$ctrlDown = true;
		}
	}

	@Inject(method = "keyReleased", at = @At("HEAD"))
	public void keyReleased(int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
		if (j == 29) { //left ctrl key
			better_saved_tabs$ctrlDown = false;
		}
	}
}