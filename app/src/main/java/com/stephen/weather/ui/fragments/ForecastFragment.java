package com.stephen.weather.ui.fragments;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.LatLng;
import com.stephen.weather.R;
import com.stephen.weather.databinding.FragmentForecastBinding;
import com.stephen.weather.databinding.RowForecastBinding;
import com.stephen.weather.modelhandlers.ForecastModelHandler;
import com.stephen.weather.viewmodels.EachDayForecastViewModel;
import com.stephen.weather.viewmodels.LocationViewModel;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.util.ArrayList;

/**
 * Created by stephenadipradhana on 12/17/16.
 */

public class ForecastFragment extends RxFragment {
    private static final String PARAM_LOCATION = "PARAM_LOCATION";
    private static final String PARAM_NAME = "PARAM_NAME";

    private ForecastModelHandler mModelHandler;
    private FragmentForecastBinding binding;
    private LatLng mLocation;
    private String mLocationName;


    public static Fragment newInstance(LatLng latLng) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAM_LOCATION, latLng);
        fragment.setArguments(args);
        return fragment;
    }

    public static Fragment newInstance(LocationViewModel locationViewModel) {
        ForecastFragment fragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAM_LOCATION, new LatLng(locationViewModel.getLatitude(), locationViewModel.getLongitude()));
        args.putString(PARAM_NAME, locationViewModel.getName());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mModelHandler = new ForecastModelHandler(getActivity());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocation = getArguments().getParcelable(PARAM_LOCATION);
        mLocationName = getArguments().getString(PARAM_NAME);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast, container, false);

        mModelHandler.getForecast(mLocation.latitude, mLocation.longitude, mLocationName)
                .compose(bindToLifecycle())
                .subscribe(forecastDetailViewModel -> {
                    binding.setData(forecastDetailViewModel);
                }, throwable -> {
                    throwable.printStackTrace();
                });

        return binding.getRoot();
    }

    @BindingAdapter("app:items")
    public static void setItems(LinearLayout view, ArrayList<EachDayForecastViewModel> list) {
        if(list == null){
            return;
        }

        LayoutInflater layoutInflater = LayoutInflater.from(view.getContext());
        for (EachDayForecastViewModel viewModel : list) {
            RowForecastBinding viewDataBinding = DataBindingUtil.inflate(layoutInflater, R.layout.row_forecast, view, false);
            viewDataBinding.setData(viewModel);
            view.addView(viewDataBinding.getRoot());
        }
    }

    @BindingAdapter("app:imageDrwable")
    public static void setImageResource(ImageView imageView, Drawable drawable){
        imageView.setImageDrawable(drawable);
    }
}
