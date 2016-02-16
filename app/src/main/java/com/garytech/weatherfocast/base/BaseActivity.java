package com.garytech.weatherfocast.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.garytech.weatherfocast.App;

import java.util.List;

import dagger.ObjectGraph;


public abstract class BaseActivity extends AppCompatActivity {

    private ObjectGraph activityGraph;
    protected ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGraph = ((App) getApplication()).createScopedGraph(getModules().toArray());
        activityGraph.inject(this);

        if ((mActionBar = getSupportActionBar()) != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityGraph = null;
    }

    protected abstract List<Object> getModules();

}

