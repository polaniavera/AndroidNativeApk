package a.choquantifier.app;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import a.choquantifier.app.R;

public class MiBlueTooth extends Thread {

    private Activity acti;
    private int    paso       = 1;
    private double Valor_peso = 0.0;
    private BluetoothAdapter BA;
    private Set<BluetoothDevice> pairedDevices;
    private BluetoothDevice ReceptorBlueTooth;
    private BluetoothSocket BTSocket;
    private InputStream mmInStream;
    private OutputStream mmOutStream;

    public MiBlueTooth(){
        this.setPriority(Thread.MAX_PRIORITY);
    }

    public void run() {
        do{
            if (paso == 1) { //Activando
                Log.d("Duvan", "PASO "+Integer.toString(paso));
                paso = 2;
                Log.d("Duvan", "1. ACTIVANDO BLUETOOTH");
                BA = BluetoothAdapter.getDefaultAdapter();
                if (!BA.isEnabled()) {
                    Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    acti.startActivity(turnOn);
                }
            }

            if (paso == 2) {    //Conectando
                if (BA.isEnabled()) {
                    Log.d("Duvan", "PASO "+Integer.toString(paso));
                    Log.d("Duvan", "2. BLUETOOTH ACTIVADO");
                    //Creando el dispositivo
                    ReceptorBlueTooth = BA.getRemoteDevice("DB:F8:14:50:28:FA");
                    java.util.UUID miUUIDs = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
                    //Creando el socket
                    BluetoothSocket tmp = null;
                    try {
                        tmp = ReceptorBlueTooth.createRfcommSocketToServiceRecord(miUUIDs);
                        Log.d("Duvan", "2. SOCKET OK");
                    } catch (IOException e) {
                        Log.d("Duvan", "2. SOCKET NO");
                        paso = 1;
                    }
                    BTSocket = tmp;
                    //Conectandose al dispositivo por medio del socket
                    try {   BluetoothSocket mmSocket;
                        BTSocket.connect();
                        InputStream tmpIn = null;
                        OutputStream tmpOut = null;
                        try {
                            tmpIn = BTSocket.getInputStream();
                            tmpOut = BTSocket.getOutputStream();
                            Log.d("Duvan", "2.1. STREAMS OK");
                            mmInStream = tmpIn;
                            mmOutStream = tmpOut;
                            paso = 3;
                        } catch (IOException e) {
                            Log.d("Duvan", "2.1. STREAMS NO");
                            paso = 1;
                        }
                        Log.d("Duvan", "3. CONECTADO");
                    } catch (IOException connectException) {
                        paso = 1;
                        Log.d("Duvan", "3. CONEXION NO");
                        try {
                            paso = 1;
                            BTSocket.close();
                            Log.d("Duvan", "4. SOCKET CLOSE");
                        } catch (IOException closeException)
                        {
                        }
                    }
                }
                try{ Thread.sleep(200); }catch(InterruptedException ex){}
            }

            if (paso == 3) {    //Recibiendo datos
                byte[] buffer = new byte[1024];
                int bytes;
                writeByte(65);
                try{ Thread.sleep(10); }catch(InterruptedException ex){}
                try
                {   bytes = mmInStream.read(buffer);
                    if(bytes>=2)
                    {
                        try
                        {
                            int bytesAvailable = mmInStream.available();
                            if(bytesAvailable > 0)
                            {
                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInStream.read(packetBytes);

                                int gramosL=0, gramosH=0, gramos =0;

                                if(bytesAvailable>=2)
                                {   gramosL = packetBytes[1];
                                    gramosH = packetBytes[0];
                                    gramos = 0;
                                    gramos = gramos + (gramosL & 1) * 1;
                                    gramos = gramos + (gramosL & 2) ;
                                    gramos = gramos + (gramosL & 4) ;
                                    gramos = gramos + (gramosL & 8) ;
                                    gramos = gramos + (gramosL & 16) ;
                                    gramos = gramos + (gramosL & 32) ;
                                    gramos = gramos + (gramosL & 64) ;
                                    gramos = gramos + (gramosL & 128) ;
                                    gramos = gramos + (gramosH & 1)/1*256 ;
                                    gramos = gramos + (gramosH & 2)/2*512 ;
                                    gramos = gramos + (gramosH & 4)/4*1024 ;
                                    gramos = gramos + (gramosH & 8)/8*2048 ;
                                    gramos = gramos + (gramosH & 16)/16*4096 ;

                                    if(  (packetBytes[0] & 0xE0 )  == 0xE0 )
                                    {   gramos = gramos ;
                                    }
                                    else
                                    {   gramos = - gramos;
                                    }

                                    Valor_peso=(double) gramos;
                                    //escribirControl();
                                    Log.d("Duvan","xxxyyy "+Integer.toString( gramos ) );

                                }
                            }

                        }
                        catch (Exception e)
                        {
                        }
                    }
                } catch (IOException e) {
                    Log.d("Duvan", "ERR BUFFER");
                    paso=1;
                }
                try{ Thread.sleep(500); }catch(InterruptedException ex){}
            }
        }while(true);

    }

    public void iniciar( )
    {   paso=1;
        //Codigo para iniciar el Hilo
        this.start();
    }

    public void reactivarSiMurio( )
    {   if(paso==2)
    {   this.start();
    }
    }

    public void parar( )
    {   paso=0;
        //Codigo para parar el Hilo
    }

    public String peso( )
    {   return Double.toString(Valor_peso);
    }

    public void write(byte[] bytes)
    {   try
    {   if(paso==3)
    {   mmOutStream.write(bytes);
        //Log.d("Duvan","ESCRIBI: "+ Byte.toString(bytes[0]) );
        //Log.d("Duvan","PASO: "+ Integer.toString(paso) );
        //Log.d("Duvan","ALIVE : "+ Boolean.toString( this.isAlive() ) );
        //Log.d("Duvan","ESTADO: "+ this.getState().toString()  );
    }
    } catch (IOException e)
    {   Log.d("Duvan","FALLO AL ESCRIBIR"+Integer.toString(paso) );
        paso=1;
    }
    }

    public void writeByte(int numero)
    {   byte[] data;
        data=new byte[1];
        data[0]=Byte.parseByte(Integer.toString(numero));
        write(data);
    }

    /*
    private void escribirControl()
    {   TextView textoPeso = (TextView) findViewById(R.id.textView1);
        textoPeso.setText(peso());
    }
    */

    public void cancel() {
        try
        {   paso=1;
            BTSocket.close();
        } catch (IOException e) {}
    }

}
