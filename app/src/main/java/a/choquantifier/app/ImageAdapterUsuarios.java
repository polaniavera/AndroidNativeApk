package a.choquantifier.app;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;

public class ImageAdapterUsuarios extends BaseAdapter {
    private Context mContext;
    public int countTotal=0;
    public  ArrayList<String> lstCodigos;
    public  ArrayList<String> lstNombres;

    public ImageAdapterUsuarios(Context contexto, int numero)
    {   mContext = contexto;
        lstCodigos = new ArrayList();
        lstNombres = new ArrayList();

        String consultaSQL = "SELECT codigo, nombre FROM usuario WHERE codigo>1";
        DatosDB_SQLite admin = new DatosDB_SQLite(contexto, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getReadableDatabase();
        Cursor fila = bd.rawQuery(consultaSQL,  null);
        countTotal=0;

        if (fila.moveToFirst())
        {   do {
                lstCodigos.add(fila.getString(0));
                lstNombres.add(fila.getString(1));
                countTotal=countTotal+1;
            }while (fila.moveToNext());
        }

        bd.close();
        admin.close();

    }

    public ImageAdapterUsuarios(Context c) {
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
        TextView textoC = new TextView(mContext);
        TextView textoN = new TextView(mContext);
        Fila.setOrientation(LinearLayout.HORIZONTAL);
        Fila.setPadding(10,10,10,10);

        textoC.setText(lstCodigos.get(position).toString());
        textoN.setText(lstNombres.get(position).toString());

        textoC.setWidth(100);
        textoN.setWidth(450);
        textoC.setTextAppearance(mContext, R.style.Colcodigo);
        textoN.setTextAppearance(mContext, R.style.Colcodigo);
        Fila.addView(textoC);
        Fila.addView(textoN);
        return Fila;
    }

}