package oelf.uebungen;

import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import SlidingTabs.SlidingTabLayout;


public class MainActivity extends ActionBarActivity {

    ActionBarDrawerToggle mDrawerToggle;
    DrawerLayout mDrawerLayout;
    SlidingTabLayout mSlidingTabLayout;
    ViewPager mViewPager;

    String DB_PATH;
    private SQLiteDatabase mDataBase;
    private static String DB_NAME = "uebungen.db";
    private SQLiteDatabase db1;
    private ViewGroup insertPoint;
    private MyOnClickListener clickListener;

    private LinearLayout zeigeMenue(int tiefe, int idknotenSuche) {
        Cursor c = this.db1.rawQuery("SELECT idkategorie, bezeichnung, idknoten FROM kategorien WHERE idknoten=" + idknotenSuche + " ORDER BY idknoten, idkategorie", null);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        if (tiefe > 0) {
            layout.setVisibility(View.GONE);
        }

        if (c.getCount() > 0) {
            int counter = 0;
            while (c.moveToNext()) {
                String bezeichnung = c.getString(c.getColumnIndex("bezeichnung"));
                int idknoten = c.getInt(c.getColumnIndex("idknoten"));
                int idkategorie = c.getInt(c.getColumnIndex("idkategorie"));

                View navButton = getLayoutInflater().inflate(R.layout.menuitem, null);
                navButton.setTag(new MyTag("kategorie", idkategorie, idknoten));
                ((TextView) navButton.findViewById(R.id.txtNavButtonText)).setText(bezeichnung);
                navButton.setOnClickListener(this.clickListener);

                int marginLeft = 0;
                if (tiefe > 0) {
                    marginLeft = tiefe * 25;
                }

                marginLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginLeft, getResources().getDisplayMetrics());
                ((LinearLayout.LayoutParams) ((LinearLayout) navButton.findViewById(R.id.txtNavButton)).getLayoutParams()).setMargins(marginLeft, 0, 0, 0);

                layout.addView(navButton);

                counter++;
            }

            for (int i = 0; i < counter * 2; i += 2) {
                MyTag myTag = (MyTag) layout.getChildAt(i).getTag();
                layout.setTag(new MyTag("kategorie", myTag.idknoten, 0));

                layout.addView(this.zeigeMenue(tiefe + 1, myTag.idkategorie), i + 1);
            }
        }

        return layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBMain db;
        db = new DBMain(this);
        try {
            db.createDB();
        } catch (IOException ioe) {
            throw new Error("Database not created....");
        }

        try {
            db.openDB();
        } catch (SQLException sqle) {
            throw sqle;
        }

        this.db1 = openOrCreateDatabase("uebungen", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        this.insertPoint = (ViewGroup) findViewById(R.id.mainNavDrawer);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(4); // tabcachesize (=tabcount for better performance)
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);

        // use own style rules for tab layout
        mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);

        Resources res = getResources();
        mSlidingTabLayout.setSelectedIndicatorColors(res.getColor(R.color.tab_indicator_color));
        mSlidingTabLayout.setDistributeEvenly(true);
        mViewPager.setAdapter(new MainTabs(getApplicationContext()));

        mSlidingTabLayout.setViewPager(mViewPager);

        this.clickListener = new MyOnClickListener(mViewPager, mDrawerLayout);

        LinearLayout layout = this.zeigeMenue(0, 0);
        this.insertPoint.addView(layout);

        AppContent.init(this, this.db1, mViewPager);

        // Tab events
        if (mSlidingTabLayout != null) {
            mSlidingTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.START | Gravity.LEFT)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }
}
