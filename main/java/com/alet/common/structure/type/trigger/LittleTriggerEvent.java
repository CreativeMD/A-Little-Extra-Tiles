package com.alet.common.structure.type.trigger;

import java.util.ArrayList;

import com.creativemd.creativecore.common.gui.CoreControl;
import com.creativemd.creativecore.common.gui.GuiControl;
import com.creativemd.creativecore.common.gui.container.GuiParent;
import com.creativemd.creativecore.common.gui.controls.gui.GuiPanel;

import net.minecraft.nbt.NBTTagCompound;

public abstract class LittleTriggerEvent {
	
	public String name = "";
	public String id = "";
	
	public LittleTriggerEvent(String id) {
		this.id = id;
	}
	
	public static LittleTriggerEvent getLittleTrigger(String name, String id) {
		
		System.out.println(name);
		switch (name) {
		case "Modify Motion":
			return new LittleTriggerModifyMotion(id);
		case "Modify Health":
			return new LittleTriggerModifyHealth(id);
		case "Execute Command":
			return new LittleTriggerExecuteCommand(id);
		default:
			break;
		}
		return null;
	}
	
	public static LittleTriggerEvent createFromNBT(NBTTagCompound nbt) {
		
		String id = nbt.getString("trigger");
		String[] split = id.split("(?<=\\D)(?=\\d)");
		String name = split[0];
		
		switch (name) {
		case "Modify Motion":
			return new LittleTriggerModifyMotion(id, nbt.getDouble("xStrength"), nbt.getDouble("yStrength"), nbt.getDouble("zStrength"), nbt.getDouble("forward"));
		case "Modify Health":
			return new LittleTriggerModifyHealth(id, nbt.getFloat("damageAmount"), nbt.getFloat("healAmount"), nbt.getString("damageType"), nbt.getInteger("effectPerTick"), nbt.getBoolean("heal"), nbt.getBoolean("harm"));
		case "Execute Command":
			return new LittleTriggerExecuteCommand(id, nbt.getString("command"));
		default:
			break;
		}
		return null;
		
	}
	
	public abstract NBTTagCompound createNBT();
	
	public abstract void updateControls(GuiParent parent);
	
	public abstract void updateValues(CoreControl source);
	
	public static void wipeControls(GuiPanel panel) {
		panel.controls = new ArrayList<GuiControl>();
	}
}
