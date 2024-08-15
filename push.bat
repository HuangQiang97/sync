@echo off
REM 获取当前日期时间，并格式化为 "yyyymmdd-hhmmss"
for /f "tokens=1-4 delims=/- " %%a in ('date /t') do ( 
    set year=%%d
    set month=%%b
    set day=%%c
)
for /f "tokens=1-2 delims=:" %%a in ('time /t') do (
    set hour=%%a
    set minute=%%b
)
set second=%time:~-2%

set timestamp=%year%%month%%day%-%hour%%minute%%second%

REM 将当前文件夹下所有文件添加到Git暂存区
git add .

REM 提交并使用当前时间作为提交信息
git commit -m "%timestamp%"

REM 推送到远程仓库
git push

@echo on
