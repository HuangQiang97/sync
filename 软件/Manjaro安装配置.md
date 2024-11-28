## Manjaro安装配置

### 换源 

> 换源
>
> ```
>sudo pacman-mirrors -i -c China -m rank
> ```
> 
> 添加源
>
> ```
>sudo vi /etc/pacman.conf
> 添加：
> [archlinuxcn]
> SigLevel = Optional TrustedOnly
> #中科大源
> Server = https://mirrors.ustc.edu.cn/archlinuxcn/$arch
> #清华源
> Server = https://mirrors.tuna.tsinghua.edu.cn/archlinuxcn/$arch
> [arch4edu]
> SigLevel = TrustAll
> Server = https://mirrors.tuna.tsinghua.edu.cn/arch4edu/$arch
> ```
> 
> 安装签名
> 
> ```
> sudo pacman-mirrors -g
>sudo pacman -Syyu
> sudo pacman -S archlinuxcn-keyring
>```

### 安装工具

>```
>sudo pacman -S yaourt
>sudo yaourt -S yay
>```

### Chrome浏览器

> ```
> sudo pacman -S google-chrome
> ```

### 中文输入法

>安装
>
>```
>sudo pacman -S fcitx-im  # 默认全部安装
>sudo pacman -S fcitx-configtool
>sudo pacman -Sy base-devel 
>sudo pacman -S fcitx-googlepinyin  # 安装谷歌拼音
>yay -S fcitx-sogoupinyin 
>sudo pacman -S fcitx-rime    
>
>```
>
>配置
>
>```
>vim ~/.xprofile
>vim ~/.profile 
>添加：
>export GTK_IM_MODULE=fcitx
>export QT_IM_MODULE=fcitx
>export XMODIFIERS="@im=fcitx"
>生效
>source ~/.xprofile
>source ~/.profile
>```
>
>```
>fctix 配置中添加Google输入法，中州毫，搜狗
>切换激活：ctr+shift
>```

### Vim

>```
>sudo pacman -S vim
>```

### Java

>```
>安装JDK：yaourt jdk
>查看可选环境：archlinux-java status
>设置环境：sudo archlinux-java set jdk-xxx
>```
>
>IDEA
>
>```
>sudo mkdir /opt/Intellij
>sudo tar -zxvf xxx.tar.gz -C /opt/Intellij
>cd /opt/Intellij/xxx
>sudo chmod a=+rx bin/idea.sh    
>bin/idea.sh
>```
>
>class注释
>
>```
>#if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
>#parse("File Header.java")
>/**
>* @author   huangqiang
>* @date ${DATE} ${TIME}
>* @description  TODO
>* @modified 
>* @version  TODO
>*/
>public class ${NAME} {
>}
>勾选 enable live Templates
>```
>
>方法注释
>
>```
>快捷方式：/*
>/**
>
>* @create by: huangqiang
>* @description: TODO
>* @create time: $date$ $time$
>* @params: $params$
>* @return: $return$
>*/
>```
>
>```
>alibaba插件
>```
>
>

### Go

> 解压并移动 	
>
> ```
> tar -C /usr/local -xzf go1.10.3.linux-amd64.tar.gz
> ```
>
> 变量
>
> ```
> vim /etc/profile 
> 添加：
> export GOROOT=/usr/local/go
> export GOPATH=/home/huangqiang/GoBase
> export PATH=$PATH:/usr/local/go/bin
> 生效
> source /etc/profile
> ```
>
> ```
> 设置代理 :	go env -w GOPROXY=https://goproxy.cn,direct
> 验证	  :	go env
> ```
>
> 

### Python

>安装
>
>```
>bash Miniconda3-latest-Linux-x86_64.sh 
>```
>
>激活
>
>```
>cd ~
>source .bashrc
>```
>
>配置
>
>```
>生成文件:
>conda config --set show_channel_urls yes
>修改为：
>vim /home/huangqiang/.condarc 
>
>channels:
>- defaults
>show_channel_urls: true
>channel_alias: https://mirrors.tuna.tsinghua.edu.cn/anaconda
>default_channels:
>- https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/main
>- https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/free
>- https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/r
>- https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/pro
>- https://mirrors.tuna.tsinghua.edu.cn/anaconda/pkgs/msys2
>custom_channels:
>conda-forge: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>msys2: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>bioconda: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>menpo: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloudy
>pytorch: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>simpleitk: https://mirrors.tuna.tsinghua.edu.cn/anaconda/cloud
>```
>
>加入变量
>
>```
>echo 'export PATH="/home/huangqiang/miniconda3/bin/:$PATH"' >> ~/.bashrc 
>echo 'export PATH="/home/huangqiang/miniconda3/bin:$PATH"' >> ~/.zshrc
>生效
>source ~/.bashrc
>source ~/.zshrc
>```
>
>```
>安装包： matplotlib pandas imageio scipy sympy jupyterlab seaborn numpy pylint scikit-learn
>sudo pacman -S nodejs
>yay -S npm
>清除索引缓存 conda clean -i 
>```
>
>无法跳转浏览器
>
>```
>jupyter notebook --generate-config
>sudo vim jupyter_notebook_config.py
>写入：
>import webbrowser
>webbrowser.register('chrome',None,webbrowser.GenericBrowser(u'/opt/google/chrome/chrome'))
>c.NotebookApp.browser = 'chrome'
>c.NotebookApp.notebook_dir='myworkdir'
>
>```

###  Typora

> ```
> yaourt typora
> ```

### Vscode

>安装
>
>```
>sudo pacman -S visual-studio-code-bin 
>```
>
>插件
>
>```
>C/C++, chinese language, Debugger fro java, Draw.io, Gitlen， Go, Gruvbox, java extension pack , Java test runner,Language support for java, Maekdown all in one ,Maven for java,project manager for java, pylance, python, visual studio intellicode, vscode icons
>```
>
>launch.json
>
>```
>{
>// 使用 IntelliSense 了解相关属性。 
>// 悬停以查看现有属性的描述。
>// 欲了解更多信息，请访问: https://go.microsoft.com/fwlink/?linkid=830387
>"version": "0.2.0",
>"configurations": [
>  {
>      "name": "Go",
>       "type": "go",
>       "request": "launch",
>       "mode": "debug",
>       "program": "${file}"
>   },
>   {
>       "type": "java",
>       "name": "Java",
>       "request": "launch",
>       "mainClass": ""
>   },
>
>   {
>  "name": "Python",
>       "type": "python",
>       "request": "launch",
>       "program": "${file}",
>       "console": "integratedTerminal"
>   },
>   {
>       "name": "C/C++",
>       "type": "cppdbg",
>       "request": "launch",
>       "program": "${fileDirname}/${fileBasenameNoExtension}",
>       "args": [],
>       "stopAtEntry": false,
>       "cwd": "${workspaceFolder}",
>       "environment": [],
>       "externalConsole": false,
>       "MIMode": "gdb",
>       "setupCommands": [
>           {
>               "description": "为 gdb 启用整齐打印",
>               "text": "-enable-pretty-printing",
>               "ignoreFailures": true
>           }
>       ],
>       "preLaunchTask": "g++ build active file",
>       "miDebuggerPath": "/usr/bin/gdb"
>   },
>]
>}
>
>```
>
>tasks.json
>
>````
>{
>"version": "2.0.0",
>"tasks": [
>  {
>      "type": "shell",
>       "label": "g++ build active file",
>       "command": "/usr/bin/g++",
>       "args": [
>           "-g",
>           "${file}",
>           "-o",
>           "${fileDirname}/${fileBasenameNoExtension}"
>       ],
>       "options": {
>           "cwd": "/usr/bin"
>       },
>       "problemMatcher": [
>           "$gcc"
>       ],
>       "group": {
>           "kind": "build",
>           "isDefault": true
>       },
>       "presentation": {
>           "echo": true,
>           "reveal": "always",
>           "focus": false,
>           "panel": "new",
>           "showReuseMessage": true,
>           "clear": false
>       },
>   },
>
>]
>}
>
>
>````
>
>```
>setting.json
>
>{
>    "workbench.iconTheme": "vscode-icons",
>    "editor.suggestSelection": "first",
>    "vsintellicode.modify.editor.suggestSelection": "automaticallyOverrodeDefaultValue",
>    "editor.fontSize": 17,
>    "vsicons.dontShowNewVersionMessage": true,
>    "terminal.integrated.inheritEnv": false,
>    "python.languageServer": "Pylance",
>    "debug.console.fontSize": 17,
>    "markdown.preview.fontSize": 17,
>    "terminal.integrated.fontSize": 18,
>    "jupyter.alwaysTrustNotebooks": true,
>    "jupyter.askForKernelRestart": false,
>    "jupyter.interactiveWindowMode": "perFile",
>    "workbench.colorTheme": "Gruvbox Dark (Medium)",
>
>    //Matlab 设置
>    "matlab.linterEncoding": "utf8",
>    "matlab.linterConfig": "/opt/Matlab/bin/glnxa64/mlint",
>    "matlab.matlabpath": "/opt/Matlab/bin/matlab",
>    "matlab.mlintpath": "/opt/Matlab/bin/glnxa64/mlint",
>    "files.associations": {
>    "*.m": "matlab"
>    },
>    "[matlab]": {
>    "files.encoding": "utf8"
>    },
>    "files.autoGuessEncoding": true,
>}
>```
>
>

###  Notepadqq

> ```
> yaourt notepadqq
> ```

###  ZSH

>安装zsh 
>
>```
>sudo  pacman -S zsh
>```
>
>zsh设为默认shell
>
>```
>sudo chsh -s /bin/zsh
>reboot 
>```
>
>安装oh my zsh
>
>```
>sh -c "$(wget -O- https://gitee.com/shmhlsy/oh-my-zsh-install.sh/raw/master/install.sh)"
>```
>
>安装插件
>
>```shell
>cd ~/.oh-my-zsh/custom/plugins/
>
>mkdir incr && cd incr
>wget http://mimosa-pudica.net/src/incr-0.2.zsh
>
>cd ~/.oh-my-zsh/custom/plugins/
>
>git clone https://gitee.com/huangqiang97/zsh-syntax-highlighting.git
>git clone git://github.com/zsh-users/zsh-autosuggestions
>
>git clone https://gitee.com/watson8544/mirrors-autojump.git
>cd /home/huangqiang/.oh-my-zsh/custom/plugins/autojump/
>./install.py
>// sudo chmod 777 \home\huangqiang\.oh-my-zsh\custom\plugins\autojump
>编辑 ~/.zshrc 
>
>plugins=(git extract z  wd zsh-syntax-highlighting autojump zsh-autosuggestions)
>
>source $ZSH/oh-my-zsh.sh
>source /home/huangqiang/.oh-my-zsh/custom/plugins/incr/incr*.zsh
>
>[[ -s /root/.autojump/etc/profile.d/autojump.sh ]] && source /root/.autojump/etc/profile.d/autojump.sh
>autoload -U compinit && compinit -u
>
>修改生效：source ~/.zshrc 
>```

### 管理

> 清除系统中无用的包
>
> ```
> sudo pacman -R $(pacman -Qdtq)
> ```
>
> 清除已下载的安装包
>
> ```
> sudo pacman -Scc
> ```
>
> 保存一周日志
>
> ```
> sudo journalctl --vacuum-time=1w
> ```
>
> 管理器
>
> ```
> ysy -S stacer
> ```
>
> 时间
>
> ```
> manjaro 设置管理器 -> 时间和日期
> ```
>
> 开启系统监视器
>
> ```
> 状态栏右键->面板->面板首选项->项目->添加->网络监视器
> 双击网络监视器->编辑属性(文字，显示方式，设备名称）
> 网络指示器上右键->编辑连接->双击有线或WIFI->编辑->设备名称
> ```
>
> ```
> 设置 - 面板 - 项目 - 添加 - 系统负载监视器
> ```
>
> 更改显示
>
> ```
> 设置-> 外观 -> 字体 -> DPI=128
> 设置-> 外观 -> 样式 -> dark
> ```
> 
> 去除蜂鸣警告音
> 
>```
> 新建文件：/etc/modprobe.d/blacklist.conf
>写入：	  blacklist pcspkr
> ```

###  Tim

> 安装
>
> ```
> yay -S com.qq.im.deepin
> ```
> 
> 中文无法输入
> 
>```
> vim /opt/deepinwine/apps/Deepin-TIM/run.sh
>最开头添加,
> export GTK_IM_MODULE=fcitx
> export QT_IM_MODULE=fcitx
> export XMODIFIERS="@im=fcitx"
> ```
> 
> 卸载
> 
>```
> 删除: ~/.wine
>进入: ~/.local/share/applications/wine/Programs/ 
> 删除： <软件名称> .desktop 
> 进入： ~/.config/menus/applications-merged/
> 删除不要的文件
> ```

### wechat

> ```
> yay -S com.qq.weixin.deepin
> ```
> 
> 

### WPS

> 安装
>
> ```
> yay -S wps-office ttf-wps-fonts
> yay -S wps-office-mui-zh-cn
> ```
>
> 解决无法输入中文问题：
>
> ```
> /usr/bin/wps
> /usr/bin/wpp
> /usr/bin/et
> 最前面添加：
> export XMODIFIERS="@im=fcitx"
> export GTK_IM_MODULE="fcitx"
> export QT_IM_MODULE="fcitx"
> ```
>
> 

### 网易云音乐

> ```
> sudo pacman -S netease-cloud-music
> ```

### Foxit

> ```
> yaourt foxit
> ```

### 控制台命令thefuck

> ```
> sudo pacman -S thefuck
> ```

### Shadowsocks-qt5

> ```
> sudo pacman -S shadowsocks-qt5
> ```

### Mathpix

> ```
> yaourt mathpix_snipping_tool
> ```

### Ifuse

> 安装
>
> ```
> yaourt ifuse usbmuxd libplist libimobiledevice
> ```
>
> ```
> 在home目录中创建一个iPhone目录。
> 挂载：ifuse ~/iPhone
> 卸载：sudo umount ~/iPhone
> ```

### 坚果云

> ```
> sudo yaourt nutstore
> ```

### clang

> ```
> sudo pacman -S clang
> 
> sudo pacman -S lldb
> ```

### 迅雷

> ```
> yaourt -S deepin.com.thunderspeed
> ```

### 百度网盘

> ```
> yaourt -S deepin-baidu-pan
> ```

### VMware

> 安装
>
> ```
> yaourt vmware-workstation
> ```
>
> 卸载
>
> ```
> vmware-installer -l
> sudo vmware-installer -u vmware-workstation
> ```

### FTP

> 安装：
>
> ```
> 	yaourt vsftpd
> ```
>
> 编辑：
>
> ```
> vim /etc/vsftpd.conf
> 编辑
> #禁止匿名访问
> anonymous_enable=NO
> #接受本地用户
> local_enable=YES
> #允许上传
> write_enable=YES
> #用户只能访问限制的目录
> chroot_local_user=YES
> #设置固定目录，在结尾添加。如果不添加这一行，各用户对应自己的目录，当然这个文件夹自己建
> local_root=yourFtpDir
> pam_service_name=vsftpd
> pasv_promiscuous=YES
> 
> 
> ```
>
> ```
> 添加用户：
> sudo useradd -d yourFtpDir -M ftpUseName
> sudo passwd ftpUseName
> 调整权限：
> sudo chmod a-w yourFtpDir
> sudo mkdir yourFtpDir/data
> 登录调整：
> sudo nano /etc/pam.d/vsftpd
> 注释掉：auth    required pam_shells.so
> 重启：
> systemctl start vsftpd 
> systemctl restart vsftpd
> ```

### TexStudio

### uget

> ```bash
> # aria2安装
> yay aria2
> uget->编辑->设置 -> 插件 -> 插件匹配顺序-> aria2
> # uget-integrator安装，根据浏览器种类自行选择
> yay -S uget-integrator-chrome uget-integrator-chromium uget-integrator-opera uget-integrator-firefox
> # 安装插件： https://github.com/ugetdm/uget-integrator
> https://chrome.google.com/webstore/detail/uget-integration/efjgjleilhflffpbnkaofpmdnajdpepi
> 
> ```
>
> 

### Virtual Box

> ```
> sudo pacman -S virtualbox
> uname -r # 5.8.18-1-MANJARO
> sudo pacman -S linux58-virtualbox-host-modules
> sudo modprobe vboxdrv      
> ```

### 硬盘

> 控制面板-> 硬件-> 电源-> 选择电源按钮功能-> 关闭快速启动

### 关闭触控板

### 深度截图

> 深度截图
>
> 设置-> 键盘 -> 快捷键 -> 增加 
>
> F1 : deepin-screenshot

### debtap

> ```
> 安装：
> yay -S debtap
> yay -S bash binutils pkgfile fakeroot
> sudo debtap -u
> 构建：
> debtap myprogram.deb
> sudo pacman -U myprogram.pkg.tar.xz
> 卸载：
> sudo pacman -R myprogram
> 
> ```
>
> 

### firefox

> 调节鼠标速度
>
> ```
> about:config
> mousewheel.default.delta_multiplier_y
> ```

### edge

> 软件包管理器 设置 首选项 AUR 启用AUR支持
>
> microsoft-edge-dev-bin

### 字体

> ```
> #移动
> sudo cp -r sarasa/ /usr/share/fonts/
> cd /usr/share/fonts/sarasa/
> sudo fc-cache -fv       
> 
> 设置-外观-字体
> 设置终端字体： liberation mino regular / 文泉驿等宽微米黑 regular
> 
> # 更新字体缓存
> sudo mkfontscale
> sudo mkfontdir
> sudo fc-cache
> sudo fc-cache -fv
> 
> ```
>
> 

### teamviewer

> ```
> teamviewer --daemon start
> ```
>
> 

### 拓展

> ```
> https://greasyfork.org/zh-CN/scripts/1682-google-hit-hider-by-domain-search-filter-block-sites
> ```
>
> 

### Matlab

> ```
> cd /mnt/
> sudo mkdir cdrom  
> sudo mount -o loop R2015b_glnxa64.iso   /mnt/cdrom/
> cd /mnt/cdrom
> sudo ./install
> 09806-07443-53955-64350-21751-41297
> sudo cp Crack/R2015b/bin/glnxa64/* /opt/Matlab/bin/glnxa64
> yay -S matlab-support  
> sudo ln -s /usr/lib64/libncursesw.so.6  /opt/Matlab/bin/glnxa64/libncurses.so.5
> cd /opt/Matlab/bin
> sudo ./matlab
> ```
>
> ```
> Matlab.desktop   
> 
> [Desktop Entry]
> Name=Matlab
> Exec=/opt/Matlab/bin/matlab -desktop
> Icon=/opt/Matlab/toolbox/shared/dastudio/resources/MatlabIcon.png
> Type=Application
> Name[zh_CN]=Matlab
> ```
>
> ```
> cd /opt/Matlib/sys/java/jre/glnx86/jre/lib/fonts/
> sudo mkdir fallback
> cd fallback
> sudo ln -s  /usr/share/fonts/wenquanyi/wqy-microhei/wqy-microhei.ttc  ./wqy-microhei.ttc
> sudo mkfontdir
> sudo mkfontscale
> cd /opt/Matlib/bin/glnxa64
> sudo mkdir exclude
> sudo mv libfreetype* exclude
> ```
>
> ```
> yaourt -S libselinux
> cd /opt/Matlib/
> ! bin/glnxa64/MATLABWindow
> # https://ww2.mathworks.cn/matlabcentral/answers/364551-why-is-matlab-unable-to-run-the-matlabwindow-application-on-linux
> #cd sys/os/glnxa64
> #sudo mkdir exclude
> #sudo mv libstdc++.so.* exclude/
> ```
>
> ```
> windows 快捷键
> ```
>
> 

### 坚果云

> ```
> yay -S nutstore
> ```
>
> 

### albert

> ```
> sudo pacman -S albert
> ```
>
> 开机自启
>
> 开启应用

### Understand

> 解压
>
> 移动
>
> 要安装在个人目录下，不然权限报错。
>
> ```
> cd /home/huangqiang/Applications/scitools/bin/linux64/
> mkdir tmp
> sudo mv ./libfreetype.so.6  ./tmp/  
> ./understand
> ```
>
> 破解
>
> ```
> "legacy licensing" -> "add eval or sdl (regcode)"
> 185F996AEEC2
> 7808F4308398
> F38075B00218
> EBF578C60F6E
> 00479F7EE8D6
> ```
>
> 变量
>
> ```
> export PATH=$PATH:/home/huangqiang/Applications/scitools/bin/linux64
> ```
>
> 快捷方式
>
> ```
> understand.desktop
> ---------------------------------
> [Desktop Entry]
> Name=Understand
> Exec=/home/huangqiang/Applications/scitools/bin/linux64/understand
> Icon=/home/huangqiang/Applications/scitools/bin/linux64/understand_64.png
> Type=Application
> Terminal=false
> Comment=Analyze it, measure it, visualize it, maintain it - Understand it
> GenericName=Static analysis tool
> Categories=Development;IDE;
> ```
>
> 

### Listen1

> ```
> yay -S listen1-desktop-appimage
> ```
>
> 

### shotwell

### Tmux

> ```
> yay -S tmux
> ```
>
> 

### Screen

> ```
> yay -S screen
> ```
>
> ```
> screen  启动screen
> Ctrl+a S 上下划分窗口
> Ctrl+a | 左右划分窗口
> Ctrl+a c 创建新会话
> Ctrl+a " 列出所有会话
> Ctrl+a 0 切换到会话0
> Ctrl+a A 重命名会话
> Ctrl+a tab 切换到下一个窗口
> Ctrl+a Ctrl+a 返回上一个窗口
> Ctrl+a Q 关闭除当前窗口外所有窗口
> Ctrl+a X 关闭当前窗口
> Ctrl+a k 杀死当前会话
> Ctrl+a \ 杀死所有会话和窗口并退出
> ```
>
> 

