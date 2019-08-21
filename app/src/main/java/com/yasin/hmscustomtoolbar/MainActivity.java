package com.yasin.hmscustomtoolbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yasin.hmstoolbar.HmsUniversalToolBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        HmsUniversalToolBar hmsToolBar = findViewById(R.id.hmsToolBar);
    }
}
