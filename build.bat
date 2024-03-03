@echo off
setlocal enabledelayedexpansion

echo Starting application build...

timeout /nobreak /t 1 > nul

echo Obtaining address...

timeout /nobreak /t 1 > nul

for /f "tokens=2 delims=:" %%a in ('ipconfig ^| find "IPv4 Address"') do (
    set "ipAddress=%%a"
)

set "ipAddress=!ipAddress:~1!"

echo %ipAddress% > local_ip.txt

echo IP address has been saved to local_ip.txt

echo Starting configuration...

timeout /nobreak /t 1 > nul

java -jar config-builder.jar build

echo Cleaning up process...

timeout /nobreak /t 1 > nul

java -jar config-builder.jar clean

pause