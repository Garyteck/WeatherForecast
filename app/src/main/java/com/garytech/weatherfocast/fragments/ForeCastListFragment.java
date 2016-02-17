/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.garytech.weatherfocast.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.garytech.weatherfocast.adapter.ForeCastAdapter;
import com.garytech.weatherfocast.interaction.OnListFragmentInteraction;
import com.garytech.weatherfocast.model.Forecast;
import com.garytech.weatherforecast.R;


/**
 * Master View consisting of a ListFragment
 */
public class ForeCastListFragment extends ListFragment {

    /**
     * Bundle key used to save data
     */
    public static final String FORECAST_PARAMETER = "forecat_parameter";
    /**
     * Callback used to tell the Activity to update the slave view
     */
    OnListFragmentInteraction mCallback;

    /**
     * Set of data
     */
    public com.garytech.weatherfocast.model.Forecast[] mData;

    /**
     * Empty fragment is used by the Fragmentmanager when the fragment is recreated
     */
    public ForeCastListFragment() {
    }

    public static ForeCastListFragment newInstance(com.garytech.weatherfocast.model.Forecast[] forecast) {
        ForeCastListFragment fragment = new ForeCastListFragment();
        Bundle args = new Bundle();
        args.putSerializable(FORECAST_PARAMETER, forecast);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (com.garytech.weatherfocast.interaction.OnListFragmentInteraction) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnListFragmentInteraction");
        }
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mData = (Forecast[]) savedInstanceState.getSerializable(FORECAST_PARAMETER);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mData = (Forecast[]) getArguments().getSerializable(FORECAST_PARAMETER);
            setListAdapter(new ForeCastAdapter(mData, getActivity()));

        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setSelector(R.drawable.listview_selector);
        getListView().setItemChecked(0, true);

    }

    /**
     * This method is called when an item on the list was clicked
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mCallback.onListItemTouched(position);
        getListView().clearChoices();
        getListView().setItemChecked(position, true);
        getListView().setSelected(true);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(FORECAST_PARAMETER, mData);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }
}