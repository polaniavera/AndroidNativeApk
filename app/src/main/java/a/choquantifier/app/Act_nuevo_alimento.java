package a.choquantifier.app;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

public class Act_nuevo_alimento extends ActionBarActivity {

    private PieChart mChart;
    private Typeface tf;
    private EditText txtAlimento, txtPorcion, txtCHO, txtFibra, txtGrasa, txtProteina;
    private Spinner spIG;
    private double porcion, cho, fibra, grasa, proteina;
    private Button btnAdd;
    private int diabetico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_nuevo_alimento);
        cargarSpinner(R.id.spinnerIG,R.array.ig_array);

        //OBTENER VARIABLE GLOBAL
        final GlobalData globalVariable = (GlobalData) getApplicationContext();
        diabetico = globalVariable.getDiabetico();

        txtAlimento = (EditText) findViewById(R.id.txtAlimento);
        txtPorcion  = (EditText) findViewById(R.id.txtTamPorcion);
        txtCHO      = (EditText) findViewById(R.id.txtTamCHO);
        txtFibra    = (EditText) findViewById(R.id.txtTamFibra);
        txtGrasa    = (EditText) findViewById(R.id.txtTamGrasa);
        txtProteina = (EditText) findViewById(R.id.txtTamProteina);
        spIG        = (Spinner)  findViewById(R.id.spinnerIG);
        btnAdd      = (Button) findViewById(R.id.btnAddAli);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

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

        txtAlimento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!txtAlimento.getText().toString().equals("")){
                    mChart.setCenterText(generateCenterSpannableText());
                }
            }
        });

        txtAlimento.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnAdd.setText("Composición");
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void afterTextChanged(Editable s){}
        });

        txtPorcion.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnAdd.setText("Composición");
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void afterTextChanged(Editable s){}
        });

        txtCHO.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnAdd.setText("Composición");
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void afterTextChanged(Editable s){}
        });

        txtFibra.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnAdd.setText("Composición");
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void afterTextChanged(Editable s){}
        });

        txtGrasa.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnAdd.setText("Composición");
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void afterTextChanged(Editable s){}
        });

        txtProteina.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnAdd.setText("Composición");
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            @Override
            public void afterTextChanged(Editable s){}
        });
    }

    public void cargarSpinner(int spinerId, int listadoResource) {
        Spinner spinner1 = (Spinner) findViewById(spinerId);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource( this, listadoResource, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());
    }

    public  class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {}
        public void onNothingSelected(AdapterView parent) {}
    }

    public void btnSalAli_Click(View view){
        finish();
    }

    public boolean confirmarValores(){
        boolean salir = true;
        if (txtAlimento.getText().toString().equals("") && salir) {
            salir = false;
            Toast.makeText(this, "Digite Alimento", Toast.LENGTH_SHORT).show();
        }
        if (txtPorcion.getText().toString().equals("") && salir) {
            salir = false;
            Toast.makeText(this, "Digite Porcion", Toast.LENGTH_SHORT).show();
        }
        if (txtCHO.getText().toString().equals("") && salir) {
            salir = false;
            Toast.makeText(this, "Digite CHO", Toast.LENGTH_SHORT).show();
        }
        if (txtFibra.getText().toString().equals("") && salir) {
            salir = false;
            Toast.makeText(this, "Digite Fibra", Toast.LENGTH_SHORT).show();
        }
        if (spIG.getSelectedItem().toString().equals("") && salir) {
            salir = false;
            Toast.makeText(this, "Elija IG", Toast.LENGTH_SHORT).show();
        }
        if (txtGrasa.getText().toString().equals("") && salir) {
            salir = false;
            Toast.makeText(this, "Digite Grasa", Toast.LENGTH_SHORT).show();
        }
        if (txtProteina.getText().toString().equals("") && salir) {
            salir = false;
            Toast.makeText(this, "Digite Proteína", Toast.LENGTH_SHORT).show();
        }
        if (salir) {
            porcion = Double.parseDouble(txtPorcion.getText().toString());
            cho = Double.parseDouble(txtCHO.getText().toString());
            fibra = Double.parseDouble(txtFibra.getText().toString());
            grasa = Double.parseDouble(txtGrasa.getText().toString());
            proteina = Double.parseDouble(txtProteina.getText().toString());
            if (((cho + fibra + grasa + proteina) > porcion) || (porcion == 0)) {
                salir = false;
                Toast.makeText(this, "Verifique valores ingresados", Toast.LENGTH_SHORT).show();
            }
        }
        return salir;
    }

    public void agregarAlimento(){

        double factor_cho   = cho  /porcion;
        double factor_fibra = fibra/porcion;
        double factor_grasa = grasa/porcion;
        double factor_proteina = proteina/porcion;
        int indice_glicemico =0;

        if(spIG.getSelectedItem().toString().equals("01-Bajo")) {indice_glicemico=30;}
        if(spIG.getSelectedItem().toString().equals("02-Medio")){indice_glicemico=45;}
        if(spIG.getSelectedItem().toString().equals("03-Alto")) {indice_glicemico=80;}

        DatosDB_SQLite admin = new DatosDB_SQLite(this , DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase bd = admin.getWritableDatabase();
        admin.guardarAlimento(txtAlimento.getText().toString(), factor_cho, factor_fibra, indice_glicemico, factor_grasa, factor_proteina);

        bd.close();
        admin.close();
        Toast.makeText(this, "Alimento agregado" , Toast.LENGTH_SHORT).show();
        finish();
        if (diabetico == DatosDB_SQLite.USU_DIABETICO) {
            Intent i = new Intent(this, Act_activity_tabs.class );
            startActivity(i);
        }else{
            //Intent i = new Intent(this, Act_activity_tabs_nd.class );
            Intent i = new Intent(this, Act_activity_tabs_nd.class );
            startActivity(i);
        }
    }

    private SpannableString generateCenterSpannableText() {

        String nombreAl = txtAlimento.getText().toString();
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


    public void ConfiAdd_Click(View view) {

        String botonStr = btnAdd.getText().toString();

        if(botonStr.equals("Composición") && confirmarValores())
                calcularComponentes();

        if(botonStr.equals("Agregar") && confirmarValores())
                agregarAlimento();
    }

    public void calcularComponentes(){

        ArrayList<PieEntry> yVals = new ArrayList<PieEntry>();
        float porCho, porFibra, porGrasa, porProteina, porNoDefinido;

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.

        porCho=(float) (cho/porcion)*100;
        porFibra=(float) (fibra/porcion)*100;
        porGrasa=(float) (grasa/porcion)*100;
        porProteina=(float) (proteina/porcion)*100;

        yVals.add(0, new PieEntry(porCho, "CHO"));
        yVals.add(1, new PieEntry(porFibra, "Fibra"));
        yVals.add(2, new PieEntry(porGrasa, "Grasa"));
        yVals.add(3, new PieEntry(porProteina, "Proteina"));

        double totalComponentes = cho + fibra + grasa + proteina;

        if(totalComponentes < porcion){
            double noDefinido = porcion - totalComponentes;
            porNoDefinido=(float) (noDefinido/porcion)*100;
            yVals.add(4, new PieEntry(porNoDefinido, "No Definido"));
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

        Button btnAdd = (Button) findViewById(R.id.btnAddAli);
        btnAdd.setText("Agregar");
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

}