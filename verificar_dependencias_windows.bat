@echo off
setlocal

rem Verifica se o Java está instalado
java -version >nul 2>nul
if %errorlevel% neq 0 (
    echo Java nao esta instalado no sistema.
    echo Abrindo navegador para baixar o Java...
    start https://www.java.com/en/download/
) else (
    echo Java esta instalado no sistema.
)

rem Verifica se o JDK está instalado
javac -version >nul 2>nul
if %errorlevel% neq 0 (
    echo JDK nao esta instalado no sistema.
    echo Abrindo navegador para baixar o JDK...
    start https://jdk.java.net/22/
) else (
    echo JDK esta instalado no sistema.
)

rem Verifica se o MinGW-w64 está instalado
g++ --version >nul 2>nul
if %errorlevel% neq 0 (
    echo MinGW-w64 nao esta instalado no sistema.
    echo Abrindo navegador para baixar o MinGW-w64...
    start https://whitgit.whitworth.edu/tutorials/installing_mingw_64/-/archive/master/installing_mingw_64-master.zip
) else (
    echo MinGW-w64 esta instalado no sistema.
)

endlocal
