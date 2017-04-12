package com.gaoyuan.weixinrecord.example;

/**
 * 作者：wgyscsf on 2017/1/2 18:44
 * 邮箱：wgyscsf@163.com
 * 博客：http://blog.csdn.net/wgyscsf
 */
public class Record {
    private String id;
    private String path;
    private int second;
    private boolean isPlayed;//是否已经播放过
    private boolean isPlaying;//是否正在播放

    public boolean isPlaying() {
        return isPlaying;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public boolean isPlayed() {
        return isPlayed;
    }

    public void setPlayed(boolean played) {
        isPlayed = played;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", second=" + second +
                ", isPlayed=" + isPlayed +
                ", isPlaying=" + isPlaying +
                '}';
    }
}
