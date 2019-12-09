package com.facebook.react;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ScriptLoadBridge;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;
import com.rnsplitbundle.util.ScriptUtil;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class ScriptReactActivity  extends AppCompatActivity
        implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {
    // BUNDLE 存储位置类型
    protected enum BUNDLE_TYPE{
        ASSET_TYPE,
        FILE_TYPE
    }
    protected abstract String getBundlePath(); // 返回 Bundle 文件路径
    protected abstract BUNDLE_TYPE getBundlePathType();  //  Bundle 文件存储路径类型

    private final ReactActivityTestDelegate mDelegate;

    protected ScriptReactActivity() {
        mDelegate = createReactActivityDelegate();
    }

    /**
     * 初始化 ReactContext 上下文环境
     */
    private void initialReactContext(final Bundle saveInstanceState) {
          mDelegate.initReactDelegate();
          ReactInstanceManager rim = mDelegate.getReactInstanceManager();
          boolean hasStartedCreatingInitialContext = rim.hasStartedCreatingInitialContext();
          if(!hasStartedCreatingInitialContext){
              rim.createReactContextInBackground();
              rim.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                  @Override
                  public void onReactContextInitialized(ReactContext context) {
                      // 加载子模块，并绑定视图
                      loadJSBundle();
                      mDelegate.onCreate(saveInstanceState);
                      rim.removeReactInstanceEventListener(this);
                  }
              });
          }else{
              loadJSBundle();
              mDelegate.onCreate(saveInstanceState);
          }
    }

    /**
     * 加载 bundle
     */
    private void loadJSBundle() {

        String bundlePath = getBundlePath();
        BUNDLE_TYPE bundleType = getBundlePathType();
        if(bundleType == BUNDLE_TYPE.ASSET_TYPE) {
            // 从 Asset 目录下加载 bundle 文件
            ScriptLoadBridge.loadScriptFromAsset(this,getReactInstanceManager().getCurrentReactContext().getCatalystInstance(),bundlePath,false);
            //ScriptUtil.loadScriptFromAsset(this,rim.getCurrentReactContext().getCatalystInstance(),bundlePath,false);
        } else {
            // 从 File 目录下加载 bundle 文件
            File scriptFile = new File(getApplicationContext().getFilesDir()
                    + File.separator + bundlePath);
            ScriptUtil.loadScriptFromFile(scriptFile.getAbsolutePath(), getReactInstanceManager().getCurrentReactContext().getCatalystInstance(), bundlePath,false);
        }
    }

    /**
     * Returns the name of the main component registered from JavaScript. This is used to schedule
     * rendering of the component. e.g. "MoviesApp"
     */
    protected @Nullable
    String getMainComponentName() {
        return null;
    }

    /** Called at construction time, override if you have a custom delegate implementation. */
    protected ReactActivityTestDelegate createReactActivityDelegate() {
        Log.e("lookat","createReactActivityDelegate");
        return new ReactActivityTestDelegate(this, getMainComponentName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialReactContext(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDelegate.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDelegate.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelegate.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDelegate.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mDelegate.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        return mDelegate.onKeyLongPress(keyCode, event) || super.onKeyLongPress(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (!mDelegate.onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onNewIntent(Intent intent) {
        if (!mDelegate.onNewIntent(intent)) {
            super.onNewIntent(intent);
        }
    }

    @Override
    public void requestPermissions(
            String[] permissions, int requestCode, PermissionListener listener) {
        mDelegate.requestPermissions(permissions, requestCode, listener);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        mDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mDelegate.onWindowFocusChanged(hasFocus);
    }

    protected final ReactNativeHost getReactNativeHost() {
        return mDelegate.getReactNativeHost();
    }

    protected final ReactInstanceManager getReactInstanceManager() {
        return mDelegate.getReactInstanceManager();
    }

    protected final void loadApp(String appKey) {
        mDelegate.loadApp(appKey);
    }
}
