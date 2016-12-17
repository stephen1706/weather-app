package com.stephen.weather.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.stephen.weather.BR;
import com.stephen.weather.R;
import com.stephen.weather.viewmodels.CurrentWeatherViewModel;

import java.util.ArrayList;

/**
 * Created by stephenadipradhana on 12/14/16.
 */

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherViewHolder> {
    private final ArrayList<CurrentWeatherViewModel> mViewModel;

    public WeatherListAdapter(ArrayList<CurrentWeatherViewModel> viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public WeatherViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.row_city_list, viewGroup, false);

        return new WeatherViewHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(WeatherViewHolder customViewHolder, int i) {
        ViewDataBinding viewDataBinding = customViewHolder.getViewDataBinding();
        viewDataBinding.setVariable(BR.data, mViewModel.get(i));
    }

    @Override
    public int getItemCount() {
        return mViewModel == null ? 0 : mViewModel.size();
    }

    public void setItems(CurrentWeatherViewModel currentWeatherViewModel) {
        for(int i=0;i<mViewModel.size();i++){
            if(mViewModel.get(i).getLocationName().get().equals(currentWeatherViewModel.getLocationName().get())){
                mViewModel.get(i).setDescription(currentWeatherViewModel.getDescription().get());
                mViewModel.get(i).setTemperature(currentWeatherViewModel.getTemperature().get());
//                mViewModel.set(i, currentWeatherViewModel);//replace with full data
                break;
            }
        }
        notifyDataSetChanged();
    }

//    public interface OnItemClickListener {
//        void onItemClick(View view, int position);
//    }
}
