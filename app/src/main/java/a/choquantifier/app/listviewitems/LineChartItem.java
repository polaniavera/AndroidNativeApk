
package a.choquantifier.app.listviewitems;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import a.choquantifier.app.R;
import a.choquantifier.app.custom.HourAxisValueFormatter;
import a.choquantifier.app.custom.HourMarkerView;

public class LineChartItem extends ChartItem {

    private Typeface mTf;

    public LineChartItem(ChartData<?> cd, Context c) {
        super(cd);

        mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
    }

    @Override
    public int getItemType() {
        return TYPE_LINECHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_linechart, null);
            holder.chart = (LineChart) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling
        // holder.chart.setValueTypeface(mTf);
        Description desc = new Description();
        desc.setText("");
        holder.chart.setDescription(desc);
        holder.chart.setDrawGridBackground(false);
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
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        
        YAxis rightAxis = holder.chart.getAxisRight();
        rightAxis.setTypeface(mTf);
        rightAxis.setLabelCount(5, false);
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        // set data
        holder.chart.setData((LineData) mChartData);

        // do not forget to refresh the chart
        holder.chart.invalidate();
        holder.chart.animateX(750);

        final LineChartItem.ViewHolder _holder = holder;
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
        LineChart chart;
    }
}
