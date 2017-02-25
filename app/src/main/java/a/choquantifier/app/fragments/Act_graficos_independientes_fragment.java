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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import a.choquantifier.app.Act_configuracion;
import a.choquantifier.app.DatosDB_SQLite;
import a.choquantifier.app.GlobalData;
import a.choquantifier.app.R;
import a.choquantifier.app.listviewitems.BarChartItem;
import a.choquantifier.app.listviewitems.ChartItem;
import a.choquantifier.app.listviewitems.LineChartItem;

/**
 * Demonstrates the use of charts inside a ListView. IMPORTANT: provide a
 * specific height attribute for the chart inside your listview-item
 *
 * @author C Polania
 */

public class Act_graficos_independientes_fragment extends Fragment {

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

    private TextView Output;
    private int year;
    private int month;
    private int day;
    static final int DATE_PICKER_ID = 1111;
    ListView lv;
    ChartDataAdapter cda;
    
    View view;
    static Context context;

    //Ejecuta onCreateView cuando se sale de las tabs adyacentes offSet(1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.act_graficos_independientes, container, false);
        context = getActivity().getApplicationContext();
        
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //No permitir que rote la pantalla
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setHasOptionsMenu(true);

        lv = (ListView) view.findViewById(R.id.listView1);
        Output = (TextView) view.findViewById(R.id.Output);

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

        getDataCharts();
        convertData();

        ArrayList<ChartItem> list = new ArrayList<ChartItem>();
        // 3 items
        list.add(new LineChartItem(getDataGlucosa(), context));
        list.add(new LineChartItem(getDataCho(), context));
        list.add(new BarChartItem(getDataBolos(), context));

        cda = new ChartDataAdapter(context, list);
        //ChartDataAdapter cda = new ChartDataAdapter(context, list);
        lv.setAdapter(cda);

        return view;
    }

    /** adapter that supports 3 different item types */
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

        public ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }

        @Override
        public int getItemViewType(int position) {
            // return the views type
            return getItem(position).getItemType();
        }

        @Override
        public int getViewTypeCount() {
            return 2; // we have 2 different item-types
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){

            // Get current date by calendar
            //final Calendar c = Calendar.getInstance();
            //year  = c.get(Calendar.YEAR);
            //month = c.get(Calendar.MONTH);
            //day   = c.get(Calendar.DAY_OF_MONTH);

            //setDataChart(year, month, day);
        }else{
            //list.clear();
        }
    }

    private LineData getDataGlucosa() {

        if(glucoEntry.size()==0){
            glucoEntry.add(new Entry(0, 0));
            choEntry.add(new Entry(0, 0));
            boloAlimenticioEntry.add(new BarEntry(0, 0));
            boloCorreccionEntry.add(new BarEntry(0, 0));
        }

        LineDataSet d = new LineDataSet(glucoEntry, "Glucosa");
        d.setLineWidth(2.5f);
        d.setCircleRadius(2.7f);
        //d.setHighLightColor(Color.rgb(244, 117, 117));
        d.setColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        d.setCircleColor(Color.BLACK);
        d.setDrawCircleHole(true);
        d.setDrawFilled(true);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_red);
            d.setFillDrawable(drawable);
        }
        else {
            d.setFillColor(Color.RED);
        }

        d.setDrawValues(false);

        //LineData cd = new LineData(horaArr, d);
        LineData cd = new LineData();

        cd.addDataSet(d);

        return cd;
    }

    private LineData getDataCho() {

        LineDataSet d = new LineDataSet(choEntry, "Carbohidratos");
        d.setLineWidth(2.5f);
        d.setCircleRadius(2.7f);
        //d.setHighLightColor(Color.rgb(244, 117, 117));
        d.setColor(ColorTemplate.VORDIPLOM_COLORS[2]);
        d.setCircleColor(Color.BLACK);
        d.setDrawCircleHole(true);
        d.setDrawFilled(false);
        d.setDrawValues(false);

        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(context, R.drawable.fade_blue);
            d.setFillDrawable(drawable);
        }
        else {
            d.setFillColor(Color.BLUE);
        }

        //LineData cd = new LineData(horaArr, d);
        LineData cd = new LineData();

        cd.addDataSet(d);

        return cd;
    }

    private BarData getDataBolos() {

        BarDataSet set1 = new BarDataSet(bolosEntry, "Bolo Alimenticio");
        set1.setStackLabels(new String[]{"Alimenticio", "Corrección"});
        set1.setColors(new int[] { R.color.verde, R.color.rojo}, context);
        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(set1);

        BarData cd = new BarData(sets);
        cd.setBarWidth(2000f);
        cd.setDrawValues(false);

        return cd;

        /*
        BarDataSet d1 = new BarDataSet(boloAlimenticioArr, "Bolo Alimenticio");
        d1.setBarSpacePercent(20f);
        d1.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d1.setHighLightAlpha(255);
        d1.setColor(BLUE);

        BarDataSet d2 = new BarDataSet(boloCorreccionArr, "Bolo Corrección");
        d2.setBarSpacePercent(20f);
        d2.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d2.setHighLightAlpha(255);
        d2.setColor(RED);

        BarDataSet d3 = new BarDataSet(boloTotalArr, "Bolo Total");
        d3.setBarSpacePercent(20f);
        d3.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d3.setHighLightAlpha(255);
        d3.setColor(GREEN);

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d1);
        sets.add(d2);
        sets.add(d3);

        BarData cd = new BarData(horaArr, sets);
        return cd;
        */
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
        String hora = "\"" + android.text.format.DateFormat.format("HH:mm:ss", new java.util.Date()).toString() + "\"";

        //OBTENER VARIABLE GLOBAL
        final GlobalData globalVariable = (GlobalData) context;
        int codigo = globalVariable.getCodigo();

        String selectQuery =
                "SELECT historial_hora,historial_gs_actual,historial_cho_totales,historial_bolo_alimenticio,historial_bolo_correccion,historial_bolo_total " +
                        "FROM historial " +
                        "WHERE (historial_usuario=" + codigo + " AND  historial_fecha=" + fecha + " )";

        DatosDB_SQLite admin = new DatosDB_SQLite(context, DatosDB_SQLite.NOMBRE_DB, null, DatosDB_SQLite.VERSION_DB);
        SQLiteDatabase db = admin.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            int i = 0;
            do {
                horaArr.add(cursor.getString(0));
                glucoArr.add(cursor.getFloat(1));
                choArr.add(cursor.getFloat(2));
                boloAlimenticioArr.add(cursor.getFloat(3));
                boloCorreccionArr.add(cursor.getFloat(4));
                boloTotalArr.add(cursor.getFloat(5));
                i++;
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
        Output.setText(new StringBuilder().append(month + 1)
                .append("-").append(day).append("-").append(year)
                .append(" "));

        getDataCharts();
        convertData();

        ArrayList<ChartItem> list = new ArrayList<ChartItem>();

        // 3 items
        list.add(new LineChartItem(getDataGlucosa(), context));
        list.add(new LineChartItem(getDataCho(), context));
        list.add(new BarChartItem(getDataBolos(), context));

        ChartDataAdapter cda = new ChartDataAdapter(context, list);
        ListView lv = (ListView) view.findViewById(R.id.listView1);
        lv.setAdapter(cda);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_graficos_independientes, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.actionCalendar: {
                showDialog(DATE_PICKER_ID);
                break;
            }
            case R.id.action_settings: {
                Intent i = new Intent(context, Act_configuracion.class);
                startActivity(i);
                break;
            }
            case R.id.action_exit: {
                getActivity().finish();
                break;
            }
            case R.id.action_picker: {
                showDialog(DATE_PICKER_ID);
                break;
            }
            /*case R.id.actionToggleValues: {
                //List<IBarDataSet> sets = lv.getChildAt(0)
                //List<IBarDataSet> sets = mChart.getData().getDataSets();

                for (IBarDataSet iSet : sets) {

                    BarDataSet set = (BarDataSet) iSet;
                    set.setDrawValues(!set.isDrawValuesEnabled());
                }
                mChart.invalidate();
                break;
            }
            case R.id.animateX: {
                mChart.animateX(3000);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(3000);
                break;
            }
            case R.id.animateXY: {

                mChart.animateXY(3000, 3000);
                break;
            }*/
        }
        return true;
    }

}
