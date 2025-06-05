package servselene;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSink;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.JOptionPane;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import servselene.tarefas.VermelhaScheduler;

import servselene.tools.FirmwareSerialListener;

import java.io.*;
import java.net.InetSocketAddress;

public class main {

    public Instances db = null;

    static String messageString = "#RESET";

    public static void SerialLig(SerialPort comPort, boolean liga, int relay) throws FileNotFoundException {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Enviando Dados...");
        OutputStream os = comPort.getOutputStream();
        System.out.println("Pegou o output stream!");
        try {
            String msg = "#"+(liga ? "LIGAR" : "VALOR")+relay;
            System.out.println("Mensagem enviada: "+msg);
            os.write(msg.getBytes());
            os.flush();
            os.close();
            System.out.println("Aguardando os Dados de Resposta...");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        FileOutputStream file = new FileOutputStream("dados.txt");
        DataOutputStream data = new DataOutputStream(file);
        SerialPort coms[] = SerialPort.getCommPorts();
        String mensagem_op = "";
        for(int i = 0; i < coms.length; i++){
            mensagem_op += "Porta número: "+i+" - "+coms[i].getDescriptivePortName();
            mensagem_op += "\n";
        }
        mensagem_op += "Qual o número da porta escolhida: \n";
        //String opcao = JOptionPane.showInputDialog(mensagem_op);
        String opcao = "0";
        SerialPort comPort = SerialPort.getCommPorts()[Integer.parseInt(opcao)];
        System.out.println(comPort.getDescriptivePortName());
        comPort.setBaudRate(9600);
        comPort.openPort();
        System.out.println("Porta Aberta...");
        FirmwareSelene firmware = new FirmwareSelene(comPort);
        FirmwareSerialListener firmwareListener = new FirmwareSerialListener(data,firmware);
        comPort.addDataListener(firmwareListener);

        Timer timer = new Timer();
        VermelhaScheduler task = new VermelhaScheduler(firmware);
        timer.schedule(task, 1 * 1000, 1 * 1000);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        //server.createContext("/upload", new UploadHandler());
        //server.createContext("/access", new AccessHandler());
        server.createContext("/iniciar", new LigarRelay(comPort, true, 1));
        server.createContext("/parar", new LigarRelay(comPort, true, 2));
        server.createContext("/buscar-vermelha", new LigarRelay(comPort, true, 3));
        server.createContext("/buscar-verde", new LigarRelay(comPort, true, 4));
        server.createContext("/buscar-azul", new LigarRelay(comPort, true, 5));

        server.setExecutor(null); // creates a default executor
        server.start();
        System.out.println("Servidor iniciado na porta 8080...");
    }

    static class LigarRelay implements HttpHandler {

        private SerialPort comPort;
        private boolean ligar;
        private int relay;

        public LigarRelay(SerialPort comPort, boolean ligar, int number){
            this.comPort = comPort;
            this.ligar = ligar;
            this.relay = number;
        }

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            SerialLig(this.comPort, this.ligar, this.relay);
            String resposta = String.format(
                "{\n" +
                "  \"version\": \"1.0\",\n" +
                "  \"response\": {\n" +
                "    \"outputSpeech\": {\n" +
                "      \"type\": \"PlainText\",\n" +
                "      \"text\": \"Ligando a porta %d.\"\n" +
                "    },\n" +
                "    \"shouldEndSession\": true\n" +
                "  }\n" +
                "}", this.relay
            );

            byte[] responseBytes = resposta.getBytes("UTF-8");
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }

    }

    static class UploadHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                // Recebe o arquivo enviado pelo cliente
                InputStream inputStream = exchange.getRequestBody();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    requestBody.append(line);
                }

                // Armazena as informações em um arquivo JSON
                String jsonFilePath = "file.json";
                FileWriter fileWriter = new FileWriter(jsonFilePath);
                fileWriter.write(requestBody.toString());
                fileWriter.close();

                // Retorna uma resposta para o cliente
                String response = "Arquivo recebido e armazenado com sucesso.";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
                exchange.sendResponseHeaders(500, 0);
            } finally {
                exchange.close();
            }
        }
    }

    static class AccessHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                // Retorna a página HTML com um formulário para enviar um arquivo
                String htmlResponse = "<html><body>" +
                        "<h1>Envie um arquivo e informações:</h1>" +
                        "<form method=\"POST\" enctype=\"multipart/form-data\" action=\"/upload\">" +
                        "Arquivo: <input type=\"file\" name=\"file\"><br>" +
                        "Informações: <input type=\"text\" name=\"info\"><br>" +
                        "<input type=\"submit\" value=\"Enviar\">" +
                        "</form>" +
                        "</body></html>";

                // Envia a resposta HTML para o cliente
                exchange.sendResponseHeaders(200, htmlResponse.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(htmlResponse.getBytes());
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
                exchange.sendResponseHeaders(500, 0);
            } finally {
                exchange.close();
            }
        }
    }
    
}
