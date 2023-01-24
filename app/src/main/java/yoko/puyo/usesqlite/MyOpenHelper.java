package yoko.puyo.usesqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyOpenHelper extends SQLiteOpenHelper{
    public static final int DB_VERSION = 3;     //これが変わるとonUpgradeが呼び出される
    public static final String BOOKS = "BOOK";
    private static final String TAG = "abc";

    public MyOpenHelper(Context context) {
        super(context, "MYDB", null, DB_VERSION);
        Log.d(TAG,"MyOpenHelperConstructer");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + BOOKS + " ( " +
                        " ID INTEGER PRIMARY KEY AUTOINCREMENT, " +     //値を指定しないとauto increment
                        " ISBN        TEXT, " +                     //ISBN
                        " TITLE       TEXT, " +                     //書名
                        " AUTHOR      TEXT, " +                     //著者
                        " PRICE       INTEGER, " +                  //価格
                        " MEMO        TEXT) "                       //メモ
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS BOOK");
        onCreate(db);
    }
}
