package org.rhm.better_saved_tabs.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.rhm.better_saved_tabs.BetterSavedTabsCommon;

public class BetterSavedTabsFabric implements ModInitializer {
	@Override
	public void onInitialize() {
		BetterSavedTabsCommon.init();
	}
}
