package com.rnsplitbundle;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ScriptLoadBridge;
import com.rnsplitbundle.common.Global;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ChoiceActivity extends AppCompatActivity {
    private ReactInstanceManager rim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews(){
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-2,-2));
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));
        Button button = new Button(this);
        button.setWidth(400);
        button.setHeight(100);
        button.setText("进入开发模式");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoiceActivity.this,MainActivity.class));
            }
        });
        Button button1 = new Button(this);
        button1.setWidth(400);
        button1.setHeight(100);
        button1.setText("进入业务A页面");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoiceActivity.this,MoudleAActivity.class));
            }
        });
        Button button3 = new Button(this);
        button3.setWidth(400);
        button3.setHeight(100);
        button3.setText("进入业务B页面");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChoiceActivity.this,MoudleBActivity.class));
            }
        });
        linearLayout.addView(button);
        linearLayout.addView(button1);
        linearLayout.addView(button3);
        linearLayout.setGravity(Gravity.CENTER);
        relativeLayout.removeAllViews();
        relativeLayout.addView(linearLayout);
        setContentView(relativeLayout);
        getReactInstanceManager();
    }

    private void getReactInstanceManager() {
        rim = ((MainApplication)getApplication()).getReactNativeHost().getReactInstanceManager();
    }

    private void loadReactContext(){
        if(!Global.isDev){
            if(!rim.hasStartedCreatingInitialContext()){
                rim.createReactContextInBackground();
                rim.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
                    @Override
                    public void onReactContextInitialized(ReactContext context) {
                        loadSubMoudle();
                        Toast.makeText(ChoiceActivity.this, "react 环境初始化完成", Toast.LENGTH_SHORT).show();
                        rim.removeReactInstanceEventListener(this);
                    }
                });
            }
        }
    }

    private void loadSubMoudle(){//预加载moudle a页面
        //ScriptLoadBridge.loadScriptFromAsset(this,rim.getCurrentReactContext().getCatalystInstance(),"adiff.bundle",false);
    }

    private void unLoadReactContext(){
        if(!Global.isDev){
            rim.destroy();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadReactContext();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unLoadReactContext();
    }
}
