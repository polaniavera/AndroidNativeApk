package a.choquantifier.app.custom;

import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

/**
 * Created by Philipp Jahoda on 12/09/15.
 */
public class MyFillFormatter {

    private float mFillPos = 0f;

    public MyFillFormatter(float fillpos) {
        this.mFillPos = fillpos;
    }

    public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
        // your logic could be here
        return mFillPos;
    }
}
