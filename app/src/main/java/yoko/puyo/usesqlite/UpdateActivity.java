package yoko.puyo.usesqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

public class UpdateActivity extends AppCompatActivity{
    TextView textId;
    EditText editISBN;
    EditText editTitle;
    EditText editAuthor;
    EditText editprice;
    EditText editMemo;
    Button updateBtn;
    MyDAO dao;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        textId = findViewById(R.id.updateId);
        editISBN = findViewById(R.id.updateISBN);
        editTitle = findViewById(R.id.updateTitle);
        editAuthor = findViewById(R.id.updateAuthor);
        editprice = findViewById(R.id.updatePrice);
        editMemo = findViewById(R.id.updateMemo);
        updateBtn = findViewById(R.id.update);
        //------------------------------送信されたid
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        //------------------------------dao
        dao = new MyDAO(this);
        //------------------------------変更前のデータを表示
        dispNow();
        //------------------------------変更ボタン
        updateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                update();
                finish();
            }
        });
    }
    //------------------------------変更前のデータを表示
    private void dispNow() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        Map<String, Object> map = dao.selectById(id);
        if(map != null) {
            Integer id2 = (Integer)map.get("id");
            String isbn = (String)map.get("isbn");
            String title = (String)map.get("title");
            String author = (String)map.get("author");
            Integer price = (Integer)map.get("price");
            String memo = (String)map.get("memo");

            if(id2 != null)    textId.setText(id2.toString());
            if(isbn != null)   editISBN.setText(isbn);
            if(title != null)  editTitle.setText(title);
            if(author != null) editAuthor.setText(author);
            if(price != null)  editprice.setText(price.toString());
            if(memo != null)   editMemo.setText(memo);
        }
    }
    //------------------------------DBの更新
    private void update() {
        String isbn = editISBN.getText().toString();        //入力無しはnullではなく””
        String title = editTitle.getText().toString();
        String author = editAuthor.getText().toString();
        String price = editprice.getText().toString();
        String memo = editMemo.getText().toString();

        ContentValues values = new ContentValues();
        values.put("ISBN", isbn);
        values.put("title", title);
        values.put("author", author);
        values.put("price", Integer.parseInt(price));
        values.put("memo", memo);

        dao.update(id,values);
    }
}