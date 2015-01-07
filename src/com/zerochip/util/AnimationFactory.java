package com.zerochip.util;


import android.R.integer;
import android.content.Context;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;

public class AnimationFactory
{
    private WorkContext mWorkContext = null;

    public AnimationFactory(WorkContext mWorkContext)
    {
        super();
        this.mWorkContext = mWorkContext;
    }
    
    public AnimationSet GetAnimationSet(int Resource)
    {
        return (AnimationSet) AnimationUtils.loadAnimation(mWorkContext.mContext,
                Resource);
    }
}
