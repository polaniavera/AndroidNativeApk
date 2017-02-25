package a.choquantifier.app;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.text.format.Time;

public class Act_registro extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_registro);
        cargarSpinner(R.id.spinnerDiab,R.array.diabetis_array);
        cargarSpinner(R.id.spinnerSex,R.array.sexo_array);
        cargarSpinner(R.id.spinnerParent,R.array.parent_array);
        cargarSpinner(R.id.spinnerdia,R.array.dia_array);
        cargarSpinner(R.id.spinnermes,R.array.mes_array);
        cargarSpinner(R.id.spinneranio,R.array.anio_array);

        Spinner  spUsuario    = (Spinner)  findViewById(R.id.spinnerDiab);
        vistas(1);

        spUsuario.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener()
                                             {   @Override
                                                 public void onItemSelected(AdapterView parent, View view, int position, long id){ selectItem(position);}
                                                 private void selectItem(int position){}
                                                 @Override
                                                 public void onNothingSelected(AdapterView parent){}
                                             }
                                           );
    }


    public void ConfiAdd_Click(View view) {
        Spinner spUsuario = (Spinner) findViewById(R.id.spinnerDiab);
        Button btnAdd   = (Button) findViewById(R.id.btnAdd);
        String botonStr = btnAdd.getText().toString();

        if(botonStr.equals("Confirmar") )
        {   if (spUsuario.getSelectedItem().equals("Con Diabetes"))
            {   spUsuario.setEnabled(false);
                vistas(2);
            }
           if (spUsuario.getSelectedItem().equals("Sin Diabetes"))
            {   spUsuario.setEnabled(false);
                vistas(3);
            }
        }

        if(botonStr.equals("Calcular IMC") && (!spUsuario.getSelectedItem().equals("")) )
        {   calcularIMC();
        }

        if(botonStr.equals("Agregar") && (!spUsuario.getSelectedItem().equals("")) ) {
            calcularIMC();
            if (ValidarDatos())
            {   if (insertarUserDB()) {
                    Toast.makeText(this, "Guardado OK", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                Toast.makeText(this, "No Guardado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public boolean insertarUserDB(){
        boolean salida = true;
        String consultaSQL="";

        Spinner spUsuario = (Spinner) findViewById(R.id.spinnerDiab);
        EditText txtNombre = (EditText) findViewById(R.id.txtNombre);
        EditText txtNick = (EditText) findViewById(R.id.txtNick);
        EditText txtPass1 = (EditText) findViewById(R.id.txtPass1);
        EditText txtPass2 = (EditText) findViewById(R.id.txtPass2);
        Spinner spParentesco = (Spinner) findViewById(R.id.spinnerParent);
        Spinner spSexo = (Spinner) findViewById(R.id.spinnerSex);
        Spinner spDia = (Spinner) findViewById(R.id.spinnerdia);
        Spinner spMes = (Spinner) findViewById(R.id.spinnermes);
        Spinner spAnio = (Spinner) findViewById(R.id.spinneranio);
        EditText txtTalla = (EditText) findViewById(R.id.txtTalla);
        EditText txtKg = (EditText) findViewById(R.id.txtKg);
        EditText txtIMC= (EditText) findViewById(R.id.txtIMC);

        String d_a="3"; if(spUsuario.getSelectedItem().toString().equals("Con Diabetes")){d_a="1";}    if(spUsuario.getSelectedItem().toString().equals("Sin Diabetes")){d_a="2";}
        String d_b="\""+spParentesco.getSelectedItem().toString()+"\"";   if( spParentesco.getSelectedItem().toString().equals("") ){d_b="\""+"Yo"+"\"";}
        String d_c="\""+txtNombre.getText().toString()  +"\"";
        String d_d="\""+txtNick.getText().toString()    +"\"";
        String d_e="\""+txtPass1.getText().toString()   +"\"";
        String d_f="\""+spSexo.getSelectedItem().toString().substring(0,1)  +"\"";
        String d_g="\""+spAnio.getSelectedItem().toString()+"-"+spMes.getSelectedItem().toString().split("-")[0] +"-"+spDia.getSelectedItem().toString()+"\"";
        String d_h=txtTalla.getText().toString();
        String d_i=txtKg.getText().toString();
        String d_j=txtIMC.getText().toString();

        consultaSQL="INSERT INTO usuario(diabetico,parentesco,nombre,nick,password,sexo,fechanacimiento,talla,peso,imc) VALUES("+d_a+","+d_b+","+d_c+","+d_d+","+d_e+","+d_f+","+d_g+","+d_h+","+d_i+","+d_j+");";

        DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getWritableDatabase();
        bd.execSQL(consultaSQL);
        bd.close();
        admin.close();

        return salida;
    }


    public boolean ValidarDatos()
    {   boolean salida=true;
        String mensaje="Guardado";

        Spinner spUsuario = (Spinner) findViewById(R.id.spinnerDiab);
        EditText txtNombre = (EditText) findViewById(R.id.txtNombre);
        EditText txtNick = (EditText) findViewById(R.id.txtNick);
        EditText txtPass1 = (EditText) findViewById(R.id.txtPass1);
        EditText txtPass2 = (EditText) findViewById(R.id.txtPass2);
        Spinner spParentesco = (Spinner) findViewById(R.id.spinnerParent);
        Spinner spSexo = (Spinner) findViewById(R.id.spinnerSex);
        Spinner spDia = (Spinner) findViewById(R.id.spinnerdia);
        Spinner spMes = (Spinner) findViewById(R.id.spinnermes);
        Spinner spAnio = (Spinner) findViewById(R.id.spinneranio);
        EditText txtTalla = (EditText) findViewById(R.id.txtTalla);
        EditText txtKg = (EditText) findViewById(R.id.txtKg);
        EditText txtIMC= (EditText) findViewById(R.id.txtIMC);
        String fecha="";
        int dia =0;
        int mes =0;
        int anio=0;

        if(  spUsuario.getSelectedItem().toString().equals("")                                                                        && salida )   {   salida=false;   mensaje="Error seleccion usuario";        }
        if(  txtNombre.getText().toString().equals("")                                                                                && salida )   {   salida=false;   mensaje="Digite el nombre";               }
        if(  txtNick.getText().toString().equals("")                                                                                  && salida )   {   salida=false;   mensaje="Digite el Nick";                 }
        if(  txtPass1.getText().toString().equals("")                                                                                 && salida )   {   salida=false;   mensaje="Digite el Password1";            }
        if(  txtPass2.getText().toString().equals("")                                                                                 && salida )   {   salida=false;   mensaje="Digite el Password2";            }
        if( !txtPass1.getText().toString().equals(txtPass2.getText().toString())                                                      && salida )   {   salida=false;   mensaje="Password1 diferente Password2";  }
        if(  spParentesco.getSelectedItem().toString().equals("") && (spUsuario.getSelectedItem().toString().equals("No Diabetico"))  && salida )   {   salida=false;   mensaje="Elija parentesco";               }
        if(  spSexo.getSelectedItem().toString().equals("")                                                                           && salida )   {   salida=false;   mensaje="Elija Sexo";                     }
        if(  spDia.getSelectedItem().toString().equals("")                                                                            && salida )   {   salida=false;   mensaje="Elija Dia";                      }
        if(  spMes.getSelectedItem().toString().equals("")                                                                            && salida )   {   salida=false;   mensaje="Elija Mes";                      }
        if(  spAnio.getSelectedItem().toString().equals("")                                                                           && salida )   {   salida=false;   mensaje="Elija Año";                      }
        Time feachAct = new Time();
        feachAct.setToNow();

        if(!(spAnio.getSelectedItem().toString().equals("") || spMes.getSelectedItem().toString().split("-")[0].equals("") || spDia.getSelectedItem().toString().equals("")))
        {   anio= Integer.parseInt(spAnio.getSelectedItem().toString());
            mes = Integer.parseInt(spMes.getSelectedItem().toString().split("-")[0]);
            dia = Integer.parseInt(spDia.getSelectedItem().toString());
            fecha=   spAnio.getSelectedItem().toString()+"/"+spMes.getSelectedItem().toString().split("-")[0] +"/"+spDia.getSelectedItem().toString();
            if(salida && (anio*372+mes*31+dia)>(feachAct.year*372 +(feachAct.month+1)*31 + feachAct.monthDay) && salida){salida=false; mensaje="fecha "+fecha+" futuro";}
            if(salida && dia>30 && mes==4                  ){salida=false; mensaje="Error dias Abril";              }
            if(salida && dia>30 && mes==6                  ){salida=false; mensaje="Error dias Junio";              }
            if(salida && dia>30 && mes==9                  ){salida=false; mensaje="Error dias Septiembre";         }
            if(salida && dia>30 && mes==11                 ){salida=false; mensaje="Error dias Noviembre";          }
            if(salida && dia>29 && mes==2 && ((anio%4)==0) ){salida=false; mensaje="Error dias Febrero biciesto";   }
            if(salida && dia>28 && mes==2 && ((anio%4)!=0) ){salida=false; mensaje="Error dias Febrero no biciesto";}
        }
        if(  txtTalla.getText().toString().equals("0")                                                                                && salida )   {   salida=false;   mensaje="Digite su Talla";                }
        if(  txtKg.getText().toString().equals("0")                                                                                   && salida )   {   salida=false;   mensaje="Digite su Peso";                 }
        if(  txtIMC.getText().toString().equals("0")                                                                                  && salida )   {   salida=false;   mensaje="Error calculo IMC";              }
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        return salida;
    }

    public void cancel9_click(View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void calcularIMC()
    {   float talla, peso, imc;
        EditText txtTalla = (EditText) findViewById(R.id.txtTalla);
        EditText txtKg = (EditText) findViewById(R.id.txtKg);
        EditText txtIMC = (EditText) findViewById(R.id.txtIMC);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        if( txtTalla.getText().toString().equals("")) { txtTalla.setText("0");}
        if( txtKg.getText().toString().equals(""))    { txtKg.setText("0");}
        talla = Float.parseFloat    (   txtTalla.getText().toString()   );
        peso = Float.parseFloat     (   txtKg.getText().toString()      );
        if(talla>0 && peso>0)
        {   imc = peso / ((talla/100)*(talla/100));
            txtIMC.setText(Float.toString(imc));
            btnAdd.setText("Agregar");
        }
        else
        {   txtIMC.setText("0");
            btnAdd.setText("Agregar");
        }
    }

    public void cargarSpinner(int spinerId, int listadoResource) {
        Spinner spinner1 = (Spinner) findViewById(spinerId);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource( this, listadoResource, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }

    public void vistas(int vista)
    {   Spinner spUsuario = (Spinner) findViewById(R.id.spinnerDiab);
        Spinner spSexo = (Spinner) findViewById(R.id.spinnerSex);
        Spinner spParentesco = (Spinner) findViewById(R.id.spinnerParent);
        LinearLayout linLa = (LinearLayout) findViewById(R.id.linearFecha);
        Spinner spDia = (Spinner) findViewById(R.id.spinnerdia);
        Spinner spMes = (Spinner) findViewById(R.id.spinnermes);
        Spinner spAnio = (Spinner) findViewById(R.id.spinneranio);
        EditText txtNombre = (EditText) findViewById(R.id.txtNombre);
        EditText txtNick = (EditText) findViewById(R.id.txtNick);
        EditText txtPass1 = (EditText) findViewById(R.id.txtPass1);
        EditText txtPass2 = (EditText) findViewById(R.id.txtPass2);
        EditText txtTalla = (EditText) findViewById(R.id.txtTalla);
        EditText txtKg = (EditText) findViewById(R.id.txtKg);
        EditText txtIMC= (EditText) findViewById(R.id.txtIMC);
        TextView lbl1 = (TextView) findViewById(R.id.lbl1);
        TextView lbl1a = (TextView) findViewById(R.id.lbl1a);
        TextView lbl1b = (TextView) findViewById(R.id.lbl1b);
        TextView lbl1c = (TextView) findViewById(R.id.lbl1c);
        TextView lbl2 = (TextView) findViewById(R.id.lbl2);
        TextView lbl3 = (TextView) findViewById(R.id.lbl3);
        TextView lbl4 = (TextView) findViewById(R.id.lbl4);
        TextView lbl5 = (TextView) findViewById(R.id.lbl5);
        TextView lbl6 = (TextView) findViewById(R.id.lbl6);
        TextView lbl7 = (TextView) findViewById(R.id.lbl7);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        Button btnCan = (Button) findViewById(R.id.btnCan);

        LinearLayout LinLay1 = (LinearLayout) findViewById(R.id.LinLay1);
        LinearLayout LinLay2 = (LinearLayout) findViewById(R.id.LinLay2);
        LinearLayout LinLay3 = (LinearLayout) findViewById(R.id.LinLay3);
        LinearLayout LinLay4 = (LinearLayout) findViewById(R.id.LinLay4);
        LinearLayout LinLay5 = (LinearLayout) findViewById(R.id.LinLay5);
        LinearLayout LinLay6 = (LinearLayout) findViewById(R.id.LinLay6);
        LinearLayout LinLay7 = (LinearLayout) findViewById(R.id.LinLay7);
        LinearLayout LinLay8 = (LinearLayout) findViewById(R.id.LinLay8);
        LinearLayout LinLay9 = (LinearLayout) findViewById(R.id.LinLay9);
        LinearLayout LinLayA = (LinearLayout) findViewById(R.id.LinLayA);

        if(vista==1) {
            spSexo.setVisibility(View.GONE);
            spParentesco.setVisibility(View.GONE);
            spDia.setVisibility(View.GONE);
            spMes.setVisibility(View.GONE);
            spAnio.setVisibility(View.GONE);
            txtNombre.setVisibility(View.GONE);
            txtNick.setVisibility(View.GONE);
            txtPass1.setVisibility(View.GONE);
            txtPass2.setVisibility(View.GONE);
            lbl1a.setVisibility(View.GONE);
            lbl1b.setVisibility(View.GONE);
            lbl1c.setVisibility(View.GONE);
            txtTalla.setVisibility(View.GONE);
            txtKg.setVisibility(View.GONE);
            txtIMC.setVisibility(View.GONE);
            linLa.setVisibility(View.GONE);
            lbl1.setVisibility(View.GONE);
            lbl2.setVisibility(View.GONE);
            lbl3.setVisibility(View.GONE);
            lbl4.setVisibility(View.GONE);
            lbl5.setVisibility(View.GONE);
            lbl6.setVisibility(View.GONE);
            lbl7.setVisibility(View.GONE);
            btnCan.setVisibility(View.GONE);
            LinLay1.setVisibility(View.GONE);
            LinLay2.setVisibility(View.GONE);
            LinLay3.setVisibility(View.GONE);
            LinLay4.setVisibility(View.GONE);
            LinLay5.setVisibility(View.GONE);
            LinLay6.setVisibility(View.GONE);
            LinLay7.setVisibility(View.GONE);
            LinLay8.setVisibility(View.GONE);
            LinLay9.setVisibility(View.GONE);
            LinLayA.setVisibility(View.GONE);
            btnAdd.setText("Confirmar");
            spUsuario.setEnabled(true);
        }

        if(vista==2) {
            spSexo.setVisibility(View.VISIBLE);
            linLa.setVisibility(View.VISIBLE);
            spDia.setVisibility(View.VISIBLE);
            spMes.setVisibility(View.VISIBLE);
            spParentesco.setVisibility(View.VISIBLE);
            spAnio.setVisibility(View.VISIBLE);
            txtNombre.setVisibility(View.VISIBLE);
            txtNick.setVisibility(View.VISIBLE);
            txtPass1.setVisibility(View.VISIBLE);
            txtPass2.setVisibility(View.VISIBLE);
            txtTalla.setVisibility(View.VISIBLE);
            txtKg.setVisibility(View.VISIBLE);
            txtIMC.setVisibility(View.VISIBLE);
            lbl1.setVisibility(View.VISIBLE);
            lbl1a.setVisibility(View.VISIBLE);
            lbl1b.setVisibility(View.VISIBLE);
            lbl1c.setVisibility(View.VISIBLE);
            lbl2.setVisibility(View.VISIBLE);
            lbl3.setVisibility(View.VISIBLE);
            lbl4.setVisibility(View.VISIBLE);
            lbl5.setVisibility(View.VISIBLE);
            lbl6.setVisibility(View.VISIBLE);
            lbl7.setVisibility(View.VISIBLE);
            btnCan.setVisibility(View.VISIBLE);
            LinLay1.setVisibility(View.VISIBLE);
            LinLay2.setVisibility(View.VISIBLE);
            LinLay3.setVisibility(View.VISIBLE);
            LinLay4.setVisibility(View.VISIBLE);
            LinLay5.setVisibility(View.GONE);
            LinLay6.setVisibility(View.VISIBLE);
            LinLay7.setVisibility(View.VISIBLE);
            LinLay8.setVisibility(View.VISIBLE);
            LinLay9.setVisibility(View.VISIBLE);
            LinLayA.setVisibility(View.VISIBLE);
            btnAdd.setText("Calcular IMC");
        }

        if(vista==3) {
            spSexo.setVisibility(View.VISIBLE);
            spParentesco.setVisibility(View.VISIBLE);
            linLa.setVisibility(View.VISIBLE);
            spDia.setVisibility(View.VISIBLE);
            spMes.setVisibility(View.VISIBLE);
            spAnio.setVisibility(View.VISIBLE);
            txtNombre.setVisibility(View.VISIBLE);
            txtNick.setVisibility(View.VISIBLE);
            txtPass1.setVisibility(View.VISIBLE);
            txtPass2.setVisibility(View.VISIBLE);
            txtTalla.setVisibility(View.VISIBLE);
            txtKg.setVisibility(View.VISIBLE);
            txtIMC.setVisibility(View.VISIBLE);
            lbl1.setVisibility(View.VISIBLE);
            lbl1a.setVisibility(View.VISIBLE);
            lbl1b.setVisibility(View.VISIBLE);
            lbl1c.setVisibility(View.VISIBLE);
            lbl2.setVisibility(View.VISIBLE);
            lbl3.setVisibility(View.VISIBLE);
            lbl4.setVisibility(View.VISIBLE);
            lbl5.setVisibility(View.VISIBLE);
            lbl6.setVisibility(View.VISIBLE);
            lbl7.setVisibility(View.VISIBLE);
            btnCan.setVisibility(View.VISIBLE);
            LinLay1.setVisibility(View.VISIBLE);
            LinLay2.setVisibility(View.VISIBLE);
            LinLay3.setVisibility(View.VISIBLE);
            LinLay4.setVisibility(View.VISIBLE);
            LinLay5.setVisibility(View.VISIBLE);
            LinLay6.setVisibility(View.VISIBLE);
            LinLay7.setVisibility(View.VISIBLE);
            LinLay8.setVisibility(View.VISIBLE);
            LinLay9.setVisibility(View.VISIBLE);
            LinLayA.setVisibility(View.VISIBLE);
            btnAdd.setText("Calcular IMC");
        }
    }

    public  class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {}
        public void onNothingSelected(AdapterView parent) {}
    }

}
