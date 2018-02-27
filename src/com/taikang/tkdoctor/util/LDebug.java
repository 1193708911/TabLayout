package com.taikang.tkdoctor.util;

import java.util.ArrayList;
import android.util.Log;

public class LDebug {
    
    private static final String TAG = "Debug-";
    private static final int _level= 3;
    private static final int def_level = 2;
    
    private static final String LINEDEVICE = ">>>==================================================<<<";
    
    
    public static void o(Object obj, ArrayList<String[]> al){
        
        LDebug.o(obj, LINEDEVICE);
        
        for(int i=0;i<al.size();i++){
            LDebug.o(obj , al.get(i));
        }
        
        LDebug.o(obj, LINEDEVICE);
        
    }
    
    public static void o(Object obj, String[] str){
        StringBuilder sbTemp = new StringBuilder();
        
        for(int i=0;i<str.length;i++){
            sbTemp.append("[").append(str[i]).append("]").append(",");
        }
        
        sbTemp.setLength(sbTemp.length() - 1 );
        
        LDebug.o(obj ,sbTemp.toString());
    }
    
    public static void o(Object obj, boolean b){
        LDebug.o(obj, String.valueOf(b));
    }
    
    public static void o(Object obj, int i){
        LDebug.o(obj, String.valueOf(i));
    }
    
    public static void o(String str)
    {
        LDebug.o(LDebug._level, str);
        return ;
    }
    
    public static void o(Object obj, String str)
    {
        LDebug.o("{" + obj.getClass().getSimpleName() + "} ==>> " + str);
    }
    
    public static void o(int level, Object obj, String str)
    {
        LDebug.o(level, "{" + obj.getClass().getSimpleName() + "}" + str);
    }
    
    public static void o(int level, String str)
    {
        if (level > LDebug.def_level)
        {
            Log.v(LDebug.TAG + LDebug._level , "(" + level + ")[" + System.currentTimeMillis() + "]" + str);
        }
        
        return ;
    }
    
    

}
