package yoko.puyo.usesqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener{
    static final String TAG = "abc";

    ListView listView;
    SimpleAdapter adapter;
    ArrayList<Map<String, Object>> list;
    MyDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //------------------------------- ListViewとSimpleAdapter
        listView = findViewById(R.id.listView1);
        View header = (View)getLayoutInflater().inflate(R.layout.row_header,null);
        listView.addHeaderView(header);
        dao = new MyDAO(this);
        list = dao.selectAllBooks();
        adapter = new SimpleAdapter(
                this,
                list,                            	//ArrayList
                R.layout.row,                   	//ListView内の1項目を定義したxml
                new String[] { "id", "isbn", "title", "author", "price", "memo" },  	//mapのキー
                new int[] {R.id.rowId, R.id.rowISBN, R.id.rowTitle, R.id.rowAuthor, R.id.rowPrice, R.id.rowMemo }	//row.xml内のid
        );
        listView.setAdapter(adapter);	//ListViewにAdapterをセット
        listView.setOnItemLongClickListener(this);


        //------------------------------- 新規登録ボタン
        Button toInsert = findViewById(R.id.toInsert);
        toInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,InsertActivity.class);
                startActivity(intent);
            }
        });
        //------------------------------- 検索ボタン
        Button select = findViewById(R.id.select);
        select.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText cond = findViewById(R.id.cond);
                list = dao.selectByTitle(cond.getText().toString());
                Log.d(TAG, "list size=" + list.size());
                Log.d(TAG,"Adapter.getCount="+ adapter.getCount());
//                adapter.notifyDataSetChanged(); //Adapter更新　なぜかここではこれがきかない　onResumeでは大丈夫なのに
                listView.invalidateViews();     //ListView更新
            }
        });
        Log.d(TAG, "list size=" + list.size());
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume");
        list = dao.selectAllBooks();    //daoのselectXXの戻り値のArrayListは同じアドレスを返すようにしている
        adapter.notifyDataSetChanged(); //そのためこれでListViewの値が更新される
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        Log.d(TAG, "タップされたのは　position=" + position);    //positionは表題含めて０から始まる
        Log.d(TAG, "タップされたのは　id=" + id);                //idは表題含めず1行目が０から始まる
        Map curr = list.get((int)id);
        Integer idObj = (Integer)(curr.get("id"));
        int idInt = idObj.intValue();

        //AlertDialog表示
        CharSequence[] items = {"削除","詳細","変更"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setItems(items, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        dao.deleteBooksById(idInt);
                        list = dao.selectAllBooks();    //daoのselectXXの戻り値のArrayListは同じアドレスを返すようにしている
                        adapter.notifyDataSetChanged(); //そのためこれでListViewの値が更新される
                        break;
                    case 1:
                        Intent intentD = new Intent(MainActivity.this, DetailActivity.class);
                        intentD.putExtra("id",idInt);
                        startActivity(intentD);
                        break;
                    case 2:
                        Intent intentU = new Intent(MainActivity.this, UpdateActivity.class);
                        intentU.putExtra("id",idInt);
                        startActivity(intentU);
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

        return true;
    }
}