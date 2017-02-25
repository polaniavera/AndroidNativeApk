package a.choquantifier.app;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

/**
 * Created by C POLANIA on 29/11/2016.
 */

public class Act_activity_tabs_nd extends ActivityGroup {

    private String tabIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_activity_tabs_nd);

        TabHost host = (TabHost)findViewById(R.id.tabHostNd);
        host.setup(this.getLocalActivityManager());

        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        //Tab 1
        spec = host.newTabSpec("Tab A");
        tabIndicator="Carbohidratos";
        spec.setIndicator(tabIndicator);
        intent = new Intent().setClass(this, Act_modo_calculo.class );
        spec.setContent(intent);
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab B");
        spec.setIndicator("Historico");
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent().setClass(this, Act_grafico_nodiabetico.class);
        spec.setContent(intent);
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("Tab C");
        spec.setIndicator("Alimentos");
        intent = new Intent().setClass(this, Act_nuevo_alimento.class);
        spec.setContent(intent);
        host.addTab(spec);

        //Tab 4
        spec = host.newTabSpec("Tab Four");
        spec.setIndicator("Editar");
        intent = new Intent().setClass(this, Act_editar_alimento.class);
        spec.setContent(intent);
        host.addTab(spec);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_grafico_nd, menu);
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