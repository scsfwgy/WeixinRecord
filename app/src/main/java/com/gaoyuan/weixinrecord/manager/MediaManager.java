package com.gaoyuan.weixinrecord.manager;


import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.util.Log;

import java.io.IOException;

public class MediaManager {


    private static final String TAG = "MediaManager";
    private static MediaPlayer mPlayer;
    private static boolean isPause;
    //以下开始传感器监听
    static SensorManager mSensorManager;
    static Sensor sensor;
    private static Context mContext;
    //声音管理器
    static AudioManager mAudioManager;

    public static void playSound(String filePathString,
                                 OnCompletionListener onCompletionListener) {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            //保险起见，设置报错监听
            mPlayer.setOnErrorListener(new OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mPlayer.reset();
                    return false;
                }
            });
        } else {
            mPlayer.reset();//就重置
        }

        try {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnCompletionListener(onCompletionListener);
            mPlayer.setDataSource(filePathString);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void playSound(String filePathString,
                                 OnCompletionListener onCompletionListener, Context context) {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            //保险起见，设置报错监听
            mPlayer.setOnErrorListener(new OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mPlayer.reset();
                    return false;
                }
            });
        } else {
            mPlayer.reset();//就重置
        }

        try {
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setOnCompletionListener(onCompletionListener);
            mPlayer.setDataSource(filePathString);
            mPlayer.prepare();
            mPlayer.start();
            initSensorListener(context);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initSensorListener(Context context) {
        mContext = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        // 注册传感器
        mSensorManager.registerListener(new SensorEventListener() {
                                            @Override
                                            public void onSensorChanged(SensorEvent event) {
                                                try {
                                                    float mProximiny = event.values[0];
                                                    boolean isSpeaker = false;
                                                    if (mProximiny >= sensor.getMaximumRange()) {
                                                        isSpeaker = true;
                                                        changeAdapterType(isSpeaker);
                                                    } else {
                                                        isSpeaker = false;
                                                        changeAdapterType(isSpeaker);
                                                    }
                                                } catch (Exception ex) {
                                                    ex.printStackTrace();
                                                }
                                            }

                                            @Override
                                            public void onAccuracyChanged(Sensor sensor, int accuracy) {

                                            }
                                        }, sensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    //停止函数
    public static void pause() {
        if (mPlayer != null && mPlayer.isPlaying()) {
            mPlayer.pause();
            isPause = true;
        }
    }

    //继续
    public static void resume() {
        if (mPlayer != null && isPause) {
            mPlayer.start();
            isPause = false;
        }
    }


    public static void release() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    //切换声筒或听筒
    private static void changeAdapterType(boolean on) {
        ((Activity) mContext).setVolumeControlStream(AudioManager.STREAM_SYSTEM);
        pause();
        if (on) {
            //扩音声筒
            mAudioManager.setMicrophoneMute(false);
            mAudioManager.setSpeakerphoneOn(true);
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            Log.e(TAG, "changeAdapterType: 当前为扩音模式");
        } else {
            //耳麦听筒
            mAudioManager.setSpeakerphoneOn(false);
            mAudioManager.setMicrophoneMute(true);
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            Log.e(TAG, "changeAdapterType: 当前为耳麦模式");
        }
        resume();

    }


}
