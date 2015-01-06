package com.zerochip.remotehomemonitor;

import com.zerochip.util.GetNetWorkState;
import com.zerochip.util.SimpleTextToSpeech;
import com.zerochip.util.WorkContext;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

public class SetupWizardActivity extends Activity
{
    private static final String TAG = "com.zerochip.remotehomemonitor.SetupWizardActivity";
    public static final boolean DEBUG = true;
    private WorkContext mWorkContext = null;
    private float mTtsSpeechRate = 1.0f; // 朗读速率
    private Handler mHandler = new Handler();
    /**
     * 全部的设置向导页面
     */
    private int setupPageId[] =
    { R.id.l_welcom_setup, R.id.l_admin_setup, R.id.l_telnet_setup,
            R.id.l_devices_setup };

    /**
     * 
     */
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
                mWorkContext.mPreferences = getSharedPreferences(
                        "RemoteHomeMonitor", Activity.MODE_PRIVATE);
            }
        });
        Log.e(TAG, "onCreate-------");
        setContentView(R.layout.activity_setupwizard);

    }

    
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        InitView();
    }


    private void InitView()
    {
        ShowWelcomePage();

    }
    /**
     * @function ShowWelcomePage
     * @note: 显示第一页，不显示其它页面；
     */
    private void ShowWelcomePage()
    {
        LinearLayout PageLinearLayout = null;
        for (int i = 0; i < setupPageId.length; i++)
        {
            PageLinearLayout = (LinearLayout)findViewById(setupPageId[i]);
            if(setupPageId[i] == R.id.l_welcom_setup)
            {
                PageLinearLayout.setVisibility(View.VISIBLE);
            }else {
                PageLinearLayout.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @function NextButtonOnClick
     * @note　"下一步"按键按下
     * @param v
     */
    public void NextButtonOnClick(View v)
    {
        if (DEBUG)
            Log.e(TAG, "NextButtonOnClick id =" + v.getId());
        switch (v.getId())
        {
        case R.id.b_welcome_next:
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    SetShowPage(R.id.l_welcom_setup,R.id.l_admin_setup);
                }
            });
            break;
        case R.id.b_admin_next:
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    SetShowPage(R.id.l_admin_setup,R.id.l_telnet_setup);
                }
            });
            break;
        case R.id.b_telnet_next:
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    SetShowPage(R.id.l_telnet_setup,R.id.l_devices_setup);
                }
            });
            break;
        default:
            break;
        }
    }
    /**
     * @function PreviousButtonOnClick
     * @note　"上一步"按键按下
     * @param v
     */
    public void PreviousButtonOnClick(View v)
    {
        if (DEBUG)
            Log.e(TAG, "PreviousButtonOnClick id =" + v.getId());
        switch (v.getId())
        {
        case R.id.b_admin_previous:
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    SetShowPage(R.id.l_admin_setup, R.id.l_welcom_setup);
                }
            });
            break;
        case R.id.b_telnet_previous:
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    SetShowPage(R.id.l_telnet_setup, R.id.l_admin_setup);
                }
            });
            break;
        case R.id.b_device_previous:
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    SetShowPage(R.id.l_devices_setup, R.id.l_telnet_setup);
                }
            });
            break;
        default:
            break;
        }
    }
    /**
     * @function SetShowPage
     * @note　两个页面之间跳转
     * @param CurrentPageLinearLayoutId  当前页面
     * @param GotoPageLinearLayoutId    要跳转的页面
     */
    private void SetShowPage(int CurrentPageLinearLayoutId, int GotoPageLinearLayoutId)
    {
        LinearLayout  CurrentPageLinearLayout =   (LinearLayout)findViewById(CurrentPageLinearLayoutId);
        LinearLayout  GotoPageLinearLayout = (LinearLayout)findViewById(GotoPageLinearLayoutId);
        CurrentPageLinearLayout.setVisibility(View.GONE);
        GotoPageLinearLayout.setVisibility(View.VISIBLE);
    }
    
    /**
     * @function AddButtonOnClick
     * @note 设置设备面"增加"按键按下
     * @param v
     */
    public void AddButtonOnClick(View v)
    {
        if (DEBUG)
            Log.e(TAG, "NextButtonOnClick id =" + v.getId());
        if(v.getId() == R.id.b_device_add)
        {
            //增加设备
        }
    }
    /**
     * @function FinishButtonOnClick
     * @note 设置设备面"完成"按键按下
     * @param v
     */
    public void FinishButtonOnClick(View v)
    {
        if (DEBUG)
            Log.e(TAG, "PreviousButtonOnClick id =" + v.getId());
        if(v.getId() == R.id.b_finish)
        {
            finish();
            //跳转到登录页面
        }
    }
}
