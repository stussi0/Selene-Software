package servselene.tools;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import servselene.FirmwareSelene;
import servselene.main;

public class FirmwareSerialListener implements SerialPortDataListener {

    DataOutputStream data;
    FirmwareSelene firmware;

    private StringBuilder buffer = new StringBuilder();

    public FirmwareSerialListener(DataOutputStream data, FirmwareSelene firmware){
        this.data = data;
        this.firmware = firmware;
    }

    @Override
    public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

    @Override
    public void serialEvent(SerialPortEvent event) {
        byte[] newData = event.getReceivedData();
        for (byte b : newData) {
            char c = (char) b;
            if (c == '\n') { // Final da mensagem (ajuste para seu caso)
                String fullMessage = buffer.toString().trim();
                buffer.setLength(0); // limpa buffer

                // Aqui você trata a mensagem recebida
                if (fullMessage.startsWith("#DIST=")) {
                    String valorStr = fullMessage.substring(6); // extrai o que está após o "VALOR="
                    //System.out.println("Valor Distancia recebido: " + valorStr);

                    try {
                        int valor = Integer.parseInt(valorStr);
                        // use o valor inteiro como quiser
                        //System.out.println("Valor como inteiro: " + valor);
                        firmware.setDistancia((double)valor);
                    } catch (NumberFormatException e) {
                        System.err.println("Valor inválido recebido: " + valorStr);
                    }
                } else if (fullMessage.startsWith("#ANG=")) {
                    String valorStr = fullMessage.substring(5); // extrai o que está após o "VALOR="
                    //System.out.println("Valor ângulo recebido: " + valorStr);

                    try {
                        int valor = Integer.parseInt(valorStr);
                        // use o valor inteiro como quiser
                        //System.out.println("Valor como inteiro: " + valor);
                        firmware.setAngulo((double)valor);
                    } catch (NumberFormatException e) {
                        System.err.println("Valor inválido recebido: " + valorStr);
                    }
                } else if (fullMessage.startsWith("#INTN=")) {
                    String valorStr = fullMessage.substring(6); // extrai o que está após o "VALOR="
                    //System.out.println("Valor Intensidade recebido: " + valorStr);

                    try {
                        int valor = Integer.parseInt(valorStr);
                        // use o valor inteiro como quiser
                        //System.out.println("Valor como inteiro: " + valor);
                        firmware.setIntensidade((double)valor);
                    } catch (NumberFormatException e) {
                        System.err.println("Valor inválido recebido: " + valorStr);
                    }
                } else if (fullMessage.startsWith("#FORMA=")) {
                    String valorStr = fullMessage.substring(7); // extrai o que está após o "VALOR="
                    //System.out.println("Valor Forma recebido: " + valorStr);

                    try {
                        int valor = Integer.parseInt(valorStr);
                        // use o valor inteiro como quiser
                        //System.out.println("Valor como inteiro: " + valor);
                        firmware.setForma((double)valor);
                    } catch (NumberFormatException e) {
                        System.err.println("Valor inválido recebido: " + valorStr);
                    }
                } else {
                    System.out.println("Mensagem não reconhecida: " + fullMessage);
                }
            } else {
                buffer.append(c); // acumula caractere
            }
        }
    }
    
}
