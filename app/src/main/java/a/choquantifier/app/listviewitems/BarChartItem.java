package a.choquantifier.app.listviewitems;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import a.choquantifier.app.R;
import a.choquantifier.app.custom.HourAxisValueFormatter;
import a.choquantifier.app.custom.HourMarkerView;

public class BarChartItem extends ChartItem {
    
    private Typeface mTf;
    
    public BarChartItem(ChartData<?> cd, Context c) {
        super(cd);

        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");

    }

    @Override
    public int getItemType() {
        return TYPE_BARCHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_barchart, null);
            holder.chart = (BarChart) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling
        Description desc = new Description();
        desc.setText("");
        holder.chart.setDescription(desc);
        holder.chart.setDrawGridBackground(false);
        holder.chart.setDrawBarShadow(false);
        holder.chart.getAxisRight().setDrawLabels(false);
        holder.chart.getAxisLeft().setDrawLabels(true);
        holder.chart.getXAxis().setDrawLabels(true);

        XAxis xAxis = holder.chart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setTypeface(mTf);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(86400);  //Rango eje X 86400 segundos (24 h)

        //Formatea eje x en HH:mm
        IAxisValueFormatter xAxisFormatter = new HourAxisValueFormatter(0);
        xAxis.setValueFormatter(xAxisFormatter);
        
        YAxis leftAxis = holder.chart.getAxisLeft();
        leftAxis.setTypeface(mTf);
        leftAxis.setLabelCount(5, false);
        leftAxis.setSpaceTop(20f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
       
        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setSpaceTop(20f);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        mChartData.setValueTypeface(mTf);
        
        // set data
        holder.chart.setData((BarData) mChartData);

        // do not forget to refresh the chart
        holder.chart.invalidate();
        holder.chart.animateY(700);

        final ViewHolder _holder = holder;
        final Context _c = c;
        // listener for selecting and drawing
        holder.chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                _holder.chart.setHighlightPerTapEnabled(true);
                HourMarkerView myMarkerView= new HourMarkerView(_c, R.layout.custom_marker_view, 0);
                _holder.chart.setMarkerView(myMarkerView);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        return convertView;
    }
    
    private static class ViewHolder {
        BarChart chart;
    }

}
