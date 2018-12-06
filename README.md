# Jenkins + Sonarqube 代码检测 java 工具设计
## 客户端 SDK 选型
1. `jenkinsci/java-client-api`（选择）

　　`Github`中`Jenkins`官方所使用的方式。
```
<dependency>
    <groupId>com.offbytwo.jenkins</groupId>
    <artifactId>jenkins-client</artifactId>
    <version>0.3.8</version>
</dependency>
```
2. `cdancy/jenkins-rest`

　　早期文档中出现的`Jenkins`的`restful接口`客户端
```
<dependency>
    <groupId>com.cdancy</groupId>
    <artifactId>jenkins-rest</artifactId>
    <version>0.0.13</version>
</dependency>
```
## 思路
1. 项目模板模式进行参数替换完成新项目的创建
2. 抽离公共参数及变动参数

### 测试示例
1. 基础成功示例见：[https://github.com/nanquanyuhao/codeanalysis4j](https://github.com/nanquanyuhao/codeanalysis4j)

### 抽离参数
　　组件参数分为公共配置类参与使用调用变量两类，如下：

| 参数名 | 简要说明 | 是否公共 |
| --- | --- | :---: |
| `serverUri` | 远端对接`Jenkins`地址 | 是 |
| `jenkinsUsername` | 对接`Jenkins`的管理员用户名 | 是 |
| `jenkinsPassword` | 对接`Jenkins`的管理员用户密码 | 是 |
| `repositoryURL` | 对接代码仓库`Gitlab`的直接访问地址，形如：`http://192.168.41.67:10080/`，即：`协议`+`端点`（`/`结尾） | 是 |
| `templateProject` | 作为代码检查项目构建模板的项目名，需要在`Jenkins`环境中已配置好 | 是 |
| `projectKey` | 项目唯一标识，建议由调用方生产唯一约束的值作为`projectKey`，尽量不要包含特殊字符。出于唯一性需要，其同时作为在`Jenkins`平台中建立的任务名称 | 否 |
| `projectName` | `Sonarqube`仪表盘及项目代码质量明细页面中展示的项目名称，不具有唯一性。仍建议由调用方传入，辨识性高且尽量唯一。 | 否 |
| `projectURL` | 代码仓库中，项目相对于根路径的地址，形如：`liyeheng/paas.git`，即：`组织或个人归属路径`+`项目名`+`.git`。（其与`repositoryURL`拼接后绝对定位某项目，如：`http://192.168.41.67:10080/liyeheng/paas.git`）。 | 否 |

### 工具类封装
1. 创建包含全部公共参数的工具类，其在使用至项目中时需要交给项目（通过为`Spring`）初始化
2. 项目接口调用，提供项目的构建创建、构建触发等常用接口。

## 使用
### 依赖
　　配置`Maven`依赖如下：
```
...
    <dependency>
        <groupId>io.github.nanquanyuhao</groupId>
        <artifactId>codeanalysis4j</artifactId>
        <version>0.0.1</version>
    </dependency>
...
```
### 配置
1. `xml`配置文件示例如下：
```
...
    <bean id="codeAnalysis" class="net.nanquanyuhao.codeanalysis4j.impl.CodeAnalysisManagerImpl">
        <!-- 远端对接 Jenkins 地址 -->
        <constructor-arg name="serverUri" value="http://techjenkins:8080"></constructor-arg>
        <!-- Jenkins 的管理员用户名 -->
        <constructor-arg name="jenkinsUsername" value="liyh1928"></constructor-arg>
        <!-- Jenkins 的管理员密码 -->
        <constructor-arg name="jenkinsPassword" value="1qaz@WSX"></constructor-arg>
        <!-- Jenkins 内拉取代码的仓库地址 -->
        <constructor-arg name="repositoryURL" value="http://192.168.41.67:10080/"></constructor-arg>
        <!-- Jenkins 内模板项目的 Job 名称 -->
        <constructor-arg name="templateProjectName" value="sonarqube-template"></constructor-arg>
    </bean>
...
```
2. 代码初始化示例如下：
```
...
    CodeAnalysisManager cam = new CodeAnalysisManagerImpl("http://techjenkins:8080", "liyh1928", "1qaz@WSX", "http://192.168.41.67:10080/", "sonarqube-template");
...
```
### 具体使用
1. 创建代码检测任务并进行检测
```
...
     CodeAnalysisJob caj = cam.createJob("liyeheng.paas","liyeheng/paas","liyeheng/paas.git");
...
```
2. 调用已创建的代码检测任务进行代码检测
```
...
     cam.build("liyeheng.paas");
...
```