# Selene-Software
Servidor do robo Selene

## Sumario

-   [Dependências](#dependências)
-   [Instalação](#instalação)
    -   [Windows](#windows)
        -   [MinGW-w64](#mingw-w64)
        -   [JRE](#jre)
        -   [JDK](#jdk)
        -   [JDBC](#jdbc)
        -   [WEKA](#weka)
    -   [Linux](#linux)
        -   [MinGW-w64](#mingw-w64)
        -   [JRE](#jre)
        -   [JDK](#jdk)
        -   [JDBC](#jdbc)
        -   [WEKA](#weka)
-   [Descrição](#descrição)
-   [Manual de Usuário](#manual-de-usuário)

## Dependências

| Nome    | Versão | Doc                                                              |
| ------- | ------ | ---------------------------------------------------------------- |
| g++     | >= 4.9 | https://whitgit.whitworth.edu/tutorials/installing_mingw_64      |
| OpenJDK | >= 17  | [OpenJDK 21 - Mas está com nome de 17](https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_windows-x64_bin.zip) |
| JDBC    | >= 42.7.3 | https://jdbc.postgresql.org/download/ |
| WEKA Monolithic | = 3.8.0 | https://sourceforge.net/projects/weka/files/weka-3-8/3.8.0/ |

## Instalação

**_Passo 1 :_** Após clonar o repositório execute o comando para verificar se todas as dependências estão instaladas:

### Windows

```bash
.\verificar_dependencias_windows.bat
```

Caso alguma dependência não esteja instalada ele te direcionará, utilizando o browser padrão para o site de download de cada uma delas. Baixe as dependências necessárias e as instale-as da seguinte forma:

#### MinGW-w64 

Após baixar o arquivo zip de instalação (installing_mingw_64-master.zip) descompacte-o.
Dentor dele haverá outro arquivo zip denominado mingw64.zip, também descompacte-o.
Crie um diretório para a instalação, como por exemplo: C:\mingw-w64.
Dos arquivos descompactados, mova a pasta "mingw64" para "C:\mingw-w64".
Abra as "Propriedades do Sistema" a acrescente nas variáveis de ambiente na variável PATH a pasta: "C:\mingw-w64\mingw64\bin"

Aplique as modificações e faça o teste digitendo no prompt de comando:

```bash
g++ --version
```

#### JRE

Após baixar o arquivo abra-o.

Click em instalar e aguarde o término do procedimento.
Para testar a instalação abra o terminal e digite o comando:

```bash
java -version
```

#### JDK

Após baixar o arquivo, crie uma pasta "Java" em arquivos de programas.

Descompacte o arquivo zipado dentro da pasta "Java" criada.

Abra a "Propriedades do Sistema" e clique em "Variáveis de Ambiente":

Crie uma nova variável de ambiente chamada "JAVA_HOME" e coloque o caminho da pasta descompactada.

Clique na variável "Path" e clique em "editar", então adicione a pasta bin presente dentro da pasta descompactada do "Java".

Clique em Ok em todas as janelas e o procediemnto estará encerrado.

Para testar a instalação abra o terminal e digite o comando:

```bash
javac -version
```

#### JDBC

Após baixar o conector JDBC do PostgreSQL coloque-o na pasta libs do diretório raíz do projeto.

Abra o Terminal de Comandos no diretório raíz do projeto e digite a seguinte linha substituindo %SEU LOGIN% pelo nome de usuário que você usa para acessar o banco e %SUA SENHA% pela senha que você usa no usuário informado:

```
echo #Configurações do Banco de Dados > init.cnf
echo -n db.user=%SEU LOGIN% >> init.cnf
echo -n db.password=%SUA SENHA% >> init.cnf
echo -n jdbc.driver=org.postgresql.Driver >> init.cnf
echo -n db.url=jdbc:postgresql://172.16.0.199/anglo >> init.cnf
```

Salve o arquivo após a edição.

#### WEKA

Após baixar a biblioteca Weka Monolithic coloque-a na pasta libs do diretório raíz do projeto.

### Linux

```bash
.\verificar_dependencias_linux.sh
```

Caso alguma dependência não esteja instalada ele te direcionará, utilizando o browser padrão para o site de download de cada uma delas. Baixe as dependências necessárias e as instale-as da seguinte forma:

#### MinGW-w64 

Caso sua distribuição linux não tenha o gcc pré instalado é necessário seguir um dos seguintes passos: Gereciador de Pacote ou Instalação utilizando tar.gz
Caso sua distribuição linux tenha um gerenciador de pacotes é recomendado que você use-a para realizar a instalação, caso contrário leia o manual de instalação pelo código fonte em https://gcc.gnu.org/install/index.html , e depois configure utilizando o seguinte manual: https://gcc.gnu.org/wiki/InstallingGCC .

#### JRE

É recomendado que se instale o JRE utilizando o gerenciador de pacotes da sua distribuição, porêm caso não exista esta opção utilize o arquivo que for baixado após a execução do script ou entre no site: https://www.java.com/en/download/linux_manual.jsp e baixe a versão compatível com seu Sistema Operacional.
Depois siga o procedimento descrito em: https://www.java.com/en/download/help/linux_x64_install.html#install
Para testar a instalação abra o terminal e digite o comando:

```bash
java -version
```

#### JDK

É recomendado que se instale o JRE utilizando o gerenciador de pacotes da sua distribuição, porêm caso não exista esta opção utilize o arquivo que for baixado após a execução do script ou entre no site: https://docs.oracle.com/en/java/javase/22/install/installation-jdk-linux-platforms.html
Para testar a instalação abra o terminal e digite o comando:

```bash
javac -version
```

**_Passo 2 :_** Para criar o conjunto de diretórios, Bytecodes e Javadocs execute o comando:

#### JDBC

Após baixar o conector JDBC do PostgreSQL coloque-o na pasta libs do diretório raíz do projeto.

Abra o Terminal no diretório raíz do projeto e digite a seguinte linha substituindo %SEU LOGIN% pelo nome de usuário que você usa para acessar o banco e %SUA SENHA% pela senha que você uso no usuário informado:

```
echo #Configurações do Banco de Dados >> init.cnf
echo db.user=%SEU LOGIN% >> init.cnf
echo db.password=%SUA SENHA% >> init.cnf
echo jdbc.driver=org.postgresql.Driver >> init.cnf
echo db.url=jdbc:postgresql://localhost/selene >> init.cnf
```

Salve o arquivo após a edição.

#### WEKA

Após baixar a biblioteca Weka Monolithic coloque-a na pasta libs do diretório raíz do projeto.

### Windows

```bash
.\build.bat
```

### Linux

```bash
.\build.sh
```

> [!NOTE]
> Após a instação para testar ou utilizar a API é necessário no windows apenas dois clicks no executável, mas no linux é necessário fornecer permissão para o programa executar;

**_Passo 3 :_** Execute os comandos:

### Windows

```bash
exec
```

### Linux

```bash
sudo chmod -x exec
./exec
```

## Descrição

Uma API que gera a partir de descrições do banco de dados e preferrências do usuário o backend que comunica-se com o banco e o frontend que auxilia o usuário à manipular o recurso;

## Manual de Usuário

Download `Selene` in 👉 [AQUI](https://github.com/stussi0/Selene-Software/archive/refs/heads/main.zip)

> [!WARNING]
> Para que funcione corretamente o Selene instale o OpenJDK na versão ^17 download em 👉 [OpenJDK](https://download.java.net/java/GA/jdk17.0.2/dfd4a8d0985749f896bed50d7138ee7f/8/GPL/openjdk-17.0.2_windows-x64_bin.zip)

Descompacte a pasta e execute o `selene`;

Bom Uso!