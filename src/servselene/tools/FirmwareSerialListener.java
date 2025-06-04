package servselene.tools;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import servselene.main;

public class FirmwareSerialListener implements SerialPortDataListener {

    DataOutputStream data;

    public FirmwareSerialListener(DataOutputStream data){
        this.data = data;
    }

    @Override
    public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

    @Override
    public void serialEvent(SerialPortEvent event) {
        byte[] newData = event.getReceivedData();
        char[] infor = new char[newData.length];
        //System.out.println("Received data of size: " + newData.length);
        for (int i = 0; i < newData.length; ++i){
            infor[i] = (char)newData[i];
            System.out.print((char)newData[i]);
        }
        String msg = String.copyValueOf(infor);
        try {
            data.writeBytes(msg);
        } catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //System.out.println("\n");
    }
    
}
