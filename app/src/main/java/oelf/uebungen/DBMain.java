package oelf.uebungen;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBMain extends SQLiteOpenHelper {
    private static String DB_PATH  = Config.DATABASE_PATH;
    private static String DB_NAME = Config.DATABASE_NAME;
    private SQLiteDatabase dbObj;
    private final Context context;

    public DBMain(Context context) {
        super(context, DB_NAME, null, 3);

        this.context = context;
  }

    public void createDB() throws IOException {
        this.getReadableDatabase();

        try {
            copyDB();
        } catch (IOException e) {
            throw new Error("Error copying database");
        }
    }

    public void copyDB() throws IOException {
        try {
            InputStream ip = context.getAssets().open(DB_NAME + ".db");
            String op = DB_PATH + DB_NAME;
            OutputStream output = new FileOutputStream(op);
            byte[] buffer = new byte[1024];

            int length;
            while ((length = ip.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            output.flush();
            output.close();
            ip.close();
        } catch (IOException e) {
            Log.v("error", e.toString());
        }
    }

    public void openDB() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        dbObj = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if (dbObj != null) {
            dbObj.close();
        }

        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
