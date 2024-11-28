# windows软件列表

[toc]

### 7-zip

### autodarkmode

> 设置启动时间

### Baidupan

>文件路径，同时上传下载任务数，禁止开机自启

### Baidu Ai studio

> ```shell
> //环境
> pip install -i https://pypi.tuna.tsinghua.edu.cn/simple torch==1.6.0+cu101 torchvision==0.7.0+cu101 -f https://download.pytorch.org/whl/torch_stable.html
> pip install -i https://pypi.tuna.tsinghua.edu.cn/simple numpy matplotlib  scipy  pandas scikit-learn tensorboard 
> pip install -i https://pypi.tuna.tsinghua.edu.cn/simple opencv jupyterlab sympy pylint seaborn
> //查看占用
> nvidia-smi -l 3
> ```

### CAJ viewer

>1、安装历史版本
>
>2、计算机--我的文档--My eBooks--下面有2-3个XML文件，分别是“ad0”“event”“help”,用记事本打开，删除全部全部，然后将文件设置为只读。
>		3、控制面板--防火墙--阻止CAJviewer上网

### Chrome

> 文件路径，搜索引擎，Ghelper，油猴，uBlock Origin，darkreader，有道划词翻译， bitwarden，AI/ML Papers with Code Everywhere - CatalyzeX，Enable Copy，Google学术搜索按钮，HTTPS Everywhere，IDM Integration Module，Listen 1，SmoothScroll，uBlacklist，Zotero Connector，卡巴斯基保护，封锁网站，谷歌上网助手 开发版

```
// ==UserScript==
// @name         知乎助手
// @namespace    http://tampermonkey.net/
// @version      1.0.28
// @description  功能简介：设置面板默认隐藏，按右下角黑色+号，显示。一，屏蔽时间线中纯视频营销号回答，二屏蔽各类广告。三，根据关键词屏蔽回答
// @author       桃源隐叟
// @match        *://www.zhihu.com/*
// @match        *://www.zhihu.com
// @grant        none
//@require https://code.jquery.com/jquery-2.1.4.min.js
// ==/UserScript==

(function() {
    'use strict';
    /* globals jQuery, $, waitForKeyElements */

    // Your code here...
    var controlPanel=`<p class="toggle-control" style="z-index:201;position:fixed;right:100px;bottom:100px;margin:2px 1px 1px 2px;text-decoration:underline;">
<img src="http://pic.90sjimg.com/design/00/21/84/57/58fd89ee39300.png!/fw/250/quality/90/unsharp/true/compress/true/canvas/250x250/cvscolor/FFFFFFFF" style="width:30px;height:30px;"></p>
<div style="z-index:200;position:fixed;right:100px;bottom:100px;border:1px solid #888;padding:30px;border-radius:5px;background-color:white;display:none" id="control-div">
<h2>设置屏蔽选项</h2>
<br>
<span>屏蔽购物推荐</span><input type="radio" name="recommend" value="on" checked>开<input type="radio" name="recommend" value="off">关<br>
<span>屏蔽信息流广告</span><input type="radio" name="ads" value="on" checked>开<input type="radio" name="ads" value="off">关<br>
<span>屏蔽首页关键词</span><input type="radio" name="keyword" value="on" checked>开<input type="radio" name="keyword" value="off">关<br>
<input type="text" placeholder="test1,test2" class="blockkeyword"><br>
<span>屏蔽问题关键词</span><input type="radio" name="qKeyword" value="on" checked>开<input type="radio" name="qKeyword" value="off">关<br>
<input type="text" placeholder="知乎盐选" class="questionB"><br>
<span>屏蔽知乎</span><input type="radio" name="zhihu" value="on" >开<input type="radio" name="zhihu" value="off" checked>关<br>
<input type="text" placeholder="好好工作，暂时别看知乎，目前XX还没有完成" class="blocksite"><br>
</div>`

    document.body.insertAdjacentHTML("afterBegin",controlPanel);


    window.onload=()=>{
        initSetting();
        loadSetting();
        funcBlockAds();
        funcBlockByKeyWord();
        funcBlockSite();
        funcBlockQuestion();
    }

    document.body.onscroll=function(){
        funcBlockRecommend();
        funcBlockAds();
        funcBlockByKeyWord();
        funcBlockSite();
        funcBlockQuestion();
    }


    function funcBlockRecommend(){
        if($("[name='recommend']:checked")[0].value==="on"){
            $(".RichText-MCNLinkCardContainer").css("display","none");
        }else{
            $(".RichText-MCNLinkCardContainer").css("display","block");
        }
    }
    function funcBlockAds(){
        if($("[name='ads']:checked")[0].value==="on")
        {
            $(".Card").find(".ZVideoItem").parent().parent().css("display","none");
            $(".TopstoryItem--advertCard").css("display","none");
            $(".Pc-card").css("display","none");
        }else{
            $(".Card").find(".ZVideoItem").parent().parent().css("display","block");
            $(".TopstoryItem--advertCard").css("display","block");
            $(".Pc-card").css("display","block");
        }
    }

    function funcBlockByKeyWord(){
        //var blockKeywords=$(".blockkeyword")[0].value;
        var blockKeywords="龙牙,乌合麟麟,孟晚舟,妹子,华为,远方青木";
        if(blockKeywords!=""){
            var bkArray=blockKeywords.split(",");
            for(let i=0;i<bkArray.length;i++){
                if($("[name='keyword']:checked")[0].value==="on"){
                    $(`.TopstoryItem:contains(${bkArray[i]})`).css("display","none");
                }else{
                    $(`.TopstoryItem:contains(${bkArray[i]})`).css("display","block");
                }
            }
        }

    }

    function funcBlockQuestion(){
        //var questionBs=$(".questionB")[0].value;
        var questionBs="龙牙,乌合麟麟,孟晚舟,妹子,华为,远方青木";
        if(questionBs!=""){
            var qb=questionBs.split(",");
            for(let i=0;i<qb.length;i++){
                if($("[name='qKeyword']:checked")[0].value==="on"){
                    $(`.List-item:contains(${qb[i]})`).css("display","none");
                }else{
                    $(`.List-item:contains(${qb[i]})`).css("display","block");
                }
            }
        }

    }

    function funcBlockSite(){
        if($("[name='zhihu']:checked")[0].value==="on"){
            var blockTip=$(".blocksite")[0].value?$(".blocksite")[0].value:$(".blocksite")[0].placeholder;
            var blockHtml=`<h1 style="text-align:center;font-size:50px;">${blockTip}</h1>`;
            //$("body").css("display","none");

            //$("body").html(blockHtml);

            var bodyChildren=$("body").children();
            for(let i=0;i<bodyChildren.length;i++){
                if(bodyChildren[i].id!="control-div"){
                    $(bodyChildren[i]).css("display","none")
                }
            }
            //$("#control-div").css("display","block");
            $(".toggle-control").css("display","block");
            $("body").prepend(blockHtml);
            $("#container").css("display","none");
            $("iframe").css("display","none");
        }else{
            //$("body").html("");
        }
    }


    $("[name='recommend']").on("click",function(){
        setCookie('recommend',$("[name='recommend']:checked")[0].value);
    });

    $("[name='ads']").on("click",function(){
        setCookie('ads',$("[name='ads']:checked")[0].value);
    });

    $("[name='keyword']").on("click",function(){
        setCookie('blockkeywordSwitch',$("[name='keyword']:checked")[0].value);
        setCookie('blockkeyword',$(".blockkeyword")[0].value);
    });

    $("[name='qKeyword']").on("click",function(){
        setCookie('questionBlockSwitch',$("[name='qKeyword']:checked")[0].value);
        setCookie('questionKeyword',$(".questionB")[0].value);
    });

    $("[name='zhihu']").on("click",function(){
        setCookie('blocksiteswitch',$("[name='zhihu']:checked")[0].value);
        setCookie('blocksiteTip',$(".blocksite")[0].value);
    });

    $(".blockkeyword").blur(function(){
        setCookie('blockkeyword',$(".blockkeyword")[0].value);
    });

    $(".questionB").blur(function(){
        setCookie('questionKeyword',$(".questionB")[0].value);
    });

    $(".blocksite").blur(function(){
        setCookie('blocksiteTip',$(".blocksite")[0].value);
    });

    $(".toggle-control").click(function(){
        $("#control-div").toggle();
    });


    function setCookie(name,value)
    {
        var Days = 30;
        var exp = new Date();
        exp.setTime(exp.getTime() + Days*24*60*60*1000);
        document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
    }

    function getCookie(name)
    {
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");

        if(arr=document.cookie.match(reg))

            return unescape(arr[2]);
        else
            return null;
    }

    function loadSetting(){
        if(getCookie("recommend")!=null){
            $(`[name='recommend'][value=${getCookie("recommend")}]`)[0].checked=true;
        }else{
        }

        if(getCookie("ads")!=null){
            $(`[name='ads'][value=${getCookie("ads")}]`)[0].checked=true;
        }else{
        }

        if(getCookie("blockkeywordSwitch")!=null){
            $(`[name='keyword'][value=${getCookie("blockkeywordSwitch")}]`)[0].checked=true;
            $(".blockkeyword")[0].value=getCookie("blockkeyword");
        }else{
        }

        if(getCookie("questionBlockSwitch")!=null){
            $(`[name='qKeyword'][value=${getCookie("questionBlockSwitch")}]`)[0].checked=true;
            $(".questionB")[0].value=getCookie("questionKeyword");
        }else{
        }

        if(getCookie("blocksiteswitch")!=null){
            $(`[name='zhihu'][value=${getCookie("blocksiteswitch")}]`)[0].checked=true;
            $(".blocksite")[0].value=getCookie("blocksiteTip");
        }else{
        }
    }

    function initSetting(){
        if(getCookie("recommend")==null){
            setCookie('recommend',$("[name='recommend']:checked")[0].value);
        }else{
        }

        if(getCookie("ads")==null){
            setCookie('ads',$("[name='ads']:checked")[0].value);
        }else{
        }

        if(getCookie("blockkeywordSwitch")==null){
            setCookie('blockkeywordSwitch',$("[name='keyword']:checked")[0].value);
            setCookie('blockkeyword',$(".blockkeyword")[0].value);
        }else{
        }

        if(getCookie("questionBlockSwitch")==null){
            setCookie('questionBlockSwitch',$("[name='qKeyword']:checked")[0].value);
            setCookie('questionKeyword',$(".questionB")[0].value);
        }else{
        }

        if(getCookie("blocksiteswitch")==null){
            setCookie('blocksiteswitch',$("[name='zhihu']:checked")[0].value);
            setCookie('blocksiteTip',$(".blocksite")[0].value);
        }else{
        }
    }

})();

```



> ```
> ! 2021-09-26 https://www.bilibili.com
> www.bilibili.com##.grid-anchor.short-margin.bili-grid
> www.bilibili.com##.eva-extension-body
> www.bilibili.com##.bili-layout > .no-margin.bili-grid
> www.bilibili.com##section.bili-grid:nth-of-type(4)
> www.bilibili.com##section.bili-grid:nth-of-type(5)
> www.bilibili.com##.bangumi-area > div.bili-grid
> www.bilibili.com##.guo-chuang-area > div.bili-grid
> www.bilibili.com##.variety-body
> www.bilibili.com##section.bili-grid:nth-of-type(9)
> www.bilibili.com##section.bili-grid:nth-of-type(10)
> www.bilibili.com##section.bili-grid:nth-of-type(11)
> www.bilibili.com##section.bili-grid:nth-of-type(12)
> www.bilibili.com##section.bili-grid:nth-of-type(13)
> www.bilibili.com##section.bili-grid:nth-of-type(14)
> www.bilibili.com##section.bili-grid:nth-of-type(15)
> www.bilibili.com##section.bili-grid:nth-of-type(16)
> www.bilibili.com##section.bili-grid:nth-of-type(17)
> www.bilibili.com##section.bili-grid:nth-of-type(18)
> www.bilibili.com##section.bili-grid:nth-of-type(19)
> www.bilibili.com##section.bili-grid:nth-of-type(20)
> www.bilibili.com##section.bili-grid:nth-of-type(22)
> www.bilibili.com##.information-body
> www.bilibili.com##.information-area
> www.bilibili.com##section.bili-grid:nth-of-type(24)
> www.bilibili.com##section.bili-grid:nth-of-type(25)
> www.bilibili.com##section.bili-grid:nth-of-type(26)
> www.bilibili.com##section.bili-grid:nth-of-type(27)
> www.bilibili.com##section.bili-grid:nth-of-type(28)
> www.bilibili.com##section.bili-grid:nth-of-type(29)
> www.bilibili.com##.bili-footer
> www.bilibili.com##.variety-area
> www.bilibili.com##.eva-extension-area
> www.bilibili.com##.unlogin-popover-avatar.unlogin-popover
> www.bilibili.com##.r-con
> www.bilibili.com##.common
> www.bilibili.com##.left
> ||i0.hdslb.com/bfs/activity-plat/static/b86d13b837429c58b4c061e598b3b23f/g6YHRXaKP0_w320_h100.png$image
> www.bilibili.com##.inside-wrp
> 
> ! 2021-09-28 https://www.bilibili.com
> www.bilibili.com###bili_fashion > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_information > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_ent > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_read > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_movie > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_teleplay > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_cinephile > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_documentary > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com##.report-scroll-module.report-wrap-module.b-wrap.s-rec.space-between
> www.bilibili.com##.international-footer
> www.bilibili.com###bili_animal > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_food > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com##.b-wrap.first-screen > div.space-between
> www.bilibili.com###bili_live > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_douga > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_report_anime > div.space-between
> www.bilibili.com###bili_report_guochuang > div.space-between
> www.bilibili.com###bili_manga > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_music > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_dance > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_game > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_knowledge > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_report_cheese > .space-between
> www.bilibili.com###bili_tech > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_sports > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_car > .report-scroll-module.report-wrap-module.space-between
> www.bilibili.com###bili_life > .report-scroll-module.report-wrap-module.space-between
> 
> ! 2021-09-29 https://live.bilibili.com
> live.bilibili.com##.guide-content
> live.bilibili.com##.login-guide
> 
> ! 2021-09-30 https://pan.baidu.com
> pan.baidu.com##.share-center
> pan.baidu.com##[href^="http://wan.baidu.com/cover"] > .ad-warn
> 
> ```
>

### clash

> settings->system proxy -> bypass domain/ipnet 
>
> ```
>   - "ieee.org"
>   - "seu.edu.cn"
>   - "pypi.tuna.tsinghua.edu.cn"
>   - "mirrors.tuna.tsinghua.edu.cn/anaconda"
> ```
>
> pip :`pip config set global.index-url https://pypi.tuna.tsinghua.edu.cn/simple`
>
> conda 使用清华源

### CUDA

> cuda安装除Geforce外组件，
>
> cudnn内容复制到C:\Program Files\NVIDIA GPU Computing Toolkit\CUDA\v9.2

### Cmder

> powershell管理员权限运行  .\Cmder.exe /REGISTER ALL
>
> git添加到环境变量：`C:\DevTools\cmder\vendor\git-for-windows\bin`
>
> 更换字体：concolas
>
> 关闭热键：ctr+, 字体 字号

### Git

> ```
> git config --global user.name "huangqiang"
> git config --global user.email "huangqiang97@126.com"
> ssh-keygen -t rsa -C "huangqiang97@126.com"
> ```
>
> 把id_rsa.pub添加到github SSH密钥中
>
> ---------
>
> 博客设置：
>
> ```git clone https://gitee.com/huangqiang97/huangqiang97.git```
>
> Create git repository:
>
> ```
> mkdir huangqiang97
> cd huangqiang97
> git init
> git commit -m "first commit"
> git remote add origin https://gitee.com/huangqiang97/huangqiang97.git
> git push -u origin master
> ```
>
> Existing repository
>
> ```
> cd existing_git_repo
> git remote add origin https://gitee.com/huangqiang97/huangqiang97.git
> git push -u origin master
> ```

### Ditto

> 设定保存时间与条数，开机自启

###  Dotnet

> 将安装路径加入环境变量

### Everything

> 开机自启

### Eudic

> 安装词库
>
> 鼠标取词
>
> 开机启动

### PDF X-change viewer

> 简体文字

### Huorong

> 开机自启菜单，文件右键菜单，垃圾清理，

### Go

> Go开启代理：```go env -w GOPROXY=https://goproxy.cn,direct```
>
> 新建环境变量：```set GOPATH=D:\root\GoTools;D:\root\Go```，环境变量-用户变量：```GOPATH=D:\root\GoTools;D:\root\Go```
>
> 查看环境参数：```go env```
>
> path:        ``` GoInstallPath\bin```
>
> GOROOT: ```GoInstallPath```
>
> GOPATH:  工作目录，下有：src pkg bin子目录，可存在多个，优先级依次降低
>
> 找包顺序：```gomod =off : goroot/src->gopath0/src->gopath2/src```
>
> 	``` gomod =on  : goroot/pkg/mod->gopath0/pkg/mod->gopath2/pkg/mod```
>
> ------------------
>
> 自动初始化目录：```sh ./create_go_proj.sh porject_name```
>
> ```sh
> #!/bin/bash
> 
> #########################################################
> 
> # Useage : ./create_go_proj.sh
> # sh ./create_go_proj.sh porject_name
> # Description: 创建一个go可编译的工程
> #————————————–————————————–
> # 默认情况下运行本程序，会生成如下目录和文件:
> # test
> # ├── bin
> # ├── install.sh
> # ├── pkg
> # └── src
> # ├── config
> # │   └── config.go
> # └── test
> # └── main.go
> #————————————–————————————–
> # 5 directories, 3 files
> # 其中:
> # 1, install.sh为安装文件，
> # 2, config.go为test项目的配置文件
> # 3, main.go
> # 生成完毕之后运行进入test目录，运行install.sh会生成如下文件和目录
> # ├── bin
> # │   └── test
> # ├── install.sh
> # ├── pkg
> # │   └── darwin_amd64
> # │   └── config.a
> # └── src
> # ├── config
> # │   └── config.go
> # └── test
> # └── main.go
> # 6 directories, 5 files
> #
> # 多了两个文件
> # 1, bin目录下的test，这个是可执行文件
> # 2, pkg/darwin_amd64下的config.a，这个是config编译后产生的文件
> #
> #########################################################
> PWD=$(pwd)
> cd $PWD
> 
> if [[ "$1" = "" ]]; then
> echo "Useage: ./mk_go_pro.sh porject_name"
> echo -ne "Please input the Porject Name[test]"
> read Answer
> if [ "$Answer" = "" ]; then
> echo -e "test";
> PRO_NAME=test;
> else
> PRO_NAME=$Answer;
> fi
> else
> PRO_NAME=$1;
> fi
> 
> #########################################################
> 
> #创建目录
> echo "Init Directory …"
> mkdir -p $PRO_NAME/bin
> mkdir -p $PRO_NAME/pkg
> mkdir -p $PRO_NAME/src/config
> mkdir -p $PRO_NAME/src/$PRO_NAME
> 
> 
> #########################################################
> #创建 install.sh 文件
> echo "Create install/install.sh …"
> cd $PRO_NAME
> echo "#!/bin/bash" > install.sh
> echo "if [ ! -f install.sh ]; then" >> install.sh
> echo "echo "install must be run within its container folder" 1>&2" >> install.sh
> echo "exit 1" >> install.sh
> echo "fi" >> install.sh
> echo >> install.sh
> echo "CURDIR=\`pwd\`" >> install.sh
> echo "OLDGOPATH=\"\$GOPATH\"" >> install.sh
> echo "export GOPATH=\"\$CURDIR\"" >> install.sh
> echo >> install.sh
> echo "gofmt -w src" >> install.sh
> echo "go install $PRO_NAME" >> install.sh
> echo "export GOPATH=\"\$OLDGOPATH\"" >> install.sh
> echo >> install.sh
> echo "echo "finished"" >>install.sh
> chmod +x install.sh
> 
> #创建 config.go 文件
> echo "Create src/config/config.go …"
> cd src/config
> echo package config > config.go
> echo >> config.go
> echo func LoadConfig\(\) { >> config.go
> echo >> config.go
> echo "}" >> config.go
> 
> #创建 main.go
> echo "Create src/$PRO_NAME/main.go …"
> cd ../$PRO_NAME/
> echo "package main" > main.go
> echo >> main.go
> echo "import (" >> main.go
> echo " \"config\"" >> main.go
> echo " \"fmt\"" >> main.go
> echo ")" >> main.go
> echo >> main.go
> echo "func main() {" >> main.go
> echo " config.LoadConfig()" >> main.go
> echo " fmt.Println(\"Hello $PRO_NAME!\")" >> main.go
> echo "}" >> main.go
> echo "All Done!"
> ```

### Goland

> 字体
>
> 将当前工程路径加入project gopath
>
> proj gopath 优先于 glob gopath
>
> solarized  themes
>
> 

### Intel 核显驱动

### IDEA

> 字体
>
> class注释：
> ```
> 
> #if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
> #parse("File Header.java")
> /**
>  * @author  huangqiang
>  * @date    ${DATE} ${TIME}
>  * @description TODO
>  * @modified    
>  * @version TODO
>  */
> public class ${NAME} {
> }
> 勾选 enable live Templates
> 
> ```
>
> 方法注释：
>
> ```
> 快捷方式：/*
> /**
> 
>  * @create  huangqiang
>  * @description TODO
>  * @time    $date$ $time$
>  * @param   $params$
>  * @return  $return$
>  */
> 
> ```
>
> alibaba插件 , gruvbox ,solarized themes , junitGenerator
>
> ```
> ######################################################################################## 
> ## 
> ## Available variables: 
> ##         $entryList.methodList - List of method composites 
> ##         $entryList.privateMethodList - List of private method composites 
> ##         $entryList.fieldList - ArrayList of class scope field names 
> ##         $entryList.className - class name 
> ##         $entryList.packageName - package name 
> ##         $today - Todays date in MM/dd/yyyy format 
> ## 
> ##            MethodComposite variables: 
> ##                $method.name - Method Name 
> ##                $method.signature - Full method signature in String form 
> ##                $method.reflectionCode - list of strings representing commented out reflection code to access method (Private Methods) 
> ##                $method.paramNames - List of Strings representing the method's parameters' names 
> ##                $method.paramClasses - List of Strings representing the method's parameters' classes 
> ## 
> ## You can configure the output class name using "testClass" variable below. 
> ## Here are some examples: 
> ## Test${entry.ClassName} - will produce TestSomeClass 
> ## ${entry.className}Test - will produce SomeClassTest 
> ## 
> ######################################################################################## 
> ## 
> #macro (cap $strIn)$strIn.valueOf($strIn.charAt(0)).toUpperCase()$strIn.substring(1)#end 
> ## Iterate through the list and generate testcase for every entry. 
> #foreach ($entry in $entryList) 
> #set( $testClass="${entry.className}Test") 
> ## 
> package test.$entry.packageName; 
> 
> import static org.junit.jupiter.api.Assertions.*;
> 
> import org.junit.jupiter.api.*;
> 
> /** 
> * ${entry.className} Tester. 
> * 
> * @author huangqiang
> * @since 
> * @version 1.0 
> */ 
> public class $testClass { 
> 
>     @BeforeAll
>     public static void beforeAll() throws Exception { 
>     } 
> 
> 
>     @BeforeEach
>     public void beforeEach() throws Exception { 
>     } 
> 
>     @AfterEach
>     public void afterEach() throws Exception { 
>     } 
> 
>     @AfterAll
>     public static void afterAll() throws Exception { 
>     } 
> 
> #foreach($method in $entry.methodList) 
>     /** 
>     * 
>     * Method: $method.signature 
>     * 
>     */ 
>     @Test
>     public void test#cap(${method.name})() throws Exception { 
>     //TODO: Test goes here... 
>     } 
> 
> #end 
> 
> #foreach($method in $entry.privateMethodList) 
> /** 
> * 
> * Method: $method.signature 
> * 
> */ 
>     @Test
>     public void test#cap(${method.name})() throws Exception { 
>         //TODO: Test goes here... 
>         #foreach($string in $method.reflectionCode) 
>         $string 
>         #end 
>     } 
> 
> #end 
> } 
> #end
> ```
>
> 

### IDM

> 下载路径，线程数，菜单图标样式：3D,下载图标：mini
>
> chrome 插件：https://chrome.google.com/webstore/detail/idm-integration-module/ngpampappnmepgilojfohadhhmbhlaek

### Imagine

### Itools

### JJDown

> 下载路径

### Listen1

> 主题

### JDK

> JDK/bin 加入环境变量

### MacType

> 更改字体

### Matlab

> 修改快捷方式起始目录
>
> 220200896@seu.edu.cn 
>
> 112358Hq

### MDict

### MS Todo

> 日程

### Mysql

> VC_redist.exe
>
> bin加入环境变量
>
> my.ini
>
> ```
> [client]
> # 设置mysql客户端默认字符集
> default-character-set=utf8mb4
> 
> [mysqld]
> # 设置3306端口
> port = 3306
> # 设置mysql的安装目录
> basedir=D:\\mysql
> # 允许最大连接数
> max_connections=20
> # 服务端使用的字符集默认为8比特编码的latin1字符集
> character-set-server=utf8mb4
> # 创建新表时将使用的默认存储引擎
> default-storage-engine=INNODB
> ```
>
> ```shell
> 
> cd mysql/bin
> mysqld --initialize --console   //会生成临时密码
> mysqld install
> net start mysql
> // 更改密码 eBOBDo!PG4T?
> ALTER user 'root'@'localhost' IDENTIFIED BY 'root';
> FLUSH PRIVILEGES;
> 
> 
> 
> // 更改加密方式
> ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
> flush privileges;
> ```
>
> IDEA :mysql serverTimeZone :  Asia/Shanghai

### MinGW

> ```
> 加入系统环境
> D:\MinGw_x86_64\mingw64\bin
> 
> D:\MinGw_x86_64\mingw64\lib
> 
> D:\MinGw_x86_64\mingw64\include
> ```

### Mathpix

> 禁止开机自启
>
> 登录

### Miniconda

>```
>conda create -n pytorch python=3.9
>activate pytorch
>conda install pytorch torchvision torchaudio cpuonly -c pytorch
>conda  install tensorboard numpy matplotlib imageio scipy seaborn pandas scikit-learn jupyterlab   
>
>
>conda create -n tensorflow python=3.9
>activate tensorflow
>conda  install tensorboard tensorflow numpy matplotlib imageio scipy seaborn pandas scikit-learn jupyterlab autopep8 
>
>
>
>conda config
>.condarc
>
>channels:
>- defaults
>show_channel_urls: true
>default_channels:
>- https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main
>- https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/r
>- https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/msys2
>custom_channels:
>conda-forge: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>msys2: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>bioconda: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>menpo: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>pytorch: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>simpleitk: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>
>jupyter插件：lsp,jupyterlab_variableinspector，drawio
>pip install jupyter-lsp
>conda install  nodejs
>jupyter labextension install @krassowski/jupyterlab-lsp
>pip install python-language-server[python]
>重建：jupyter lab build
>```
>
>win + r 运行 regedit 
>
>打开：计算机\HKEY_CLASSES_ROOT\Directory\Background\shell\   
>
>新建项 ： jupyter
>
>把jupyter项中默认字符串数据改为 ： jupyter lab
>
>在jupyter项中新建字符串：名称为 Icon ，数据指向jupyter icon
>
>在jupyter项中新建项 command 默认字符串数据改为  "D:\MiniConda\envs\pytorch\Scripts\jupyter-lab.exe" "%V"
>
>D:\root\MiniConda D:\root\MiniConda\Scripts D:\root\MiniConda\\Library\bin

### Notion

### Notepad3 

>  字体

### Nox

> 安卓模拟器

### Nutstore

> 同步文件夹

### NeteaseMail

> 文件夹

### NeteaseDict

> 取词：  shift+鼠标
>
> 离线词典
>
> 开机自启

### NodeJS

### Office

> 关闭自动更新
>
> 更改文件夹

### Potplayer

> lavfilter
>
> 独显解码

### PowerToys

> 下载文件：https://github.com/IzaiahSun/PowerToys ，将zip里的modules\launcher\plugins，将整个Community.PowerToys.Run.Plugin.Everything文件夹复制到系统中已经安装好的PowerToys目录\modules\launcher\Plugins中.



### Pycharm

### PyQt

> pip install pyqt5
>
> pip install  pyqt5-tools
>
> 新建环境变量： QT_QPA_PLATFORM_PLUGIN_PATH=D:\MiniConda\Lib\site-packages\pyqt5_tools\Qt\plugins
>
> 
>
> PyCharm的File->Settings->Tools->External tools 添加工具：
>
> name = QtDesigner
>
> Program = D:\MiniConda\Lib\site-packages\pyqt5_tools\Qt\bin\designer.exe
>
> working dir =  E:\PycharmProject\QtProject\UI
>
> 
>
> name = PyUIC
>
> Program = D:\MiniConda\python.exe
>
> Argu = -m PyQt5.uic.pyuic  $FileName$ -o $FileNameWithoutExtension$.py
>
> working dir = E:\PycharmProject\QtProject\UI

### Simplestickynotes

### Snipaste

> 开机自启

### TexLive

> ```D:\Texlive\texlive\2019\bin\win32``` 加入系统路径

### Texstudio

### Typora

> ```C:\Users\huang\AppData\Roaming\Typora\themes``` 加入主题
>
> pandoc

### thunder

> Thunder\Data\ThunderPush\updatethunder\Program\OnlineInstall.exe 只读
>
> 修改文件夹

### Tim

> 缓存文件路径，禁止开机自启
>
> https://www.geek-share.com/detail/2705472906.html

### TrafficMonitor

> 状态栏，开机自启

### v2rayN

开机自启动

### VMware

> 15：UY758-0RXEQ-M81WP-8ZM7Z-Y3HDA
>
> 16：ZF3R0-FHED2-M80TY-8QYGC-NPKYF
>
> ---
>
> Manjaro
>
> 换源 
>
> ```
> sudo pacman-mirrors -i -c China -m rank
> 
> sudo vi /etc/pacman.conf
> 修改/etc/pacman.conf,在最后一行添加：
> [archlinuxcn]
> SigLevel = Optional TrustedOnly
> #中科大源
> Server = https://mirrors.ustc.edu.cn/archlinuxcn/$arch
> #清华源
> Server = https://mirrors.tuna.tsinghua.edu.cn/archlinuxcn/$arch
> [antergos]
> SigLevel = TrustAll
> Server = https://mirrors.tuna.tsinghua.edu.cn/antergos/$repo/$arch
> [arch4edu]
> SigLevel = TrustAll
> Server = https://mirrors.tuna.tsinghua.edu.cn/arch4edu/$arch
> 
> 安装archlinuxcn签名钥匙：
> sudo pacman-mirrors -g
> sudo pacman -Syyu
> sudo pacman -Syy
> sudo pacman -S archlinuxcn-keyring
> sudo pacman -S antergos-keyring
> 
> ```
>
> 安装工具
>
> ```
> sudo pacman -S yaourt
> sudo yaourt -S yay
> 
> ```
>
> Chrome浏览器
>
> ```
> sudo pacman -S google-chrome
> ```
>
> 中文输入法
>
> ```
> sudo pacman -S fcitx-im  # 默认全部安装
> sudo pacman -S fcitx-configtool
> sudo pacman -S fcitx-googlepinyin  # 安装谷歌拼音
> sudo pacman -S fcitx-rime    
> 配置 ~/.xprofile、~/.profile 添加：
> 		export GTK_IM_MODULE=fcitx
> 		export QT_IM_MODULE=fcitx
> 		export XMODIFIERS="@im=fcitx"
> source ~/.xprofile
> source ~/.profile
> fctix 配置中添加Google输入法，中州毫
> ```
>
> Vim
>
> ```
> sudo pacman -S vim
> ```
>
> Java
>
> ```
> 安装JDK：yaourt jdk
> 查看可选环境：archlinux-java status
> 设置环境：sudo archlinux-java set jdk-9.0.4
> ```
>
> Go
>
> ```
> 解压并移动 tar -C /usr/local -xzf go1.10.3.linux-amd64.tar.gz
> 编辑 /etc/profile 添加：
> export GOROOT=/usr/local/go
> export GOPATH=/home/huangqiang/GoBase
> export PATH=$PATH:/usr/local/go/bin
> 使用命令 source /etc/profile 生效。
> 设置代理 go env -w GOPROXY=https://goproxy.cn,direct
> 验证	go env
> ```
>
> Python
>
> ```
> MiniConda
> 
> 安装 bash Miniconda3-latest-Linux-x86_64.sh 
> cd ~
> 激活 source .bashrc
> 生成配置文件 conda config --set show_channel_urls yes
> 编辑 /home/huangqiang/.condarc 修改为：
> channels:
>   - defaults
> show_channel_urls: true
> channel_alias: https://mirrors.tuna.tsinghua.edu.cn/anaconda
> default_channels:
>   - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main
>   - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free
>   - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/r
>   - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/pro
>   - https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/msys2
> custom_channels:
>   conda-forge: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>   msys2: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>   bioconda: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>   menpo: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloudy
>   pytorch: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>   simpleitk: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
> 清除索引缓存 conda clean -i 
> 安装包： matplotlib pandas imageio scipy sympy jupyterlab searorn numpy pylint
> 加入变量
> echo 'export PATH="/home/huangqiang/miniconda3/bin/:$PATH"' >> ~/.bashrc 
> echo 'export PATH="/home/huangqiang/miniconda3/bin:$PATH"' >> ~/.zshrc
> source ~/.bashrc
> source ~/.zshrc
> ```
>
>  Typora
>
> ```
> yaourt typora
> ```
>
> Vscode
>
> ```
> sudo pacman -S visual-studio-code-bin 
> 
> launch.json
> 
> {
>     // 使用 IntelliSense 了解相关属性。 
>     // 悬停以查看现有属性的描述。
>     // 欲了解更多信息，请访问: https://go.microsoft.com/fwlink/?linkid=830387
>     "version": "0.2.0",
>     "configurations": [
>         {
>             "name": "Go",
>             "type": "go",
>             "request": "launch",
>             "mode": "debug",
>             "program": "${file}"
>         },
>         {
>             "type": "java",
>             "name": "Java",
>             "request": "launch",
>             "mainClass": ""
>         },
> 
>         {
>             "name": "Python",
>             "type": "python",
>             "request": "launch",
>             "program": "${file}",
>             "console": "integratedTerminal"
>         },
>         {
>             "name": "C/C++",
>             "type": "cppdbg",
>             "request": "launch",
>             "program": "${fileDirname}/${fileBasenameNoExtension}",
>             "args": [],
>             "stopAtEntry": false,
>             "cwd": "${workspaceFolder}",
>             "environment": [],
>             "externalConsole": false,
>             "MIMode": "gdb",
>             "setupCommands": [
>                 {
>                     "description": "为 gdb 启用整齐打印",
>                     "text": "-enable-pretty-printing",
>                     "ignoreFailures": true
>                 }
>             ],
>             "preLaunchTask": "g++ build active file",
>             "miDebuggerPath": "/usr/bin/gdb"
>         },
>     ]
> }
> 
> tasks.json
> 
> 
> {
>     "version": "2.0.0",
>     "tasks": [
>         {
>             "type": "shell",
>             "label": "g++ build active file",
>             "command": "/usr/bin/g++",
>             "args": [
>                 "-g",
>                 "${file}",
>                 "-o",
>                 "${fileDirname}/${fileBasenameNoExtension}"
>             ],
>             "options": {
>                 "cwd": "/usr/bin"
>             },
>             "problemMatcher": [
>                 "$gcc"
>             ],
>             "group": {
>                 "kind": "build",
>                 "isDefault": true
>             },
>             "presentation": {
>                 "echo": true,
>                 "reveal": "always",
>                 "focus": false,
>                 "panel": "new",
>                 "showReuseMessage": true,
>                 "clear": false
>             },
>         },
> 
>     ]
> }
> 
> ```
>
>  Notepadqq
>
> ```
> yaourt notepadqq
> ```
>
>  ZSH
>
> ```
> https://gist.github.com/OndrejValenta/41c4c60f837ccd56c57ba496177676b3
> https://zhuanlan.zhihu.com/p/62501175
> 
> 安装zsh : 		 
> yum update -y
> yum install util-linux-user
>  yum install zsh -y
> zsh设为默认shell:  
>  chsh -s /bin/zsh root
> 安装oh my zsh :   
>  yum install wget git -y
>  sh -c "$(curl -fsSL https://raw.github.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"
> 安装插件：			
>  cd ~/.oh-my-zsh/plugins/
>  mkdir incr && cd incr
>  wget http://mimosa-pudica.net/src/incr-0.2.zsh
>  echo 'source ~/.oh-my-zsh/plugins/incr/incr*.zsh' >> ~/.zshrc
>  cd ~/.oh-my-zsh/plugins/
>  git clone https://gitee.com/huangqiang97/zsh-autosuggestions $ZSH_CUSTOM/plugins/zsh-autosuggestions
>  cd ~/.oh-my-zsh/plugins/
>  git clone https://github.com/zsh-users/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-syntax-highlighting
>  vim ~/.zshrc
>  # 加入插件列表
>  plugins=(
>   git
>   zsh-autosuggestions
>   zsh-syntax-highlighting
>  )
> 修改生效：
> source ~/.zshrc 
> ```
> 
> 清除系统中无用的包
> 
> ```
> sudo pacman -R $(pacman -Qdtq)
> ```
>
>  清除已下载的安装包
>
> ```
> sudo pacman -Scc
> ```
>
>  开启系统监视器
>
> ```
> 设置 - 面板 - 项目 - 添加 - 系统负载监视器
> ```
>
>  字体
>
> ```
> 设置终端字体： liberation mino regular
> ```
>
>  更改显示
>
> ```
> 设置-> 外观 -> 字体 -> DPI=128
> 设置-> 外观 -> 样式 -> dark
>```
> 
>### Tim
> 
> ```
> sudo pacman -S deepin.com.qq.office
> 中文无法输入：
>找到如下路径: /opt/deepinwine/apps/Deepin-TIM
> 编辑其中的run.sh文件,在最开头添加,
>export GTK_IM_MODULE=fcitx
> export QT_IM_MODULE=fcitx
> export XMODIFIERS="@im=fcitx"
> ```
> 
> ### WPS
> 
> ```
> sudo pacman -S wps-office
> sudo pacman -S ttf-wps-fonts
>解决无法输入中文问题：
> /usr/bin/wps
>/usr/bin/wpp
> /usr/bin/et
> 中最前面添加：
> export XMODIFIERS="@im=fcitx"
> export GTK_IM_MODULE="fcitx"
> export QT_IM_MODULE="fcitx"
> ```
> 
> ### 网易云音乐
> 
> ```
> sudo pacman -S netease-cloud-music
> ```
>
> ### IDEA
>
> ```
> sudo mkdir /opt/Intellij
> sudo tar -zxvf xxx.tar.gz -C /opt/Intellij
>cd /opt/Intellij/xxx
> sudo chmod a=+rx bin/idea.sh    
>bin/idea.sh
> ```
> 
> ### 去除蜂鸣警告音
> 
> ```
> 新建文件：/etc/modprobe.d/blacklist.conf
> 写入：blacklist pcspkr
>```
> 
>


### Virtual Box

> ubuntu server 64

### VScode

> 插件：C/C++, chinese language package, code runner, java package, dependency analytics, docker, excell viewer, jupyter, jupyter keymap, go, matlab, matlab snippets, matlab-formatter, python, pylance, red hat commons, remote containners, spring initializr java support, latex workshop, Markdown all in one , vscode icon, leetcode, lombok, musql, xml
>
> ```json
> {
>     "editor.suggestSelection": "first",
>     "vsintellicode.modify.editor.suggestSelection": "automaticallyOverrodeDefaultValue",
>     "editor.fontSize": 18,
>     "debug.console.fontSize": 18,
>     "markdown.preview.fontSize": 18,
>     "terminal.integrated.fontSize": 18,
>     /////////////////////////////////////////////////////////////////////////////////
>     //Matlab 设置
>     "matlab.linterConfig": "D:\\root\\Matlab\\bin\\win64\\mlint.exe",
>     "matlab.matlabpath": "D:\\root\\Matlab\\\\bin\\matlab.exe",
>     "matlab.mlintpath": "D:\\root\\Matlab\\\\bin\\win64\\mlint.exe",
>     "files.associations": {
>         "*.m": "matlab"
>     },
>     "[matlab]": {
>         "files.encoding": "gb2312"
>     },
>     "files.autoGuessEncoding": true,
>     "editor.snippetSuggestions": "top",
>     /////////////////////////////////////////////////////////////////////////////////////
>     "files.exclude": {
>         "**/.classpath": true,
>         "**/.project": true,
>         "**/.settings": true,
>         "**/.factorypath": true
>     },
>     "diffEditor.ignoreTrimWhitespace": true,
>     "explorer.confirmDelete": false,
>     // icon主题 
>     "workbench.iconTheme": "vscode-icons",
>     "C_Cpp.updateChannel": "Insiders",
>     // java error path
>     "java.errors.incompleteClasspath.severity": "ignore",
>     // python 函数补全括号
>     "python.autoComplete.addBrackets": true,
>     "python.pythonPath": "D:\\root\\MiniConda\\python.exe",
>     // python 开启代码检查
>     "python.linting.enabled": true,
>     "python.linting.pylintEnabled": true,
>     // Java path
>     "java.home": "D:\\root\\JDK\\16",
>     // 3S自动保存
>     "files.autoSave": "afterDelay",
>     "python.linting.pylintPath": "D:\\root\\MiniConda\\pkgs\\pylint-2.9.1-py37haa95532_1\\Scripts\\pylint",
>     "editor.formatOnPaste": true,
>     "editor.formatOnType": true,
>     "editor.formatOnSave": true,
>     "vsicons.dontShowNewVersionMessage": true,
>     "git.path": "D:\\root\\Git\\git-cmd.exe",
>     "workbench.startupEditor": "newUntitledFile",
>     "breadcrumbs.enabled": true,
>     "editor.renderWhitespace": "none",
>     "editor.renderControlCharacters": false,
>     "editor.minimap.enabled": true,
>     "java.semanticHighlighting.enabled": true,
>     "latex-workshop.view.pdf.viewer": "tab",
>     "python.languageServer": "Pylance",
>     "go.formatTool": "goimports",
>     "go.useLanguageServer": true,
>     "go.autocompleteUnimportedPackages": true,
>     // gopath支持多个路径，路径间用 ; 分割，src优先级依次降低。vscode中gopath会覆盖系统变量中GOPATH
>     // F:\\GoBasePath中安装vscode运行go的包
>     "go.gopath": "D:\\root\\GoTools;D:\\prog\\GolandProject\\HelloGo;D:\\root\\Go",
>     "go.toolsManagement.autoUpdate": true,
>     "workbench.editorAssociations": {
>         "*.ipynb": "jupyter-notebook"
>     },
>     "code-runner.runInTerminal": true,
>     "code-runner.executorMap": {
>         "matlab": "cd $dir && matlab -nosplash -nodesktop -r $fileNameWithoutExt",
>         "javascript": "node",
>         "java": "cd $dir && javac $fileName && java $fileNameWithoutExt",
>         "c": "cd $dir && gcc $fileName -o $fileNameWithoutExt && $dir$fileNameWithoutExt",
>         "cpp": "cd $dir && g++ $fileName -o $fileNameWithoutExt && $dir$fileNameWithoutExt",
>         "objective-c": "cd $dir && gcc -framework Cocoa $fileName -o $fileNameWithoutExt && $dir$fileNameWithoutExt",
>         "php": "php",
>         "python": "python -u",
>         "perl": "perl",
>         "perl6": "perl6",
>         "ruby": "ruby",
>         "go": "go run",
>         "lua": "lua",
>         "groovy": "groovy",
>         "powershell": "powershell -ExecutionPolicy ByPass -File",
>         "bat": "cmd /c",
>         "shellscript": "bash",
>         "fsharp": "fsi",
>         "csharp": "scriptcs",
>         "vbscript": "cscript //Nologo",
>         "typescript": "ts-node",
>         "coffeescript": "coffee",
>         "scala": "scala",
>         "swift": "swift",
>         "julia": "julia",
>         "crystal": "crystal",
>         "ocaml": "ocaml",
>         "r": "Rscript",
>         "applescript": "osascript",
>         "clojure": "lein exec",
>         "haxe": "haxe --cwd $dirWithoutTrailingSlash --run $fileNameWithoutExt",
>         "rust": "cd $dir && rustc $fileName && $dir$fileNameWithoutExt",
>         "racket": "racket",
>         "scheme": "csi -script",
>         "ahk": "autohotkey",
>         "autoit": "autoit3",
>         "dart": "dart",
>         "pascal": "cd $dir && fpc $fileName && $dir$fileNameWithoutExt",
>         "d": "cd $dir && dmd $fileName && $dir$fileNameWithoutExt",
>         "haskell": "runhaskell",
>         "nim": "nim compile --verbosity:0 --hints:off --run",
>         "lisp": "sbcl --script",
>         "kit": "kitc --run",
>         "v": "v run",
>         "sass": "sass --style expanded",
>         "scss": "scss --style expanded",
>         "less": "cd $dir && lessc $fileName $fileNameWithoutExt.css",
>         "FortranFreeForm": "cd $dir && gfortran $fileName -o $fileNameWithoutExt && $dir$fileNameWithoutExt",
>         "fortran-modern": "cd $dir && gfortran $fileName -o $fileNameWithoutExt && $dir$fileNameWithoutExt",
>         "fortran_fixed-form": "cd $dir && gfortran $fileName -o $fileNameWithoutExt && $dir$fileNameWithoutExt",
>         "fortran": "cd $dir && gfortran $fileName -o $fileNameWithoutExt && $dir$fileNameWithoutExt"
>     },
>     "leetcode.endpoint": "leetcode-cn",
>     "leetcode.workspaceFolder": "C:\\Users\\huangqiang\\.leetcode",
>     "leetcode.defaultLanguage": "golang",
>     "redhat.telemetry.enabled": true,
>     "notebook.cellToolbarLocation": {
>         "default": "right",
>         "jupyter-notebook": "left"
>     },
>     "leetcode.hint.configWebviewMarkdown": false,
>     "leetcode.hint.commentDescription": false,
>     "leetcode.hint.commandShortcut": false,
>     "leetcode.editor.shortcuts": [
>         "submit",
>         "test",
>         "solution"
>     ],
>     "python.defaultInterpreterPath": "D:\\root\\MiniConda\\python.exe",
>     "editor.wordWrap": "on",
>     "java.project.importOnFirstTimeStartup": "automatic",
>     "java.jdt.ls.vmargs": "-XX:+UseParallelGC -XX:GCTimeRatio=4 -XX:AdaptiveSizePolicyWeight=90 -Dsun.zip.disableMemoryMapping=true -Xmx1G -Xms100m -javaagent:\"c:\\Users\\huangqiang\\.vscode\\extensions\\gabrielbb.vscode-lombok-1.0.1\\server\\lombok.jar\"",
>     "scm.inputFontSize": 18,
>     "workbench.editor.untitled.hint": "hidden",
>     "python.analysis.completeFunctionParens": true,
>     // autopep8
>     "python.formatting.provider": "autopep8",
>     "python.formatting.autopep8Args": [
>         "--max-line-length=256"
>     ],
>     // 集成powershell7
>     "editor.fontFamily": "'JetBrainsMono Nerd Font', Consolas, 'Courier New', monospace",
>     "terminal.integrated.fontFamily": "JetBrainsMono Nerd Font",
>     "editor.fontLigatures": true,
>     "terminal.integrated.profiles.windows": {
>         "pwsh7": {
>             "path": "C:\\Program Files\\PowerShell\\7\\pwsh.exe",
>             "args": [
>                 "-nologo"
>             ]
>         },
>     },
>     "terminal.integrated.defaultProfile.windows": "pwsh7",
> }
> ```
>
> ```json
> launch.json
> {
> // 使用 IntelliSense 了解相关属性。 
> // 悬停以查看现有属性的描述。
> // 欲了解更多信息，请访问: https://go.microsoft.com/fwlink/?linkid=830387
> "version": "0.2.0",
> "configurations": [
> {
>    "name": "C",
>    "type": "cppdbg",
>    "request": "launch",
>    "program": "${fileDirname}\\build\\${fileBasenameNoExtension}.exe",
>    "args": [],
>    "stopAtEntry": false,
>    "cwd": "${fileDirname}",
>    "environment": [],
>    "externalConsole": false,
>    "internalConsoleOptions": "neverOpen",
>    "MIMode": "gdb",
>    "miDebuggerPath": "D:\\root\\Mingw64\\bin\\gdb.exe",
>    "preLaunchTask": "Cbuild"
> },
> {
>    "name": "CPP",
>    "type": "cppdbg",
>    "request": "launch",
>    "program": "${fileDirname}\\build\\${fileBasenameNoExtension}.exe",
>    "args": [],
>    "stopAtEntry": false,
>    "cwd": "${fileDirname}",
>    "environment": [],
>    "externalConsole": false,
>    "internalConsoleOptions": "neverOpen",
>    "MIMode": "gdb",
>    "miDebuggerPath": "D:\\root\\Mingw64\\bin\\gdb.exe",
>    "preLaunchTask": "CPPbuild"
> }
> ]
> }
> ```
>
> ```json
> task.json
> {
> "version": "2.0.0",
> "tasks": [
> { //这个大括号里是‘构建（build）’任务
> "label": "CPPbuild", //任务名称，可以更改，不过不建议改
> "type": "shell", //任务类型，process是vsc把预定义变量和转义解析后直接全部传给command；shell相当于先打开shell再输入命令，所以args还会经过shell再解析一遍
> //##################################可变##################################//
> "command": "g++", //编译命令，这里是gcc，编译c++的话换成g++
> "args": [ //方括号里是传给gcc命令的一系列参数，用于实现一些功能
> //-----------------------------可变-----------------------------//
> "${file}", //单文件程序编译，指定要编译的是当前文件
> //"${fileDirname}\\*.c",       //多文件编译编译，把当前文件夹下文件全部编译，写c++把 *.c 换成 *.cpp
> "-o", //指定输出文件的路径和名称
> //-----------------------------可变-----------------------------//
> "${fileDirname}\\build\\${fileBasenameNoExtension}.exe", //承接上一步的-o，让可执行文件输出到源码文件所在的文件夹下的bin文件夹内，并且让它的名字和源码文件相同
> "-g", //生成和调试有关的信息
> //"-Wall", // 开启额外警告
> //"-static-libgcc",  // 静态链接libgcc
> //"-fexec-charset=GBK", // 生成的程序使用GBK编码，不加这一条会导致Win下输出中文乱码
> //##################################可变##################################//
> "-std=c++20", // 语言标准，可根据自己的需要进行修改，写c++要换成c++的语言标准，比如c++11
> ],
> "group": { //group表示‘组’，我们可以有很多的task，然后把他们放在一个‘组’里
> "kind": "build", //表示这一组任务类型是构建
> "isDefault": true //表示这个任务是当前这组任务中的默认任务
> },
> "presentation": { //执行这个任务时的一些其他设定
> "echo": true, //表示在执行任务时在终端要有输出
> "reveal": "always", //执行任务时是否跳转到终端面板，可以为always，silent，never
> "focus": false, //设为true后可以使执行task时焦点聚集在终端，但对编译来说，设为true没有意义，因为运行的时候才涉及到输入
> "panel": "shared" //每次执行这个task时都新建一个终端面板，也可以设置为share，共用一个面板，不过那样会出现‘任务将被终端重用’的提示，比较烦人
> },
> "problemMatcher": "$gcc" //捕捉编译时编译器在终端里显示的报错信息，将其显示在vscode的‘问题’面板里
> },
> { //这个大括号里是‘构建（build）’任务
> "label": "Cbuild", //任务名称，可以更改，不过不建议改
> "type": "shell", //任务类型，process是vsc把预定义变量和转义解析后直接全部传给command；shell相当于先打开shell再输入命令，所以args还会经过shell再解析一遍
> //##################################可变##################################//
> "command": "gcc", //编译命令，这里是gcc，编译c++的话换成g++
> "args": [ //方括号里是传给gcc命令的一系列参数，用于实现一些功能
> //-----------------------------可变-----------------------------//
> "${file}", //单文件程序编译，指定要编译的是当前文件
> //"${fileDirname}\\*.c",       //多文件编译编译，把当前文件夹下文件全部编译，写c++把 *.c 换成 *.cpp
> "-o", //指定输出文件的路径和名称
> //-----------------------------可变-----------------------------//
> "${fileDirname}\\build\\${fileBasenameNoExtension}.exe", //承接上一步的-o，让可执行文件输出到源码文件所在的文件夹下的bin文件夹内，并且让它的名字和源码文件相同
> "-g", //生成和调试有关的信息
> //"-Wall", // 开启额外警告
> //"-static-libgcc",  // 静态链接libgcc
> //"-fexec-charset=GBK", // 生成的程序使用GBK编码，不加这一条会导致Win下输出中文乱码
> //##################################可变##################################//
> "-std=c2x", // 语言标准，可根据自己的需要进行修改，写c++要换成c++的语言标准，比如c++11
> ],
> "group": { //group表示‘组’，我们可以有很多的task，然后把他们放在一个‘组’里
> "kind": "build", //表示这一组任务类型是构建
> "isDefault": true //表示这个任务是当前这组任务中的默认任务
> },
> "presentation": { //执行这个任务时的一些其他设定
> "echo": true, //表示在执行任务时在终端要有输出
> "reveal": "always", //执行任务时是否跳转到终端面板，可以为always，silent，never
> "focus": false, //设为true后可以使执行task时焦点聚集在终端，但对编译来说，设为true没有意义，因为运行的时候才涉及到输入
> "panel": "shared" //每次执行这个task时都新建一个终端面板，也可以设置为share，共用一个面板，不过那样会出现‘任务将被终端重用’的提示，比较烦人
> },
> "problemMatcher": "$gcc" //捕捉编译时编译器在终端里显示的报错信息，将其显示在vscode的‘问题’面板里
> },
> { //这个大括号里是‘运行(run)’任务，一些设置与上面的构建任务性质相同
> "label": "Crun",
> "type": "shell",
> "dependsOn": "Cbuild", //任务依赖，因为要运行必须先构建，所以执行这个任务前必须先执行build任务，
> //-----------------------------可变-----------------------------//
> "command": "${fileDirname}\\build\\${fileBasenameNoExtension}.exe", //执行exe文件，只需要指定这个exe文件在哪里就好
> "group": {
> "kind": "test", //这一组是‘测试’组，将run任务放在test组里方便我们用快捷键执行
> "isDefault": true
> },
> "presentation": {
> "echo": true,
> "reveal": "always",
> "focus": true, //这个就设置为true了，运行任务后将焦点聚集到终端，方便进行输入
> "panel": "shared" //每次执行这个task时都新建一个终端面板，也可以设置为share，共用一个面板，不过那样会出现‘任务将被终端重用’的提示，比较烦人
> }
> },
> { //这个大括号里是‘运行(run)’任务，一些设置与上面的构建任务性质相同
> "label": "CPPrun",
> "type": "shell",
> "dependsOn": "CPPbuild", //任务依赖，因为要运行必须先构建，所以执行这个任务前必须先执行build任务，
> //-----------------------------可变-----------------------------//
> "command": "${fileDirname}\\build\\${fileBasenameNoExtension}.exe", //执行exe文件，只需要指定这个exe文件在哪里就好
> "group": {
> "kind": "test", //这一组是‘测试’组，将run任务放在test组里方便我们用快捷键执行
> "isDefault": true
> },
> "presentation": {
> "echo": true,
> "reveal": "always",
> "focus": true, //这个就设置为true了，运行任务后将焦点聚集到终端，方便进行输入
> "panel": "shared" //每次执行这个task时都新建一个终端面板，也可以设置为share，共用一个面板，不过那样会出现‘任务将被终端重用’的提示，比较烦人
> }
> }
> ]
> }
> ```
>
> ---
>
> Centos
>
> 开启SSH远程：[windows宿主机如何SSH连接VMware的Linux虚拟机 - 云+社区 - 腾讯云 (tencent.com)](https://cloud.tencent.com/developer/article/1679861)
>
> 为用户添加root权限
>
> ```shell
> 修改 /etc/sudoers 文件,在root行下面添加：
> root ALL=(ALL) ALL 
> myUsr ALL=(ALL) ALL 
> ```
>
> 桌面设置：https://opensofty.com/zh-cn/2020/2/29/%E5%9C%A8centos-8%E4%B8%8A%E8%B0%83%E6%95%B4gnome%E6%A1%8C%E9%9D%A2%E7%8E%AF%E5%A2%83/
>
> 添加到终端：ssh huangqiang@192.168.126.128
>
>  ZSH
>
> ```
> https://gist.github.com/OndrejValenta/41c4c60f837ccd56c57ba496177676b3
> https://zhuanlan.zhihu.com/p/62501175
> 
> 安装zsh : 		 
>  yum install zsh -y
> zsh设为默认shell:  
>  chsh -s /bin/zsh root
> 安装oh my zsh :   
>  yum install wget git -y
>  sh -c "$(curl -fsSL https://raw.github.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"
> 安装插件：			
>  cd ~/.oh-my-zsh/plugins/
>  mkdir incr && cd incr
>  wget http://mimosa-pudica.net/src/incr-0.2.zsh
>  echo 'source ~/.oh-my-zsh/plugins/incr/incr*.zsh' >> ~/.zshrc
>  cd ~/.oh-my-zsh/plugins/
>  git clone https://gitee.com/huangqiang97/zsh-autosuggestions $ZSH_CUSTOM/plugins/zsh-autosuggestions
>  vim ~/.zshrc
>  # 加入插件列表
>  plugins=(
>   git
>   zsh-autosuggestions
>  )
>  cd ~/.oh-my-zsh/plugins/
>  git clone https://github.com/zsh-users/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-syntax-highlighting
>  vim ~/.zshrc
>  # 加入插件列表
>  plugins=(
>   git
>   zsh-autosuggestions
>   zsh-syntax-highlighting
>  )
> 修改生效：
> source ~/.zshrc 
> ```
>
> 

### waifu2x

### Wechat

> 更改文件夹

### Wub

> 关闭更新

### Zotero

> 修改文件存储路径

### Xtransltor

> 登录
>
> 百度翻译API：20200807000535541	KeCMk7T53dhu9qOcSfuR

### DDM 

>  DDM驱动



### WSL

>开启系统支持
>
>https://docs.microsoft.com/en-us/windows/wsl/install-manual
>
>```
>dism.exe /online /enable-feature /featurename:VirtualMachinePlatform /all /norestart
>
>dism.exe /online /enable-feature /featurename:Microsoft-Windows-Subsystem-Linux /all /norestart
>```

### Sublime

>插件： https://zhuanlan.zhihu.com/p/91942738



### 卡巴斯基

> 安全软件

### 关闭自动更新

> NoUpdate.exe

### 启用模糊拼音

### 关闭盖子无操作

> 控制面板  -> 硬件 和声音 -> 电源选项 

### 高性能模式

> 控制面板  -> 硬件 和声音 -> 电源选项 -> 创建电源选项 -> 高性能

### 图标

> 个性化 -> 主题 -> 桌面图标

### 去除快捷方式箭头

> win+R ==> regedit ==> HKEY_CLASSES_ROOT ==> lnkfile ==> 删除lsShortcut

### 键盘

>   ```
>   sc config i8042prt start= disabled
>   sc config i8042prt start= demand
>   ```

```text
#添加阿里源
deb http://mirrors.aliyun.com/ubuntu/ focal main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ focal-security main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal-security main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ focal-updates main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal-updates main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ focal-proposed main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal-proposed main restricted universe multiverse
deb http://mirrors.aliyun.com/ubuntu/ focal-backports main restricted universe multiverse
deb-src http://mirrors.aliyun.com/ubuntu/ focal-backports main restricted universe multiverse
#添加清华源
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal-updates main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal-updates main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal-backports main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal-backports main restricted universe multiverse
deb https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal-security main restricted universe multiverse
# deb-src https://mirrors.tuna.tsinghua.edu.cn/ubuntu/ focal-security main restricted universe multiverse multiverse
```

### Kafka
```
docker run -d --name zookeeper -p 2181:2181 -t zookeeper
docker run  -d --name kafka -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=local-ip:2181 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://local-ip:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -e ALLOW_PLAINTEXT_LISTENER=yes -t bitnami/kafka

kafka-topics.sh --create --zookeeper local-ip:2181 --replication-factor 1 --partitions 1 --topic test-topic  
kafka-console-producer.sh --broker-list local-ip:9092 --topic test-topic
kafka-console-consumer.sh --bootstrap-server local-ip:9092 --topic test-topic --from-beginning
```
### docker迁移
https://developer.aliyun.com/article/980658