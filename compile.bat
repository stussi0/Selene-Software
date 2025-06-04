@echo off
setlocal enabledelayedexpansion

echo Definindo o caminho do diretorio atual...
set current_dir=%~dp0

echo Compilar todos os arquivos .java...
javac -encoding UTF-8 -cp %current_dir%dependencies.jar -d %current_dir%out %current_dir%src\servselene\main.java %current_dir%src\servselene\tools\*.java %current_dir%src\servselene\tarefas\*.java -Xlint

echo Verificando se a compilacao foi bem-sucedida:
if %errorlevel% neq 0 (
    echo Erro durante a compilacao!
    exit /b %errorlevel%
) else (
    echo Compilacao bem-sucedida!
)

endlocal
