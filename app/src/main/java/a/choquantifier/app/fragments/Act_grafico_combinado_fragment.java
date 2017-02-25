package a.choquantifier.app.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.CombinedChart.DrawOrder;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import a.choquantifier.app.Act_configuracion;
import a.choquantifier.app.DatosDB_SQLite;
import a.choquantifier.app.GlobalData;
import a.choquantifier.app.R;
import a.choquantifier.app.custom.HourAxisValueFormatter;
import a.choquantifier.app.custom.HourMarkerView;

public class Act_grafico_combinado_fragment extends Fragment {

    CombinedData data = new CombinedData();
    private CombinedChart mChart;
    private final int itemcount = 12;

    ArrayList<String> horaArr=new ArrayList<String>();
    ArrayList<Entry> glucoEntry=new ArrayList<Entry>();
    ArrayList<Entry> choEntry=new ArrayList<Entry>();
    ArrayList<BarEntry> boloAlimenticioEntry=new ArrayList<BarEntry>();
    ArrayList<BarEntry> boloCorreccionEntry=new ArrayList<BarEntry>();
    ArrayList<BarEntry> boloTotalEntry=new ArrayList<BarEntry>();
    ArrayList<BarEntry> bolosEntry=new ArrayList<BarEntry>();

    ArrayList<Float> glucoArr=new ArrayList<Float>();
    ArrayList<Float> choArr=new ArrayList<Float>();
    ArrayList<Float> boloAlimenticioArr=new ArrayList<Float>();
    ArrayList<Float> boloCorreccionArr=new ArrayList<Float>();
    ArrayList<Float> boloTotalArr=new ArrayList<Float>();

    private int year;
    private int month;
    private int day;

    long referenceTimestamp = 0;

    static final int DATE_PICKER_ID = 1111;
    private TextView Output;

    View view;
    static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.act_grafico_combinado, container, false);
        context = getActivity().getApplicationContext();
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //No permitir que rote la pantalla
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setHasOptionsMenu(true);

        mChart = (CombinedChart) view.findViewById(R.id.chart1);
        Output = (TextView) view.findViewById(R.id.Output);
        Description desc = new Description();
        desc.setText("");
        mChart.setDescription(desc);
        mChart.setBackgroundColor(Color.WHITE);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        //mChart.setYRange(0, 30, true);

        // draw bars behind lines
        mChart.setDrawOrder(new DrawOrder[] {
                DrawOrder.BAR, DrawOrder.BUBBLE, DrawOrder.CANDLE, DrawOrder.LINE, DrawOrder.SCATTER
        });

        mChart.setOnChartGestureListener(new OnChartGestureListener() {

            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture){

            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture){

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {
                // TODO Auto-generated method stub


            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2,
                                     float velocityX, float velocityY) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {
                // TODO Auto-generated method stub

            }
        });

        // listener for selecting and drawing
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                mChart.setHighlightPerTapEnabled(true);
                HourMarkerView myMarkerView= new HourMarkerView(context, R.layout.custom_marker_view, referenceTimestamp);
                mChart.setMarkerView(myMarkerView);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        //No permitir que rote la pantalla
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Get current date by calendar
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        // Show current date
        Output.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));

        //Trae datos de la BD
        getDataCharts();
        //Pasa los valores de los array de la BD al array<Entry>
        convertData();

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(86400);  //Rango eje X 86400 segundos (24 h)

        //Formatea eje x en HH:mm
        IAxisValueFormatter xAxisFormatter = new HourAxisValueFormatter(referenceTimestamp);
        xAxis.setValueFormatter(xAxisFormatter);

        data.setData(getDataLines());
        data.setData(getDataBolos());

        mChart.setData(data);
        mChart.invalidate();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
            // Get current date by calendar
            final Calendar c = Calendar.getInstance();
            year  = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day   = c.get(Calendar.DAY_OF_MONTH);

            setDataChart(year, month, day);
        }
    }

    private LineData getDataLines() {

        LineData d = new LineData();

        if(glucoEntry.size()==0){
            glucoEntry.add(new Entry(0, 0));
            choEntry.add(new Entry(0, 0));
            boloAlimenticioEntry.add(new BarEntry(0, 0));
            boloCorreccionEntry.add(new BarEntry(0, 0));
        }

        LineDataSet set = new LineDataSet(glucoEntry, "Glucosa");
        LineDataSet set2 = new LineDataSet(choEntry, "Carbohidratos");
        set.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        set.setLineWidth(2.5f);
        //set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        //set.setFillColor(Color.rgb(240, 238, 70));
        //set.setDrawCubic(true);
        set.setValueTextSize(12f);
        set.setDrawValues(false);
        //set.setValueTextColor(Color.rgb(240, 238, 70));
        set.setCircleColor(Color.BLACK);
        set.setDrawCircleHole(true);
        set.setDrawFilled(false);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        set2.setColor(ColorTemplate.VORDIPLOM_COLORS[3]);
        set2.setLineWidth(2.5f);
        set2.setCircleRadius(5f);
        set2.setCircleColor(Color.BLACK);
        set2.setDrawCircleHole(true);
        set2.setDrawFilled(false);
        set2.setValueTextSize(12f);
        set2.setDrawValues(false);
        set2.setAxisDependency(YAxis.AxisDependency.RIGHT);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_blue);
            set2.setFillDrawable(drawable);
        }
        else {
            set2.setFillColor(Color.BLUE);
        }

        d.addDataSet(set);
        d.addDataSet(set2);

        return d;
    }

    private BarData getDataBolos() {

        BarDataSet set1 = new BarDataSet(bolosEntry, "Bolo Alimenticio");
        set1.setStackLabels(new String[]{"Alimenticio", "Corrección"});
        set1.setColors(new int[] { R.color.verde, R.color.rojo}, context);
        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(set1);

        BarData cd = new BarData(sets);
        cd.setBarWidth(2000f);
        cd.setValueTextSize(12f);
        cd.setDrawValues(false);

        return cd;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_grafico_combinado, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionCalendar: {
                showDialog(DATE_PICKER_ID);
                //getActivity().showDialog(DATE_PICKER_ID);
                break;
            }
            case R.id.actionToggleLineValues: {
                for (IDataSet set : mChart.getData().getDataSets()) {
                    if (set instanceof LineDataSet)
                        set.setDrawValues(!set.isDrawValuesEnabled());
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleBarValues: {
                for (IDataSet set : mChart.getData().getDataSets()) {
                    if (set instanceof BarDataSet)
                        set.setDrawValues(!set.isDrawValuesEnabled());
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleAxis: {
                IDataSet set = mChart.getData().getDataSets().get(1);
                if(set.getAxisDependency()== YAxis.AxisDependency.LEFT){
                    set.setAxisDependency(YAxis.AxisDependency.RIGHT);
                    mChart.getAxisRight().setDrawLabels(true);
                }else{
                    set.setAxisDependency(YAxis.AxisDependency.LEFT);
                    mChart.getAxisRight().setDrawLabels(false);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionGridAxis: {
                if(!mChart.getAxisRight().isDrawGridLinesEnabled()){
                    mChart.getAxisRight().setDrawGridLines(true);
                    mChart.getXAxis().setDrawGridLines(true);
                }else{
                    mChart.getAxisRight().setDrawGridLines(false);
                    mChart.getXAxis().setDrawGridLines(false);
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                for (IDataSet set : mChart.getData().getDataSets()) {
                    if (set instanceof LineDataSet) {
                        LineDataSet set1 = (LineDataSet) set;
                        if (set1.isDrawCirclesEnabled())
                            set1.setDrawCircles(false);
                        else
                            set1.setDrawCircles(true);
                    }
                }
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleStepped: {
                for (IDataSet set : mChart.getData().getDataSets()) {
                    if (set instanceof LineDataSet) {
                        LineDataSet set1 = (LineDataSet) set;
                        set1.setMode(set1.getMode() == LineDataSet.Mode.STEPPED
                                ? LineDataSet.Mode.LINEAR
                                : LineDataSet.Mode.STEPPED);
                        mChart.invalidate();
                    }
                    //mChart.invalidate();
                    break;
                }
            }
            case R.id.actionToggleHorizontalCubic: {
                for (IDataSet set : mChart.getData().getDataSets()) {
                    if (set instanceof LineDataSet) {
                        LineDataSet set1 = (LineDataSet) set;
                        set1.setMode(set1.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER
                                ? LineDataSet.Mode.LINEAR
                                : LineDataSet.Mode.HORIZONTAL_BEZIER);
                        mChart.invalidate();
                    }
                    //mChart.invalidate();
                    break;
                }
            }
            case R.id.animateY: {
                mChart.animateY(3000, Easing.EasingOption.EaseInCubic);
                break;
            }
            case R.id.action_picker: {
                showDialog(DATE_PICKER_ID);
                //getActivity().showDialog(DATE_PICKER_ID);
                break;
            }
            case R.id.action_settings: {
                Intent j = new Intent(context, Act_configuracion.class);
                startActivity(j);
                break;
            }
        }
        return true;
    }

    private void getDataCharts(){

        glucoArr.clear();
        choArr.clear();
        boloAlimenticioArr.clear();
        boloCorreccionArr.clear();
        boloTotalArr.clear();
        horaArr.clear();
        glucoEntry.clear();
        choEntry.clear();
        boloAlimenticioEntry.clear();
        boloCorreccionEntry.clear();
        boloTotalEntry.clear();
        bolosEntry.clear();

        /////////************BASE DE DATOS*******************//////////////////
        //Obtener datos de la BD
        String fecha = "\"" + year + "-" + (month+1) + "-" + day + "\"";
        String hora = "\"" + android.text.format.DateFormat.format("HH:mm:ss", new Date()).toString() + "\"";

        //OBTENER VARIABLE GLOBAL
        final GlobalData globalVariable = (GlobalData) context;
        int codigo = globalVariable.getCodigo();

        String selectQuery =
                "SELECT historial_hora,historial_gs_actual,historial_cho_totales,historial_bolo_alimenticio,historial_bolo_correccion,historial_bolo_total " +
                        "FROM historial WHERE (historial_usuario=" + codigo + " AND historial_fecha=" + fecha + " )";

        DatosDB_SQLite admin = new DatosDB_SQLite(context, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                horaArr.add(cursor.getString(0));
                glucoArr.add(cursor.getFloat(1));
                choArr.add(cursor.getFloat(2));
                boloAlimenticioArr.add(cursor.getFloat(3));
                boloCorreccionArr.add(cursor.getFloat(4));
                boloTotalArr.add(cursor.getFloat(5));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        /////////************BASE DE DATOS*******************//////////////////
    }

    private void convertData(){

        float[] arrayHora = new float[horaArr.size()];
        float[] arrayGluco = new float[horaArr.size()];
        float[] arrayCho = new float[horaArr.size()];
        float[] arrayBoloAl = new float[horaArr.size()];
        float[] arrayBoloCo = new float[horaArr.size()];
        float[] arrayBoloTo = new float[horaArr.size()];

        //pasa las horas a int y los arrayList leidos de la BD a array
        for (int i = 0; i < horaArr.size(); ++i) {
            String time = horaArr.get(i); //hh:mm:ss
            String[] units = time.split(":"); //will break the string up into an array
            int hours = Integer.parseInt(units[0]); //first element
            int minutes = Integer.parseInt(units[1]); //second element
            int seconds = Integer.parseInt(units[2]);
            int duration = 3600 * hours + 60 * minutes + seconds; //add up our values
            arrayHora[i] = duration;
            arrayGluco[i] = glucoArr.get(i);
            arrayCho[i] = choArr.get(i);
            arrayBoloAl[i] = boloAlimenticioArr.get(i);
            arrayBoloCo[i] = boloCorreccionArr.get(i);
            arrayBoloTo[i] = boloTotalArr.get(i);
        }

        //Pasa los array a los Entry
        for (int i=0; i< horaArr.size(); ++i) {
            glucoEntry.add(new Entry(arrayHora[i], arrayGluco[i]));
            choEntry.add(new Entry(arrayHora[i], arrayCho[i]));
            boloAlimenticioEntry.add(new BarEntry(arrayHora[i], arrayBoloAl[i]));
            boloCorreccionEntry.add(new BarEntry(arrayHora[i], arrayBoloCo[i]));
            boloTotalEntry.add(new BarEntry(arrayHora[i], arrayBoloTo[i]));
            float bol1 = boloAlimenticioEntry.get(i).getY();
            float bol2 = boloCorreccionEntry.get(i).getY();
            float bol3 = boloTotalEntry.get(i).getY();
            bolosEntry.add(new BarEntry(arrayHora[i], new float[]{bol1, bol2}));
        }
    }

    //Rutina datePicker
    protected void showDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                DatePickerDialog newFragment = new DatePickerDialog(getActivity(), pickerListener, year, month, day);
                newFragment.show();
        }
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            setDataChart(year, month, day);
        }
    };

    public void setDataChart(int year, int month, int day){
        // Show selected date
        Output.setText(new StringBuilder().append(month + 1).append("-").append(day).append("-").append(year).append(" "));

        //Trae datos de la BD
        getDataCharts();
        //Pasa los valores de los array de la BD al array<Entry>
        convertData();

        // 3 items
        data.setData(getDataLines());
        data.setData(getDataBolos());

        mChart.setData(data);
        mChart.invalidate();
    }

}
