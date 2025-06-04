@echo off
setlocal enabledelayedexpansion

echo Definir o caminho do diretório atual...
set current_dir=%~dp0

echo %current_dir%

echo Class-Path: file:/%CD%/ file:/%CD%/libs/ file:/%CD%/libs/jSerialComm-2.9.2.jar file:/%CD%/libs/weka.jar file:/%CD%/libs/ij.jar" > manifest.txt
jar cvfm %current_dir%dependencies.jar manifest.txt
echo Removendo o arquivo manifest temporario...
del manifest.txt

echo Compilar todos os arquivos .java
javac -encoding UTF-8 -cp %current_dir%dependencies.jar -d %current_dir%out %current_dir%src\servselene\main.java %current_dir%src\servselene\tools\*.java %current_dir%src\servselene\tarefas\*.java -Xlint

echo Verificar se a compilação foi bem-sucedida
if %errorlevel% neq 0 (
    echo Erro durante a compilacao!
    exit /b %errorlevel%
) else (
    echo Compilacao bem-sucedida!
)

echo Criar o arquivo .jar com classe executável...
cd %current_dir%out
echo Criando o arquivo de manifesto com o caminho da main class...
echo Main-Class: servselene/main > manifest.txt
echo Acrescentando as classes ao arquivo .jar...
jar cvfm %current_dir%out\main.jar manifest.txt servselene\*.class servselene\tools\*.class servselene\tarefas\*.class

echo Verificar se a criação do .jar foi bem-sucedida:
if %errorlevel% neq 0 (
    echo Erro durante a criacao do arquivo JAR!
    exit /b %errorlevel%
) else (
    echo Arquivo JAR criado com sucesso!
)

echo Remover o arquivo manifest temporário...
del %current_dir%out\manifest.txt

echo Gerando javadocs...
cd %current_dir%
javadoc -encoding UTF-8 -cp %current_dir%dependencies.jar -d docs -sourcepath src -subpackages servselene

echo Testando compilacao:
java -jar out/main.jar -v
