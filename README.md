# Home-Security-System
Home Security System Base On IOT
# Description
基于物联网的智能家居安保系统, 使用ETC-WSN传感器, 可以实现串口数据的传输和发送, 实现了一个简单的UI. 
两种报警情况:
1. 当检测到烟雾时报警.
2. 当红外热敏传感器检测到人并且根据摄像头的图像进行行人检测并且检测到有人时报警UI.

# Prepare before use
1. 先配置好opencv和串口gnu.io环境
2. 连接好温湿度, 烟雾, 红外热敏传感器
3. 运行edu.tyut.run包中的SyncRun.java (注意设置ParamConfig的串口号参数)

# INFO
1. edu.tyut.utils为串口数据接收以及数据处理包
2. edu.tyut.pedestrainDetection 为行人检测包
3. edu.tyut.UI 为UI包
4. edu.tyut.run 为项目执行包

