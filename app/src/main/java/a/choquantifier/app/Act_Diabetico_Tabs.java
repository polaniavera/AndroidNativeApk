package a.choquantifier.app;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import a.choquantifier.app.fragments.Act_editar_alimento_fragment;
import a.choquantifier.app.fragments.Act_grafico_combinado_fragment;
import a.choquantifier.app.fragments.Act_graficos_independientes_fragment;
import a.choquantifier.app.fragments.Act_modo_dosis_fragment;
import a.choquantifier.app.fragments.Act_nuevo_alimento_fragment;


public class Act_Diabetico_Tabs extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_diabetico_tabs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //Realizar scroll en el fragment y no en el appBar
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        params.setScrollFlags(0);  // clear all scroll flags

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
              @Override
              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

              }

              @Override
              public void onPageSelected(int position) {
                  switch(position) {
                      case 0:
                          viewPager.setCurrentItem(0);
                          toolbar.setTitle("Calcular Dosis");
                          break;
                      case 1:
                          viewPager.setCurrentItem(1);
                          toolbar.setTitle("Gráfico Integrado");
                          break;
                      case 2:
                          viewPager.setCurrentItem(2);
                          toolbar.setTitle("Gráfico Independiente");
                          break;
                      case 3:
                          viewPager.setCurrentItem(3);
                          toolbar.setTitle("Nuevo Alimento");
                          break;
                      case 4:
                          viewPager.setCurrentItem(4);
                          toolbar.setTitle("Editar Alimento");
                          break;
                  }
              }

                @Override
                public void onPageScrollStateChanged(int position){}
          });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        LinearLayout linearLayout = (LinearLayout)tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        //linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_END);
        //linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ContextCompat.getColor(getApplicationContext(), R.color.barra));
        drawable.setSize(1, 1);
        linearLayout.setDividerPadding(0);
        linearLayout.setDividerDrawable(drawable);
    }

    private void setupTabIcons() {
        int[] tabIcons = {
                R.drawable.tab_dosis_selector,
                R.drawable.tab_chartcombined_selector,
                R.drawable.tab_chartindependent_selector,
                R.drawable.tab_food_selector,
                R.drawable.tab_edit_selector
        };

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Act_modo_dosis_fragment(), "");
        adapter.addFrag(new Act_grafico_combinado_fragment(), "");
        adapter.addFrag(new Act_graficos_independientes_fragment(), "");
        adapter.addFrag(new Act_nuevo_alimento_fragment(), "");
        adapter.addFrag(new Act_editar_alimento_fragment(), "");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
