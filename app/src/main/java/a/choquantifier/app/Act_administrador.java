package a.choquantifier.app;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.text.*;

public class Act_administrador extends ActionBarActivity {
    int us_id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_administrador);

        //OBTENER VARIABLE GLOBAL
        final GlobalData globalVariable = (GlobalData) getApplicationContext();
        us_id = globalVariable.getCodigo();

        final GridView gridview = (GridView) findViewById(R.id.gridviewuser);
        ImageAdapterUsuarios Datos = new ImageAdapterUsuarios(this,1);
        gridview.setAdapter(Datos);
        gridview.setOnItemClickListener
         (      new AdapterView.OnItemClickListener()
                {   public void onItemClick(AdapterView<?> parent, View v, int position, long id)
                    {   int codigo = Integer.parseInt(gridview.getAdapter().getItem(position).toString());
                        TextView txtInformacion = (TextView) findViewById(R.id.txtInformacion);
                        TextView txtID = (TextView) findViewById(R.id.txtIdUS);
                        DatosDB_SQLite admin = new DatosDB_SQLite(parent.getContext() , DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
                        SQLiteDatabase bd = admin.getWritableDatabase();
                        txtID.setText( Integer.toString(codigo) );
                        us_id = codigo;
                        txtInformacion.setText( Html.fromHtml( admin.getInformacionUsuario(us_id) ));
                        Button btnBorrar = (Button) findViewById(R.id.btnBorrarUS);
                        btnBorrar.setVisibility(View.VISIBLE);
                        bd.close();
                        admin.close();
                    }
                }
         );
    }

    public void BorrarUS_click(View view){
        Button btnBorrar = (Button) findViewById(R.id.btnBorrarUS);
        btnBorrar.setVisibility(View.GONE);
        LinearLayout Cajita  =  (LinearLayout) findViewById(R.id.LinLayBorrar);
        if (Cajita.getVisibility()==View.VISIBLE){
            Cajita.setVisibility(View.GONE);
        }
        else{
            Cajita.setVisibility(View.VISIBLE);
        }
    }

    public void BorrarUS_NO_click(View view)
    {   LinearLayout Cajita  =  (LinearLayout) findViewById(R.id.LinLayBorrar);
        Cajita.setVisibility(View.GONE);
        Button btnBorrar = (Button) findViewById(R.id.btnBorrarUS);
        btnBorrar.setVisibility(View.VISIBLE);
    }

    public void BorrarUS_SI_click(View view)
    {   LinearLayout Cajita  =  (LinearLayout) findViewById(R.id.LinLayBorrar);
        Cajita.setVisibility(View.GONE);
        TextView txtCodigo =  (TextView)     findViewById(R.id.txtIdUS);
        DatosDB_SQLite admin = new DatosDB_SQLite(this , DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getWritableDatabase();
        admin.deleteUsuario(us_id);
        bd.close();
        admin.close();
        final GridView gridview = (GridView) findViewById(R.id.gridviewuser);

        ImageAdapterUsuarios Datos = new ImageAdapterUsuarios(this,1);
        gridview.invalidateViews();
        gridview.setAdapter(Datos);
        TextView txtInformacion = (TextView) findViewById(R.id.txtInformacion);
        txtInformacion.setText( "Seleccione un usuario para ver su descripción." );
        Toast.makeText(this, "Usuario "+ txtCodigo.getText().toString() +"Borrado.", Toast.LENGTH_SHORT).show();
    }

    public void salirConf_click(View view){
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_administrador, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) { return true; }
        return super.onOptionsItemSelected(item);
    }

}