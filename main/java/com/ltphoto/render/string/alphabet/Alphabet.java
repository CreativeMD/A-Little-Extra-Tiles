package com.ltphoto.render.string.alphabet;

import com.ltphoto.render.string.DrawCharacter.Facing;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class Alphabet {
	
	public BufferBuilder bufferbuilder;
	public RenderWorldLastEvent event;
	public int numOfVecs;
	
	public Alphabet(BufferBuilder bufferbuilder, RenderWorldLastEvent event) {
		this.bufferbuilder = bufferbuilder;
		this.event = event;
	}
	
	public void setBuffer(BufferBuilder bufferbuilder) {
		this.bufferbuilder = bufferbuilder;
	}
	
	public void setEvent(RenderWorldLastEvent event) {
		this.event = event;
	}
	
	public void character(char input, Vec3d start, float red, float green, float blue, float alpha) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		
		double minX = start.x;
		double minY = start.y;
		double minZ = start.z;
		
		double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
		double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
		double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();
		
		switch (input) {
		case 'A':
			A a = new A(start, Facing.UP, 1, bufferbuilder, event);
			break;
		case 'B':
			B b = new B(start, Facing.UP, 1, bufferbuilder, event);
			break;
		case 'C':
			C c = new C(start, Facing.UP, 1, bufferbuilder, event);
			break;
		case 'D':
			break;
		case 'E':
			E e = new E(start, Facing.UP, 1, bufferbuilder, event);
			break;
		case 'F':
			break;
		case 'G':
			break;
		case 'H':
			break;
		case 'I':
			I i = new I(start, Facing.UP, 1, bufferbuilder, event);
			break;
		case 'J':
			break;
		case 'K':
			K k = new K(start, Facing.UP, 1, bufferbuilder, event);
			break;
		case 'L':
			L l = new L(start, Facing.UP, 1, bufferbuilder, event);
			break;
		case 'M':
			break;
		case 'N':
			break;
		case 'O':
			O o = new O(start, Facing.UP, 1, bufferbuilder, event);
			break;
		case 'Q':
			break;
		case 'R':
			break;
		case 'S':
			break;
		case 'T':
			T t = new T(start, Facing.UP, 1, bufferbuilder, event);
			break;
		case 'U':
			break;
		case 'V':
			break;
		case 'W':
			break;
		case 'X':
			break;
		case 'Y':
			break;
		case 'Z':
			break;
		default:
		
		}
		
	}
}
