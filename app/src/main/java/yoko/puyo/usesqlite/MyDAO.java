package yoko.puyo.usesqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDAO{
    public static final String BOOKS = MyOpenHelper.BOOKS;
    private static final String TAG = "abc";

    MyOpenHelper helper;
    SQLiteDatabase db;
    ArrayList<Map<String, Object>> listForSelect;
    //------------------------------------------------コンストラクタ
    public MyDAO(Context context) {
        helper = new MyOpenHelper(context);
        db = helper.getWritableDatabase();      //読み書き可
        listForSelect = new ArrayList<>();
    }
    //-------------------------------------------- INSERT execSQL使用
    public void insertTitle(String name) {
        //SQL文を作って挿入することもできるが、挿入できたか確かめることができない
        String sql = "INSERT INTO " + BOOKS + "(NAME) VALUES('" + name + "')";
        db.execSQL(sql);
    }
    //---------------------------------------------- INSERT insertメソッド使用
    public void insert(ContentValues values) {
        //第2引数は第3引数が何も入っていない時 insert into table (第2引数) values (null)のSQLを実行
        //戻り値-1は　エラー
        Log.d(TAG,"insert1");
        Log.d(TAG,values.get("memo").toString());
        long rowId = db.insert(BOOKS, null, values);
        Log.d(TAG,"insert2");
    }
    //-------------------------------------------- DELETE execSQL使用
    public void deleteAll() {
        db.execSQL("DELETE FROM " + BOOKS);
    }
    //-------------------------------------------- DELETE deleteメソッド使用
    public void deleteBooksById(int id) {
        String[] ids = {Integer.toString(id)};
        int num = db.delete(BOOKS, "ID=?", ids);
    }
    //-------------------------------------------- UPDATE CUSTOMERS BY MAIL
    public void update(int id, ContentValues values) {
        String[] ids = {Integer.toString(id)};
        int num = db.update(BOOKS, values, "ID=?", ids);
    }
    //------------------------------------------------　すべてのBOOKSデータ取得
    public ArrayList<Map<String, Object>> selectAllBooks() {
        Cursor cursor = db.query(
                BOOKS,      // table name
                null,       // The array of columns to return (pass null to get all)
                null,       // The columns for the WHERE clause
                null,       // The values for the WHERE ?clause
                null,       // don't group the rows
                null,       // don't filter by row groups
                "ID ASC"    // The sort order
        );
        cursor.moveToFirst();
        int count = cursor.getCount();
        listForSelect.clear();
        for (int i = 0; i < count; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            int id = cursor.getInt(0);
            map.put("id", id);
            String isbn = cursor.getString(1);
            map.put("isbn", isbn);
            String title = cursor.getString(2);
            map.put("title", title);
            String author = cursor.getString(3);
            map.put("author", author);
            int price = cursor.getInt(4);
            map.put("price", price);
            String memo = cursor.getString(5);
            map.put("memo", memo);

            listForSelect.add(map);
            cursor.moveToNext();
            printMap(map);
        }
        cursor.close(); // 忘れずに！
        return listForSelect;
    }
    //------------------------------------------------　指定されたtitleのデータ取得
    public ArrayList<Map<String, Object>> selectByTitle(String partOfTitle) {
        String where = "TITLE LIKE '%%" + partOfTitle + "%%'";
        Cursor cursor = db.query(
                BOOKS,
                null,   // The array of columns to return (pass null to get all)
                where,     // WHERE clause
                null,      // The values for the WHERE ?clause
                null,      // don't group the rows
                null,      // don't filter by row groups
                null       // The sort order
        );
        cursor.moveToFirst();
        int count = cursor.getCount();
        listForSelect.clear();
        for (int i = 0; i < count; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            int id = cursor.getInt(0);
            map.put("id", id);
            String isbn = cursor.getString(1);
            map.put("isbn", isbn);
            String title = cursor.getString(2);
            map.put("title", title);
            String author = cursor.getString(3);
            map.put("author", author);
            int price = cursor.getInt(4);
            map.put("price", price);
            String memo = cursor.getString(5);
            map.put("memo", memo);
            listForSelect.add(map);
            cursor.moveToNext();
        }
        cursor.close(); // 忘れずに！
        return listForSelect;
    }
    public Map<String, Object> selectById(int id) {
        String where = "ID=?";
        String[] hatenas = {String.valueOf(id)};
        Cursor cursor = db.query(
                BOOKS,
                null,   // The array of columns to return (pass null to get all)
                where,     // WHERE clause
                hatenas,      // The values for the WHERE ?clause
                null,      // don't group the rows
                null,      // don't filter by row groups
                null       // The sort order
        );
        int count = cursor.getCount();
        if(count != 1) {
            return null;
        } else {
            cursor.moveToFirst();
            Map<String,Object> map = new HashMap<>();
            int id1 = cursor.getInt(0);
            map.put("id", id1);
            String isbn = cursor.getString(1);
            map.put("isbn", isbn);
            String title = cursor.getString(2);
            map.put("title", title);
            String author = cursor.getString(3);
            map.put("author", author);
            int price = cursor.getInt(4);
            map.put("price", price);
            String memo = cursor.getString(5);
            map.put("memo", memo);
            return map;
        }
    }
    //------------------------------------------------printMap
    public void printMap(Map<String, Object> map) {
        StringBuilder str = new StringBuilder("");
        Set<String> keys = map.keySet();
        for (String key : keys) {
            str.append(key);
            str.append("=");
            str.append(map.get(key));
            str.append("   ");
        }
        Log.d(TAG, str.toString());
    }
    //------------------------------------------------printMap
    public void printListOfMap(ArrayList<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            StringBuilder str = new StringBuilder("");
            Set<String> keys = map.keySet();
            for (String key : keys) {
                str.append(key);
                str.append("=");
                str.append(map.get(key));
                str.append("   ");
            }
            Log.d(TAG, str.toString());
        }
    }
}