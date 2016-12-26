package oguzhan.alarm;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by ErenBasaran on 05/04/16.
 */
public class ServerBTConnection extends Thread {

    private final BluetoothServerSocket serverSocket;
    private static final String serviceName = "BluetoothDataTransfer";

    public ServerBTConnection(BluetoothAdapter btAdapter) {
        Log.d("BluetoothDevice Server", "Bluetooth Server Connection Thread running...");

        BluetoothServerSocket tempServerSocket = null;

        try {
            tempServerSocket = btAdapter.listenUsingRfcommWithServiceRecord(serviceName, UUID.fromString("f4798eca-54ed-49b7-8b12-925b1084aa5a"));
        } catch (IOException e) {
            Log.d("BluetoothDevice Server", "Could not get a BluetoothServerSocket: " + e.toString());
        }

        serverSocket = tempServerSocket;

    }


    @Override
    public void run() {
        BluetoothSocket btSocket = null;
        while(true) {

            try {
                btSocket = serverSocket.accept();

            } catch (IOException e) {
                Log.d("BluetoothDevice Server", "Could not accept an incoming connection.");
                break;
            }

            if(btSocket != null) {
                Log.d("BluetoothDevice", "Socket accepted sucessfully.");
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    Log.d("BluetoothDevice Server", "Could not close ServerSocket: " + e.toString());
                }

                break;
            }
        }
    }


    public void cancel(){

        try {
            serverSocket.close();
        } catch (IOException e) {
            Log.d("BluetoothDevice Server", "Could not close connection: " + e.toString());
        }
    }
}
