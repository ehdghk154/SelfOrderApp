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

public class PizzaActivity extends AppCompatActivity {
    int j = 0; // 배열 계산을 위한 변수(담기 버튼에 사용)
    int ItemPrice = 0; // 선택한 메뉴의 가격
    static int MAX_COUNT = 50;
    final Intent intent = new Intent();
    String selectItem; // 선택한 아이템 텍스트
    final String[] total = new String[MAX_COUNT]; // 담은 모든 아이템
    int[] total_price = new int[MAX_COUNT]; // 아이템 가격들
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.snack);
        System.out.println(total);
        Button btn1, btn2;

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        // 메뉴 이름들
        String[] menu = {"불고기피자 16000원", "페퍼로니피자 16000원", "하와이안피자 18000원", "치즈크러스트피자 17000원", "바이트골드피자 19000원", "포테이토피자 16000원", "콜라1L 1500원"};
        // 메뉴 가격들
        final int[] price = {16000, 16000, 18000, 17000, 19000, 16000};
        // 어댑터에 쓸 ArrayList = 아이템이 들어가게 되는 곳
        final ArrayList<String> items = new ArrayList<String>();
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        CustomAdapter adapter = new CustomAdapter(this, 0, items);

        // listview 생성 및 adapter 지정.
        final ListView lv = (ListView) findViewById(R.id.lv1);
        lv.setAdapter(adapter);
        //리스트뷰의 메뉴를 선택할 때 발생하는 클릭 리스너
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectItem = (String)adapterView.getAdapter().getItem(i); // 선택한 아이템 저장
                ItemPrice = price[i]; // 선택한 아이템의 가격 저장
            }
        });
        //리스트뷰에 메뉴 가져오기
        for(int i = 0; i < menu.length; i++){
            items.add(menu[i]); // 화면에 보여줄 아이템을 추가
        }

        // 담기 버튼 클릭 리스너
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total[j] = selectItem; // total=담은 아이템들, selectItem=선택한 아이템
                total_price[j] = ItemPrice; // total_price=담은 아이템 가격들, ItemPrice=선택한 아이템 가격
                j += 1;
                //Toast.makeText(getApplicationContext(), selectItem+"를 장바구니에 담았습니다.", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), "장바구니에 담았습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        // 뒤로 버튼 클릭 리스너 => 뒤로 돌아갈 때 담았던 값을 넘겨줌
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
    // 사진을 추가하기 위한 CustomAdapter
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
            if("불고기피자 16000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.pizza01);
            else if("페퍼로니피자 16000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.pizza02);
            else if("하와이안피자 18000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.pizza03);
            else if("치즈크러스트피자 17000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.pizza04);
            else if("바이트골드피자 19000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.pizza05);
            else if("포테이토피자 16000원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.pizza06);
            else if("콜라1L 1500원".equals(items.get(position)))
                imageView.setImageResource(R.drawable.pizza07);

            //사진 옆의 텍스트를 설정
            TextView textView = (TextView)v.findViewById(R.id.textView);
            textView.setText(items.get(position));

            return v;
        }
    }
}
