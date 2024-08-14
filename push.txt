@echo off
:: 获取当前日期和时间
for /f "tokens=1-4 delims=-/. " %%a in ('wmic os get localdatetime ^| find "."') do (
    set datetime=%%a
)

set year=%datetime:~0,4%
set month=%datetime:~4,2%
set day=%datetime:~6,2%
set hour=%datetime:~8,2%
set minute=%datetime:~10,2%
set second=%datetime:~12,2%

set timestamp=%year%%month%%day% %hour%:%minute%:%second%

:: 添加所有文件
git add --all

:: 提交修改，备注为当前时间
git commit -m "%timestamp%"

:: 推送到远程仓库
git push