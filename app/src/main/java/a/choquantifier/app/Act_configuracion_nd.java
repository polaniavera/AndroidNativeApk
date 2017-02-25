package a.choquantifier.app;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Act_configuracion_nd extends ActionBarActivity {
    int USUARIO=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_configuracion_nd);

        //OBTENER VARIABLE GLOBAL
        final GlobalData globalVariable = (GlobalData) getApplicationContext();
        USUARIO = globalVariable.getCodigo();
    }

    public void ejecutarSQL(String SQL)
    {   String consultaSQL=SQL;
        DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getWritableDatabase();
        bd.execSQL(consultaSQL);
        bd.close();
        admin.close();
    }

    // CAMBIO DE PARAMETROS USUARIO //
    public void Conf_ParUser_NoDiab_click(View view) {
        LinearLayout Cajita  =  (LinearLayout) findViewById(R.id.LinLayBoxParametros);
        LinearLayout Cajitax =  (LinearLayout) findViewById(R.id.LinLayBoxPassword);
        EditText txtPara1=  (EditText)     findViewById(R.id.txtTalla);
        EditText     txtPara2=  (EditText)     findViewById(R.id.txtKg);
        EditText     txtPara3=  (EditText)     findViewById(R.id.txtIMC);
        Button btnCalG =  (Button)       findViewById(R.id.button42);
        btnCalG.setText("Calcular IMC");
        if (Cajita.getVisibility()==View.VISIBLE)
        {   Cajita.setVisibility(View.GONE);
        }
        else
        {   Cajita.setVisibility(View.VISIBLE);
            Cajitax.setVisibility(View.GONE);
            txtPara1.setText("");
            txtPara2.setText("");
            txtPara3.setText("");
        }
    }

    public void Conf_ParaUsu_clickCan(View view) {
        LinearLayout Cajita  =  (LinearLayout) findViewById(R.id.LinLayBoxParametros);
        EditText     txtPara1=  (EditText)     findViewById(R.id.txtTalla);
        EditText     txtPara2=  (EditText)     findViewById(R.id.txtKg);
        EditText     txtPara3=  (EditText)     findViewById(R.id.txtIMC);
        Cajita.setVisibility(View.GONE);
        txtPara1.setText("");
        txtPara2.setText("");
        txtPara3.setText("");
    }

    public void Conf_ParaUsu_clickCam(View view) {
        LinearLayout Cajita  =  (LinearLayout) findViewById(R.id.LinLayBoxParametros);
        EditText     txtPara1=  (EditText)     findViewById(R.id.txtTalla);
        EditText     txtPara2=  (EditText)     findViewById(R.id.txtKg);
        EditText     txtPara3=  (EditText)     findViewById(R.id.txtIMC);
        Button       btnCalG =  (Button)       findViewById(R.id.button42);

        String par_Tall = txtPara1.getText().toString();
        String par_Peso = txtPara2.getText().toString();
        String par_IMC  = txtPara3.getText().toString();

        if (par_Tall.length()>0 && par_Peso.length()>0)
        {   double Talla = Double.parseDouble(par_Tall);
            double Peso  = Double.parseDouble(par_Peso);
            double IMC = 0;

            if ((Talla>0) && (Peso>0))
            {   IMC = (Peso/((Talla/100)*(Talla/100)))*100;
                IMC = Math.round(IMC);
                IMC =IMC /100;
                txtPara3.setText( Double.toString(IMC) );

                if(btnCalG.getText().toString().equals("Guardar"))
                {   DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
                    SQLiteDatabase bd = admin.getWritableDatabase();
                    admin.setPesoTalla(USUARIO, par_Tall, par_Peso, par_IMC);
                    bd.close();
                    admin.close();
                    Cajita.setVisibility(View.GONE);
                    txtPara1.setText("");
                    txtPara2.setText("");
                    txtPara3.setText("");
                    Toast.makeText(this, "IMC almacenado exitosamente", Toast.LENGTH_SHORT).show();
                }
                else {
                    btnCalG.setText("Guardar");
                }
            }

        }else
        {
            Toast.makeText(this, "Digite valores de talla y peso", Toast.LENGTH_SHORT).show();
        }
    }

    // CAMBIO DE PASSWORD //
    public void Conf_Password_NoDiab_click(View view) {
        LinearLayout Cajita  =  (LinearLayout) findViewById(R.id.LinLayBoxParametros);
        LinearLayout Cajitax =  (LinearLayout) findViewById(R.id.LinLayBoxPassword);
        EditText     txtPass1=  (EditText)     findViewById(R.id.txtPass1);
        EditText     txtPass2=  (EditText)     findViewById(R.id.txtPass2);

        if (Cajitax.getVisibility()==View.VISIBLE)
        {   Cajitax.setVisibility(View.GONE);
        }
        else
        {   Cajitax.setVisibility(View.VISIBLE);
            Cajita.setVisibility(View.GONE);
            txtPass1.setText("");
            txtPass2.setText("");
        }
    }

    public void Conf_Password_clickCan(View view) {
        LinearLayout Cajita  =  (LinearLayout) findViewById(R.id.LinLayBoxPassword);
        EditText     txtPass1=  (EditText)     findViewById(R.id.txtPass1);
        EditText     txtPass2=  (EditText)     findViewById(R.id.txtPass2);
        Cajita.setVisibility(View.GONE);
        txtPass1.setText("");
        txtPass2.setText("");
    }

    public void Conf_Password_clickCam(View view) {
        LinearLayout Cajita  =  (LinearLayout) findViewById(R.id.LinLayBoxPassword);
        EditText     txtPass1=  (EditText)     findViewById(R.id.txtPass1);
        EditText     txtPass2=  (EditText)     findViewById(R.id.txtPass2);
        String password1 = txtPass1.getText().toString();
        String password2 = txtPass2.getText().toString();

        if( password1.equals(password2) && (password1.length()>0) )
        {   DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
            SQLiteDatabase bd = admin.getWritableDatabase();
            admin.setPassword(USUARIO,password2);
            bd.close();
            admin.close();
            Cajita.setVisibility(View.GONE);
            txtPass1.setText("");
            txtPass2.setText("");
            Toast.makeText(this, "Password cambiado exitosamente", Toast.LENGTH_SHORT).show();
        }
        else
        {   txtPass1.setText("");
            txtPass2.setText("");
            Toast.makeText(this, "Los passwords no coinciden o estan en blanco", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancel7_click(View view) {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

