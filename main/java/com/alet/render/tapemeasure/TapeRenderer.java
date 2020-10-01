package com.alet.render.tapemeasure;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.util.Color;

import com.alet.ALET;
import com.alet.common.util.TapeMeasureKeyEventHandler;
import com.alet.items.ItemTapeMeasure;
import com.alet.items.ItemTapeMeasure.PosData;
import com.alet.render.string.StringRenderer;
import com.alet.render.string.DrawCharacter.Facing;
import com.alet.render.string.StringRenderer.Middle;
import com.alet.render.tapemeasure.shape.Box;
import com.alet.render.tapemeasure.shape.Circle;
import com.alet.render.tapemeasure.shape.Line;
import com.alet.tiles.SelectLittleTile;
import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.creativemd.littletiles.client.render.overlay.PreviewRenderer;
import com.creativemd.littletiles.common.action.LittleAction;
import com.creativemd.littletiles.common.item.ItemMultiTiles;
import com.creativemd.littletiles.common.tile.math.vec.LittleAbsoluteVec;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.ingredient.LittleIngredients;
import com.creativemd.littletiles.common.util.ingredient.LittleInventory;
import com.creativemd.littletiles.common.util.ingredient.NotEnoughIngredientsException;
import com.creativemd.littletiles.common.util.ingredient.StackIngredient;
import com.creativemd.littletiles.common.util.ingredient.StackIngredientEntry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TapeRenderer {
	
	public static int counter = 0;
	public static boolean inInv = false;
	public static Minecraft mc = Minecraft.getMinecraft();
	public static EntityPlayer player;
	public static Tessellator tessellator = Tessellator.getInstance();
	public static BufferBuilder bufferbuilder = tessellator.getBuffer();
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void render(RenderWorldLastEvent event) {
		player = mc.player;

		List<String> list = LittleGridContext.getNames();
	    List<String> allMatches = new ArrayList<String>();
		List<Integer> allIndexes = new ArrayList<Integer>();
		
		ItemStack stack = ItemStack.EMPTY;
		ItemStack ingredient = new ItemStack(ALET.tapeMeasure, 1);
		LittleInventory inventory = new LittleInventory(player);
		
		//Sees if the tape measure is in player's inventory
		inInv = false;
		for(int i = 0; i < inventory.size(); i++) {
			if(inventory.get(i).getItem() instanceof ItemTapeMeasure) {
				stack = inventory.get(i);
				inInv = true;
				break;
			}
		}
		NBTTagCompound nbt = (stack.hasTagCompound()) ? stack.getTagCompound() : new NBTTagCompound();

		//Makes sure it has at least one context. Without a context it will not render anything.
		if(nbt.hasNoTags() || !nbt.hasKey("context0")) {
			nbt.setInteger("context0", 0);
			stack.setTagCompound(nbt);
		}
		
		String nbtStr = (stack.hasTagCompound()) ? nbt.toString() : "";
	    Matcher m = Pattern.compile("context\\d+").matcher(nbtStr);
	    while (m.find()) {
	      allMatches.add(m.group());
	    }
	    for (String string : allMatches) {
	    	int x = Integer.parseInt(string.split("context")[1]);
		    allIndexes.add(x);
		}
	    
	    GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.glLineWidth(2.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
		GlStateManager.disableDepth();
		bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
		PosData data = ItemTapeMeasure.data;
		if(data != null) {
		    for(int index : allIndexes) {
	    		int index1 = index;
	    		int index2 = index+1;
	    		int selectedIndex = (nbt.hasKey("index")) ? nbt.getInteger("index")*2 : 0;
				int contextSize = (nbt.hasKey("context"+index1)) ? Integer.parseInt(list.get(nbt.getInteger("context"+index1))) : Integer.parseInt(list.get(0));
	    		int selectedContextSize = (nbt.hasKey("context"+selectedIndex)) ? Integer.parseInt(list.get(nbt.getInteger("context"+selectedIndex))) : Integer.parseInt(list.get(0));
	
				//Gets Color
				int color = (nbt.hasKey("color"+index1)) ? nbt.getInteger("color"+index1) : ColorUtils.WHITE;
	    		Color colour = ColorUtils.IntToRGBA(color);
	    		float r = colour.getRed()/255F;
	    		float g = colour.getGreen()/255F;
	    		float b = colour.getBlue()/255F;
	    		//*********
	    		
				EnumFacing facingMin = (nbt.hasKey("facing"+index1)) ? EnumFacing.byName(nbt.getString("facing"+index1)) : EnumFacing.UP;
				EnumFacing facingMax = (nbt.hasKey("facing"+index2)) ? EnumFacing.byName(nbt.getString("facing"+index2)) : EnumFacing.UP;
								
				double[] posEdit = ItemTapeMeasure.facingOffset(data.result.hitVec.x, data.result.hitVec.y, data.result.hitVec.z, contextSize, data.result.sideHit);
				SelectLittleTile tilePosMin = data.tilePosCursor;
				SelectLittleTile tilePosMax = data.tilePosCursor;

				boolean[] shouldRender = {false, false};
				if(nbt.hasKey("x"+index1)) { //If the nbt has x then it will have y and z. If it does use the constant nbt value to display measurement
					tilePosMin = new SelectLittleTile(new Vec3d(Double.parseDouble(nbt.getString("x"+index1)), Double.parseDouble(nbt.getString("y"+index1)),
						Double.parseDouble(nbt.getString("z"+index1))),LittleGridContext.get(contextSize), data.result.sideHit);
					shouldRender[0] = true;
				}
				
				if(nbt.hasKey("x"+index2)) { //If the nbt has x then it will have y and z. If it does use the constant nbt value to display measurement
					tilePosMax = new SelectLittleTile(new Vec3d(Double.parseDouble(nbt.getString("x"+index2)), Double.parseDouble(nbt.getString("y"+index2)),
						Double.parseDouble(nbt.getString("z"+index2))),LittleGridContext.get(contextSize), data.result.sideHit);
					shouldRender[1] = true;
				}
				
				if(shouldRender[0] || shouldRender[1])
					if(nbt.getInteger("shape"+index1) == 0) {
						Box.drawBox(tilePosMin, tilePosMax, r, g, b, 1.0F);
					}else if(nbt.getInteger("shape"+index1) == 1) {
						Line.drawLine(tilePosMin, tilePosMax, r, g, b, 1.0F);
						Box.drawBox(tilePosMin, contextSize, r, g, b, 1.0F);
						Box.drawBox(tilePosMax, contextSize,r, g, b, 1.0F);
					}
		    }
		}
		tessellator.draw();

		GlStateManager.enableDepth();
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();	
	}
	
	public static void renderCursor(NBTTagCompound nbt, int index1, int contextSize, SelectLittleTile tilePosCursor) {
		int color = (nbt.hasKey("color"+index1)) ? nbt.getInteger("color"+index1) : ColorUtils.WHITE;
		Color colour = ColorUtils.IntToRGBA(color);
		float r = colour.getRed()/255F;
		float g = colour.getGreen()/255F;
		float b = colour.getBlue()/255F;

		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.glLineWidth(2.0F);
		GlStateManager.disableTexture2D();
		GlStateManager.depthMask(false);
		GlStateManager.disableDepth();
		bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
		
		Box.drawBox(tilePosCursor, contextSize, r, g, b, 1.0F);
		
		tessellator.draw();

		GlStateManager.enableDepth();
		GlStateManager.depthMask(true);
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();	
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 
					if(i==0) {
						Box.drawBox(tilePosMin, tilePosMax, 1.0F, 0.0F, 0.0F, 1.0F);
					//	Box.drawBox(centerX_1, centerY_1, centerZ_1, contextSize, 1.0F, 0.0F, 0.0F, 1.0F);
					//	Box.drawBox(centerX_2, centerY_2, centerZ_2, contextSize, 1.0F, 0.0F, 0.0F, 1.0F);
					//	Line.drawLine(bufferbuilder, centerX_1, centerY_1, centerZ_1, centerX_2, centerY_2, centerZ_2, 1.0F, 0.0F, 0.0F, 1.0F);
					}
					if(i==2) {
						Box.drawBox(tilePosMin, contextSize, 0.0F, 1.0F, 0.0F, 1.0F);
						Box.drawBox(tilePosMax, contextSize, 0.0F, 1.0F, 0.0F, 1.0F);
						Line.drawLine(bufferbuilder, tilePosMin, tilePosMax, 0.0F, 1.0F, 0.0F, 1.0F);
					}
					if(i==4) {
						Box.drawBox(tilePosMin, tilePosMax, 0.0F, 0.0F, 1.0F, 1.0F);
					}
	 */
	
		/*
		for(int i=0;i<tape.measure.size();i+=2) {
			if(tape.measure.get(i) != null && tape.measure.get(i+1) != null) {
				//System.out.println(i);
				double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
				double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
				double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();

				double centerX_1 = tape.measure.get(i).centerX;
				double centerY_1 = tape.measure.get(i).centerY;
				double centerZ_1 = tape.measure.get(i).centerZ;
				
				double centerX_2 = tape.measure.get(i+1).centerX;
				double centerY_2 = tape.measure.get(i+1).centerY;
				double centerZ_2 = tape.measure.get(i+1).centerZ;
				
				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
				GlStateManager.glLineWidth(2.0F);
				GlStateManager.disableTexture2D();
				GlStateManager.depthMask(false);
				GlStateManager.disableDepth();
				
				bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
				
				switch (tape.measure.get(i).shapeType) {
				case Box:
					player.sendStatusMessage(new TextComponentString("X: " + distence(centerX_1, centerX_2)+ " Y: " + distence(centerY_1, centerY_2) + " Z: " + distence(centerZ_1, centerZ_2)), true);
					Box.drawBox(centerX_1, centerX_2, centerY_1, centerY_2, centerZ_1, centerZ_2, tape, i);
					break;
				case Line:
					//player.sendStatusMessage(new TextComponentString(Line.distence(tape.measure.get(i).center, tape.measure.get(i+1).center)), true);
					if(i==0)
						Line.drawLine(bufferbuilder, centerX_1, centerY_1, centerZ_1, centerX_2, centerY_2, centerZ_2, 1.0F, 0.0F, 0.0F, 1.0F);
					if(i==2)
						Line.drawLine(bufferbuilder, centerX_1, centerY_1, centerZ_1, centerX_2, centerY_2, centerZ_2, 0.0F, 1.0F, 0.0F, 1.0F);
				
					//StringRenderer.drawString(Middle.T, i, tape, Line.distence(tape.measure.get(i).center, tape.measure.get(i+1).center), Facing.UP, 0.0F, 1.0F, 0F, 1.0F);

					break;
				case Circle:
					Circle.drawCircle(centerX_1, centerY_1, centerZ_1, radius(centerX_1, centerX_2), 1.0F, 1.0F, 1.0F, 1.0F);				
					break;
				default:
					break;
				}
				
				//StringRenderer.drawString(Middle.Z, i, tape, distence(centerZ_1, centerZ_2), Facing.UP ,0.0F, 1.0F, 0F, 1.0F);
				//StringRenderer.drawString(Middle.X, i, tape, distence(centerX_1, centerX_2), Facing.WEST, 0.0F, 1.0F, 0F, 1.0F);
				//StringRenderer.drawString(Middle.Y, i, tape, distence(centerY_1, centerY_2), Facing.UP, 0.0F, 1.0F, 0F, 1.0F);
				
				//StringRenderer.drawString(Middle.Y, i, tape, "Y", Facing.EAST, 0.0F, 1.0F, 0F, 1.0F);

				//System.out.println(bufferbuilder.getVertexCount());
				tessellator.draw();
				//System.out.println(bufferbuilder.getVertexCount());

				GlStateManager.enableDepth();
				GlStateManager.depthMask(true);
				GlStateManager.enableTexture2D();
				GlStateManager.disableBlend();	
			}
		}
*/
		//Box.drawBox(centerX_1, centerX_2, centerY_1, centerY_2, centerZ_1, centerZ_2, tape);
		//Box.drawBox(centerX_1, centerX_2, centerY_1, centerY_2, centerZ_1, centerZ_2, tape);

		//Circle.drawCircle(centerX_1, centerY_1, centerZ_1, radius(centerX_1, centerX_2), 1.0F, 1.0F, 1.0F, 1.0F);
		//Circle.drawCircle(centerX_1, centerY_1, centerZ_1, radius(centerX_1, centerX_2), 1.0F, 1.0F, 1.0F, 1.0F);
		

