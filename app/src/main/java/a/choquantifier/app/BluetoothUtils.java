package a.choquantifier.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import a.choquantifier.app.fragments.Act_modo_calculo_fragment;
import a.choquantifier.app.fragments.Act_modo_dosis_fragment;

public class BluetoothUtils {

    private static final String UUID_CODE = "00001101-0000-1000-8000-00805F9B34FB";
    private ArrayList<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
    private static BluetoothAdapter adapter;
    private String deviceName = "KS-6001BT2.0";
    private static BluetoothSocket mmSocket;
    private static int parent;

    static Act_modo_dosis_fragment modo_dosis = new Act_modo_dosis_fragment();
    static Act_modo_calculo_fragment modo_calculo = new Act_modo_calculo_fragment();

    public BluetoothUtils(int _parent) {
        adapter = BluetoothAdapter.getDefaultAdapter();
        //parent 1=modo_dosis, 2=modo_calculo
        parent = _parent;
    }

    /**
     * Devuelve el nombre de los dispositivos vinculados para poder
     * seleccionarlo
     *
     * @return
     */
    public String[] getNames() {
        String names[] = new String[devices.size()];
        for (int i = 0; i < devices.size(); i++)
            names[i] = devices.get(i).getName();
        return names;
    }

    /**
     * Método para conectar a un dispositivo Bluetooth según su posición en
     * la lista.
     */
    public boolean connect(ArrayList<BluetoothDevice> btDeviceList) {
        try {
            BluetoothDevice device = null;

            devices.clear();

            for (BluetoothDevice d : adapter.getBondedDevices())
                devices.add(d);

            for (int i = 0; i < devices.size(); i++) {
                System.out.println("devices "+devices.get(i).getName());
                for(int j = 0; j < btDeviceList.size(); j++){
                    if (devices.get(i).getAddress().equals(btDeviceList.get(j).getAddress())) {
                        device = devices.get(i);
                        System.out.println("device name 3.2.1 " + device.getName());
                    }
                }
            }

            // Conectamos con el dispositivo
            if (device != null) {
                mmSocket = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString(UUID_CODE));
                mmSocket.connect();
                return true;
            } else {
                System.out.println("Error al conectar con el dispositivo");
                return false;
            }

        } catch (IOException e) {
            // Si ha ocurrido algun error devolvemos false
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metodo para desconectar
     */
    public void disconnect() {
        if (isConnected()) {
            try {
                mmSocket.close();
            } catch (IOException e) {

            }
        }
    }

    /**
     * Metodo para desactivar
     */
    public void deactivate() {
        if (isEnable())
                adapter.disable();
    }

    /**
     * Método que nos indica si está o no conectado el socket bluetooth
     */
    public boolean isConnected() {
        if (mmSocket == null)
            return false;

        return mmSocket.isConnected();
    }

    /**
     * Método que nos indica si está o no activado la interface bluetooth
     */
    public boolean isEnable() {
        // Obtenemos el dispositivo bluetooth del terminal
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null)
            return false;

        return adapter.isEnabled();
    }

    /**
     * Método para enviar un dato mediante bluetooth
     *
     * @param dato
     */
    public void send(int dato) {

        // Si el socket está a null es que no hemos conectado
        if (mmSocket == null)
            return;

        try {
            mmSocket.getOutputStream().write(dato);
            System.out.println("dato enviado "+dato);
        } catch (IOException e) {
        }
    }


    public InputStream read() {

        if (mmSocket == null)
            return null;
        try {
            return mmSocket.getInputStream();
        } catch (IOException e) {
        }
        return null;

    }


    //***********************************************************************************//
    //***********************************************************************************//
    //***********************************************************************************//


    public static class ConnectThread extends Thread {
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(UUID.fromString(UUID_CODE));
            } catch (IOException e) {  }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            adapter.cancelDiscovery();

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) {  }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            BluetoothUtils.manageConnectedSocket innerObject = new  BluetoothUtils.manageConnectedSocket(mmSocket);
            innerObject.run();
        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

    private static class manageConnectedSocket extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;

        public manageConnectedSocket(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
            } catch (IOException e) {  }
            mmInStream = tmpIn;
        }

        public void run() {
            if (mmSocket != null) {
            Thread t = new Thread() {
                public void run() {
                    while (true) {
                        try {
                            if (mmInStream.available() > 0) {
                                byte[] packetBytes = new byte[mmInStream.available()];
                                mmInStream.read(packetBytes);
                                if(parent==1){
                                    modo_dosis.update(packetBytes);
                                }else{
                                    modo_calculo.update(packetBytes);
                                }
                            }
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            };
            t.start();
            //Toast.makeText(Act_modo_dosis.this, "Conexión establecida", Toast.LENGTH_SHORT).show();
            //} else {
            //    Toast.makeText(Act_modo_dosis.this,"No bluetooth device connected, please try again",Toast.LENGTH_SHORT).show();
            }

        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) { }
        }
    }

}
