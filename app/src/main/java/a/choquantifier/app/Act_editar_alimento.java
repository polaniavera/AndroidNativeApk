package a.choquantifier.app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Act_editar_alimento extends ActionBarActivity {

    private PieChart mChart;
    private Typeface tf;
    private EditText txtFactorCHO, txtFactorGrasa, txtFactorProteina, txtFactorFibra, txtIg;
    private Spinner spinnerAlimento;
    private float choFactor, fibraFactor, grasaFactor, proteinaFactor, indiceGlicemio;
    private String nombreAl;
    private Button btnAdd;
    private int diabetico;
    ArrayList<ArrayList<String>> tiposArr = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> alimentosArr = new ArrayList<ArrayList<String>>();
    String idAlimento="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_editar_alimento);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        spinnerAlimento = (Spinner) findViewById(R.id.spNoIndustrializado);
        mChart = (PieChart) findViewById(R.id.chart1);
        mChart.setUsePercentValues(true);
        // no description text
        Description desc = new Description();
        desc.setText("");
        mChart.setDescription(desc);
        //mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);
        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        mChart.setCenterText(generateCenterSpannableText());
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);
        mChart.setHoleRadius(30f);
        mChart.setTransparentCircleRadius(40f);
        mChart.setDrawCenterText(true);
        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        setInitialData();

        llenarSpinnerWithTipos(R.id.spTipoAlimento);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                mChart.setHighlightPerTapEnabled(true);
                if (e == null)
                    return;
                Log.i("VAL SELECTED", "Value: " + e.getY() + ", xIndex: " + e.getX() + ", DataSet index: " + e.getData());
            }

            @Override
            public void onNothingSelected() {
                Log.i("PieChart", "nothing selected");
            }
        });

    }

    private SpannableString generateCenterSpannableText() {

        String nombreAl ="";
        //String nombreAl = txtAlimento.getText().toString();
        if(nombreAl.equals(""))
            nombreAl="Alimento";

        SpannableString s = new SpannableString(nombreAl);

        //SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        //s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        //s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        //s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        //s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        //s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        //s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    public void calcularComponentes(){

        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();
        float porCho, porFibra, porGrasa, porProteina, porNoDefinido;

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.

        txtFactorCHO      = (EditText) findViewById(R.id.txtFactorCHO);
        txtIg             = (EditText) findViewById(R.id.txtIg);
        txtFactorProteina = (EditText) findViewById(R.id.txtFactorProteina);
        txtFactorGrasa    = (EditText) findViewById(R.id.txtFactorGrasa);
        txtFactorFibra    = (EditText) findViewById(R.id.txtFactorFibra);

        txtFactorCHO.setText      (Double.toString(choFactor));
        txtIg.setText             (Double.toString(indiceGlicemio));
        txtFactorProteina.setText (Double.toString(proteinaFactor));
        txtFactorGrasa.setText    (Double.toString(grasaFactor));
        txtFactorFibra.setText    (Double.toString(fibraFactor));

        porCho= choFactor*100;
        porFibra= fibraFactor*100;
        porGrasa= grasaFactor*100;
        porProteina= proteinaFactor*100;

        yVals.add(0, new PieEntry(porCho, "CHO"));
        yVals.add(1, new PieEntry(porFibra, "Fibra"));
        yVals.add(2, new PieEntry(porGrasa, "Grasa"));
        yVals.add(3, new PieEntry(porProteina, "Proteina"));

        float totalComponentes = porCho + porFibra + porGrasa + porProteina;

        if(totalComponentes < 100){
            float noDefinido = 100 - totalComponentes;
            //porNoDefinido=(float) (noDefinido/porcion)*100;
            yVals.add(4, new PieEntry(noDefinido, "No Definido"));
        }

        PieDataSet dataSet = new PieDataSet(yVals, "Composición");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(10f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData();
        data.setDataSet(dataSet);

        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        //mChart.highlightValues(null);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        mChart.invalidate();

    }

    public void setInitialData(){
        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        yVals.add(0, new PieEntry(100, "No Definido"));
        PieDataSet dataSet = new PieDataSet(yVals, "Composición");

        // add a lot of colors
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        dataSet.setColors(colors);

        PieData data = new PieData();
        data.setDataSet(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);
        mChart.setData(data);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        mChart.invalidate();
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Act_editar_alimento.this,android.R.layout.simple_spinner_item, tiposArr.get(1));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
                String name = parent.getItemAtPosition(position).toString();
                String Tipo = getCodTipo(position);
                if(!Tipo.equals(""))
                {   llenarSpinnerWithNoIndustralizados(R.id.spNoIndustrializado,Tipo);
                    Toast.makeText( Act_editar_alimento.this,"Cargando "+name+"-"+ Long.toString(id) ,Toast.LENGTH_SHORT).show();
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
        bd.close();
        admin.close();
        List<String> list = new ArrayList<String>(alimentosArr.get(0).size());
        //Sort Data Alphabetical order
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareTo(rhs);
            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Act_editar_alimento.this,android.R.layout.simple_spinner_item, alimentosArr.get(1));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position,long id) {
                String name = parent.getItemAtPosition(position).toString();
                idAlimento = getCodAlimento(position);
                int codigo= Integer.parseInt(idAlimento);
                getDatoAlimento(codigo);
                Toast.makeText( Act_editar_alimento.this,"Eligio "+name,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nuevo_alimento, menu);
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

    public void getDatoAlimento(int codigo){

        DatosDB_SQLite admin = new DatosDB_SQLite(getApplicationContext() , DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase db = admin.getWritableDatabase();

        String selectQuery =
                "SELECT nombre, factorcho, factorfibra, indiceglicemico, factorgrasa, factorproteina " +
                        "FROM alimentos " +
                        "WHERE (codigo=" + codigo + " )";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                nombreAl = cursor.getString(0);
                choFactor = cursor.getFloat(1);
                fibraFactor = cursor.getFloat(2);
                indiceGlicemio = cursor.getFloat(3);
                grasaFactor = cursor.getFloat(4);
                proteinaFactor = cursor.getFloat(5);
            } while(cursor.moveToNext());
        }

        db.close();
        admin.close();

        calcularComponentes();
    }

}