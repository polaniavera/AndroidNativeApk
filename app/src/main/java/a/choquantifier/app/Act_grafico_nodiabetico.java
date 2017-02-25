package a.choquantifier.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.LimitLine.LimitLabelPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import a.choquantifier.app.custom.HourAxisValueFormatter;
import a.choquantifier.app.custom.HourMarkerView;

/**
 * Created by LENOVO on 30/11/2016.
 */

public class Act_grafico_nodiabetico extends AppCompatActivity{

    private LineChart mChartNd;
    private TextView tvX, tvY;
    long referenceTimestamp = 0;

    ArrayList<String> horaArr=new ArrayList<String>();
    ArrayList<Entry> choEntry=new ArrayList<Entry>();
    ArrayList<Float> choArr=new ArrayList<Float>();

    private TextView Output;
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.act_grafico_nodiabetico);

        Output = (TextView) findViewById(R.id.Output);
        mChartNd = (LineChart) findViewById(R.id.chartNd);

        // no description text
        Description desc = new Description();
        desc.setText("");
        mChartNd.setDescription(desc);
        mChartNd.setNoDataText("La fecha seleccionada no tiene datos almacenados");

        // enable touch gestures
        mChartNd.setTouchEnabled(true);

        // enable scaling and dragging
        mChartNd.setDragEnabled(true);
        mChartNd.setScaleEnabled(true);
        // mChartNd.setScaleXEnabled(true);
        // mChartNd.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChartNd.setPinchZoom(true);

        // set an alternative background color
        // mChartNd.setBackgroundColor(Color.GRAY);

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


        // x-axis limit line
        LimitLine llYAxis = new LimitLine(100f, "CHO Recomendados");
        llYAxis.setLineWidth(4f);
        llYAxis.enableDashedLine(10f, 10f, 0f);
        llYAxis.setLabelPosition(LimitLabelPosition.RIGHT_BOTTOM);
        llYAxis.setTextSize(10f);

        YAxis rightAxis = mChartNd.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        YAxis leftAxis = mChartNd.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.addLimitLine(llYAxis);

        XAxis xAxis = mChartNd.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(86400);  //Rango eje X 86400 segundos (24 h)

        //Formatea eje x en HH:mm
        IAxisValueFormatter xAxisFormatter = new HourAxisValueFormatter(referenceTimestamp);
        xAxis.setValueFormatter(xAxisFormatter);
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line

        Typeface tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mChartNd.getAxisRight().setEnabled(false);

        //mChartNd.getViewPortHandler().setMaximumScaleY(2f);
        //mChartNd.getViewPortHandler().setMaximumScaleX(2f);

        mChartNd.animateY(1500, Easing.EasingOption.EaseInCubic);

        // get the legend (only possible after setting data)
        Legend l = mChartNd.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(LegendForm.LINE);

        // // dont forget to refresh the drawing
        // mChartNd.invalidate();

        // listener for selecting and drawing
        mChartNd.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                mChartNd.setHighlightPerTapEnabled(true);
                HourMarkerView myMarkerView= new HourMarkerView(getApplicationContext(), R.layout.custom_marker_view, referenceTimestamp);
                mChartNd.setMarkerView(myMarkerView);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        mChartNd.setData(getDataLines());
        mChartNd.invalidate();
    }

    private LineData getDataLines() {

        LineData d = new LineData();

        if(choEntry.size()==0){
            choEntry.add(new Entry(0, 0));
        }

        LineDataSet set = new LineDataSet(choEntry, "Carbohidratos");
        set.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        set.setLineWidth(2.5f);
        //set.setCircleColor(Color.rgb(240, 238, 70));
        set.setCircleRadius(5f);
        //set.setFillColor(Color.rgb(240, 238, 70));
        //set.setDrawCubic(true);
        //set.setDrawValues(true);
        set.setValueTextSize(12f);
        //set.setValueTextColor(Color.rgb(240, 238, 70));
        set.setCircleColor(Color.BLACK);
        set.setDrawCircleHole(true);
        set.setDrawFilled(true);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);

        // set the line to be drawn like this "- - - - - -"
        //set.enableDashedLine(10f, 5f, 0f);
        //set.enableDashedHighlightLine(10f, 5f, 0f);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue);
            set.setFillDrawable(drawable);
        }
        else {
            set.setFillColor(Color.BLUE);
        }

        d.addDataSet(set);

        return d;
    }

    private void getDataCharts(){

        choArr.clear();
        horaArr.clear();
        choEntry.clear();

        /////////************BASE DE DATOS*******************//////////////////
        //Obtener datos de la BD

        //String fecha = "\"" + year + "-" + (month+1) + "-" + day + "\"";
        String fecha = "\"" + android.text.format.DateFormat.format("yyyy-MM-dd", new java.util.Date()).toString() + "\"";
        String hora = "\"" + android.text.format.DateFormat.format("HH:mm:ss", new java.util.Date()).toString() + "\"";

        //OBTENER VARIABLE GLOBAL
        final GlobalData globalVariable = (GlobalData) getApplicationContext();
        int codigo = globalVariable.getCodigo();

        String selectQuery =
                "SELECT historial_hora,historial_gs_actual,historial_cho_totales,historial_bolo_alimenticio,historial_bolo_correccion,historial_bolo_total " +
                        "FROM historial " +
                        "WHERE (historial_usuario=" + codigo + " AND  historial_fecha=" + fecha + " )";

        DatosDB_SQLite admin = new DatosDB_SQLite(this, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                horaArr.add(cursor.getString(0));
                choArr.add(cursor.getFloat(2));
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        /////////************BASE DE DATOS*******************//////////////////
    }

    private void convertData(){

        float[] arrayHora = new float[horaArr.size()];
        float[] arrayCho = new float[horaArr.size()];

        //pasa las horas a int y los arrayList leidos de la BD a array
        for (int i = 0; i < horaArr.size(); ++i) {
            String time = horaArr.get(i); //hh:mm:ss
            String[] units = time.split(":"); //will break the string up into an array
            int hours = Integer.parseInt(units[0]); //first element
            int minutes = Integer.parseInt(units[1]); //second element
            int seconds = Integer.parseInt(units[2]);
            int duration = 3600 * hours + 60 * minutes + seconds; //add up our values
            arrayHora[i] = duration;
            arrayCho[i] = choArr.get(i);
        }

        //Pasa los array a los Entry
        for (int i=0; i< horaArr.size(); ++i) {
            choEntry.add(new Entry(arrayHora[i], arrayCho[i]));
        }
    }

    //Rutina datePicker
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            Output.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

            getDataCharts();
            convertData();

            mChartNd.setData(getDataLines());
            mChartNd.invalidate();
        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_grafico_nd, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionCalendar: {
                showDialog(DATE_PICKER_ID);
                break;
            }
            case R.id.actionToggleValues: {
                List<ILineDataSet> sets = mChartNd.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setDrawValues(!set.isDrawValuesEnabled());
                }

                mChartNd.invalidate();
                break;
            }
            case R.id.actionGridAxis: {
                IDataSet set = mChartNd.getData().getDataSets().get(0);
                if(!mChartNd.getXAxis().isDrawGridLinesEnabled()){
                    mChartNd.getAxisLeft().setDrawGridLines(true);
                    mChartNd.getXAxis().setDrawGridLines(true);
                }else{
                    mChartNd.getAxisLeft().setDrawGridLines(false);
                    mChartNd.getXAxis().setDrawGridLines(false);
                }
                mChartNd.invalidate();
                break;
            }
            case R.id.actionToggleFilled: {

                List<ILineDataSet> sets = mChartNd.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawFilledEnabled())
                        set.setDrawFilled(false);
                    else
                        set.setDrawFilled(true);
                }
                mChartNd.invalidate();
                break;
            }
            case R.id.actionToggleCircles: {
                List<ILineDataSet> sets = mChartNd.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    if (set.isDrawCirclesEnabled())
                        set.setDrawCircles(false);
                    else
                        set.setDrawCircles(true);
                }
                mChartNd.invalidate();
                break;
            }
            case R.id.actionToggleStepped: {
                List<ILineDataSet> sets = mChartNd.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.STEPPED
                            ? LineDataSet.Mode.LINEAR
                            :  LineDataSet.Mode.STEPPED);
                }
                mChartNd.invalidate();
                break;
            }
            case R.id.actionToggleHorizontalCubic: {
                List<ILineDataSet> sets = mChartNd.getData()
                        .getDataSets();

                for (ILineDataSet iSet : sets) {

                    LineDataSet set = (LineDataSet) iSet;
                    set.setMode(set.getMode() == LineDataSet.Mode.HORIZONTAL_BEZIER
                            ? LineDataSet.Mode.LINEAR
                            :  LineDataSet.Mode.HORIZONTAL_BEZIER);
                }
                mChartNd.invalidate();
                break;
            }
            case R.id.animateY: {
                mChartNd.animateY(3000, Easing.EasingOption.EaseInCubic);
                break;
            }
            case R.id.action_settings: {
                finish();
                Intent i = new Intent(this, Act_configuracion_nd.class);
                startActivity(i);
                break;
            }
            case R.id.action_picker: {
                showDialog(DATE_PICKER_ID);
                break;
            }
        }
        return true;
    }

}
