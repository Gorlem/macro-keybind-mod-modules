package ml.gorlem.modules.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.eq2online.macros.compatibility.AbstractionLayer;
import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacroEvent;
import net.eq2online.macros.scripting.api.IMacroEventDispatcher;
import net.eq2online.macros.scripting.api.IMacroEventManager;
import net.eq2online.macros.scripting.api.IMacroEventProvider;
import net.eq2online.macros.scripting.api.IMacroEventVariableProvider;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.eq2online.macros.scripting.parser.ScriptCore;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

import com.google.common.collect.Collections2;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;

@APIVersion(17)
public class EventProviderPlayer
	implements IMacroEventProvider, IMacroEventDispatcher, IMacroEventVariableProvider {
	
	private IMacroEvent onDeath;
	private IMacroEvent onRespawn;
	private IMacroEvent onPotionEffect;
	
	private boolean triggeredDeath = false;
	
	private Collection<PotionEffect> lastPotions;
	
	private Map<String, Object> vars = new HashMap<String, Object>();
	
	public EventProviderPlayer(IMacroEvent e) {
		
	}
	
	public EventProviderPlayer() {
		
	}

	@Override
	public void onInit() {
		ScriptCore core = ScriptContext.MAIN.getCore();
		core.registerEventProvider(this);
	}

	@Override
	public IMacroEventDispatcher getDispatcher() {
		return this;
	}

	@Override
	public void registerEvents(IMacroEventManager manager) {
		onDeath = manager.registerEvent(this, "onDeath");
		onRespawn = manager.registerEvent(this, "onRespawn");
		onPotionEffect = manager.registerEvent(this, "onPotionEffect");
		onPotionEffect.setVariableProviderClass(this.getClass());
	}

	@Override
	public String getHelp(IMacroEvent macroEvent, int eventId, int line) {
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onTick(IMacroEventManager manager, Minecraft minecraft) {
		EntityPlayer player = AbstractionLayer.getPlayer();
		if( player != null) {
			if( player.isDead && !triggeredDeath && onDeath != null ) {
				manager.sendEvent(onDeath);
				triggeredDeath = true;
			} else if( !player.isDead && triggeredDeath && onRespawn != null ) {
				manager.sendEvent(onRespawn);
				triggeredDeath = false;
			}
			
			if( onPotionEffect != null ) {
				List<PotionEffect> potions = new ArrayList<PotionEffect>(player.getActivePotionEffects());
				
				for(PotionEffect potion : potions) {
					if( !lastPotions.contains(potion) ) {
						manager.sendEvent(onPotionEffect,
								String.valueOf( potion.getPotionID() ),
								String.valueOf( potion.getAmplifier() ),
								String.valueOf( potion.getDuration() )
						);
					}
				}
				
				lastPotions = potions;
			}
		}
	}

	@Override
	public void updateVariables(boolean clock) {
		
	}

	@Override
	public Object getVariable(String variableName) {
		return vars.get(variableName);
	}

	@Override
	public Set<String> getVariables() {
		return vars.keySet();
	}

	@Override
	public void initInstance(String[] instanceVariables /* potionID, amplifier, duration */) {
		if( instanceVariables.length == 3 ) {
			Potion potion = Potion.potionTypes[Integer.valueOf(instanceVariables[0])];
			String potionName = StatCollector.translateToLocal(potion.getName());
			
			vars.put("NEWEFFECTID", instanceVariables[0]);
			vars.put("NEWEFFECT", potionName.toUpperCase().replace(" ", ""));
			
			int amplifier = Integer.valueOf(instanceVariables[1]);
			
			if (amplifier == 1)      potionName = potionName + " II";
	        else if (amplifier == 2) potionName = potionName + " III";
	        else if (amplifier == 3) potionName = potionName + " IV";
	        else if (amplifier == 4) potionName = potionName + " V";
	        else if (amplifier == 5) potionName = potionName + " VI";
			
			vars.put("NEWEFFECTNAME", potionName);
			vars.put("NEWEFFECTPOWER", amplifier + 1);
			vars.put("NEWEFFECTTIME", Integer.valueOf(instanceVariables[2]) / 20);
		}
	}

}
