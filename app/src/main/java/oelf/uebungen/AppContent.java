package oelf.uebungen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AppContent {
    private static ActionBarActivity sActivity;
    private static SQLiteDatabase sDb;
    private static MyOnClickListener sClickListener;
    private static ViewPager sViewPager;

    public static void init(ActionBarActivity activity, SQLiteDatabase db, ViewPager mViewPager) {
        sActivity = activity;
        sDb = db;
        sViewPager = mViewPager;

        sClickListener = new MyOnClickListener(sViewPager);
    }

    public static void showErklaerung(View view, int iduebung) {
        LinearLayout lay = new LinearLayout(view.getContext());
        lay.setOrientation(LinearLayout.VERTICAL);

        View txtItem = sActivity.getLayoutInflater().inflate(R.layout.text_item, null);

        String sql = "SELECT bezeichnung FROM uebungen WHERE iduebung = " + iduebung;
        Cursor c = sDb.rawQuery(sql, null);
        c.moveToFirst();
        String bezeichnung = c.getString(c.getColumnIndex("bezeichnung"));

        sActivity.getSupportActionBar().setSubtitle(bezeichnung);

        TextView txtContent = (TextView) txtItem.findViewById(R.id.txtContent);
        txtContent.setText("Inhalt");

        lay.addView(txtItem);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layoutPager);
        linearLayout.removeAllViews();
        linearLayout.addView(lay);
    }

    public static void showUebungen(View view, int idkategorie) {
        LinearLayout lay = new LinearLayout(view.getContext());
        lay.setOrientation(LinearLayout.VERTICAL);

        String sql = "SELECT iduebung, bezeichnung, equipment, schwierigkeit FROM uebungen WHERE idkategorie = " + idkategorie;
        Cursor c = sDb.rawQuery(sql, null);
        if (c.getCount() > 0) {
            while (c.moveToNext()) {

                int iduebung = c.getInt(c.getColumnIndex("iduebung"));
                String bezeichnung = c.getString(c.getColumnIndex("bezeichnung"));
                String equipment = c.getString(c.getColumnIndex("equipment"));
                String schwierigkeit = c.getString(c.getColumnIndex("schwierigkeit"));

                View uebungItem = sActivity.getLayoutInflater().inflate(R.layout.uebung_item, null);

                TextView txtBezeichung = (TextView) uebungItem.findViewById(R.id.txtTitel);
                txtBezeichung.setText(bezeichnung);

                TextView txtEquipment = (TextView) uebungItem.findViewById(R.id.txtEquipment);
                txtEquipment.setText(txtEquipment.getText() + equipment);

                TextView txtSchwierigkeit = (TextView) uebungItem.findViewById(R.id.txtSchwierigkeit);
                txtSchwierigkeit.setText(txtSchwierigkeit.getText() + schwierigkeit);

                uebungItem.setTag(new MyTag("uebung", iduebung));
                uebungItem.setOnClickListener(sClickListener);

                lay.addView(uebungItem);
            }
        }

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.layoutPager);
        linearLayout.removeAllViews();
        linearLayout.addView(lay);
    }
}
