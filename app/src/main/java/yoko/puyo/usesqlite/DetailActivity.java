package yoko.puyo.usesqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;

public class DetailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", -1);

        MyDAO dao = new MyDAO(this);
        Map<String, Object> map = dao.selectById(id);
        if(map != null) {
            Integer id2 = (Integer)map.get("id");
            String isbn = (String)map.get("isbn");
            String title = (String)map.get("title");
            String author = (String)map.get("author");
            Integer price = (Integer)map.get("price");
            String memo = (String)map.get("memo");

            if(id2 != null) {
                TextView textId = findViewById(R.id.detailId);
                textId.setText(id2.toString());
            }
            if(isbn != null) {
                TextView textISBN = findViewById(R.id.detailISBN);
                textISBN.setText(isbn);
            }
            if(title != null) {
                TextView textTitle = findViewById(R.id.detailTitle);
                textTitle.setText(title);
            }
            if(author != null) {
                TextView textAuthor = findViewById(R.id.detailAuthor);
                textAuthor.setText(author);
            }
            if(price != null) {
                TextView textprice = findViewById(R.id.detailPrice);
                textprice.setText(price.toString());
            }
            if(memo != null) {
                TextView textMemo = findViewById(R.id.detailMemo);
                textMemo.setText(memo);
            }
        }
    }
}