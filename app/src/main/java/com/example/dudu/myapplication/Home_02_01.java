package com.example.dudu.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Home_02_01 extends AppCompatActivity {

    Calendar myCalendar = Calendar.getInstance();

    ImageView home_02_01_book_image;
    EditText home_02_01_book_name;
    EditText home_02_01_book_author;
    TextView home_02_01_book_date;
    EditText home_02_01_book_main;
    Button home_02_01_book_plus_B;
    ImageButton home_02_01_back_B;

    String set_date;
    Boolean Regeneration = false;


    static ArrayList<Home_02_02_ArrayList> home_02_02_ArrayList = new ArrayList<>();

    protected void onCreate(Bundle savedInstancesState) {

        super.onCreate(savedInstancesState);
        setContentView(R.layout.home_02_01);
        home_02_01_book_image = findViewById(R.id.home_02_01_book_image);
        home_02_01_book_name = findViewById(R.id.home_02_01_book_name);
        home_02_01_book_author = findViewById(R.id.home_02_01_author);
        home_02_01_book_date = (TextView) findViewById(R.id.home_02_01_date);
        home_02_01_book_main = findViewById(R.id.home_02_01_main);
        home_02_01_book_plus_B = findViewById(R.id.home_02_01_plus_B);
        home_02_01_back_B = findViewById(R.id.home_02_01_back_B);

        //뒤로가기
        home_02_01_back_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        //서재 등록 날짜 세팅
        Date date = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yy년 MM월 dd일 HH시 mm분", java.util.Locale.getDefault());
        set_date = dateFormat.format(date);
        home_02_01_book_date.setText(set_date);

        //등록 버튼

        findViewById(R.id.home_02_01_plus_B).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_02_01.this);
                alert_confirm.setMessage("등록하시겠습니까?").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }

                        }).setNegativeButton("네",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (home_02_01_book_name.getText().toString().length() <= 0) {

                                    MainActivity.showToast(Home_02_01.this, "제목을 입력해주세요.");

                                } else if (home_02_01_book_author.getText().toString().length() <= 0) {

                                    MainActivity.showToast(Home_02_01.this, "저자를 입력해주세요.");

                                } else if (home_02_01_book_main.getText().toString().length() <= 0) {

                                    MainActivity.showToast(Home_02_01.this, "내용를 입력해주세요.");

                                }else {

                                    home_02_02_ArrayList.add(new Home_02_02_ArrayList(R.drawable.home_02_default, home_02_01_book_name.getText().toString(), home_02_01_book_author.getText().toString(), home_02_01_book_date.getText().toString(), home_02_01_book_main.getText().toString()));

                                    Intent intent1 = new Intent(Home_02_01.this, Home_02.class);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(intent1);

                                    return;
                                }


                            }
                        });

                android.app.AlertDialog alert = alert_confirm.create();

                alert.show();

            }

        });

    }

    //임시저장 불러오기
    @Override
    protected void onResume() {
        super.onResume();

        if (Regeneration) {

            Regeneration = false;

            android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_02_01.this);
            alert_confirm.setMessage("작성 중 내용 불러오기").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent1 = new Intent(Home_02_01.this, Home_02_01.class);

                    finish();

                    startActivity(intent1);

                }

            }).setNegativeButton("네",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

//                            Log.d("책등록", "등록됨");
                            return;
                        }
                    });

            android.app.AlertDialog alert = alert_confirm.create();

            alert.show();
        }

    }

    //날짜 표시 메소드
    private void updateLabel() {
        String myFormat = "yyyy년 MM월 dd일";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        home_02_01_book_date.setText(sdf.format(myCalendar.getTime()));
    }

    //뒤로가기 오버라이드
    @Override
    public void onBackPressed() {

        //등록 중단 취소
        android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_02_01.this);
        alert_confirm.setMessage("등록을 중단 하시겠습니까?").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

            //등록중단
        }).setNegativeButton("네", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                android.app.AlertDialog.Builder alert_confirm = new android.app.AlertDialog.Builder(Home_02_01.this);
                alert_confirm.setMessage("임시 저장 하시겠습니까?").setCancelable(false).setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

                    }

                }).setNegativeButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Regeneration = true;
                        Intent intent1 = new Intent(Home_02_01.this, Home_02.class);
                        startActivity(intent1);
                        return;
                    }
                });

                android.app.AlertDialog alert = alert_confirm.create();
                alert.show();
            }
        });

        android.app.AlertDialog alert = alert_confirm.create();
        alert.show();

    }



}




// ---------------------- 캘린더 날짜 선택 -------------------------[
//            //오늘 날짜 세팅
//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth, int dayOfhour, int hourOfmin) {
//
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                updateLabel();
//
//            }
//
//        };
//
//            //선택 날짜 캐치
//        findViewById(R.id.home_02_01_date).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                new DatePickerDialog(Home_02_01.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//
//            }
//
//        });