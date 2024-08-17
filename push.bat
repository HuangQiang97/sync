@echo off
setlocal enabledelayedexpansion

REM 获取当前日期时间，并格式化为 "yyyymmdd-hhmmss"
for /f "tokens=1-4 delims=/- " %%a in ('date /t') do (
    set day=%%a
    set month=%%b
    set year=%%c
)

for /f "tokens=1-2 delims=: " %%a in ('time /t') do (
    set hour=%%a
    set minute=%%b
)
set second=%time:~6,2%

REM 处理个位数的月和日，确保是两位数
if !month! LSS 10 set month=0!month!
if !day! LSS 10 set day=0!day!

REM 处理个位数的小时，确保是两位数
if !hour! LSS 10 set hour=0!hour!

set timestamp=!year!!month!!day!-!hour!!minute!!second!

REM 将当前文件夹下所有文件添加到Git暂存区
git add .

REM 提交并使用当前时间作为提交信息
git commit -m "!timestamp!"

REM 推送到远程仓库
git push

endlocal
@echo on
