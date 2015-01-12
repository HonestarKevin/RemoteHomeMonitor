package com.zerochip.remotehomemonitor;

import com.zerochip.util.AnimationFactory;
import com.zerochip.util.GetNetWorkState;
import com.zerochip.util.SimpleTextToSpeech;
import com.zerochip.util.WorkContext;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

public class LoginActivity extends Activity
{
    private WorkContext mWorkContext = null;
    private float mTtsSpeechRate = 1.0f; // 朗读速率
    private Handler mHandler = new Handler();
    private static final String TAG = "com.zerochip.remotehomemonitor.LoginActivity";
    public static final boolean DEBUG = false;
    private LayoutInflater mainLayoutInflater = null;
    
    //所有布局的父容器
    public FrameLayout ViewContainerFrameLayout = null;
    

    public boolean CheckNeedSetup()
    {
        if (mWorkContext.mPreferences != null)
        {
            if (DEBUG)
                Log.e(TAG,
                        "mWorkContext.mPreferences != null + "
                                + mWorkContext.mPreferences
                                        .getBoolean(
                                                mWorkContext.configNeedRunSetupWizardString,
                                                true));
            return mWorkContext.mPreferences.getBoolean(
                    mWorkContext.configNeedRunSetupWizardString, true);
        }
        else
        {
            if (DEBUG)
                Log.e(TAG, "mWorkContext.mPreferences null error");
            return true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mainLayoutInflater = LayoutInflater.from(this);
        mWorkContext = new WorkContext();
        mWorkContext.mContext = this;
        mWorkContext.mActivity = this;
        // TODO Auto-generated method stub
        mWorkContext.mHandler = mHandler;
        mWorkContext.mResources = getResources();
        mWorkContext.mGetNetWorkState = new GetNetWorkState(
                mWorkContext.mContext);
        mWorkContext.mSimpleTextToSpeech = new SimpleTextToSpeech(
                mWorkContext.mContext, mTtsSpeechRate);
        mWorkContext.mAnimationFactory = new AnimationFactory(mWorkContext);
        mWorkContext.mPreferences = getSharedPreferences(
                mWorkContext.configFileNameString, Activity.MODE_WORLD_READABLE
                        + Activity.MODE_WORLD_WRITEABLE);
        setContentView(R.layout.activity_main);
        boolean NeedSetupUserInfo = CheckNeedSetup();
        // 如果是第一次打开这个APK。就提示用户设置用户名，密码。增加帐号等；
        if (NeedSetupUserInfo)
        {
            this.startActivity(new Intent(this.getApplicationContext(),
                    SetupWizardActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume()
    {
        InitView();
        super.onResume();
    }

    private void InitView()
    {
        ViewContainerFrameLayout = (FrameLayout)findViewById(R.id.view_container_parents);
        ViewContainerFrameLayout.addView(mainLayoutInflater.inflate(R.layout.interface_login, null));
    }

    @Override
    protected void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();
    }

    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
    }
    
    /**
     * @function LoginButtonOnClick
     * @note 登录面"登录"按键按下
     * @param v
     */
    public void LoginButtonOnClick(View v)
    {
        if (DEBUG)
            Log.e(TAG, "LoginButtonOnClick id =" + v.getId());
        if (v.getId() == R.id.b_login)
        {
            // 增加设备
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                }
            });
            // 跳转到登录页面
        }
    }
    
    /**
     * @function CancleButtonOnClick
     * @note 登录面"取消"按键按下
     * @param v
     */
    public void CancleButtonOnClick(View v)
    {
        if (DEBUG)
            Log.e(TAG, "CancleButtonOnClick id =" + v.getId());
        if (v.getId() == R.id.b_cancle_login)
        {
            // 增加设备
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                }
            });
            // 跳转到登录页面
        }
    }
}
