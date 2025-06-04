#!/bin/bash

# Verifica se o Java está instalado
java -version >/dev/null 2>&1
if [ $? -ne 0 ]; then
    echo "Java nao esta instalado no sistema."
    echo "Abrindo navegador para baixar o Java..."
    xdg-open "https://www.java.com/en/download/"
else
    echo "Java esta instalado no sistema."
fi

# Verifica se o JDK está instalado
javac -version >/dev/null 2>&1
if [ $? -ne 0 ]; then
    echo "JDK nao esta instalado no sistema."
    echo "Abrindo navegador para baixar o JDK..."
    xdg-open "https://jdk.java.net/22/"
else
    echo "JDK esta instalado no sistema."
fi

# Verifica se o compilador GCC está instalado (e, portanto, o g++)
g++ --version >/dev/null 2>&1
if [ $? -ne 0 ]; then
    echo "GCC (g++) nao esta instalado no sistema."
    echo "Abrindo navegador para baixar o GCC (g++)..."
    xdg-open "https://gcc.gnu.org/"
else
    echo "GCC (g++) esta instalado no sistema."
fi
