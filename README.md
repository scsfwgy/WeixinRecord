## WeixinRecord
### 简介
> 仿安卓微信客户端录音与播放功能。其中录音功能以工具类形式封装，可以直接调用。播放功能以example形式提供解决思路，可以自己定制需要的特定功能。

### 功能
* 录音功能：
	* 长按录音、
	* 录音动画显示、
	* 上滑取消、
	* 录音限制总时长、
	* 倒计时震动提醒。
	* 文件可以直接在IOS上播放
	* M+版本权限拦截
	* 录制保存以及更新到本地数据库

* 播放功能：
	* 播放动画处理
	* 播放切换
	* 重复点击播放停止
	* 退出当前页面终止语音播放
	* 小红点处理

### TODO
* 手机适配
* 扩音器和听筒的切换
* 播放功能的封装

### 效果
![这里写图片描述](http://img.blog.csdn.net/20170107160119893?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2d5c2NzZg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
![这里写图片描述](http://img.blog.csdn.net/20170107160134475?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2d5c2NzZg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
![这里写图片描述](http://img.blog.csdn.net/20170107160145475?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2d5c2NzZg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
![这里写图片描述](http://img.blog.csdn.net/20170107160156412?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvd2d5c2NzZg==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

### 使用

* 请参考工程项目ExampleActivity.java

### 工程

[https://github.com/scsfwgy/WeixinRecord](https://github.com/scsfwgy/WeixinRecord "https://github.com/scsfwgy/WeixinRecord")

### 参考
* 参考：
* 录音类参考网上资料，具体位置不祥，作者看到请联系。

### log
#### 2017/04/12
* 解决按下反馈不及时问题、偶尔崩溃问题。
* 解决数据保存问题；小红点状态记录处理（对于录音删除请自行处理，很简单）。
