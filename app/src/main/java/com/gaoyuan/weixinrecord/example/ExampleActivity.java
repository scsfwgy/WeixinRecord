package com.gaoyuan.weixinrecord.example;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.gaoyuan.weixinrecord.R;
import com.gaoyuan.weixinrecord.manager.AudioRecordButton;
import com.gaoyuan.weixinrecord.manager.MediaManager;
import com.gaoyuan.weixinrecord.utils.PermissionHelper;

import java.util.ArrayList;
import java.util.List;

public class ExampleActivity extends AppCompatActivity {
    private ListView mEmLvRecodeList;
    private AudioRecordButton mEmTvBtn;
    List<Record> mRecords;
    ExampleAdapter mExampleAdapter;
    PermissionHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        initView();
        initData();
        initAdapter();
        initListener();
    }

    private void initView() {
        mEmLvRecodeList = (ListView) findViewById(R.id.em_lv_recodeList);
        mEmTvBtn = (AudioRecordButton) findViewById(R.id.em_tv_btn);

    }

    private void initData() {
        mRecords = new ArrayList<>();
    }

    private void initAdapter() {
        mExampleAdapter = new ExampleAdapter(this, mRecords);
        mEmLvRecodeList.setAdapter(mExampleAdapter);
    }

    private void initListener() {
        mEmTvBtn.setCanRecord(false);
//        授权处理
        mHelper = new PermissionHelper(this);

        mHelper.requestPermissions("请授予[录音]，[读写]权限，否则无法录音",
                new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {
                        mEmTvBtn.setCanRecord(true);
                        mEmTvBtn.setAudioFinishRecorderListener(new AudioRecordButton.AudioFinishRecorderListener() {
                            @Override
                            public void onFinished(float seconds, String filePath) {
                                Record recordModel = new Record();
                                recordModel.setSecond((int) seconds <= 0 ? 1 : (int) seconds);
                                recordModel.setPath(filePath);
                                recordModel.setPlayed(false);
                                mRecords.add(recordModel);
                                mExampleAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void doAfterDenied(String... permission) {
                        mEmTvBtn.setCanRecord(false);
                        Toast.makeText(ExampleActivity.this, "请授权,否则无法录音", Toast.LENGTH_SHORT).show();
                    }
                }, Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    //直接把参数交给mHelper就行了
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mHelper.handleRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onPause() {
        MediaManager.release();//保证在退出该页面时，终止语音播放
        super.onPause();
    }
}
