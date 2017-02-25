package a.choquantifier.app;

import android.app.ActionBar;
import android.app.ActivityGroup;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

/**
 * Created by C POLANIA on 29/11/2016.
 */

public class Act_activity_tabs extends ActivityGroup {

    private String tabIndicator;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_activity_tabs);

        final Resources res = getResources();

        //OBTENER VARIABLE GLOBAL
        /*final GlobalData globalVariable = (GlobalData) getApplicationContext();
        diabetico = globalVariable.getDiabetico();

        if (diabetico == DatosDB_SQLite.USU_DIABETICO)
            tabIndicator="Dosis";
        else
            tabIndicator="Carbohidratos";

        ActionBar actionbar = getActionBar();
        actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        getActionBar().setTitle("Action Bar");
        //getActionBar().setIcon(R.drawable.ab_logo);
        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        actionbar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#eeeeee")));


        actionbar1 = (Toolbar) findViewById(R.id.actionbar);
        actionbar1.setTitle("Calculador de Bolos");
        //actionbar.setMenu();
        //actionbar.setNavigationIcon(getResources().getDrawable(R.drawable.daccept));
        actionbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ActionBar actionbar = getActionBar();

        ActionBar.Tab Tab1 = actionbar.newTab().setIcon(R.drawable.bluetooth);
        ActionBar.Tab Tab2 = actionbar.newTab().setIcon(R.drawable.dcalculator);
        ActionBar.Tab Tab3 = actionbar.newTab().setCustomView(tabView);
        ActionBar.Tab Tab4 = actionbar.newTab().setIcon(R.drawable.ic_tab_4);
        ActionBar.Tab Tab5 = actionbar.newTab().setIcon(R.drawable.ic_tab_5);


        ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (int i=1; i <= 3; i++) {
            ActionBar.Tab tab = bar.newTab();
            tab.setText("Tab " + i);
            //tab.setTabListener(this);
            bar.addTab(tab);

        }*/

        //actionBar = getActionBar();
        //actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);



        final TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup(this.getLocalActivityManager());

        final TabHost.TabSpec spec1, spec2, spec3, spec4, spec5; // final TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        //Tab 1
        spec1 = host.newTabSpec("Tab One");
        tabIndicator="";
        spec1.setIndicator(tabIndicator, res.getDrawable(R.drawable.tab_dosis_selector));
        intent = new Intent().setClass(this, Act_modo_dosis.class );
        spec1.setContent(intent);
        host.addTab(spec1);

        //Tab 2
        spec2 = host.newTabSpec("Tab Two");
        spec2.setIndicator(tabIndicator, res.getDrawable(R.drawable.tab_chartcombined_selector));
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, Act_grafico_combinado.class);
        spec2.setContent(intent);
        host.addTab(spec2);

        //Tab 3
        spec3 = host.newTabSpec("Tab Three");
        spec3.setIndicator(tabIndicator, res.getDrawable(R.drawable.tab_chartindependent_selector));
        intent = new Intent().setClass(this, Act_graficos_independientes.class);
        spec3.setContent(intent);
        host.addTab(spec3);

        //Tab 4
        spec4 = host.newTabSpec("Tab Four");
        spec4.setIndicator(tabIndicator, res.getDrawable(R.drawable.tab_food_selector));
        intent = new Intent().setClass(this, Act_nuevo_alimento.class);
        spec4.setContent(intent);
        host.addTab(spec4);

        //Tab 5
        spec5 = host.newTabSpec("Tab Five");
        spec5.setIndicator(tabIndicator, res.getDrawable(R.drawable.tab_edit_selector));
        intent = new Intent().setClass(this, Act_editar_alimento.class);
        spec5.setContent(intent);
        host.addTab(spec5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.act_grafico_glucosa, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}