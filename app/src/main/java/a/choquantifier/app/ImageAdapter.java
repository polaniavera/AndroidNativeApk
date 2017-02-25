package a.choquantifier.app;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.AndroidCharacter;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.GridView;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.util.Log;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class ImageAdapter extends BaseAdapter {
    private int us_id=0;
    private Context mContext;
    public int countTotal=0;
    public double CHO_Total=0;
    public double PORCION_Total=0;
    public double IG_Ponderado=0;
    public double DOSIS_Estimada=0;
    public double PROTEINA_TOTAL=0;
    public double GRASA_TOTAL=0;
    public double FIBRA_TOTAL=0;

    public ArrayList<String> lstCodigos;
    public ArrayList<String> lstNombres;
    public ArrayList<String> lstPorciones;
    public ArrayList<String> lstFac_CHO;
    public ArrayList<String> lstFac_FIB;
    public ArrayList<String> lstFac_IG;
    public ArrayList<String> lstProteina;
    public ArrayList<String> lstGrasa;

    public ImageAdapter( android.content.Context contexto, int usuario)
    {   mContext = contexto;
        lstCodigos = new ArrayList();
        lstNombres = new ArrayList();
        lstPorciones = new ArrayList();
        lstFac_CHO = new ArrayList();
        lstFac_FIB = new ArrayList();
        lstFac_IG = new ArrayList();
        lstProteina = new ArrayList();
        lstGrasa = new ArrayList();
        us_id = usuario;

        String consultaSQL = "SELECT * FROM temporalmenu";
        DatosDB_SQLite admin = new DatosDB_SQLite(contexto, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor fila = bd.rawQuery(consultaSQL,  null);
        countTotal=0;
        CHO_Total=0;
        IG_Ponderado=0;
        DOSIS_Estimada=0;
        PORCION_Total=0;
        PROTEINA_TOTAL=0;
        GRASA_TOTAL=0;

        if (fila.moveToFirst()) {
            do {
                double porcion = Double.parseDouble(fila.getString(2));
                double FacCHO = Double.parseDouble(fila.getString(3));
                double FacFIB = Double.parseDouble(fila.getString(4));
                double IG = Double.parseDouble(fila.getString(5));
                double FacGrasa = Double.parseDouble(fila.getString(6));
                double FacProteina = Double.parseDouble(fila.getString(7));
                double CHO = 0;
                double proteina = 0;
                double grasa = 0;
                double fibra = 0;
                if ((porcion * FacFIB) > 5.0) {
                    CHO = porcion * FacCHO - porcion * FacFIB;
                } else {
                    CHO = porcion * FacCHO;
                }
                proteina = porcion * FacProteina;
                grasa = porcion * FacGrasa;
                fibra = porcion * FacFIB;
                proteina = Math.rint(proteina * 10) / 10;
                grasa = Math.rint(grasa * 10) / 10;
                fibra = Math.rint(fibra * 10) / 10;
                CHO = Math.rint(CHO * 10) / 10;
                CHO_Total = CHO_Total + CHO;
                IG_Ponderado = IG_Ponderado + porcion * IG;
                PORCION_Total = PORCION_Total + porcion;
                PROTEINA_TOTAL = PROTEINA_TOTAL+proteina;
                GRASA_TOTAL = GRASA_TOTAL+grasa;
                FIBRA_TOTAL = FIBRA_TOTAL + fibra;

                lstCodigos.add(fila.getString(0));
                lstNombres.add(fila.getString(1));
                lstPorciones.add(fila.getString(2));
                lstFac_CHO.add(Double.toString(CHO));
                lstFac_FIB.add(Double.toString(fibra));
                lstFac_IG.add(fila.getString(5));
                lstGrasa.add(Double.toString(grasa));
                lstProteina.add(Double.toString(proteina));
                countTotal = countTotal + 1;
            } while (fila.moveToNext());
            IG_Ponderado = IG_Ponderado / PORCION_Total;
            IG_Ponderado = Math.rint(IG_Ponderado*10)/10;

            //Calcular DOSIS
            double SENSIBILIDAD = 0.0;
            double ratio = 0.0;
            int hora;
            Calendar calendario = new GregorianCalendar();
            hora = calendario.get(Calendar.HOUR_OF_DAY);
            SENSIBILIDAD=admin.getSencibilidad(us_id,hora);
            ratio=admin.getRatio(us_id,hora);
            DOSIS_Estimada=CHO_Total/ratio;
        }
        bd.close();
        admin.close();
    }

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return countTotal;
    }

    public Object getItem(int position) {
        return Integer.parseInt(lstCodigos.get(position));
    }

    public long getItemId(int position) {
        return 0;
    }
    // create a new ImageView for each item referenced by the Adapter

    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout Fila = new LinearLayout(mContext);
        TextView textoN = new TextView(mContext);
        TextView textoP = new TextView(mContext);
        TextView textoCHO = new TextView(mContext);
        TextView textoIG = new TextView(mContext);
        TextView textoProteina = new TextView(mContext);
        TextView textoGrasa = new TextView(mContext);
        Fila.setPadding(5, 0, 5, 0);
        Fila.setOrientation(LinearLayout.HORIZONTAL);

        textoN.setText(lstNombres.get(position).toString());
        textoP.setText(lstPorciones.get(position).toString());
        textoCHO.setText(lstFac_CHO.get(position).toString());
        textoIG.setText(lstFac_IG.get(position).toString());
        textoProteina.setText(lstProteina.get(position).toString());
        textoGrasa.setText(lstGrasa.get(position).toString());

        //textoN.setWidth(200);
        textoN.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,0.50f));
        int height_in_pixels = textoN.getLineHeight(); //approx height text
        textoN.setHeight(height_in_pixels);
        //textoP.setWidth(100);
        textoP.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,0.1f));
        //textoCHO.setWidth(70);
        textoCHO.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,0.1f));
        //textoIG.setWidth(70);
        textoIG.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,0.1f));
        //textoProteina.setWidth(100);
        textoProteina.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,0.1f));
        //textoGrasa.setWidth(70);
        textoGrasa.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT,0.1f));
        textoN.setTextAppearance(mContext, R.style.Colcodigo);
        textoP.setTextAppearance(mContext, R.style.Colcodigo);
        textoCHO.setTextAppearance(mContext, R.style.Colcodigo);
        textoIG.setTextAppearance(mContext, R.style.Colcodigo);
        textoProteina.setTextAppearance(mContext, R.style.Colcodigo);
        textoGrasa.setTextAppearance(mContext, R.style.Colcodigo);
        Fila.addView(textoN);
        Fila.addView(textoP);
        Fila.addView(textoCHO);
        Fila.addView(textoIG);
        Fila.addView(textoProteina);
        Fila.addView(textoGrasa);

        return Fila;
    }

}