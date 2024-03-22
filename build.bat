@echo off
setlocal enabledelayedexpansion

echo Starting application build...

timeout /nobreak /t 1 > nul

echo Obtaining address...

timeout /nobreak /t 1 > nul

for /f "tokens=2 delims=:" %%a in ('ipconfig ^| find /i "IPv4 Address"') do (
    set "ipAddress=%%a"
    set "ipAddress=!ipAddress:~1!"
    set "ipAddress=!ipAddress: =!"
    echo !ipAddress!> local_ip.txt
    goto :next
)
:next

echo Local IP saved to local_ip.txt

endlocal

cd src/backend

if exist target (
	del target /f /q /s
) 

cd ../frontend

if exist node_modules (
	del node_modules /f /q /s
)

cd ../..

echo Starting configuration...

timeout /nobreak /t 1 > nul

java -jar config-builder.jar

echo Cleaning up process...

timeout /nobreak /t 1 > nul

if exist local_ip.txt (
	del local_ip.txt
)

cd src/frontend

if exist node (
	del node /f /q /s
)

pause