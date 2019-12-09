package com.rnsplitbundle;

import com.facebook.react.ScriptReactActivity;

import javax.annotation.Nullable;

public class MoudleBActivity extends ScriptReactActivity {


    @Nullable
    @Override
    protected String getMainComponentName() {
        return "SecondModule";
    }

    @Override
    protected String getBundlePath() {
        return "bdiff.bundle";
    }

    @Override
    protected BUNDLE_TYPE getBundlePathType() {
        return BUNDLE_TYPE.ASSET_TYPE;
    }

}
