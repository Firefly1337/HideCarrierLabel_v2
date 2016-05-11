package com.firefly1337.xposed.hidecarrierlabel;

import android.view.View;
import android.widget.RelativeLayout;

import de.robv.android.xposed.IXposedHookLoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Main implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("com.android.systemui")) {
            return;
        }

        findAndHookMethod("com.android.systemui.statusbar.phone.KeyguardStatusBarView", loadPackageParam.classLoader, "onFinishInflate", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                RelativeLayout layout = (RelativeLayout) param.thisObject;

                if (layout == null)
                    return;

                int carrierID = layout.getResources().getIdentifier("keyguard_carrier_text", "id", loadPackageParam.packageName);
                View mCarrierLabel = layout.findViewById(carrierID);

                if (mCarrierLabel == null)
                    return;

                mCarrierLabel.setVisibility(View.GONE);
            }
        });

    }
}
