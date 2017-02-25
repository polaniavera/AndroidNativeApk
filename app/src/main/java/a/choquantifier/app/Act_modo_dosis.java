package a.choquantifier.app;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.KeyEvent;
import android.widget.AdapterView.OnItemSelectedListener;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Collections;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import a.choquantifier.app.custom.ExpandableHeightGridView;

public class Act_modo_dosis extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter btAdapter;
    private ArrayList<BluetoothDevice> btDeviceList = new ArrayList<BluetoothDevice>();
    private BluetoothUtils bluetooth;

    String Sus_id;
    int codigo, year, month, day, gramos, GSA_ACTUAL_CORRECCION;
    double DOSIS_ESTIMADA, CHO_TOTAL, IGL_TOTAL, DOSIS_ESTIMADA_CORRECCCION;

    static final int TABS_ID = 1111;

    ArrayList<ArrayList<String>> tiposArr = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> alimentosArr = new ArrayList<ArrayList<String>>();
    String idAlimento="";

    private static Act_modo_dosis ins;
    private MenuItem menuItemBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_modo_dosis);
        ins = this;
        //No permitir que rote la pantalla
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button botonAceptar  = (Button) findViewById(R.id.btnAceptar);
        Button botonCalcular  = (Button) findViewById(R.id.btnAceptar);
        botonAceptar.setEnabled(false);
        botonCalcular.setEnabled(true);

        // Get current date by calendar
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        bluetooth = new BluetoothUtils(1);

        //OBTENER VARIABLE GLOBAL
        final GlobalData globalVariable = (GlobalData) getApplicationContext();
        codigo = globalVariable.getCodigo();
        Sus_id = Integer.toString(codigo)   ;

        //Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(ActionFoundReceiver, filter); // Don't forget to unregister during onDestroy

        // Getting the Bluetooth adapter
        btAdapter = BluetoothAdapter.getDefaultAdapter();

        //Llamar al custom grid y setear atributo expansión.
        ExpandableHeightGridView grid = (ExpandableHeightGridView) findViewById(R.id.gridview);
        grid.setExpanded(true);

        llenarSpinnerWithTipos(R.id.spTipoAlimento);
        refrescarTabla(false);
    }

    public static Act_modo_dosis getInstace(){
        return ins;
    }

    private void returnToCaller(double dosis) {
        Intent i = getIntent();
        i.putExtra("RESULTADO" ,Double.toString(DOSIS_ESTIMADA));
        i.putExtra("RESULTADO2",Double.toString(CHO_TOTAL));
        i.putExtra("RESULTADO3",Double.toString(IGL_TOTAL));
        setResult(RESULT_OK, i);
        finish();
    }

    int Touch_Times;
    int Touch_Code;
    private void refrescarTabla(boolean refrescarDatos)
    {   final GridView gridview = (GridView) findViewById(R.id.gridview);
        ImageAdapter Datos = new ImageAdapter(this,codigo);
        if (refrescarDatos)
        {   gridview.invalidateViews();
        }
        gridview.setAdapter(Datos);
        gridview.setOnItemClickListener
                (      new AdapterView.OnItemClickListener()
                       {   public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                       {   int codigoAlimento = Integer.parseInt(gridview.getAdapter().getItem(position).toString());
                           if(Touch_Code==codigoAlimento)
                           {   Touch_Code=codigoAlimento;
                               ++Touch_Times;
                               if (Touch_Times==2)
                               {   DatosDB_SQLite admin = new DatosDB_SQLite(parent.getContext() , DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
                                   SQLiteDatabase bd = admin.getWritableDatabase();
                                   admin.borrarAlimentoTemp(codigoAlimento);
                                   bd.close();
                                   admin.close();
                                   Touch_Times=0;
                                   Touch_Code=0;
                               }
                           }
                           else
                           {   Touch_Code=codigoAlimento;
                               Touch_Times=0;
                           }
                           refrescarTabla(true);
                       }
                       }
                );

        EditText txtCHO   = (EditText) findViewById(R.id.txtTotalCHO);
        EditText txtIG    = (EditText) findViewById(R.id.txtPromIG);
        EditText txtDOSIS = (EditText) findViewById(R.id.txtDosisEstimada);
        EditText txtNoInd = (EditText) findViewById(R.id.txtNoIndGrms);
        EditText txtGSA = (EditText) findViewById(R.id.txtGSA);
        EditText txtDE = (EditText) findViewById(R.id.txtDE);
        EditText txtDT = (EditText) findViewById(R.id.txtDT);
        EditText txtPro   = (EditText) findViewById(R.id.txtProTotal);
        EditText txtGrasa   = (EditText) findViewById(R.id.txtGrasaTotal);

        txtCHO.setText   (  Double.toString(Datos.CHO_Total)      );
        txtIG.setText    (  Double.toString(Datos.IG_Ponderado)   );
        txtDOSIS.setText (  Double.toString(Datos.DOSIS_Estimada) );
        txtPro.setText (  Double.toString(Datos.PROTEINA_TOTAL) );
        txtGrasa.setText (  Double.toString(Datos.GRASA_TOTAL) );
        txtNoInd.setText ("");
        txtGSA.setText ("");
        txtDE.setText ("0.0");
        txtDT.setText("0.0");
    }

    public void Aceptar_click(View view){
        EditText txtCHO   = (EditText) findViewById(R.id.txtTotalCHO);
        EditText txtIG    = (EditText) findViewById(R.id.txtPromIG);
        EditText txtDOSIS = (EditText) findViewById(R.id.txtDosisEstimada);
        EditText txtGSA = (EditText) findViewById(R.id.txtGSA);
        EditText txtDE  = (EditText) findViewById(R.id.txtDE);
        if (txtCHO.getText().toString().equals(""))  {txtCHO.setText("0");  }
        if (txtIG.getText().toString().equals(""))   {txtIG.setText("0");   }
        if (txtDOSIS.getText().toString().equals("")){txtDOSIS.setText("0");}
        if (txtGSA.getText().toString().equals("")){   txtGSA.setText("0");}
        if (txtDE.getText().toString().equals("")) {   txtDE.setText("0"); }
        double DtxtCHO   = Double.parseDouble(txtCHO.getText().toString()) ;
        double DtxtIG    = Double.parseDouble(txtIG.getText().toString()) ;
        double DtxtDOSIS = Double.parseDouble(txtDOSIS.getText().toString()) ;
        double GSA_Act  = Double.parseDouble(txtGSA.getText().toString()) ;
        double GSA_DE   = Double.parseDouble(txtDE.getText().toString()) ;
        DOSIS_ESTIMADA = DtxtDOSIS;

        if ( DtxtCHO>0 && DtxtIG>0 && DtxtDOSIS>0) {
            //String fecha = "\"" + android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date()).toString() + "\"";
            String fecha = "\"" + year + "-" + (month+1) + "-" + day + "\"";
            String hora = "\"" + android.text.format.DateFormat.format("HH:mm:ss", new java.util.Date()).toString() + "\"";

            String SDtxtCHO   = Double.toString(DtxtCHO)  ;
            String SDtxtIG    = Double.toString(DtxtIG)   ;
            String SDtxtDOSIS = Double.toString(DtxtDOSIS);
            String StGSA = Double.toString(GSA_Act);
            StGSA.replace(",", ".");
            String StDE = Double.toString(GSA_DE);
            StDE.replace(",", ".");

            String consultaSQL =
                    "INSERT INTO historial(historial_usuario, historial_fecha, historial_hora, historial_gs_actual,historial_cho_totales,historial_bolo_alimenticio,historial_bolo_correccion,historial_bolo_total) " +
                            "VALUES (" + Sus_id + "," + fecha + "," + hora + "," + StGSA + "," + SDtxtCHO +","+SDtxtDOSIS+ "," + StDE + "," + SDtxtDOSIS + ")";

            ejecutarSQL(consultaSQL);

            CHO_TOTAL=DtxtCHO;
            IGL_TOTAL=DtxtIG;
            DOSIS_ESTIMADA_CORRECCCION=GSA_DE;
            GSA_ACTUAL_CORRECCION = (int) GSA_Act;
            limpiarTablaAndTextos();
            Toast.makeText( Act_modo_dosis.this,"Entrada almacenada con éxito" ,Toast.LENGTH_SHORT).show();
            //returnToCaller(DOSIS_ESTIMADA);
            //finish();
        }

    }

    public void ejecutarSQL(String SQL)
    {   String consultaSQL=SQL;
        DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getWritableDatabase();
        bd.execSQL(consultaSQL);
        bd.close();
        admin.close();
    }

    public void limpiarTabla_click(View view){
        limpiarTablaAndTextos();
    }

    public void Cancelar_click(View view){
        limpiarTablaAndTextos();
        //returnToCaller(0.0);
        finish();
    }

    private void limpiarTablaAndTextos()
    {   DatosDB_SQLite admin = new DatosDB_SQLite(this , DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getWritableDatabase();
        admin.deleteAlimentosTemporal();
        bd.close();
        admin.close();
        refrescarTabla(true);
    }

    public void cargarSpinner(int spinerId, int listadoResource) {
        Spinner spinner1 = (Spinner) findViewById(spinerId);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource( this, listadoResource, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }

    public void llenarSpinnerWithTipos(int MySpinner)
    {   DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        Spinner spinner1 = (Spinner) findViewById(MySpinner);
        SQLiteDatabase bd = admin.getWritableDatabase();
        tiposArr = admin.getAllTipos();
        List<String> list = new ArrayList<String>();
        //Sort Data Alphabetical order
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Act_modo_dosis.this,android.R.layout.simple_spinner_item, tiposArr.get(1));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
                String name = parent.getItemAtPosition(position).toString();
                String Tipo = getCodTipo(position);
                if(!Tipo.equals(""))
                {   llenarSpinnerWithNoIndustralizados(R.id.spNoIndustrializado,Tipo);
                    //Toast.makeText( Act_modo_dosis.this,"Cargando "+name+"-"+ Long.toString(id) ,Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        bd.close();
        admin.close();
    }

    public String getCodTipo(int id){
        String codigo="";
        codigo = tiposArr.get(0).get(id);
        return codigo;
    }

    public String getCodAlimento(int id){
        String codigo="";
        codigo = alimentosArr.get(0).get(id);
        return codigo;
    }

    public void llenarSpinnerWithNoIndustralizados(int MySpinner, String Tipo)
    {   DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        Spinner spinner1 = (Spinner) findViewById(MySpinner);
        SQLiteDatabase bd = admin.getWritableDatabase();
        alimentosArr = admin.getAllNoIndustrializados(Tipo);

        List<String> list = new ArrayList<String>(alimentosArr.get(0).size());

        //Sort Data Alphabetical order
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Act_modo_dosis.this,android.R.layout.simple_spinner_item, alimentosArr.get(1));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
                String name = parent.getItemAtPosition(position).toString();
                idAlimento = getCodAlimento(position);
                //Toast.makeText( Act_modo_dosis.this,"Eligio "+name,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        bd.close();
        admin.close();
    }

    public  class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {}
        public void onNothingSelected(AdapterView parent) {}
    }

    /*-->---->---->---->---->---->---->---->-- Manejo tabla */
    public void NoIndAgregar_Click(View view){
        guardarAlimentoTemporal(view, R.id.spNoIndustrializado,R.id.txtNoIndGrms );
    }

    public void guardarAlimentoTemporal(View view, int SPINNER, int PORCION ){
        Spinner  spNoId_Nom = (Spinner)  findViewById(SPINNER);
        EditText txNoId_Gra = (EditText) findViewById(PORCION);
        if(txNoId_Gra.getText().toString().equals("")){txNoId_Gra.setText("0");}
        Double   NoId_Gra   = Double.parseDouble(txNoId_Gra.getText().toString());
        if ( NoId_Gra>0 && spNoId_Nom.getSelectedItem().toString().length()>=1 )
        {   Spinner spNoIndu = (Spinner) findViewById(SPINNER);
            int codigo= Integer.parseInt(idAlimento);
            DatosDB_SQLite admin = new DatosDB_SQLite(this , DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
            SQLiteDatabase bd = admin.getWritableDatabase();
            admin.guardarAlimentoTemporal(codigo, NoId_Gra);
            bd.close();
            admin.close();
            txNoId_Gra.setText("");
        }else
        {   Toast.makeText(this, "Digite Gramos", Toast.LENGTH_SHORT).show();
            txNoId_Gra.setText("");
        }

        refrescarTabla(true);

        /*
        // Gets linearlayout
        LinearLayout layout = (LinearLayout)findViewById(R.id.Contenedortabla);
        // Gets the layout params that will allow you to resize the layout
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
        // Changes the height and width to the specified *pixels*
        //params.height = 100;
        layout.setLayoutParams(params);
        GridView grid = (GridView)findViewById(R.id.gridview);
        ViewGroup.LayoutParams gparams = grid.getLayoutParams();
        params.height = gparams.height;
        layout.setLayoutParams(params);*/
    }

    /*--<----<----<----<----<----<----<----<-- Manejo tabla */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //returnToCaller(0.0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void calcular_click(View view) {
        int horaActual = 0;
        int sencibilidadActual;
        EditText txtGSA = (EditText) findViewById(R.id.txtGSA);
        EditText txtDE = (EditText) findViewById(R.id.txtDE);
        EditText txtDT  = (EditText) findViewById(R.id.txtDT);

        if (!txtGSA.getText().toString().equals("")) {
            double GSA_Act = Double.parseDouble(txtGSA.getText().toString());
            Time now = new Time();
            now.setToNow();
            horaActual = now.hour;
            DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
            SQLiteDatabase bd = admin.getReadableDatabase();
            String consultaSQL = "SELECT parametrosmedicos_sencibilidad FROM parametrosmedicos WHERE " +
                    "(parametrosmedicos_usuario=" + Sus_id + ") AND " +
                    "(parametrosmedicos_hora=" + Integer.toString(horaActual) + ")";
            Cursor fila = bd.rawQuery(consultaSQL, null);
            if (fila.moveToFirst()) {
                sencibilidadActual = Integer.parseInt(fila.getString(0));
                double resultado = ((GSA_Act - 110) / sencibilidadActual);
                resultado = (double) ((Math.rint(resultado * 10)) / 10);

                DOSIS_ESTIMADA_CORRECCCION = resultado;
                GSA_ACTUAL_CORRECCION = (int)(GSA_Act);

                EditText txtDOSIS = (EditText) findViewById(R.id.txtDosisEstimada);
                double dosisAli   = Double.parseDouble(txtDOSIS.getText().toString()) ;
                double dosisTotal = dosisAli + DOSIS_ESTIMADA_CORRECCCION;

                txtDE.setText(Double.toString(resultado));
                txtDT.setText(Double.toString(dosisTotal));

            }
            bd.close();
        } else
        {   txtGSA.setText("0");
        }

    }

    //Rutina datePicker
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case TABS_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                //return new DatePickerDialog(this, pickerListener, year, month,day);
                return new Dialog(Act_modo_dosis.this);
        }
        return null;
    }


    //*****************************************************************************//
    //**************************RUTINAS BLUETOOTH**********************************//
    //*****************************************************************************//

    private void CheckBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // If it isn't request to turn it on
        // List paired devices
        if(btAdapter==null) {
            Toast.makeText(Act_modo_dosis.this, "Bluetooth no soportado", Toast.LENGTH_SHORT).show();
        }
        else if(!bluetooth.isEnable()){
            Intent enableBtIntent = new Intent(btAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }else{
            if (!bluetooth.isConnected()) {
                Toast.makeText(Act_modo_dosis.this, "Iniciando conexión", Toast.LENGTH_SHORT).show();
                btAdapter.startDiscovery();
            }
            else{
                bluetooth.disconnect();
                btAdapter.cancelDiscovery();
                btDeviceList.clear();
                stopThread();
                Toast.makeText(Act_modo_dosis.this, "Equipo desconectado", Toast.LENGTH_SHORT).show();
                menuItemBt.setIcon(R.drawable.ic_bluetooth_white_48dp);
                //menuBt.findItem(R.id.actionBluetooth).setIcon(R.drawable.ic_bluetooth_white_48dp);
            }
        }
    }

    public void update(byte[] args) {
        int gramosL = 0, gramosH = 0;

        if(args.length>0) {

            gramosL = args[1];
            gramosH = args[0];
            gramos = 0;
            gramos = gramos + (gramosL & 1) * 1;
            gramos = gramos + (gramosL & 2);
            gramos = gramos + (gramosL & 4);
            gramos = gramos + (gramosL & 8);
            gramos = gramos + (gramosL & 16);
            gramos = gramos + (gramosL & 32);
            gramos = gramos + (gramosL & 64);
            gramos = gramos + (gramosL & 128);
            gramos = gramos + (gramosH & 1) / 1 * 256;
            gramos = gramos + (gramosH & 2) / 2 * 512;
            gramos = gramos + (gramosH & 4) / 4 * 1024;
            gramos = gramos + (gramosH & 8) / 8 * 2048;
            gramos = gramos + (gramosH & 16) / 16 * 4096;

            if (!((args[0] & 0xE0) == 0xE0))
                gramos = -gramos;

            runOnUiThread(new Runnable() {
                public void run() {
                    TextView textoPeso = (TextView) Act_modo_dosis.getInstace().findViewById(R.id.txtNoIndGrms);
                    textoPeso.setText(String.valueOf(gramos));
                }
            });
        }
    }

    private final BroadcastReceiver ActionFoundReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                btDeviceList.add(device);

                BluetoothUtils.ConnectThread innerObject = new  BluetoothUtils.ConnectThread(btDeviceList.get(0));
                innerObject.run();
                Toast.makeText(Act_modo_dosis.this, "Conectado con el equipo:\n" + device.getName() + ", " + device, Toast.LENGTH_SHORT).show();
                menuItemBt.setIcon(R.drawable.ic_bluetooth_grey600_48dp);
            }
        }
    };

    /* This routine is called when an activity completes.*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_BT) {
            CheckBTState();
        }
    }

    public void stopThread(){
        if(Thread.currentThread()!=null){
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
        //bluetooth.disconnect();
        //bluetooth.deactivate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (btAdapter != null) {
            btAdapter.cancelDiscovery();
            bluetooth.disconnect();
        }
        unregisterReceiver(ActionFoundReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_modo_dosis, menu);
        menuItemBt= menu.findItem(R.id.actionBluetooth);
        menuItemBt.setIcon(R.drawable.ic_bluetooth_white_48dp);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings: {
                showDialog(TABS_ID);
                break;
            }
            case R.id.actionBluetooth: {
                CheckBTState();
                break;
            }
        }
        return true;
    }







}




