package a.choquantifier.app;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Act_parametrosMedicos extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_parametros_medicos);

        //OBTENER VARIABLE GLOBAL
        final GlobalData globalVariable = (GlobalData) getApplicationContext();
        int usid = globalVariable.getCodigo();

        EditText txt_ratio0 = (EditText) findViewById(R.id.ratio0);
        EditText txt_ratio1 = (EditText) findViewById(R.id.ratio1);
        EditText txt_ratio2 = (EditText) findViewById(R.id.ratio2);
        EditText txt_ratio3 = (EditText) findViewById(R.id.ratio3);
        EditText txt_ratio4 = (EditText) findViewById(R.id.ratio4);
        EditText txt_ratio5 = (EditText) findViewById(R.id.ratio5);
        EditText txt_ratio6 = (EditText) findViewById(R.id.ratio6);
        EditText txt_ratio7 = (EditText) findViewById(R.id.ratio7);
        EditText txt_ratio8 = (EditText) findViewById(R.id.ratio8);
        EditText txt_ratio9 = (EditText) findViewById(R.id.ratio9);
        EditText txt_ratio10 = (EditText) findViewById(R.id.ratio10);
        EditText txt_ratio11 = (EditText) findViewById(R.id.ratio11);
        EditText txt_ratio12 = (EditText) findViewById(R.id.ratio12);
        EditText txt_ratio13 = (EditText) findViewById(R.id.ratio13);
        EditText txt_ratio14 = (EditText) findViewById(R.id.ratio14);      EditText txt_ratio15 = (EditText) findViewById(R.id.ratio15);
        EditText txt_ratio16 = (EditText) findViewById(R.id.ratio16);      EditText txt_ratio17 = (EditText) findViewById(R.id.ratio17);
        EditText txt_ratio18 = (EditText) findViewById(R.id.ratio18);      EditText txt_ratio19 = (EditText) findViewById(R.id.ratio19);
        EditText txt_ratio20 = (EditText) findViewById(R.id.ratio20);      EditText txt_ratio21 = (EditText) findViewById(R.id.ratio21);
        EditText txt_ratio22 = (EditText) findViewById(R.id.ratio22);
        EditText txt_ratio23 = (EditText) findViewById(R.id.ratio23);
        EditText txt_sencibilidad0 = (EditText) findViewById(R.id.sencibilidad0);         EditText txt_sencibilidad1 = (EditText) findViewById(R.id.sencibilidad1);
        EditText txt_sencibilidad2 = (EditText) findViewById(R.id.sencibilidad2);         EditText txt_sencibilidad3 = (EditText) findViewById(R.id.sencibilidad3);
        EditText txt_sencibilidad4 = (EditText) findViewById(R.id.sencibilidad4);         EditText txt_sencibilidad5 = (EditText) findViewById(R.id.sencibilidad5);
        EditText txt_sencibilidad6 = (EditText) findViewById(R.id.sencibilidad6);         EditText txt_sencibilidad7 = (EditText) findViewById(R.id.sencibilidad7);
        EditText txt_sencibilidad8 = (EditText) findViewById(R.id.sencibilidad8);         EditText txt_sencibilidad9 = (EditText) findViewById(R.id.sencibilidad9);
        EditText txt_sencibilidad10 = (EditText) findViewById(R.id.sencibilidad10);       EditText txt_sencibilidad11 = (EditText) findViewById(R.id.sencibilidad11);
        EditText txt_sencibilidad12 = (EditText) findViewById(R.id.sencibilidad12);       EditText txt_sencibilidad13 = (EditText) findViewById(R.id.sencibilidad13);
        EditText txt_sencibilidad14 = (EditText) findViewById(R.id.sencibilidad14);       EditText txt_sencibilidad15 = (EditText) findViewById(R.id.sencibilidad15);
        EditText txt_sencibilidad16 = (EditText) findViewById(R.id.sencibilidad16);       EditText txt_sencibilidad17 = (EditText) findViewById(R.id.sencibilidad17);
        EditText txt_sencibilidad18 = (EditText) findViewById(R.id.sencibilidad18);       EditText txt_sencibilidad19 = (EditText) findViewById(R.id.sencibilidad19);
        EditText txt_sencibilidad20 = (EditText) findViewById(R.id.sencibilidad20);       EditText txt_sencibilidad21 = (EditText) findViewById(R.id.sencibilidad21);
        EditText txt_sencibilidad22 = (EditText) findViewById(R.id.sencibilidad22);       EditText txt_sencibilidad23 = (EditText) findViewById(R.id.sencibilidad23);

        int hora=0;
        getRSUH(usid,hora);     txt_ratio0.setText(getRSUH_RAT);    txt_sencibilidad0.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);    txt_ratio1.setText(getRSUH_RAT);     txt_sencibilidad1.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);    txt_ratio2.setText(getRSUH_RAT);     txt_sencibilidad2.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);    txt_ratio3.setText(getRSUH_RAT);     txt_sencibilidad3.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);    txt_ratio4.setText(getRSUH_RAT);     txt_sencibilidad4.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);    txt_ratio5.setText(getRSUH_RAT);     txt_sencibilidad5.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);    txt_ratio6.setText(getRSUH_RAT);     txt_sencibilidad6.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);    txt_ratio7.setText(getRSUH_RAT);     txt_sencibilidad7.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);    txt_ratio8.setText(getRSUH_RAT);     txt_sencibilidad8.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);    txt_ratio9.setText(getRSUH_RAT);     txt_sencibilidad9.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio10.setText(getRSUH_RAT);    txt_sencibilidad10.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio11.setText(getRSUH_RAT);    txt_sencibilidad11.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio12.setText(getRSUH_RAT);    txt_sencibilidad12.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio13.setText(getRSUH_RAT);    txt_sencibilidad13.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio14.setText(getRSUH_RAT);    txt_sencibilidad14.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio15.setText(getRSUH_RAT);    txt_sencibilidad15.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio16.setText(getRSUH_RAT);    txt_sencibilidad16.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio17.setText(getRSUH_RAT);    txt_sencibilidad17.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio18.setText(getRSUH_RAT);    txt_sencibilidad18.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio19.setText(getRSUH_RAT);    txt_sencibilidad19.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio20.setText(getRSUH_RAT);    txt_sencibilidad20.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio21.setText(getRSUH_RAT);    txt_sencibilidad21.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio22.setText(getRSUH_RAT);    txt_sencibilidad22.setText(getRSUH_SEN); ++hora;
        getRSUH(usid,hora);   txt_ratio23.setText(getRSUH_RAT);    txt_sencibilidad23.setText(getRSUH_SEN); ++hora;

    }

    public void GuardarPM_click (View view)
    {

        //OBTENER VARIABLE GLOBAL
        final GlobalData globalVariable = (GlobalData) getApplicationContext();
        int usid = globalVariable.getCodigo();

        EditText txt_ratio0 = (EditText) findViewById(R.id.ratio0);        EditText txt_ratio1 = (EditText) findViewById(R.id.ratio1);
        EditText txt_ratio2 = (EditText) findViewById(R.id.ratio2);        EditText txt_ratio3 = (EditText) findViewById(R.id.ratio3);
        EditText txt_ratio4 = (EditText) findViewById(R.id.ratio4);        EditText txt_ratio5 = (EditText) findViewById(R.id.ratio5);
        EditText txt_ratio6 = (EditText) findViewById(R.id.ratio6);        EditText txt_ratio7 = (EditText) findViewById(R.id.ratio7);
        EditText txt_ratio8 = (EditText) findViewById(R.id.ratio8);        EditText txt_ratio9 = (EditText) findViewById(R.id.ratio9);
        EditText txt_ratio10 = (EditText) findViewById(R.id.ratio10);      EditText txt_ratio11 = (EditText) findViewById(R.id.ratio11);
        EditText txt_ratio12 = (EditText) findViewById(R.id.ratio12);      EditText txt_ratio13 = (EditText) findViewById(R.id.ratio13);
        EditText txt_ratio14 = (EditText) findViewById(R.id.ratio14);      EditText txt_ratio15 = (EditText) findViewById(R.id.ratio15);
        EditText txt_ratio16 = (EditText) findViewById(R.id.ratio16);      EditText txt_ratio17 = (EditText) findViewById(R.id.ratio17);
        EditText txt_ratio18 = (EditText) findViewById(R.id.ratio18);      EditText txt_ratio19 = (EditText) findViewById(R.id.ratio19);
        EditText txt_ratio20 = (EditText) findViewById(R.id.ratio20);      EditText txt_ratio21 = (EditText) findViewById(R.id.ratio21);
        EditText txt_ratio22 = (EditText) findViewById(R.id.ratio22);      EditText txt_ratio23 = (EditText) findViewById(R.id.ratio23);
        EditText txt_sencibilidad0 = (EditText) findViewById(R.id.sencibilidad0);         EditText txt_sencibilidad1 = (EditText) findViewById(R.id.sencibilidad1);
        EditText txt_sencibilidad2 = (EditText) findViewById(R.id.sencibilidad2);         EditText txt_sencibilidad3 = (EditText) findViewById(R.id.sencibilidad3);
        EditText txt_sencibilidad4 = (EditText) findViewById(R.id.sencibilidad4);         EditText txt_sencibilidad5 = (EditText) findViewById(R.id.sencibilidad5);
        EditText txt_sencibilidad6 = (EditText) findViewById(R.id.sencibilidad6);         EditText txt_sencibilidad7 = (EditText) findViewById(R.id.sencibilidad7);
        EditText txt_sencibilidad8 = (EditText) findViewById(R.id.sencibilidad8);         EditText txt_sencibilidad9 = (EditText) findViewById(R.id.sencibilidad9);
        EditText txt_sencibilidad10 = (EditText) findViewById(R.id.sencibilidad10);       EditText txt_sencibilidad11 = (EditText) findViewById(R.id.sencibilidad11);
        EditText txt_sencibilidad12 = (EditText) findViewById(R.id.sencibilidad12);       EditText txt_sencibilidad13 = (EditText) findViewById(R.id.sencibilidad13);
        EditText txt_sencibilidad14 = (EditText) findViewById(R.id.sencibilidad14);       EditText txt_sencibilidad15 = (EditText) findViewById(R.id.sencibilidad15);
        EditText txt_sencibilidad16 = (EditText) findViewById(R.id.sencibilidad16);       EditText txt_sencibilidad17 = (EditText) findViewById(R.id.sencibilidad17);
        EditText txt_sencibilidad18 = (EditText) findViewById(R.id.sencibilidad18);       EditText txt_sencibilidad19 = (EditText) findViewById(R.id.sencibilidad19);
        EditText txt_sencibilidad20 = (EditText) findViewById(R.id.sencibilidad20);       EditText txt_sencibilidad21 = (EditText) findViewById(R.id.sencibilidad21);
        EditText txt_sencibilidad22 = (EditText) findViewById(R.id.sencibilidad22);       EditText txt_sencibilidad23 = (EditText) findViewById(R.id.sencibilidad23);

        DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getWritableDatabase();

        if(txt_ratio0.getText().toString().equals("")){txt_ratio0.setText("0");}
        if(txt_ratio1.getText().toString().equals("")){txt_ratio1.setText("0");}
        if(txt_ratio2.getText().toString().equals("")){txt_ratio2.setText("0");}
        if(txt_ratio3.getText().toString().equals("")){txt_ratio3.setText("0");}
        if(txt_ratio4.getText().toString().equals("")){txt_ratio4.setText("0");}
        if(txt_ratio5.getText().toString().equals("")){txt_ratio5.setText("0");}
        if(txt_ratio6.getText().toString().equals("")){txt_ratio6.setText("0");}
        if(txt_ratio7.getText().toString().equals("")){txt_ratio7.setText("0");}
        if(txt_ratio8.getText().toString().equals("")){txt_ratio8.setText("0");}
        if(txt_ratio9.getText().toString().equals("")){txt_ratio9.setText("0");}
        if(txt_ratio10.getText().toString().equals("")){txt_ratio10.setText("0");}
        if(txt_ratio11.getText().toString().equals("")){txt_ratio11.setText("0");}
        if(txt_ratio12.getText().toString().equals("")){txt_ratio12.setText("0");}
        if(txt_ratio13.getText().toString().equals("")){txt_ratio13.setText("0");}
        if(txt_ratio14.getText().toString().equals("")){txt_ratio14.setText("0");}
        if(txt_ratio15.getText().toString().equals("")){txt_ratio15.setText("0");}
        if(txt_ratio16.getText().toString().equals("")){txt_ratio16.setText("0");}
        if(txt_ratio17.getText().toString().equals("")){txt_ratio17.setText("0");}
        if(txt_ratio18.getText().toString().equals("")){txt_ratio18.setText("0");}
        if(txt_ratio19.getText().toString().equals("")){txt_ratio19.setText("0");}
        if(txt_ratio20.getText().toString().equals("")){txt_ratio20.setText("0");}
        if(txt_ratio21.getText().toString().equals("")){txt_ratio21.setText("0");}
        if(txt_ratio22.getText().toString().equals("")){txt_ratio22.setText("0");}
        if(txt_ratio23.getText().toString().equals("")){txt_ratio23.setText("0");}

        if(txt_sencibilidad0.getText().toString().equals("")){txt_sencibilidad0.setText("0");}
        if(txt_sencibilidad1.getText().toString().equals("")){txt_sencibilidad1.setText("0");}
        if(txt_sencibilidad2.getText().toString().equals("")){txt_sencibilidad2.setText("0");}
        if(txt_sencibilidad3.getText().toString().equals("")){txt_sencibilidad3.setText("0");}
        if(txt_sencibilidad4.getText().toString().equals("")){txt_sencibilidad4.setText("0");}
        if(txt_sencibilidad5.getText().toString().equals("")){txt_sencibilidad5.setText("0");}
        if(txt_sencibilidad6.getText().toString().equals("")){txt_sencibilidad6.setText("0");}
        if(txt_sencibilidad7.getText().toString().equals("")){txt_sencibilidad7.setText("0");}
        if(txt_sencibilidad8.getText().toString().equals("")){txt_sencibilidad8.setText("0");}
        if(txt_sencibilidad9.getText().toString().equals("")){txt_sencibilidad9.setText("0");}
        if(txt_sencibilidad10.getText().toString().equals("")){txt_sencibilidad10.setText("0");}
        if(txt_sencibilidad11.getText().toString().equals("")){txt_sencibilidad11.setText("0");}
        if(txt_sencibilidad12.getText().toString().equals("")){txt_sencibilidad12.setText("0");}
        if(txt_sencibilidad13.getText().toString().equals("")){txt_sencibilidad13.setText("0");}
        if(txt_sencibilidad14.getText().toString().equals("")){txt_sencibilidad14.setText("0");}
        if(txt_sencibilidad15.getText().toString().equals("")){txt_sencibilidad15.setText("0");}
        if(txt_sencibilidad16.getText().toString().equals("")){txt_sencibilidad16.setText("0");}
        if(txt_sencibilidad17.getText().toString().equals("")){txt_sencibilidad17.setText("0");}
        if(txt_sencibilidad18.getText().toString().equals("")){txt_sencibilidad18.setText("0");}
        if(txt_sencibilidad19.getText().toString().equals("")){txt_sencibilidad19.setText("0");}
        if(txt_sencibilidad20.getText().toString().equals("")){txt_sencibilidad20.setText("0");}
        if(txt_sencibilidad21.getText().toString().equals("")){txt_sencibilidad21.setText("0");}
        if(txt_sencibilidad22.getText().toString().equals("")){txt_sencibilidad22.setText("0");}
        if(txt_sencibilidad23.getText().toString().equals("")){txt_sencibilidad23.setText("0");}

        // public void setParametrosMedicosusuarioHora(int usuario, int hora, int ratio, double sensibilidad) {
        admin.setParametrosMedicosusuarioHora(usid,0 , Integer.parseInt( txt_ratio0.getText().toString()) ,Double.parseDouble(txt_sencibilidad0.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,1 , Integer.parseInt( txt_ratio1.getText().toString()) ,Double.parseDouble(txt_sencibilidad1.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,2 , Integer.parseInt( txt_ratio2.getText().toString()) ,Double.parseDouble(txt_sencibilidad2.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,3 , Integer.parseInt( txt_ratio3.getText().toString()) ,Double.parseDouble(txt_sencibilidad3.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,4 , Integer.parseInt( txt_ratio4.getText().toString()) ,Double.parseDouble(txt_sencibilidad4.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,5 , Integer.parseInt( txt_ratio5.getText().toString()) ,Double.parseDouble(txt_sencibilidad5.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,6 , Integer.parseInt( txt_ratio6.getText().toString()) ,Double.parseDouble(txt_sencibilidad6.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,7 , Integer.parseInt( txt_ratio7.getText().toString()) ,Double.parseDouble(txt_sencibilidad7.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,8 , Integer.parseInt( txt_ratio8.getText().toString()) ,Double.parseDouble(txt_sencibilidad8.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,9 , Integer.parseInt( txt_ratio9.getText().toString()) ,Double.parseDouble(txt_sencibilidad9.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,10, Integer.parseInt(txt_ratio10.getText().toString()) ,Double.parseDouble(txt_sencibilidad10.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,11, Integer.parseInt(txt_ratio11.getText().toString()) ,Double.parseDouble(txt_sencibilidad11.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,12, Integer.parseInt(txt_ratio12.getText().toString()) ,Double.parseDouble(txt_sencibilidad12.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,13, Integer.parseInt(txt_ratio13.getText().toString()) ,Double.parseDouble(txt_sencibilidad13.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,14, Integer.parseInt(txt_ratio14.getText().toString()) ,Double.parseDouble(txt_sencibilidad14.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,15, Integer.parseInt(txt_ratio15.getText().toString()) ,Double.parseDouble(txt_sencibilidad15.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,16, Integer.parseInt(txt_ratio16.getText().toString()) ,Double.parseDouble(txt_sencibilidad16.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,17, Integer.parseInt(txt_ratio17.getText().toString()) ,Double.parseDouble(txt_sencibilidad17.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,18, Integer.parseInt(txt_ratio18.getText().toString()) ,Double.parseDouble(txt_sencibilidad18.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,19, Integer.parseInt(txt_ratio19.getText().toString()) ,Double.parseDouble(txt_sencibilidad19.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,20, Integer.parseInt(txt_ratio20.getText().toString()) ,Double.parseDouble(txt_sencibilidad20.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,21, Integer.parseInt(txt_ratio21.getText().toString()) ,Double.parseDouble(txt_sencibilidad21.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,22, Integer.parseInt(txt_ratio22.getText().toString()) ,Double.parseDouble(txt_sencibilidad22.getText().toString()));
        admin.setParametrosMedicosusuarioHora(usid,23, Integer.parseInt(txt_ratio23.getText().toString()) ,Double.parseDouble(txt_sencibilidad23.getText().toString()));

        bd.close();
        admin.close();

        finish();
    }

    // ------------->>>>>>>>>>>>> COMUNICACION CON LA BASE DE DATOS
    String getRSUH_RAT="";
    String getRSUH_SEN="";
    public void getRSUH(int usuario, int hora)
    {   DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getWritableDatabase();
        admin.getParametrosMedicosusuarioHora(usuario,hora);
        getRSUH_RAT=admin.AAA;
        getRSUH_SEN=admin.BBB;
        bd.close();
        admin.close();
    }

    // <<<<<<<<<<<<<<<<----------------- COMUNICACION CON LA BASE DE DATOS

    public void copiar(View view, int numero)
    {   EditText txt_ratio0 = (EditText) findViewById(R.id.ratio0);
        EditText txt_ratio1 = (EditText) findViewById(R.id.ratio1);
        EditText txt_ratio2 = (EditText) findViewById(R.id.ratio2);
        EditText txt_ratio3 = (EditText) findViewById(R.id.ratio3);
        EditText txt_ratio4 = (EditText) findViewById(R.id.ratio4);
        EditText txt_ratio5 = (EditText) findViewById(R.id.ratio5);
        EditText txt_ratio6 = (EditText) findViewById(R.id.ratio6);
        EditText txt_ratio7 = (EditText) findViewById(R.id.ratio7);
        EditText txt_ratio8 = (EditText) findViewById(R.id.ratio8);
        EditText txt_ratio9 = (EditText) findViewById(R.id.ratio9);
        EditText txt_ratio10 = (EditText) findViewById(R.id.ratio10);
        EditText txt_ratio11 = (EditText) findViewById(R.id.ratio11);
        EditText txt_ratio12 = (EditText) findViewById(R.id.ratio12);
        EditText txt_ratio13 = (EditText) findViewById(R.id.ratio13);
        EditText txt_ratio14 = (EditText) findViewById(R.id.ratio14);
        EditText txt_ratio15 = (EditText) findViewById(R.id.ratio15);
        EditText txt_ratio16 = (EditText) findViewById(R.id.ratio16);
        EditText txt_ratio17 = (EditText) findViewById(R.id.ratio17);
        EditText txt_ratio18 = (EditText) findViewById(R.id.ratio18);
        EditText txt_ratio19 = (EditText) findViewById(R.id.ratio19);
        EditText txt_ratio20 = (EditText) findViewById(R.id.ratio20);
        EditText txt_ratio21 = (EditText) findViewById(R.id.ratio21);
        EditText txt_ratio22 = (EditText) findViewById(R.id.ratio22);
        EditText txt_ratio23 = (EditText) findViewById(R.id.ratio23);

        EditText txt_sencibilidad0 = (EditText) findViewById(R.id.sencibilidad0);
        EditText txt_sencibilidad1 = (EditText) findViewById(R.id.sencibilidad1);
        EditText txt_sencibilidad2 = (EditText) findViewById(R.id.sencibilidad2);
        EditText txt_sencibilidad3 = (EditText) findViewById(R.id.sencibilidad3);
        EditText txt_sencibilidad4 = (EditText) findViewById(R.id.sencibilidad4);
        EditText txt_sencibilidad5 = (EditText) findViewById(R.id.sencibilidad5);
        EditText txt_sencibilidad6 = (EditText) findViewById(R.id.sencibilidad6);
        EditText txt_sencibilidad7 = (EditText) findViewById(R.id.sencibilidad7);
        EditText txt_sencibilidad8 = (EditText) findViewById(R.id.sencibilidad8);
        EditText txt_sencibilidad9 = (EditText) findViewById(R.id.sencibilidad9);
        EditText txt_sencibilidad10 = (EditText) findViewById(R.id.sencibilidad10);
        EditText txt_sencibilidad11 = (EditText) findViewById(R.id.sencibilidad11);
        EditText txt_sencibilidad12 = (EditText) findViewById(R.id.sencibilidad12);
        EditText txt_sencibilidad13 = (EditText) findViewById(R.id.sencibilidad13);
        EditText txt_sencibilidad14 = (EditText) findViewById(R.id.sencibilidad14);
        EditText txt_sencibilidad15 = (EditText) findViewById(R.id.sencibilidad15);
        EditText txt_sencibilidad16 = (EditText) findViewById(R.id.sencibilidad16);
        EditText txt_sencibilidad17 = (EditText) findViewById(R.id.sencibilidad17);
        EditText txt_sencibilidad18 = (EditText) findViewById(R.id.sencibilidad18);
        EditText txt_sencibilidad19 = (EditText) findViewById(R.id.sencibilidad19);
        EditText txt_sencibilidad20 = (EditText) findViewById(R.id.sencibilidad20);
        EditText txt_sencibilidad21 = (EditText) findViewById(R.id.sencibilidad21);
        EditText txt_sencibilidad22 = (EditText) findViewById(R.id.sencibilidad22);
        EditText txt_sencibilidad23 = (EditText) findViewById(R.id.sencibilidad23);

        if(numero==  0){   txt_ratio0.setText(txt_ratio23.getText());    txt_sencibilidad0.setText(txt_sencibilidad23.getText());}
        if(numero==  1){   txt_ratio1.setText(txt_ratio0.getText());     txt_sencibilidad1.setText(txt_sencibilidad0.getText());}
        if(numero==  2){   txt_ratio2.setText(txt_ratio1.getText());     txt_sencibilidad2.setText(txt_sencibilidad1.getText());}
        if(numero==  3){   txt_ratio3.setText(txt_ratio2.getText());     txt_sencibilidad3.setText(txt_sencibilidad2.getText());}
        if(numero==  4){   txt_ratio4.setText(txt_ratio3.getText());     txt_sencibilidad4.setText(txt_sencibilidad3.getText());}
        if(numero==  5){   txt_ratio5.setText(txt_ratio4.getText());     txt_sencibilidad5.setText(txt_sencibilidad4.getText());}
        if(numero==  6){   txt_ratio6.setText(txt_ratio5.getText());     txt_sencibilidad6.setText(txt_sencibilidad5.getText());}
        if(numero==  7){   txt_ratio7.setText(txt_ratio6.getText());     txt_sencibilidad7.setText(txt_sencibilidad6.getText());}
        if(numero==  8){   txt_ratio8.setText(txt_ratio7.getText());     txt_sencibilidad8.setText(txt_sencibilidad7.getText());}
        if(numero==  9){   txt_ratio9.setText(txt_ratio8.getText());     txt_sencibilidad9.setText(txt_sencibilidad8.getText());}
        if(numero== 10){   txt_ratio10.setText(txt_ratio9.getText());    txt_sencibilidad10.setText(txt_sencibilidad9.getText());}
        if(numero== 11){   txt_ratio11.setText(txt_ratio10.getText());   txt_sencibilidad11.setText(txt_sencibilidad10.getText());}
        if(numero== 12){   txt_ratio12.setText(txt_ratio11.getText());   txt_sencibilidad12.setText(txt_sencibilidad11.getText());}
        if(numero== 13){   txt_ratio13.setText(txt_ratio12.getText());   txt_sencibilidad13.setText(txt_sencibilidad12.getText());}
        if(numero== 14){   txt_ratio14.setText(txt_ratio13.getText());   txt_sencibilidad14.setText(txt_sencibilidad13.getText());}
        if(numero== 15){   txt_ratio15.setText(txt_ratio14.getText());   txt_sencibilidad15.setText(txt_sencibilidad14.getText());}
        if(numero== 16){   txt_ratio16.setText(txt_ratio15.getText());   txt_sencibilidad16.setText(txt_sencibilidad15.getText());}
        if(numero== 17){   txt_ratio17.setText(txt_ratio16.getText());   txt_sencibilidad17.setText(txt_sencibilidad16.getText());}
        if(numero== 18){   txt_ratio18.setText(txt_ratio17.getText());   txt_sencibilidad18.setText(txt_sencibilidad17.getText());}
        if(numero== 19){   txt_ratio19.setText(txt_ratio18.getText());   txt_sencibilidad19.setText(txt_sencibilidad18.getText());}
        if(numero== 20){   txt_ratio20.setText(txt_ratio19.getText());   txt_sencibilidad20.setText(txt_sencibilidad19.getText());}
        if(numero== 21){   txt_ratio21.setText(txt_ratio20.getText());   txt_sencibilidad21.setText(txt_sencibilidad20.getText());}
        if(numero== 22){   txt_ratio22.setText(txt_ratio21.getText());   txt_sencibilidad22.setText(txt_sencibilidad21.getText());}
        if(numero== 23){   txt_ratio23.setText(txt_ratio22.getText());   txt_sencibilidad23.setText(txt_sencibilidad22.getText());}

    }

    public void CancelarPM_click(View view){    finish();}

    public void copiar_click0(View view)     {    copiar(view, 0 );}
    public void copiar_click1(View view)     {    copiar(view, 1 );}
    public void copiar_click2(View view)     {    copiar(view, 2 );}
    public void copiar_click3(View view)     {    copiar(view, 3 );}
    public void copiar_click4(View view)     {    copiar(view, 4 );}
    public void copiar_click5(View view)     {    copiar(view, 5 );}
    public void copiar_click6(View view)     {    copiar(view, 6 );}
    public void copiar_click7(View view)     {    copiar(view, 7 );}
    public void copiar_click8(View view)     {    copiar(view, 8 );}
    public void copiar_click9(View view)     {    copiar(view, 9 );}
    public void copiar_click10(View view)    {    copiar(view,10 );}
    public void copiar_click11(View view)    {    copiar(view,11 );}
    public void copiar_click12(View view)    {    copiar(view,12 );}
    public void copiar_click13(View view)    {    copiar(view,13 );}
    public void copiar_click14(View view)    {    copiar(view,14 );}
    public void copiar_click15(View view)    {    copiar(view,15 );}
    public void copiar_click16(View view)    {    copiar(view,16 );}
    public void copiar_click17(View view)    {    copiar(view,17 );}
    public void copiar_click18(View view)    {    copiar(view,18 );}
    public void copiar_click19(View view)    {    copiar(view,19 );}
    public void copiar_click20(View view)    {    copiar(view,20 );}
    public void copiar_click21(View view)    {    copiar(view,21 );}
    public void copiar_click22(View view)    {    copiar(view,22 );}
    public void copiar_click23(View view)    {    copiar(view,23 );}

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {   getMenuInflater().inflate(R.menu.menu_parametros_medicos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {   int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}