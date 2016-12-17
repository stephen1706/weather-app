package com.stephen.weather.ui.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by stephenadipradhana on 12/14/16.
 */

public class WeatherViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding mViewDataBinding;

    public WeatherViewHolder(ViewDataBinding viewDataBinding) {
        super(viewDataBinding.getRoot());

        mViewDataBinding = viewDataBinding;
        mViewDataBinding.executePendingBindings();
    }

    public ViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }
}
