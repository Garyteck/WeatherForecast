package com.garytech.weatherfocast.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.garytech.weatherfocast.App;

import java.util.List;

import dagger.ObjectGraph;


public abstract class BaseActivity extends AppCompatActivity {

    private ObjectGraph mScopeGraph;
    protected ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScopeGraph = ((App) getApplication()).createScopedGraph(getModules().toArray());
        mScopeGraph.inject(this);

        if ((mActionBar = getSupportActionBar()) != null) {
            mActionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mScopeGraph = null;
    }

    protected abstract List<Object> getModules();

}

