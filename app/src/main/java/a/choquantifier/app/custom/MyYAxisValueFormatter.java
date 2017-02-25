package a.choquantifier.app.custom;

import com.github.mikephil.charting.components.YAxis;

import java.text.DecimalFormat;

public class MyYAxisValueFormatter {

    private DecimalFormat mFormat;

    public MyYAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    public String getFormattedValue(float value, YAxis yAxis) {
        return mFormat.format(value) + " $";
    }
}
