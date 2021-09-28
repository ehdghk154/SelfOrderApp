package com.cookandroid.myselforderapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    static final int REQ_ADD_CONTACT = 1 ; // intent를 할 때 사용할 상수
    static final int MAX_LIST = 50; // 리스트뷰 아이템의 최대 개수
    final ArrayList<String> items = new ArrayList<String>(); // 리스트뷰에 들어갈 아이템들
    ArrayAdapter adapter; // 리스트뷰 어댑터
    String payment; // 결제방식
    int t; // 아이템 인덱스를 넣을 변수
    int checked = -1; // 어떤 인덱스의 아이템이 선택되었는지 판단을 위한 변수
    Button btn1, btn2;
    ListView lv;
    int total = 0; // 총 가격

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("무인 매뉴 주문 어플");
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        //결제버튼
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom_dialog2(view);
            }
        });
        //취소버튼
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                custom_dialog(view);
            }
        });
        for(int i = 0; i < MAX_LIST; i++){
            total_menu[i] = null;
            total_price[i] = 0;
        }
    }
    String[] total_menu = new String[MAX_LIST];
    int[] total_price = new int[MAX_LIST];
    String check_name;
    //다른 액티비티에서 받아온 값을 메인 액티비티의 리스트뷰로 추가하고 이벤트를 작동하기 위한 함수
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQ_ADD_CONTACT) {
            if (resultCode == RESULT_OK) {
                // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
                adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, items);
                // listview 생성 및 adapter 지정.

                lv = (ListView) findViewById(R.id.lv2);
                lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                lv.setAdapter(adapter);

                final String menu[] = intent.getExtras().getStringArray("menu");;
                final int price[] = intent.getExtras().getIntArray("price");
                int s= 0;
                for(int i = 0; i < menu.length; i++){
                    total += price[i];
                    if(total_menu[i] == null){
                        total_menu[i] = menu[s];
                        total_price[i] = price[s];
                        s++;
                    }else{}
                }
                System.out.println(total);
                for(int i = 0; i < menu.length; i++){
                    if(menu[i] != null) {
                        //if(items.contains(menu[i]) == false) {
                        items.add(menu[i]);
                        //}
                    } else{}
                }
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        checked = lv.getCheckedItemPosition();
                        check_name = items.get(i);
                        t = i;
                    }
                });
                adapter.notifyDataSetChanged();
            }
        }
    }
    public void custom_dialog(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.custom_dl, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        Button ok_btn = dialogView.findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checked == t) {
                    items.remove(t);
                    for(int i = 0; i < total_menu.length; i++){
                        if(total_menu[i] == check_name){
                            total -= total_price[i];
                            break;
                        }
                    }
                    Toast.makeText(getApplicationContext(), "주문이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                    lv.clearChoices();
                    adapter.notifyDataSetChanged();
                    checked = 1;
                    alertDialog.dismiss();
                }else{
                    Toast.makeText(getApplicationContext(), "취소할 주문을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                }
            }
        });

        Button cancel_btn = dialogView.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void custom_dialog2(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.custom_dl2, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView tv = (TextView) dialogView.findViewById(R.id.tv1);
        tv.setText( "총 "+ total + "원 입니다. 어떤 방식으로 결제하시겠습니까?");

        final RadioButton radio1 = (RadioButton) dialogView.findViewById(R.id.radio1);
        final RadioButton radio2 = (RadioButton) dialogView.findViewById(R.id.radio2);
        final RadioButton radio3 = (RadioButton) dialogView.findViewById(R.id.radio3);
        final RadioGroup RG = (RadioGroup) dialogView.findViewById(R.id.rg);
        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(radio1.isChecked() == true)
                    payment = "현금";
                else if(radio2.isChecked() == true)
                    payment = "카드";
                else if(radio3.isChecked() == true)
                    payment = "페이";
            }
        });

        // 결제 다이얼로그 창에서의 확인 버튼 클릭 리스너
        Button ok_btn = dialogView.findViewById(R.id.ok_btn);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payment == null){
                    Toast.makeText(getApplicationContext(), "결제 방식을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                items.clear();
                adapter.notifyDataSetChanged();
                long now = System.currentTimeMillis();
                // 현재시간을 date 변수에 저장한다.
                Date date = new Date(now);
                // 시간을 나타냇 포맷을 정한다
                SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd hh시 mm분 ss초");
                // 변수에 값을 저장한다.
                String formatDate = sdfNow.format(date);
                Toast.makeText(getApplicationContext(), formatDate+ " " + payment +"(으)로 총 " + total + "원 결제되었습니다.", Toast.LENGTH_SHORT).show();
                total = 0;
                alertDialog.dismiss();
            }
        });
        Button cancel_btn = dialogView.findViewById(R.id.cancel_btn);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
    // onClick 을 직접 지정해서 Intent를 사용
    public void onClick1(View view) {
        Intent intent = new Intent(getApplicationContext(), ChickenActivity.class);
        startActivityForResult(intent, REQ_ADD_CONTACT);
    }

    public void onClick2(View view) {
        Intent intent = new Intent(getApplicationContext(), PizzaActivity.class);
        startActivityForResult(intent, REQ_ADD_CONTACT);
    }

    public void onClick3(View view) {
        Intent intent = new Intent(getApplicationContext(), ChinaActivity.class);
        startActivityForResult(intent, REQ_ADD_CONTACT);
    }

    public void onClick4(View view) {
        Intent intent = new Intent(getApplicationContext(), KoreaActivity.class);
        startActivityForResult(intent, REQ_ADD_CONTACT);
    }

    public void onClick5(View view) {
        Intent intent = new Intent(getApplicationContext(), HamburgerActivity.class);
        startActivityForResult(intent, REQ_ADD_CONTACT);
    }

    public void onClick6(View view) {
        Intent intent = new Intent(getApplicationContext(), SnackActivity.class);
        startActivityForResult(intent, REQ_ADD_CONTACT);
    }
}