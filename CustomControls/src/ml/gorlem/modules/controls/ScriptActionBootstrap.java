package ml.gorlem.modules.controls;

import ml.gorlem.modules.lib.controls.Bootstrap;
import net.eq2online.macros.gui.designable.DesignableGuiControl;
import net.eq2online.macros.scripting.api.APIVersion;

@APIVersion(17)
public class ScriptActionBootstrap extends Bootstrap {

	@Override
	public void onInit() {
		DesignableGuiControl.registerControlClass("item", CustomControlItem.class);
	}

}
