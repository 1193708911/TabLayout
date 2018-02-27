package com.taikang.tkdoctor.base;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class PreferencesService {  
    private Context context;  
  
    public PreferencesService(Context context) {  
        this.context = context;  
    }  
  
    /** 
     * 保存参数 
     * @param phone  手机 
     * @param pwd    密码   
     */  
    public void save(String phone, String pwd) {  
        //获得SharedPreferences对象  
        SharedPreferences preferences = context.getSharedPreferences("TkDoctor", Context.MODE_PRIVATE);  
        Editor editor = preferences.edit();  
        editor.putString("phone", phone);  
        editor.putString("pwd", pwd);  
        editor.commit();  
    }  
  
    /** 
     * 获取各项参数 
     * @return 
     */  
    public Map<String, String> getPerferences() {  
        Map<String, String> params = new HashMap<String, String>();  
        SharedPreferences preferences = context.getSharedPreferences("TkDoctor", Context.MODE_PRIVATE);  
        params.put("phone", preferences.getString("phone", ""));  
        params.put("pwd", preferences.getString("pwd", ""));  
        return params;  
    }  
      
      
      
}  