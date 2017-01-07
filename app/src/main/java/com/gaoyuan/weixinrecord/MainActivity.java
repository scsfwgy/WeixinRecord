package com.gaoyuan.weixinrecord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.gaoyuan.weixinrecord.example.ExampleActivity;

public class MainActivity extends AppCompatActivity {
    private TextView mAmTvBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initAdapter();
        initListener();
    }

    private void initView() {
        mAmTvBtn= (TextView) findViewById(R.id.am_tv_btn);

    }

    private void initData() {

    }

    private void initAdapter() {

    }

    private void initListener() {
        mAmTvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ExampleActivity.class);
                startActivity(intent);

            }
        });
    }
}
