package a.choquantifier.app;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

    public class Act_login extends Activity {
        private String nick;
        private int diabetico;
        private int codigo;
        private int talla;
        private int peso;
        private float imc;
        private String password;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.act_login);

            //No permitir que rote la pantalla
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            Button btnEntrar = (Button) findViewById(R.id.buttonEntrar);
            btnEntrar.getBackground().setAlpha(160);
            Button btnRegistrarse = (Button) findViewById(R.id.buttonRegistrarse);
            btnRegistrarse.getBackground().setAlpha(160);
            Button btnSalir = (Button) findViewById(R.id.buttonSalir);
            btnSalir.getBackground().setAlpha(160);
        }

    public void entrar_click(View view) {
        EditText txtNickname = (EditText) findViewById(R.id.txtNick);
        EditText txtPassword = (EditText) findViewById(R.id.txtPass);
        nick = txtNickname.getText().toString();
        password = txtPassword.getText().toString();

        //Setea las variables locales según el usuario ingresado
        setGlobales();

        if (diabetico == DatosDB_SQLite.USU_DIABETICO) {
            if (tieneParametrosMedicos(codigo))
            {   Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, Act_Diabetico_Tabs.class );
                //Intent i = new Intent(this, Act_activity_tabs.class );
                startActivity(i);
                //Intent i = new Intent(this, Act_diabetico.class);
                //i.putExtra("codigo_usuario", Integer.toString(codigo));
            }
            else
            {   Toast.makeText(this, "Configure parametros", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this, Act_parametrosMedicos.class);
                startActivity(i);
            }
        }
        if (diabetico == DatosDB_SQLite.USU_ADMIN) {
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Act_administrador.class);
            startActivity(i);
        }
        if (diabetico == DatosDB_SQLite.USU_NODIABETICO) {
            Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Act_No_Diabetico_Tabs.class );
            //Intent i = new Intent(this, Act_activity_tabs_nd.class );
            startActivity(i);
            //Intent j = new Intent(this, Act_diabetico.class);
            //j.putExtra("codigo_usuario", Integer.toString(codigo));
        }
        if (diabetico == DatosDB_SQLite.USU_NOEXISTE) {
            Toast.makeText(this, "No encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    public void setGlobales(){

        final GlobalData globalVariable = (GlobalData) getApplicationContext();

        String consultaSQL = "SELECT codigo,diabetico,talla,peso,imc FROM usuario WHERE ( nick='" + nick + "' AND password='" + password + "') ";
        DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor fila = bd.rawQuery(consultaSQL,  null);
        if (fila.moveToFirst()) {
            codigo = fila.getInt(0);
            diabetico = fila.getInt(1);
            talla = fila.getInt(2);
            peso = fila.getInt(3);
            imc = fila.getFloat(4);
        }else{
            diabetico = 0;
        }

        bd.close();
        admin.close();

        globalVariable.setUser(nick);
        globalVariable.setCodigo(codigo);
        globalVariable.setDiabetico(diabetico);
        globalVariable.setTalla(talla);
        globalVariable.setPeso(peso);
        globalVariable.setImc(imc);
    }

    public boolean tieneParametrosMedicos(int codigo) {
        boolean salida = false;
        String consultaSQL="";
        consultaSQL= "SELECT "+
        "COUNT(parametrosmedicos_usuario)    AS lineas,"+
        "SUM(parametrosmedicos_hora)         AS horas, "+
        "MIN(parametrosmedicos_sencibilidad) AS p1,    "+
        "MIN(parametrosmedicos_ratio)        AS p2     "+
        "FROM parametrosmedicos WHERE parametrosmedicos_usuario="+codigo;

        DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor fila = bd.rawQuery(consultaSQL,  null);
        if (fila.moveToFirst()) {
            int lineas=Integer.parseInt(fila.getString(0));
            if (lineas!=0)
            {   int horas = Integer.parseInt(fila.getString(1));
                int p1 = Integer.parseInt(fila.getString(2));
                int p2 = Integer.parseInt(fila.getString(3));
                if (horas == 276 && lineas == 24 && p1 != 0 && p2 != 0) {
                    salida = true;
                }
                else{
                    salida = false;
                }
            }
            else{
                int hrs;
                for(hrs=0;hrs<=23;++hrs){
                    String consulta="INSERT INTO parametrosmedicos VALUES ("+ Integer.toString(codigo) +","+Integer.toString(hrs)+",1,1)";
                    ejecutarSQL(consulta);
                }
                salida = false;
            }
        }
        bd.close();
        admin.close();
        return salida;
    }

    public void ejecutarSQL(String SQL)
    {   String consultaSQL=SQL;
        DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getWritableDatabase();
        bd.execSQL(consultaSQL);
        bd.close();
        admin.close();
    }

    public void registrar_click(View view) {
        Intent i = new Intent(this, Act_registro.class );
        startActivity(i);
    }

    public void acercade_click(View view) {
        Intent i = new Intent(this, Act_acerca_de.class );
        startActivity(i);
    }

    public void salirClickPrincipal(View view) {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
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