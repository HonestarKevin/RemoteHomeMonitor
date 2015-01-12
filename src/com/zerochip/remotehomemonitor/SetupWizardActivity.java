package com.zerochip.remotehomemonitor;

import java.util.HashSet;
import java.util.Set;

import com.zerochip.util.AnimationFactory;
import com.zerochip.util.GetNetWorkState;
import com.zerochip.util.SimpleTextToSpeech;
import com.zerochip.util.WorkContext;

import android.R.integer;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SetupWizardActivity extends Activity
{
    private static final String TAG = "com.zerochip.remotehomemonitor.SetupWizardActivity";
    public static final boolean DEBUG = LoginActivity.DEBUG;
    private WorkContext mWorkContext = null;
    private float mTtsSpeechRate = 1.0f; // 朗读速率
    private Handler mHandler = new Handler();
    private SharedPreferences.Editor editor = null;
    private HashSet<String> DevicesIDSet = new HashSet<String>();
    private HashSet<String> UserNamesSet = new HashSet<String>();
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
        editor = mWorkContext.mPreferences.edit();
        editor.clear();
        editor.commit();
        if (DEBUG)
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
            PageLinearLayout = (LinearLayout) findViewById(setupPageId[i]);
            if (setupPageId[i] == R.id.l_welcom_setup)
            {
                PageLinearLayout.setVisibility(View.VISIBLE);
            }
            else
            {
                PageLinearLayout.setVisibility(View.GONE);
            }
        }
    }

    private void ShowToast(String ShowString)
    {
        Toast.makeText(mWorkContext.mContext, ShowString, Toast.LENGTH_SHORT)
                .show();
    }

    private String GetAdminPasswd()
    {
        EditText adminPasswdEditText = (EditText) findViewById(R.id.e_setup_admin_passwd);
        EditText adminPasswdVerifyEditText = (EditText) findViewById(R.id.e_setup_admin_passwd_verify);
        if (adminPasswdEditText == null || adminPasswdVerifyEditText == null)
        {
            return null;
        }
        else
            if (adminPasswdEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_passwd));
                return null;
            }
            else
                if (adminPasswdVerifyEditText.getText().toString().equals(""))
                {
                    ShowToast(mWorkContext.mResources
                            .getString(R.string.str_please_input_passwd_verify));
                    return null;
                }
                else
                    if (!adminPasswdVerifyEditText.getText().toString()
                            .equals(adminPasswdEditText.getText().toString()))
                    {
                        ShowToast(mWorkContext.mResources
                                .getString(R.string.str_input_passwd_error_warning));
                        return null;
                    }
                    else
                    {
                        return adminPasswdEditText.getText().toString();
                    }
    }

    private String GetAdminUsername()
    {
        EditText adminUsernameEditText = (EditText) findViewById(R.id.e_setup_admin_username);
        if (adminUsernameEditText == null)
        {
            return null;
        }
        else
            if (adminUsernameEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_username));
                return null;
            }
            else
            {
                return adminUsernameEditText.getText().toString();
            }
    }

    private boolean SaveAdminInfo(String AdminUsername, String AdminPasswd)
    {
        if (editor == null || AdminUsername == null || AdminPasswd == null)
        {
            return false;
        }
        else
        {
            if (DEBUG)
                ShowToast("name: " + AdminUsername + "  passwd: " + AdminPasswd);
            editor.putString(mWorkContext.configAdminUsernameString,
                    AdminUsername);
            editor.putString(mWorkContext.configAdminPasswdString, AdminPasswd);
            editor.commit();
            return true;
        }
    }

    private boolean SaveTelnetInfo(String TelnetName,
            String TelnetConnectionNumber)
    {
        if (editor == null || TelnetName == null
                || TelnetConnectionNumber == null)
        {
            return false;
        }
        else
        {
            if (DEBUG)
                ShowToast("name: " + TelnetName + "  passwd: "
                        + TelnetConnectionNumber);
            editor.putString(mWorkContext.configTelnetnameString, TelnetName);
            editor.putString(mWorkContext.configTelnetConnectionNumberString,
                    TelnetConnectionNumber);
            editor.commit();
            return true;
        }
    }

    private String GetTelnetNumber()
    {
        EditText TelnetNumberEditText = (EditText) findViewById(R.id.e_setup_telnet_connectipn_number);
        if (TelnetNumberEditText == null)
        {
            return null;
        }
        else
            if (TelnetNumberEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_telnet_connection_number));
                return null;
            }
            else
            {
                return TelnetNumberEditText.getText().toString();
            }
    }

    private String GetTelnetName()
    {
        EditText TelnetNameEditText = (EditText) findViewById(R.id.e_setup_telnet_name);
        if (TelnetNameEditText == null)
        {
            return null;
        }
        else
            if (TelnetNameEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_telnet_name));
                return null;
            }
            else
            {
                return TelnetNameEditText.getText().toString();
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
                    SetShowPage(R.id.l_welcom_setup, R.id.l_admin_setup, false);
                }
            });
            break;
        case R.id.b_admin_next:
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    String AdminUsername = GetAdminUsername();
                    String AdminPasswd = GetAdminPasswd();
                    if (SaveAdminInfo(AdminUsername, AdminPasswd))
                    {
                        SetShowPage(R.id.l_admin_setup, R.id.l_telnet_setup,
                                false);
                    }
                }
            });
            break;
        case R.id.b_telnet_next:
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    String TelnetName = GetTelnetName();
                    String TelnetConnectionNumber = GetTelnetNumber();
                    if (SaveTelnetInfo(TelnetName, TelnetConnectionNumber))
                    {
                        SetShowPage(R.id.l_telnet_setup, R.id.l_devices_setup,
                                false);
                    }
                }
            });
            break;
        case R.id.b_device_next:
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    
                    String DevicesName = GetDevicesName();
                    String DevicesId = GetDevicesId();
                    if (AddDeviceInfo(DevicesName, DevicesId))
                    {
                        SetShowPage(R.id.l_devices_setup, R.id.l_user_info_setup,
                                false);
                    }
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
                    SetShowPage(R.id.l_admin_setup, R.id.l_welcom_setup, true);
                }
            });
            break;
        case R.id.b_telnet_previous:
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    SetShowPage(R.id.l_telnet_setup, R.id.l_admin_setup, true);
                }
            });
            break;
        case R.id.b_device_previous:
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    SetShowPage(R.id.l_devices_setup, R.id.l_telnet_setup, true);
                }
            });
            break;
        case R.id.b_user_info_previous:
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    SetShowPage(R.id.l_user_info_setup, R.id.l_devices_setup, true);
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
     * @param CurrentPageLinearLayoutId
     *            当前页面
     * @param GotoPageLinearLayoutId
     *            要跳转的页面
     * @param LeftFlage
     */
    private void SetShowPage(int CurrentPageLinearLayoutId,
            int GotoPageLinearLayoutId, boolean LeftFlage)
    {
        LinearLayout CurrentPageLinearLayout = (LinearLayout) findViewById(CurrentPageLinearLayoutId);
        LinearLayout GotoPageLinearLayout = (LinearLayout) findViewById(GotoPageLinearLayoutId);
        if (LeftFlage)
        {
            CurrentPageLinearLayout.setAnimation(mWorkContext.mAnimationFactory
                    .GetAnimationSet(R.anim.push_right_out));
            GotoPageLinearLayout.setAnimation(mWorkContext.mAnimationFactory
                    .GetAnimationSet(R.anim.push_left_in));
        }
        else
        {
            CurrentPageLinearLayout.setAnimation(mWorkContext.mAnimationFactory
                    .GetAnimationSet(R.anim.push_left_out));
            GotoPageLinearLayout.setAnimation(mWorkContext.mAnimationFactory
                    .GetAnimationSet(R.anim.push_right_in));
        }
        CurrentPageLinearLayout.setVisibility(View.GONE);
        GotoPageLinearLayout.setVisibility(View.VISIBLE);
    }

    private String GetDevicesId()
    {
        EditText DevicesNumberEditText = (EditText) findViewById(R.id.e_setup_device_number);
        if (DevicesNumberEditText == null)
        {
            return null;
        }
        else
            if (DevicesNumberEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_sensor_device_number_id));
                return null;
            }
            else
            {
                return DevicesNumberEditText.getText().toString();
            }
    }

    private boolean AddDeviceInfo(String DevicesName, String DevicesId)
    {
        if (editor == null || DevicesName == null || DevicesId == null)
        {
            return false;
        }
        else
        {
            if (DEBUG)
                ShowToast("name: " + DevicesName + "  passwd: " + DevicesId);
            editor.putString(DevicesId, DevicesName);
            DevicesIDSet.add(DevicesId);
            editor.putStringSet(mWorkContext.configDevicesIdListString,
                    DevicesIDSet);
            editor.commit();
            ShowToast(mWorkContext.mResources
                    .getString(R.string.str_device_add)
                    + mWorkContext.mResources
                            .getString(R.string.str_device_setup_name)
                    +DevicesName);
            return true;
        }
    }

    private String GetDevicesName()
    {
        EditText DevicesNameEditText = (EditText) findViewById(R.id.e_setup_device_name);
        if (DevicesNameEditText == null)
        {
            return null;
        }
        else
            if (DevicesNameEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_sensor_device_name));
                return null;
            }
            else
            {
                return DevicesNameEditText.getText().toString();
            }
    }

    
    
    private String GetUserInfoName()
    {
        EditText UserInfoNameEditText = (EditText) findViewById(R.id.e_setup_user_info_name);
        if (UserInfoNameEditText == null)
        {
            return null;
        }
        else
            if (UserInfoNameEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_sensor_device_name));
                return null;
            }
            else
            {
                return UserInfoNameEditText.getText().toString();
            }
    } 
    
    private String GetUserInfoPasswd()
    {
        EditText UserInfoPasswdEditText = (EditText) findViewById(R.id.e_setup_user_info_passwd);
        EditText UserInfoPasswdVerifyEditText = (EditText) findViewById(R.id.e_setup_user_info_passwd_verify);
        if (UserInfoPasswdEditText == null || UserInfoPasswdVerifyEditText == null)
        {
            return null;
        }
        else
            if (UserInfoPasswdEditText.getText().toString().equals(""))
            {
                ShowToast(mWorkContext.mResources
                        .getString(R.string.str_please_input_passwd));
                return null;
            }
            else
                if (UserInfoPasswdVerifyEditText.getText().toString().equals(""))
                {
                    ShowToast(mWorkContext.mResources
                            .getString(R.string.str_please_input_passwd_verify));
                    return null;
                }
                else
                    if (!UserInfoPasswdVerifyEditText.getText().toString()
                            .equals(UserInfoPasswdEditText.getText().toString()))
                    {
                        ShowToast(mWorkContext.mResources
                                .getString(R.string.str_input_passwd_error_warning));
                        return null;
                    }
                    else
                    {
                        return UserInfoPasswdEditText.getText().toString();
                    }
    }
    
    private boolean AddUserInfo(String UserInfoName, String UserInfoPasswd)
    {
        if (editor == null || UserInfoName == null || UserInfoPasswd == null)
        {
            return false;
        }
        else
        {
            if (DEBUG)
                ShowToast("name: " + UserInfoName + "  passwd: " + UserInfoPasswd);
            editor.putString(UserInfoName, UserInfoPasswd);
            UserNamesSet.add(UserInfoName);
            editor.putStringSet(mWorkContext.configUsernamesListString,
                    UserNamesSet);
            editor.commit();
            ShowToast(mWorkContext.mResources
                    .getString(R.string.str_device_add)
                    + mWorkContext.mResources
                            .getString(R.string.str_user_setup_name)
                    +UserInfoName);
            return true;
        }
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
        if (v.getId() == R.id.b_device_add)
        {
            // 增加设备
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    String DevicesName = GetDevicesName();
                    String DevicesId = GetDevicesId();
                    AddDeviceInfo(DevicesName, DevicesId);
                }
            });
        }else if(v.getId() == R.id.b_user_info_add)
        {
         // 增加用户
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    String UserInfoName = GetUserInfoName();
                    String UserInfoPasswd = GetUserInfoPasswd();
                    AddUserInfo(UserInfoName, UserInfoPasswd);
                }
            });
            
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
        if (v.getId() == R.id.b_finish)
        {
            // 增加设备
            mHandler.post(new Runnable()
            {
                @Override
                public void run()
                {
                    String UserInfoName = GetUserInfoName();
                    String UserInfoPasswd = GetUserInfoPasswd();
                    AddUserInfo(UserInfoName, UserInfoPasswd);
                    
                    editor.putBoolean(
                            mWorkContext.configNeedRunSetupWizardString, false);
                    editor.commit();
                    mWorkContext.mContext.startActivity(new Intent(
                            mWorkContext.mContext, LoginActivity.class));
                    finish();
                }
            });
            // 跳转到登录页面
        }
    }
}
