package org.rhm.better_saved_tabs.forge;

import net.minecraftforge.fml.loading.FMLPaths;
import org.rhm.better_saved_tabs.BetterSavedTabsCommon;
import net.minecraftforge.fml.common.Mod;

@Mod(BetterSavedTabsCommon.MOD_ID)
public class BetterSavedTabsForge {
	public BetterSavedTabsForge() {
		BetterSavedTabsCommon.init();
	}
}
