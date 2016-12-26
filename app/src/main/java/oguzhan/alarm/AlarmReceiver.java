package oguzhan.alarm;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class AlarmReceiver extends BroadcastReceiver {
    ArrayList<BluetoothDevice> btDeviceList;
    ArrayList<String> devicesList;
    @Override
    public void onReceive(Context context, Intent intent) {

        // For our recurring task, we'll just display a message
        Toast.makeText(context, "alarm açıldı", Toast.LENGTH_SHORT).show();
        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter != null) {

            if (!bluetoothAdapter.isEnabled()) {

                bluetoothAdapter.enable();

            }
        }
        String data="";
        try {
            data=  intent.getStringExtra("data");
        }
        catch (Exception e){
            e.printStackTrace();
            Log.e("String:","alınamadı");
        }
        Log.e("data=",data);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try {
            bluetoothAdapter.enable();
        }catch (Exception e){
            Toast.makeText(context,"bluetooth yok",Toast.LENGTH_SHORT);
            e.printStackTrace();
            return;

        }
        if(bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }


        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        BluetoothDevice bluetoothDevice=null;
        for (Iterator<BluetoothDevice> it = bondedDevices.iterator(); it.hasNext();){
            BluetoothDevice temp=it.next();
            if (temp.getName().equals("HC-05")) {
                bluetoothDevice = temp;
                Log.e("name ", temp.getName());
                Log.e("bonded ", String.valueOf(temp.getBondState()));
                Toast.makeText(context,"bluetooth cihaza baglanılıyor",Toast.LENGTH_SHORT);
            }
            else {
                Toast.makeText(context,"Cihaz bulunamadi",Toast.LENGTH_SHORT);

            }
        }
        Thread clientThread= null;
        try {
            clientThread = new ClientBTConnection(bluetoothDevice,data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            Log.e("BluetoothDevice", bluetoothDevice.getName() + " " + bluetoothDevice.getAddress());
            Log.e("BluetoothDevice", "Client connection starting...");

        }catch (Exception e){
            e.printStackTrace();
        }


        if (bluetoothDevice!=null)
          clientThread.run();





        devicesList = new ArrayList<String>();
        btDeviceList = new ArrayList<BluetoothDevice>();




    }


}