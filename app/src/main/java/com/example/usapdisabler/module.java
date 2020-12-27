package com.example.usapdisabler;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class  module implements IXposedHookLoadPackage, IXposedHookZygoteInit {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        try{
            XposedHelpers.findAndHookMethod("com.android.internal.os.Zygote", null, "getConfigurationProperty", String.class, String.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (param.args[0].equals("usap_pool_enabled")){
                        XposedBridge.log("Replace getConfigurationProperty usap_pool_enabled to false.");
                        param.setResult("false");
                    }
                }
            });
            XposedHelpers.findAndHookMethod("com.android.internal.os.Zygote", null, "getConfigurationPropertyBoolean", String.class, Boolean.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    if (param.args[0].equals("usap_pool_enabled")){
                        XposedBridge.log("Replace getConfigurationPropertyBoolean usap_pool_enabled to false.");
                        param.setResult(false);
                    }
                }
            });
        }catch (Exception e){
            XposedBridge.log(e.getMessage());
        }
    }
};