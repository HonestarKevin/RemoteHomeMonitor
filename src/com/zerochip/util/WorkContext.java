package com.zerochip.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;

/***
 * @category 常用变量
 * @author Kevin.Xu
 *
 */
public class WorkContext
{   
    public Context  mContext = null;
    public Activity mActivity = null;
    public Handler mHandler = null;
    public Resources mResources = null;
    public GetNetWorkState mGetNetWorkState = null;
    public SimpleTextToSpeech mSimpleTextToSpeech = null;
    public SharedPreferences mPreferences = null;
}
