package servselene;

import java.io.IOException;
import java.io.OutputStream;

import com.fazecast.jSerialComm.SerialPort;

public class FirmwareSelene {

    SerialPort comPort;
    OutputStream os;
    private boolean estado;
    private boolean intensidadeFlag;
    private double intensidade;
    private boolean distanciaFlag;
    private double distancia;
    private boolean anguloFlag;
    private double angulo;
    private boolean formaFlag;
    private double forma;

    public FirmwareSelene(SerialPort comPort){
        this.estado = true;
        this.comPort = comPort;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.os = this.comPort.getOutputStream();
    }

    public boolean isEstado() { 
        return intensidadeFlag && distanciaFlag && anguloFlag && formaFlag; 
    }
    public void used() { 
        this.intensidadeFlag = false; 
        this.distanciaFlag = false; 
        this.anguloFlag = false; 
        this.formaFlag = false; 
    }
    public double getDistancia() { return distancia; }
    public void setDistancia(double distancia) { 
        this.distancia = distancia; 
        this.distanciaFlag = true;
    }
    public double getAngulo() { return angulo; }
    public void setAngulo(double angulo) { 
        this.angulo = angulo; 
        this.anguloFlag = true;
    }
    public double getIntensidade() { return intensidade; }
    public void setIntensidade(double intensidade) { 
        this.intensidade = intensidade; 
        this.intensidadeFlag = true;
    }
    public double getForma() { return forma; }
    public void setForma(double forma) { 
        this.forma = forma; 
        this.formaFlag = true;
    }
    public void avancar(){
        try {
            String msg = "#AVANCAR\n";
            System.out.println("Mensagem enviada: "+msg);
            os.write(msg.getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void recuar(){
        try {
            String msg = "#RECUAR\n";
            System.out.println("Mensagem enviada: "+msg);
            os.write(msg.getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void direita(){
        try {
            String msg = "#DIREITA\n";
            System.out.println("Mensagem enviada: "+msg);
            os.write(msg.getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void esquerda(){
        try {
            String msg = "#ESQUERDA\n";
            System.out.println("Mensagem enviada: "+msg);
            os.write(msg.getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pegar(){
        try {
            String msg = "#PEGAR\n";
            System.out.println("Mensagem enviada: "+msg);
            os.write(msg.getBytes());
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
