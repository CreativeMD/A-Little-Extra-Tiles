package com.alet.client.gui.controls;

import javax.vecmath.Point2i;

import com.alet.common.util.RenderUtils;
import com.creativemd.creativecore.common.gui.GuiControl;
import com.creativemd.creativecore.common.gui.GuiRenderHelper;
import com.creativemd.creativecore.common.gui.client.style.Style;
import com.creativemd.creativecore.common.gui.container.GuiParent;
import com.creativemd.creativecore.common.utils.mc.ColorUtils;

public class GuiBezierCurve extends GuiParent {
    
    public Point2i p0 = new Point2i(0, 0);
    public Point2i p1 = new Point2i(0, 0);
    public Point2i p2 = new Point2i(0, 0);
    public Point2i p3 = new Point2i(0, 0);
    
    public GuiControl p0LockOn;
    public GuiControl p3LockOn;
    
    public GuiBezierCurve(String name, int x, int y, GuiControl p0LockOn, GuiControl p3LockOn, int width, int height) {
        super(name, x, y, width, height);
        this.p0LockOn = p0LockOn;
        this.p3LockOn = p3LockOn;
    }
    
    @Override
    protected void renderContent(GuiRenderHelper helper, Style style, int width, int height) {
        int multi = 20;
        p0.x = p0LockOn.posX + p0LockOn.getParent().posX;
        p3.x = p3LockOn.posX + p3LockOn.getParent().posX;
        p0.y = p0LockOn.posY + p0LockOn.getParent().posY;
        p3.y = p3LockOn.posY + p3LockOn.getParent().posY;
        
        int centerX = (p0.x - p3.x);
        
        p1.x = (centerX / 2) + p3.x;
        p1.y = p0.y;
        p2.x = (centerX / 2) + p3.x;
        p2.y = p3.y;
        RenderUtils
                .drawCubicBezier(new Point2i((p0.x + 4) * multi, (p0.y + 4) * multi), new Point2i((p1.x * multi), (p1.y + 4) * multi), new Point2i((p2.x * multi), (p2.y + 4) * multi), new Point2i((p3.x + 4) * multi, (p3.y + 4) * multi));
        
    }
    
    @Override
    public boolean hasBackground() {
        return false;
    }
    
    @Override
    public boolean hasBorder() {
        return false;
    }
    
    private class GuiDragNode extends GuiControl {
        
        boolean selected = false;
        
        public GuiDragNode(String name, int x, int y) {
            super(name, x, y, 1, 1);
            
        }
        
        @Override
        public boolean hasBackground() {
            return false;
        }
        
        @Override
        public boolean hasBorder() {
            return false;
        }
        
        @Override
        public boolean mousePressed(int x, int y, int button) {
            boolean results = this.isMouseOver();
            if (results) {
                selected = true;
                this.getGui().moveControlToTop(this);
            }
            return results;
        }
        
        @Override
        public void mouseReleased(int x, int y, int button) {
            selected = false;
        }
        
        @Override
        public void mouseDragged(int x, int y, int button, long time) {
            if (selected)
                if ((x < this.getParent().width - 7 && x > 2) && (y < this.getParent().height - 7 && y > 2)) {
                    this.posX = x - (this.width / 2);
                    this.posY = y - (this.height / 2);
                }
        }
        
        @Override
        protected void renderContent(GuiRenderHelper helper, Style style, int width, int height) {
            if (this.name.equals("p0")) {
                helper.drawRect(0, 0, 3, 3, ColorUtils.RED);
            }
            if (this.name.equals("p1")) {
                helper.drawRect(0, 0, 3, 3, ColorUtils.GREEN);
            }
            if (this.name.equals("p2")) {
                helper.drawRect(0, 0, 3, 3, ColorUtils.ORANGE);
            }
            if (this.name.equals("p3")) {
                helper.drawRect(0, 0, 3, 3, ColorUtils.BLUE);
            }
        }
        
    }
    
}
