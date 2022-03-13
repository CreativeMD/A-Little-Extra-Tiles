package com.alet.common.structure.type;

import net.minecraft.block.state.IBlockState;

public class MutatorData {
    public IBlockState state;
    public int color;
    public boolean colision;
    
    public MutatorData() {
        
    }
    
    public MutatorData(IBlockState state, int color, boolean colision) {
        this.state = state;
        this.color = color;
        this.colision = colision;
    }
}