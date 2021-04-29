## 部署指南

### 本地运行

1. 基础运行环境配置
   * 安装JDK 1.8, 配置JAVA_HOME
   * 安装Maven，下载链接 https://maven.apache.org/download.cgi   并配置mvn环境变量，配完之后运行 'mvn --version'确认是否配置成功
   * 安装mysql，跑Hello World也可以不用安装，安装的时候请设置密码，安装之后运行下列命令创建database
   ```
   > mysql --version   (校验安装是否成功)
   > mysql.server start (启动server)
   > mysql -u root -p  （登录mysql，命令执行之后需要输入密码）
   > show databases  (查看db所有数据库)
   > create databases niuniu_healthy   （创建db）
   ```
2. 在application.properties中配置mysql连接信息；
3. 直接运行 run MotionServerApplication；
    ```
   > mvn compile
   > 通过IDE Run MotionServerApplication文件
    ```
4. 浏览器中输入地址：http://127.0.0.1:8000/hello

### 服务器部署