package com.zerochip.remotehomemonitor;

import com.zerochip.util.GetNetWorkState;
import com.zerochip.util.SimpleTextToSpeech;
import com.zerochip.util.WorkContext;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class SetupWizardActivity extends Activity
{
    private static final String TAG = "com.zerochip.remotehomemonitor.SetupWizardActivity";
    public static final boolean DEBUG = true;
    private WorkContext mWorkContext = null;
    private float mTtsSpeechRate = 1.0f; // 朗读速率
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        mWorkContext = new WorkContext();
        mWorkContext.mContext = this;
        mWorkContext.mActivity = this;
        mHandler.post(new Runnable()
        {
            @Override
            public void run()
            {
                // TODO Auto-generated method stub
                mWorkContext.mHandler = mHandler;
                mWorkContext.mResources = getResources();
                mWorkContext.mGetNetWorkState = new GetNetWorkState(
                        mWorkContext.mContext);
                mWorkContext.mSimpleTextToSpeech = new SimpleTextToSpeech(
                        mWorkContext.mContext, mTtsSpeechRate);
                mWorkContext.mPreferences = getSharedPreferences("RemoteHomeMonitor", Activity.MODE_PRIVATE);
            }
        });
        
        Log.e(TAG, "onCreate-------");
        setContentView(R.layout.activity_setupwizard);
    }
    
    public void NextButtonOnClick(View v) 
    {
       if(DEBUG) Log.e(TAG, "NextButtonOnClick id ="+v.getId());
    }
    
    public void PreviousButtonOnClick(View v) 
    {
       if(DEBUG) Log.e(TAG, "PreviousButtonOnClick id ="+v.getId());
    }
    
    public void AddButtonOnClick(View v) 
    {
       if(DEBUG) Log.e(TAG, "NextButtonOnClick id ="+v.getId());
    }
    
    public void FinishButtonOnClick(View v) 
    {
       if(DEBUG) Log.e(TAG, "PreviousButtonOnClick id ="+v.getId());
    }
}
