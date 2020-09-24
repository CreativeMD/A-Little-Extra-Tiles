package com.creativemd.littletiles.common.util.shape;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import com.alet.common.util.shape.DragShapeOddBox;
import com.alet.common.util.shape.DragShapeSliceWall;
import com.alet.common.util.shape.DragShapeTriangle;
import com.creativemd.creativecore.common.gui.GuiControl;
import com.creativemd.creativecore.common.gui.container.GuiParent;
import com.creativemd.creativecore.common.utils.math.Rotation;
import com.creativemd.littletiles.common.tile.math.box.LittleBoxes;
import com.creativemd.littletiles.common.tile.math.vec.LittleAbsoluteVec;
import com.creativemd.littletiles.common.tile.math.vec.LittleVec;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class DragShape {
	
	private static LinkedHashMap<String, DragShape> shapes = new LinkedHashMap<>();
	
	public static Collection<DragShape> shapes() {
		return shapes.values();
	}
	
	public static Set<String> keys() {
		return shapes.keySet();
	}
	
	public static void registerDragShape(DragShape shape) {
		shapes.put(shape.key, shape);
	}
	
	public static final DragShape box = new DragShapeBox();
	public static final DragShape sphere = new DragShapeSphere();
	public static final DragShape cylinder = new DragShapeCylinder();
	public static final DragShape wall = new DragShapeWall();
	public static final DragShape line = new DragShapeLine();
	public static final DragShape oddBox = new DragShapeOddBox();
	public static final DragShape sliceWall = new DragShapeSliceWall();
	public static final DragShape triangle = new DragShapeTriangle();
	
	public static final DragShape slice = new DragShapeSliced();
	
	public static final DragShape defaultShape = box;
	
	public static DragShape getShape(String name) {
		DragShape shape = DragShape.shapes.get(name);
		return shape == null ? DragShape.defaultShape : shape;
	}
	
	public final String key;
	
	public DragShape(String name) {
		this.key = name;
	}
	
	public abstract LittleBoxes getBoxes(LittleBoxes boxes, LittleVec min, LittleVec max, EntityPlayer player, NBTTagCompound nbt, boolean preview, LittleAbsoluteVec originalMin, LittleAbsoluteVec originalMax);
	
	public abstract void addExtraInformation(NBTTagCompound nbt, List<String> list);
	
	@SideOnly(Side.CLIENT)
	public abstract List<GuiControl> getCustomSettings(NBTTagCompound nbt, LittleGridContext context);
	
	@SideOnly(Side.CLIENT)
	public abstract void saveCustomSettings(GuiParent gui, NBTTagCompound nbt, LittleGridContext context);
	
	public abstract void rotate(NBTTagCompound nbt, Rotation rotation);
	
	public abstract void flip(NBTTagCompound nbt, Axis axis);
	
	static {
		registerDragShape(box);
		registerDragShape(sphere);
		registerDragShape(cylinder);
		registerDragShape(wall);
		registerDragShape(line);
		registerDragShape(triangle);
		//registerDragShape(oddBox);
		//registerDragShape(sliceWall);
		registerDragShape(slice);
	}
	
}
