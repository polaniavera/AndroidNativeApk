package a.choquantifier.app.custom;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import a.choquantifier.app.R;

/**
 * Created by LENOVO on 09/01/2017.
 */
public class MyArrayAdapter<T> extends ArrayAdapter<T>
{
    public MyArrayAdapter(Context ctx, T [] objects)
    {
        super(ctx, R.layout.spinner_style, objects);
    }

    //other constructors
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        ((TextView)view).setTextColor(ContextCompat.getColor(view.getContext(), R.color.text));
        ((TextView) view).setGravity(Gravity.CENTER_VERTICAL);

        ((TextView) view).setPadding(10,view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom());

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View view = super.getView(position, convertView, parent);

        //we know that simple_spinner_item has android.R.id.text1 TextView:

        /* if(isDroidX) {*/
        TextView text = (TextView)view.findViewById(android.R.id.text1);
        //text.setTextColor(ContextCompat.getColor(view.getContext(), R.color.text));//choose your color :)
        ((TextView) view).setGravity(Gravity.CENTER_VERTICAL);
        /*}*/

        return view;

    }
}