package com.reactnativenavigation.playground;

import android.os.Bundle;

import com.reactnativenavigation.NavigationActivity;

import androidx.annotation.Nullable;

public class MainActivity extends NavigationActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSplashLayout();
    }

    private void setSplashLayout() {
        setContentView(R.layout.splash);
    }
}
