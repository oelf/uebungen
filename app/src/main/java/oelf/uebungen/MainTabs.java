package oelf.uebungen;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * The {@link android.support.v4.view.PagerAdapter} used to display pages in this sample.
 * The individual pages are simple and just display two lines of text. The important section of
 * this class is the {@link #getPageTitle(int)} method which controls what is displayed in the
 * {@link SlidingTabs.SlidingTabLayout}.
 */
class MainTabs extends PagerAdapter {

    Context context;
    SparseArray<View> views = new SparseArray<View>();
    String[] arrTabs;

    public MainTabs(Context context) {
        this.context = context;
        arrTabs = context.getResources().getStringArray(R.array.arrTabs);
    }

    private void getPageContent(View view, int key, int idkategorie) {
        if (idkategorie > 0) {
            switch (key) {
                case 0:
                    AppContent.showUebungen(view, idkategorie);
                    break;
                case 1:
                    AppContent.showErklaerung(view, idkategorie);
                    break;
            }
        }
    }

    /**
     * @return the number of pages to display
     */
    @Override
    public int getCount() {
        return 4;
    }

    /**
     * @return true if the value returned from {@link #instantiateItem(android.view.ViewGroup, int)} is the
     * same object as the {@link View} added to the {@link android.support.v4.view.ViewPager}.
     */
    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }

    /**
     * Return the title of the item at {@code position}. This is important as what this method
     * returns is what is displayed in the {@link SlidingTabs.SlidingTabLayout}.
     * <p/>
     * Here we construct one using the position value, but for real application the title should
     * refer to the item's contents.
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return this.arrTabs[position];
    }

    /**
     * Instantiate the {@link View} which should be displayed at {@code position}. Here we
     * inflate a layout from the apps resources and then change the text view to signify the position.
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Inflate a new layout from our resources

        LayoutInflater inflater = LayoutInflater.from(this.context);

        View view = inflater.inflate(R.layout.pager_item,
                container, false);

        //TextView txt = (TextView) view.findViewById(R.id.item_subtitle);
        //txt.setText("AppContent: " + (position + 1));

        //this.getPageContent(view, position);

        // Add the newly created View to the ViewPager
        container.addView(view);

        views.put(position, view);

        // Return the View
        return view;
    }

    /**
     * Destroy the item from the {@link android.support.v4.view.ViewPager}. In our case this is simply removing the
     * {@link View}.
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        views.remove(position);
    }

    @Override
    public void notifyDataSetChanged() {
        int position = 0;
        for (int i = 0; i < views.size(); i++) {
            position = views.keyAt(i);
            View view = views.get(position);
            // Change the content of this view
            //TextView txt = (TextView) view.findViewById(R.id.item_subtitle);
            //txt.setText("This Page " + (position + 1) + " has been refreshed");

            this.getPageContent(view, position, 0);
        }
        super.notifyDataSetChanged();
    }

    public void notifyDataSetChanged(int[] arr) {
        int position = 0;
        for (int i = 0; i < views.size(); i++) {
            position = views.keyAt(i);
            View view = views.get(position);
            // Change the content of this view
            //TextView txt = (TextView) view.findViewById(R.id.item_subtitle);
            //txt.setText("This Page " + (position + 1) + " has been refreshed");

            this.getPageContent(view, position, arr[i]);
        }
        super.notifyDataSetChanged();
    }
}
