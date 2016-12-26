package oguzhan.alarm;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.spec.ECField;
import java.util.UUID;

/**
 * Created by ErenBasaran on 04/04/16.
 */
public class ClientBTConnection extends Thread {

    private BluetoothSocket btSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private  String message;
    private BluetoothDevice btDevice;
    private static final String uuidVal = "00001101-0000-1000-8000-00805f9b34fb";

    public ClientBTConnection(BluetoothDevice device,String message) throws IOException {
        this.message=message;
        BluetoothSocket tempSocket = null;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        btDevice = device;

        try {
            tempSocket = btDevice.createRfcommSocketToServiceRecord(UUID.fromString(uuidVal));
        } catch (Exception e) {
            Log.e("BluetoothDevice Client", "Could not create RFCOMM socket: ") ;
            e.printStackTrace();
        }

        btSocket = tempSocket;
        try {
            tmpIn = btSocket.getInputStream();
            tmpOut = btSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }



         mmInStream = new DataInputStream(tmpIn);
         mmOutStream = new DataOutputStream(tmpOut);


    }

    @Override
    public void run() {

        try {
            btSocket.connect();
    if ( btSocket.isConnected()){
        Log.e("print ","connected");

        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()
        try{
            mmOutStream.write(message.getBytes());
            Log.e("yazıldı","test");
        }catch (Exception e){
            e.printStackTrace();
        }
        String received="";

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                Log.e("okunuyor ","okunuyor");
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                // Send the obtained bytes to the UI activity
                String data=new String(buffer,0,bytes);
                if (bytes!=-1){
                    received=received+data;
                }

                    mmOutStream.close();
                    mmInStream.close();
                    cancel();

            } catch (IOException e) {
               e.printStackTrace();
                break;
            }
        }
    }
        } catch (IOException e) {
            Log.e("BluetoothDevice Client", "Could not connect: " + e.toString());
            try {
                btSocket.close();
            } catch (IOException e1) {
                Log.e("BluetoothDevice Client", "Could not close connection: " + e.toString());
            }
            return;
        }
    }
    /* Call this from the main activity to send data to the remote device */

    public boolean cancel() {

        try {
            btSocket.close();
        } catch (IOException e) {

            Log.d("BluetoothDevice Client", "Could not close connection: " + e.toString());
            return false;

        }

        return true;
    }

}
