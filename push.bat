@echo off
for /f %%i in ('powershell -Command "Get-Date -Format \"yyyyMMdd-HHmmss\""') do set timestamp=%%i

REM 打印出生成的时间戳以确认格式正确
echo %timestamp%

REM 将当前文件夹下所有文件添加到Git暂存区
git add .

REM 提交并使用当前时间作为提交信息
git commit -m "%timestamp%"

REM 推送到远程仓库
git push --set-upstream origin main