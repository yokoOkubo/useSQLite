package yoko.puyo.usesqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity{
    static final String TAG = "abc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Button insert = findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                try {
                    EditText editISBN = findViewById(R.id.insertISBN);
                    EditText editTitle = findViewById(R.id.insertTitle);
                    EditText editAuthor = findViewById(R.id.insertAuthor);
                    EditText editPrice = findViewById(R.id.insertPrice);
                    EditText editMemo = findViewById(R.id.insertMemo);

                    String isbn = editISBN.getText().toString();        //入力無しはnullではなく””
                    String title = editTitle.getText().toString();
                    String author = editAuthor.getText().toString();
                    String price = editPrice.getText().toString();
                    String memo = editMemo.getText().toString();

                    ContentValues values = new ContentValues();
                    if (!isbn.equals("")) values.put("ISBN", isbn);
                    if (!title.equals("")) values.put("title", title);
                    if (!author.equals("")) values.put("author", author);
                    if (!price.equals("")) values.put("price", Integer.parseInt(price));
                    if (!memo.equals("")) values.put("memo", memo);

                    MyDAO dao = new MyDAO(InsertActivity.this);
                    dao.insert(values);

                    finish();
                } catch (Exception e) {
                    Toast.makeText(InsertActivity.this,"DBに登録することができませんでした",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}