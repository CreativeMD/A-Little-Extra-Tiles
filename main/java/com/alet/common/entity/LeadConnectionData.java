package com.alet.common.entity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class LeadConnectionData {
    
    public int connectionID;
    public int color;
    public double thickness;
    public double tautness;
    public Set<UUID> uuidsConnected = new HashSet<UUID>();
    public Set<Integer> idsConnected = new HashSet<Integer>();
    public float lightLevel;
    
    public LeadConnectionData(int color, double thickness, double tautness, float lightLevel) {
        this.color = color;
        this.thickness = thickness;
        this.tautness = tautness;
        this.lightLevel = lightLevel;
    }
    
    @Override
    public boolean equals(Object data) {
        LeadConnectionData d = (LeadConnectionData) data;
        if (this.color == d.color && this.thickness == d.thickness && this.tautness == d.tautness && this.lightLevel == d.lightLevel)
            return true;
        else
            return false;
    }
}
