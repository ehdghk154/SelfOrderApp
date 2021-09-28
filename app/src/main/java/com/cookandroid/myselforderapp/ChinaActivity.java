package com.cookandroid.myselforderapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ChinaActivity extends AppCompatActivity {
    int j = 0;
    int ItemPrice = 0;
    static int MAX_COUNT = 50;
    final Intent intent = new Intent();
    String selectItem;
    final String[] total = new String[MAX_COUNT];
    int[] total_price = new int[MAX_COUNT];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snack);
        System.out.println(total);
        Button btn1, btn2;

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        String[] menu = {"짜장면 5000원", "짬뽕 6000원", "간짜장 7000원", "삼선짬뽕 7000원", "탕수육 10000원", "깐풍기 12000원", "양장피 12000원"};
        final int[] price = {5000, 6000, 7000, 7000, 10000, 12000};

        final ArrayList<String> items = new ArrayList<String>();
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        CustomAdapter adapter = new CustomAdapter(this, 0, items);

        // listview 생성 및 adapter 지정.
        final ListView lv = (ListView) findViewById(R.id.lv1);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectItem = (String)adapterView.getAdapter().getItem(i);
                ItemPrice = price[i];
            }
        });
        for(int i = 0; i < menu.length; i++){
            items.add(menu[i]);
        }


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total[j] = selectItem;
                total_price[j] = ItemPrice;
                j += 1;
                //Toast.makeText(getApplicationContext(), selectItem+"를 장바구니에 담았습니다.", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "장바구니에 담았습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("menu", total);
                intent.putExtra("price", total_price);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
    private class CustomAdapter extends ArrayAdapter<String> {
        private ArrayList<String> items;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
            this.items = objects;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.custom_lv, null);
            }

            // ImageView 인스턴스
            ImageView imageView = (ImageView)v.findViewById(R.id.imageView);
            // 리스트뷰의 아이템에 이미지를 변경한다.
            if("짜장면 5000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.china05);
            else if("짬뽕 6000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.china06);
            else if("간짜장 7000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.china01);
            else if("삼선짬뽕 7000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.china03);
            else if("탕수육 10000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.china07);
            else if("깐풍기 12000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.china02);
            else if("양장피 12000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.china04);


            TextView textView = (TextView)v.findViewById(R.id.textView);
            textView.setText(items.get(position));

            return v;
        }
    }
}
