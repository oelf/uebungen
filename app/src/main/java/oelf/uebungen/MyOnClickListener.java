package oelf.uebungen;

import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

public class MyOnClickListener implements View.OnClickListener {

    private ViewPager viewPager;
    private DrawerLayout drawerLayout;

    public MyOnClickListener(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public MyOnClickListener(ViewPager viewPager, DrawerLayout drawerLayout) {
        this.viewPager = viewPager;
        this.drawerLayout = drawerLayout;
    }

    @Override
    public void onClick(View v) {
        MyTag myTag = (MyTag) v.getTag();

        if (myTag.source.equals("kategorie")) {
            ViewGroup container = (ViewGroup) v.getParent();
            View nextView = container.getChildAt(container.indexOfChild(v) + 1);

            if (((ViewGroup) nextView).getChildCount() > 0) {

                if (nextView.getVisibility() == View.GONE) {
                    nextView.setVisibility(View.VISIBLE);
                } else {
                    nextView.setVisibility(View.GONE);
                }

            } else {

                // close drawer if you want
                if (this.drawerLayout.isDrawerOpen(Gravity.START | Gravity.LEFT)) {
                    this.drawerLayout.closeDrawers();
                }

                // update loaded Views if you want
                MainTabs adapter = (MainTabs) this.viewPager.getAdapter();

                int[] arrViews = new int[]{myTag.idkategorie, 0, 0, 0};
                adapter.notifyDataSetChanged(arrViews);

                this.viewPager.setCurrentItem(0, true);
            }
        } else if (myTag.source.equals("uebung")) {
            MainTabs adapter = (MainTabs) this.viewPager.getAdapter();

            int[] arrViews = new int[]{0, myTag.idkategorie, myTag.idkategorie, myTag.idkategorie};
            adapter.notifyDataSetChanged(arrViews);

            this.viewPager.setCurrentItem(1, true);
        }
    }
}
