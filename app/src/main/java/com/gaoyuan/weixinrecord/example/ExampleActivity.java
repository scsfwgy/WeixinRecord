package com.gaoyuan.weixinrecord.example;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.gaoyuan.weixinrecord.R;
import com.gaoyuan.weixinrecord.history.DBManager;
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
    //db
    private DBManager mgr;

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
        //设置不想要可见或者不想被点击
        // mEmTvBtn.setVisibility(View.GONE);//隐藏
        // mEmTvBtn.setCanRecord(false);//重写该方法，设置为不可点击
    }

    private void initData() {
        mRecords = new ArrayList<>();
        //初始化DBManager
        mgr = new DBManager(this);
    }

    private void initAdapter() {
        mExampleAdapter = new ExampleAdapter(this, mRecords);
        mEmLvRecodeList.setAdapter(mExampleAdapter);

        //开始获取数据库数据
        List<Record> records = mgr.query();
        if (records == null || records.isEmpty()) return;
        for (Record record : records) {
            Log.e("wgy", "initAdapter: " + record.toString());
        }
        mRecords.addAll(records);
        mExampleAdapter.notifyDataSetChanged();
        mEmLvRecodeList.setSelection(mRecords.size() - 1);
    }

    private void initListener() {
        mEmTvBtn.setHasRecordPromission(false);
        //        授权处理
        mHelper = new PermissionHelper(this);

        mHelper.requestPermissions("请授予[录音]，[读写]权限，否则无法录音",
                new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {
                        mEmTvBtn.setHasRecordPromission(true);
                        mEmTvBtn.setAudioFinishRecorderListener((seconds, filePath) -> {
                            Record recordModel = new Record();
                            recordModel.setSecond((int) seconds <= 0 ? 1 : (int) seconds);
                            recordModel.setPath(filePath);
                            recordModel.setPlayed(false);
                            mRecords.add(recordModel);
                            mExampleAdapter.notifyDataSetChanged();
                            mEmLvRecodeList.setSelection(mRecords.size() - 1);

                            //添加到数据库
                            mgr.add(recordModel);
                        });
                    }

                    @Override
                    public void doAfterDenied(String... permission) {
                        mEmTvBtn.setHasRecordPromission(false);
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

    public DBManager getMgr() {
        return mgr;
    }

    public void setMgr(DBManager mgr) {
        this.mgr = mgr;
    }
}
