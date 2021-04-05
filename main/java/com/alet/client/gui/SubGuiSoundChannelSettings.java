package com.alet.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.alet.common.structure.type.LittleSoundPlayerALET.LittleSoundPlayerParserALET;
import com.creativemd.creativecore.common.gui.container.SubGui;
import com.creativemd.creativecore.common.gui.controls.gui.GuiButton;
import com.creativemd.creativecore.common.gui.controls.gui.GuiComboBox;
import com.creativemd.creativecore.common.gui.controls.gui.GuiLabel;
import com.creativemd.creativecore.common.gui.controls.gui.GuiTextfield;

public class SubGuiSoundChannelSettings extends SubGui {
	
	public LittleSoundPlayerParserALET littleSoundPlayerParserALET;
	
	public SubGuiSoundChannelSettings(LittleSoundPlayerParserALET littleSoundPlayerParserALET) {
		super(350, 200);
		this.littleSoundPlayerParserALET = littleSoundPlayerParserALET;
	}
	
	@Override
	public void createControls() {
		
		controls.add(new GuiButton("Done", 0, 180, 40) {
			@Override
			public void onClicked(int x, int y, int button) {
				closeGui();
			}
		});
		List<String> soundList = new ArrayList<String>();
		soundList.add("harp");
		soundList.add("banjo");
		soundList.add("bdrum");
		soundList.add("bell");
		soundList.add("bit");
		soundList.add("click");
		soundList.add("cow_bell");
		soundList.add("dbass");
		soundList.add("didgeridoo");
		soundList.add("flute");
		soundList.add("guitar");
		soundList.add("harp");
		soundList.add("icechime");
		soundList.add("iron_xylophone");
		soundList.add("pling");
		soundList.add("sdrum");
		soundList.add("xylobone");
		GuiComboBox sounds1 = new GuiComboBox("sounds1", 180, 0, 160, soundList);
		sounds1.select(littleSoundPlayerParserALET.CHSound1);
		controls.add(sounds1);
		
		GuiComboBox sounds2 = new GuiComboBox("sounds2", 180, 21, 160, soundList);
		sounds2.select(littleSoundPlayerParserALET.CHSound2);
		controls.add(sounds2);
		
		GuiComboBox sounds3 = new GuiComboBox("sounds3", 180, 42, 160, soundList);
		sounds3.select(littleSoundPlayerParserALET.CHSound3);
		controls.add(sounds3);
		
		GuiComboBox sounds4 = new GuiComboBox("sounds4", 180, 63, 160, soundList);
		sounds4.select(littleSoundPlayerParserALET.CHSound4);
		controls.add(sounds4);
		
		GuiComboBox sounds5 = new GuiComboBox("sounds5", 180, 84, 160, soundList);
		sounds5.select(littleSoundPlayerParserALET.CHSound5);
		controls.add(sounds5);
		
		GuiComboBox sounds6 = new GuiComboBox("sounds6", 180, 105, 160, soundList);
		sounds6.select(littleSoundPlayerParserALET.CHSound6);
		controls.add(sounds6);
		
		GuiComboBox sounds7 = new GuiComboBox("sounds7", 180, 126, 160, soundList);
		sounds7.select(littleSoundPlayerParserALET.CHSound7);
		controls.add(sounds7);
		
		GuiComboBox sounds8 = new GuiComboBox("sounds8", 180, 147, 160, soundList);
		sounds8.select(littleSoundPlayerParserALET.CHSound8);
		controls.add(sounds8);
		
		GuiComboBox sounds9 = new GuiComboBox("sounds9", 180, 168, 160, soundList);
		sounds9.select(littleSoundPlayerParserALET.CHSound9);
		controls.add(sounds9);
		
		GuiTextfield search1 = new GuiTextfieldSearch("search", "", 70, 0, 100, 14, "sounds1");
		controls.add(search1);
		
		GuiTextfield search2 = new GuiTextfieldSearch("search", "", 70, 21, 100, 14, "sounds2");
		controls.add(search2);
		
		GuiTextfield search3 = new GuiTextfieldSearch("search", "", 70, 42, 100, 14, "sounds3");
		controls.add(search3);
		
		GuiTextfield search4 = new GuiTextfieldSearch("search", "", 70, 63, 100, 14, "sounds4");
		controls.add(search4);
		
		GuiTextfield search5 = new GuiTextfieldSearch("search", "", 70, 84, 100, 14, "sounds5");
		controls.add(search5);
		
		GuiTextfield search6 = new GuiTextfieldSearch("search", "", 70, 105, 100, 14, "sounds6");
		controls.add(search6);
		
		GuiTextfield search7 = new GuiTextfieldSearch("search", "", 70, 126, 100, 14, "sounds7");
		controls.add(search7);
		
		GuiTextfield search8 = new GuiTextfieldSearch("search", "", 70, 147, 100, 14, "sounds8");
		controls.add(search8);
		
		GuiTextfield search9 = new GuiTextfieldSearch("search", "", 70, 168, 100, 14, "sounds9");
		controls.add(search9);
		
		controls.add(new GuiLabel("CH1:", 45, 2));
		controls.add(new GuiLabel("CH2:", 45, 23));
		controls.add(new GuiLabel("CH3:", 45, 44));
		controls.add(new GuiLabel("CH4:", 45, 65));
		controls.add(new GuiLabel("CH5:", 45, 86));
		controls.add(new GuiLabel("CH6:", 45, 107));
		controls.add(new GuiLabel("CH7:", 45, 128));
		controls.add(new GuiLabel("CH8:", 45, 149));
		controls.add(new GuiLabel("CH9:", 45, 170));
	}
	
	@Override
	public void onClosed() {
		GuiComboBox CH1 = (GuiComboBox) get("sounds1");
		GuiComboBox CH2 = (GuiComboBox) get("sounds2");
		GuiComboBox CH3 = (GuiComboBox) get("sounds3");
		GuiComboBox CH4 = (GuiComboBox) get("sounds4");
		GuiComboBox CH5 = (GuiComboBox) get("sounds5");
		GuiComboBox CH6 = (GuiComboBox) get("sounds6");
		GuiComboBox CH7 = (GuiComboBox) get("sounds7");
		GuiComboBox CH8 = (GuiComboBox) get("sounds8");
		GuiComboBox CH9 = (GuiComboBox) get("sounds9");
		
		if (CH1.getCaption() != null)
			littleSoundPlayerParserALET.CHSound1 = CH1.getCaption();
		if (CH2.getCaption() != null)
			littleSoundPlayerParserALET.CHSound2 = CH2.getCaption();
		if (CH3.getCaption() != null)
			littleSoundPlayerParserALET.CHSound3 = CH3.getCaption();
		if (CH4.getCaption() != null)
			littleSoundPlayerParserALET.CHSound4 = CH4.getCaption();
		if (CH5.getCaption() != null)
			littleSoundPlayerParserALET.CHSound5 = CH5.getCaption();
		if (CH6.getCaption() != null)
			littleSoundPlayerParserALET.CHSound6 = CH6.getCaption();
		if (CH7.getCaption() != null)
			littleSoundPlayerParserALET.CHSound7 = CH7.getCaption();
		if (CH8.getCaption() != null)
			littleSoundPlayerParserALET.CHSound8 = CH8.getCaption();
		if (CH9.getCaption() != null)
			littleSoundPlayerParserALET.CHSound9 = CH9.getCaption();
		
		super.onClosed();
	}
	
	private class GuiTextfieldSearch extends GuiTextfield {
		String comboBox;
		
		public GuiTextfieldSearch(String name, String text, int x, int y, int width, int height, String comboBox) {
			super(name, text, x, y, width, height);
			this.comboBox = comboBox;
		}
		
		@Override
		public boolean onKeyPressed(char character, int key) {
			boolean result = false;
			if (this.focused) {
				result = super.onKeyPressed(character, key);
				GuiComboBox sounds = (GuiComboBox) getParent().get(comboBox);
				
				List<String> soundList = new ArrayList<String>();
				soundList.add("harp");
				soundList.add("banjo");
				soundList.add("bdrum");
				soundList.add("bell");
				soundList.add("bit");
				soundList.add("click");
				soundList.add("cow_bell");
				soundList.add("dbass");
				soundList.add("didgeridoo");
				soundList.add("flute");
				soundList.add("guitar");
				soundList.add("harp");
				soundList.add("icechime");
				soundList.add("iron_xylophone");
				soundList.add("pling");
				soundList.add("sdrum");
				soundList.add("xylobone");
				
				List<String> foundSounds = new ArrayList<>();
				for (int i = 0; i < soundList.size(); i++) {
					if (soundList.get(i).toLowerCase().contains(text.toLowerCase()))
						foundSounds.add(soundList.get(i));
				}
				if (!foundSounds.isEmpty()) {
					sounds.lines = foundSounds;
					int index = soundList.indexOf(foundSounds.get(0));
					sounds.select(soundList.get(index));
				}
			}
			return result;
		}
	}
	
}
