# 就业管理系统

## 一、系统介绍
本系统为就业管理系统，主要围绕高校毕业生的毕业情况进行跟踪和分析，为学校领导对专业设置优化，为高校毕业生就业方向提供参考。
系统分为六大模块：就业管理，招聘咨询，通告管理，学院管理，师生管理，系统管理。 

系统默认有三个角色：管理员，老师，学生用户
- 管理员（admin/admin）：可以操作所有功能
- 老师(身份证号/身份证号后6位)：可以查询学院信息，学生信息，查看就业统计信息等
- 学生（身份证号/身份证号后6位）：可填写就业登记表，问卷调查表，查看就业统计信息等
## 二、角色运行图
### 管理员
![管理员](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/r-1-%E7%AE%A1%E7%90%86%E5%91%98.jpg)
### 老师
![老师](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/r-2-%E8%80%81%E5%B8%88.jpg)
### 学生
![学生](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/r-3-%E5%AD%A6%E7%94%9F.jpg)

## 三、所有功能介绍
### 0.登录
- 登录地址：http://localhost:8805/
- 账号密码：admin/admin

![登录](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/0-1-%E7%99%BB%E5%BD%95.jpg)
![首页](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/0-2-%E9%A6%96%E9%A1%B5.jpg)
![修改密码](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/0-3-%E4%BF%AE%E6%94%B9%E5%AF%86%E7%A0%81.jpg)

### 1.就业管理
就业管理模块主要围绕学生填写的就业登记信息和问卷调查表进行统计和分析，主要分为4个模块：就业统计，就业登记，问卷调查，分析报告
#### （1）就业统计
根据年份，就业城市，单位薪资，专业，平均薪资等统计毕业生就业情况。
![就业统计](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/1-%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86-%E5%B0%B1%E4%B8%9A%E7%BB%9F%E8%AE%A1.jpg)

#### （2）就业登记
学生根据自身实际情况，填写就业信息。
![就业登记-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/1-%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86-%E5%B0%B1%E4%B8%9A%E7%99%BB%E8%AE%B0-%E5%88%97%E8%A1%A8.jpg)
![就业登记-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/1-%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86-%E5%B0%B1%E4%B8%9A%E7%99%BB%E8%AE%B0-%E6%B7%BB%E5%8A%A0.jpg)

#### （3）问卷调查
学生填写就业问卷调查表，根据调查表统计
![问卷调查-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/1-%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86-%E9%97%AE%E5%8D%B7%E8%B0%83%E6%9F%A5-%E5%88%97%E8%A1%A8.jpg)
![问卷调查-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/1-%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86-%E9%97%AE%E5%8D%B7%E8%B0%83%E6%9F%A5-%E6%B7%BB%E5%8A%A0.jpg)

#### （4）分析报告
根据问卷调查表和就业统计的情况撰写就业形式，就业质量分析报告
![分析报告-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/1-%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86-%E5%88%86%E6%9E%90%E6%8A%A5%E5%91%8A-%E5%88%97%E8%A1%A8.jpg)
![分析报告-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/1-%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86-%E5%88%86%E6%9E%90%E6%8A%A5%E5%91%8A-%E6%B7%BB%E5%8A%A0.jpg)

### 2.招聘咨询
招聘信息发布，企业进行校园招聘发布招聘信息及招聘宣讲会
#### （1）招聘信息
企业在校园发布招聘岗位信息
![招聘信息-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/2-%E6%8B%9B%E8%81%98%E5%92%A8%E8%AF%A2-%E6%8B%9B%E8%81%98%E4%BF%A1%E6%81%AF-%E5%88%97%E8%A1%A8.jpg)
![招聘信息-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/2-%E6%8B%9B%E8%81%98%E5%92%A8%E8%AF%A2-%E6%8B%9B%E8%81%98%E4%BF%A1%E6%81%AF-%E6%B7%BB%E5%8A%A0.jpg)

#### （2）招聘宣讲会
企业发布召开招聘宣讲会
![招聘宣讲会-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/2-%E6%8B%9B%E8%81%98%E5%92%A8%E8%AF%A2-%E6%8B%9B%E8%81%98%E5%AE%A3%E8%AE%B2%E4%BC%9A-%E5%88%97%E8%A1%A8.jpg)
![招聘宣讲会-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/2-%E6%8B%9B%E8%81%98%E5%92%A8%E8%AF%A2-%E6%8B%9B%E8%81%98%E5%AE%A3%E8%AE%B2%E4%BC%9A-%E6%B7%BB%E5%8A%A0.jpg)

### 3.通告管理
管理员发布系统的公告信息和其他用户对系统进行留言反馈问题。
#### （1）公告管理
管理员发布校园公告及系统公告信息
![公告管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/3-%E9%80%9A%E5%91%8A%E7%AE%A1%E7%90%86-%E5%85%AC%E5%91%8A%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.jpg)
![公告管理-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/3-%E9%80%9A%E5%91%8A%E7%AE%A1%E7%90%86-%E5%85%AC%E5%91%8A%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)

#### （2）留言管理
系统其他用户反馈系统或校园问题
![留言管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/3-%E9%80%9A%E5%91%8A%E7%AE%A1%E7%90%86-%E7%95%99%E8%A8%80%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.jpg)
![留言管理-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/3-%E9%80%9A%E5%91%8A%E7%AE%A1%E7%90%86-%E7%95%99%E8%A8%80%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)

### 4.学院管理
主要对院系，专业，班级进行管理，作为系统的基础信息
#### （1）院系管理
管理院系信息
![院系管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/4-%E5%AD%A6%E9%99%A2%E7%AE%A1%E7%90%86-%E9%99%A2%E7%B3%BB%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.jpg)
![院系管理-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/4-%E5%AD%A6%E9%99%A2%E7%AE%A1%E7%90%86-%E9%99%A2%E7%B3%BB%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)

#### （2）专业管理
管理专业信息
![专业管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/4-%E5%AD%A6%E9%99%A2%E7%AE%A1%E7%90%86-%E4%B8%93%E4%B8%9A%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.jpg)
![专业管理-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/4-%E5%AD%A6%E9%99%A2%E7%AE%A1%E7%90%86-%E4%B8%93%E4%B8%9A%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)

#### （3）班级管理
管理班级信息
![班级管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/4-%E5%AD%A6%E9%99%A2%E7%AE%A1%E7%90%86-%E7%8F%AD%E7%BA%A7%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.jpg)
![班级管理-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/4-%E5%AD%A6%E9%99%A2%E7%AE%A1%E7%90%86-%E7%8F%AD%E7%BA%A7%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)

### 5.师生管理
对老师和学生信息进行管理
#### （1）老师管理
管理员对老师信息进行管理
![老师管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/5-%E5%B8%88%E7%94%9F%E7%AE%A1%E7%90%86-%E8%80%81%E5%B8%88%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.jpg)
![老师管理-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/5-%E5%B8%88%E7%94%9F%E7%AE%A1%E7%90%86-%E8%80%81%E5%B8%88%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)

#### （2）学生管理
老师或管理员对学生信息进行管理
![学生管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/5-%E5%B8%88%E7%94%9F%E7%AE%A1%E7%90%86-%E5%AD%A6%E7%94%9F%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.jpg)
![学生管理-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/5-%E5%B8%88%E7%94%9F%E7%AE%A1%E7%90%86-%E5%AD%A6%E7%94%9F%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)


### 6.系统管理
系统管理子模块:员工管理，部门管理，角色管理，菜单管理，岗位管理，字典管理。
#### （1）账号管理
管理员对系统登录账号进行管理，可以为新员工申请登录账号，为离职员工删除账号
![员工管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/6-1-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E8%B4%A6%E6%88%B7%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.jpg)
![员工管理-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/6-1-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E8%B4%A6%E6%88%B7%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)

#### （2）部门管理
可增加新部门，通常只有管理员和高级管理领导可用
![部门管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/6-4-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E9%83%A8%E9%97%A8%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)

#### （3）角色管理
可新增角色，并为角色赋予相应权限，通常只有管理员和高级管理领导可用
![角色管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/6-2-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E8%A7%92%E8%89%B2%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.jpg)
![角色管理-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/6-2-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E8%A7%92%E8%89%B2%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)
![角色管理-权限设置](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/6-2-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E8%A7%92%E8%89%B2%E7%AE%A1%E7%90%86-%E6%9D%83%E9%99%90%E8%AE%BE%E7%BD%AE.jpg)

#### （4）菜单管理
管理系统左侧的菜单树，及系统的功能菜单，通常只有管理员可用
![菜单管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/6-3-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E8%8F%9C%E5%8D%95%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.jpg)
![菜单管理-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/6-3-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E8%8F%9C%E5%8D%95%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)


#### （5）岗位管理
对系统的岗位信息进行增加，删除，修改，查询
![岗位管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/6-5-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E5%B2%97%E4%BD%8D%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.jpg)
![岗位管理-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/6-5-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E5%B2%97%E4%BD%8D%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)

#### （6）字典管理
对系统中常用字典数据进行管理，如城市信息，企业分类信息
![菜单管理-列表](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/6-6-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E5%AD%97%E5%85%B8%E7%AE%A1%E7%90%86-%E5%88%97%E8%A1%A8.jpg)
![菜单管理-增加](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/6-6-%E7%B3%BB%E7%BB%9F%E7%AE%A1%E7%90%86-%E5%AD%97%E5%85%B8%E7%AE%A1%E7%90%86-%E6%B7%BB%E5%8A%A0.jpg)

## 四、软件架构

基础环境：
1. JDK:1.8
2. MySQL:5.7
3. Maven3.0

使用框架：

1. 核心框架：Spring Boot 2.3.12.RELEASE
2. ORM框架：mybatis 3.4.0
4. 数据库连接池：Druid 1.2.8
5. 安全框架：Apache Shiro 1.8
6. 日志：SLF4J 1.7、Log4j
7. 前端框架：Layui,ztree,jquery,echarts



## 五、安装教程
1. 导入mysql脚本,数据库名称：sp_eta
2. 修改数据库配置：

![修改配置](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/0-99-%E9%85%8D%E7%BD%AE.jpg)
3. 启动java工程（执行assess_system工程com.assess.Application.class中main方法）

![启动项目](https://raw.githubusercontent.com/doudoutangs/eta_system/main/%E5%B0%B1%E4%B8%9A%E7%AE%A1%E7%90%86/0-4-%E5%90%AF%E5%8A%A8%E9%A1%B9%E7%9B%AE.jpg)
4. 访问：http://localhost:8805（账号admin/admin）

## 六、源码说明
0. QQ:553039957
1. gitcode主页： https://gitcode.com/user/tbb414 (推荐)
2. github主页：https://github.com/doudoutangs
3. gitee(码云)主页：https://gitee.com/doudoutang
## 七、其他项目
1. [人事管理系统](https://gitcode.com/tbb414/person_system)
2. [薪资管理系统](https://gitcode.com/tbb414/salary_system)
3. [OA系统](https://gitcode.com/tbb414/oa_system)
4. [招投标管理系统](https://gitcode.com/tbb414/bid_system)
5. [绩效考核系统](https://gitcode.com/tbb414/assess_system)
6. [就业管理系统](https://gitcode.net/tbb414/eta_system)
