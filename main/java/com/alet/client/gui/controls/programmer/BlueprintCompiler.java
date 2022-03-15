package com.alet.client.gui.controls.programmer;

import java.util.LinkedHashMap;
import java.util.Map;

import com.alet.client.gui.SubGuiMicroProcessor;
import com.alet.client.gui.controls.programmer.blueprints.GuiBluePrintNode;
import com.alet.common.programmer.functions.FunctionBranch;
import com.alet.common.programmer.functions.FunctionEventPulseReceived;
import com.alet.common.programmer.functions.FunctionIsInputEqual;
import com.alet.common.programmer.functions.FunctionSetOutput;
import com.alet.common.programmer.functions.FunctionSleep;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class BlueprintCompiler {
    
    public static Map<String, Function> functions = new LinkedHashMap<String, Function>();
    
    public static Map<String, Function> readScript(NBTTagCompound script) {
        functions.put("event1", new FunctionEventPulseReceived("isEqual2", "i0").setFunctionList(functions));
        functions.put("isEqual2", new FunctionIsInputEqual("sleep3", "i1", "i2").setFunctionList(functions));
        functions.put("sleep3", new FunctionSleep("branch4", "50").setFunctionList(functions));
        functions.put("branch4", new FunctionBranch("", "isEqual2", "setOutput4", "setOutput5").setFunctionList(functions));
        functions.put("setOutput5", new FunctionSetOutput("", "true", "o10").setFunctionList(functions));
        functions.put("setOutput6", new FunctionSetOutput("", "false", "o10").setFunctionList(functions));
        return functions;
    }
    
    public static String getValue(Object value) {
        if (value instanceof Boolean[]) {
            Boolean[] io = (Boolean[]) value;
            String v = "[";
            
            for (int i = 0; i < io.length; i++) {
                v += String.valueOf(io[i]);
                if (i != io.length - 1)
                    v += ",";
            }
            
            return v + "]";
        }
        if (value instanceof Integer) {
            Integer i = (Integer) value;
            return String.valueOf(i.intValue());
        }
        return "";
    }
    
    public static NBTTagCompound compileScriptToNBT(SubGuiMicroProcessor subGui) {
        
        NBTTagList nbtListObject = new NBTTagList();
        NBTTagList nbtListSender = new NBTTagList();
        subGui.scriptNBT = new NBTTagCompound();
        if (!subGui.eventNode.methodSenderConn.receiver.isEmpty()) {
            NBTTagCompound nbtSender = new NBTTagCompound();
            NBTTagCompound nbtReturn = new NBTTagCompound();
            BluePrintConnection conn = (BluePrintConnection) subGui.eventNode.methodSenderConn.receiver.get(0);
            GuiBluePrintNode receiver = conn.parentNode;
            for (BluePrintConnection c : subGui.eventNode.connections) {
                if (BluePrintConnection.METHOD_SENDER_CONNECTION == c.connectionType) {
                    if (c.receiver.size() != 0) {
                        BluePrintConnection cRec = (BluePrintConnection) c.receiver.get(0);
                        nbtSender.setString(c.name, cRec.parentNode.name + "." + cRec.name);
                    }
                }
                
            }
            nbtListSender.appendTag(nbtSender);
            
            nbtListObject.appendTag(nbtListSender);
            
            subGui.scriptNBT.setTag(subGui.eventNode.name, nbtListObject);
            nextNode(receiver, subGui);
        }
        return subGui.scriptNBT;
    }
    
    public static void nextNode(GuiBluePrintNode node, SubGuiMicroProcessor subGui) {
        NBTTagList nbtListObject = new NBTTagList();
        NBTTagList nbtListSender = new NBTTagList();
        NBTTagList nbtListParameter = new NBTTagList();
        NBTTagCompound nbtSender = new NBTTagCompound();
        NBTTagCompound nbtParameter = new NBTTagCompound();
        for (BluePrintConnection c : node.connections) {
            if (BluePrintConnection.METHOD_SENDER_CONNECTION == c.connectionType) {
                if (c.receiver.size() != 0) {
                    BluePrintConnection cRec = (BluePrintConnection) c.receiver.get(0);
                    nbtSender.setString(c.name, cRec.parentNode.name + "." + cRec.name);
                    nextNode(cRec.parentNode, subGui);
                }
            }
            if (BluePrintConnection.PARAMETER_CONNECTION == c.connectionType) {
                if (c.sender != null) {
                    BluePrintConnection cSend = (BluePrintConnection) c.sender;
                    nbtParameter.setString(c.name, cSend.parentNode.name + "." + cSend.name);
                    connections(cSend.parentNode, subGui);
                } else {
                    nbtParameter.setString(c.name, getValue(c.value));
                }
            }
        }
        nbtListSender.appendTag(nbtSender);
        nbtListParameter.appendTag(nbtParameter);
        
        nbtListObject.appendTag(nbtListSender);
        nbtListObject.appendTag(nbtListParameter);
        subGui.scriptNBT.setTag(node.name, nbtListObject);
        
    }
    
    public static void connections(GuiBluePrintNode node, SubGuiMicroProcessor subGui) {
        
        NBTTagList nbtListObject = new NBTTagList();
        NBTTagList nbtListParameter = new NBTTagList();
        NBTTagCompound nbtParameter = new NBTTagCompound();
        for (BluePrintConnection c : node.connections) {
            if (BluePrintConnection.PARAMETER_CONNECTION == c.connectionType)
                if (c.sender != null) {
                    BluePrintConnection cSend = (BluePrintConnection) c.sender;
                    nbtParameter.setString(c.name, cSend.parentNode.name + "." + cSend.name);
                    if (cSend.parentNode.nodeType == GuiBluePrintNode.FLOWLESS_NODE)
                        connections(cSend.parentNode, subGui);
                } else {
                    nbtParameter.setString(c.name, getValue(c.value));
                }
            
            if (node.nodeType == GuiBluePrintNode.GETTER_NODE && BluePrintConnection.RETURN_CONNECTION == c.connectionType)
                nbtParameter.setString(c.name, getValue(c.value));
            
        }
        nbtListParameter.appendTag(nbtParameter);
        
        nbtListObject.appendTag(nbtListParameter);
        subGui.scriptNBT.setTag(node.name, nbtListObject);
        
    }
}
