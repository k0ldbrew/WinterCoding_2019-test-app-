package com.koldbrew.timetable.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;

import com.koldbrew.timetable.ConnectionManager;
import com.koldbrew.timetable.R;
import com.koldbrew.timetable.data.LectureItem;
import com.koldbrew.timetable.data.Memo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private final int ADD_NEW_LECTURE = 0;
    private final int ADD_MEMO = 1;

    private TextView[] dayOfWeek = new TextView[5];
    private TextView[] date = new TextView[5];
    private TextView textView_today;
    private TextView year_month;
    private Button prev_week;
    private Button next_week;
    private int weekOfYear;
    private int colorOfToday;

    private TextView[] monday = new TextView[22];
    private TextView[] tuesday = new TextView[22];
    private TextView[] wednesday = new TextView[22];
    private TextView[] thursday = new TextView[22];
    private TextView[] friday = new TextView[22];
    private int[] bgColors = new int[5];
    private int[] textColors = new int[5];

    private HashMap<Integer, LectureItem> lectureViewMap = new HashMap<>();
    private ArrayList<LectureItem> lectures;

    private ConnectionManager cm = new ConnectionManager();

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (lectureViewMap.containsKey(v.getId())){
                Intent intent = new Intent(MainActivity.this, SearchDetailActivity.class);
                intent.putExtra("option", "memo");
                intent.putExtra("viewId", v.getId());
                intent.putExtra("lectureInfo", lectureViewMap.get(v.getId()));
                startActivityForResult(intent, ADD_MEMO);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* 배경 색상 획득 */
        colorOfToday = getResources().getColor(R.color.colorAccent);
        bgColors[0] = getResources().getColor(R.color.light_green, getTheme());
        bgColors[1] = getResources().getColor(R.color.light_orange, getTheme());
        bgColors[2] = getResources().getColor(R.color.light_pink, getTheme());
        bgColors[3] = getResources().getColor(R.color.light_purple, getTheme());
        bgColors[4] = getResources().getColor(R.color.light_yellow, getTheme());

        textColors[0] = getResources().getColor(R.color.dark_green, getTheme());
        textColors[1] = getResources().getColor(R.color.dark_orange, getTheme());
        textColors[2] = getResources().getColor(R.color.dark_pink, getTheme());
        textColors[3] = getResources().getColor(R.color.dark_purple, getTheme());
        textColors[4] = getResources().getColor(R.color.dark_yellow, getTheme());

        /* 캘린더 UI 연결 작업 */
        dayOfWeek[0] = findViewById(R.id.day1);
        dayOfWeek[1] = findViewById(R.id.day2);
        dayOfWeek[2] = findViewById(R.id.day3);
        dayOfWeek[3] = findViewById(R.id.day4);
        dayOfWeek[4] = findViewById(R.id.day5);

        date[0] = findViewById(R.id.date1);
        date[1] = findViewById(R.id.date2);
        date[2] = findViewById(R.id.date3);
        date[3] = findViewById(R.id.date4);
        date[4] = findViewById(R.id.date5);

        next_week = findViewById(R.id.button_next);
        next_week.setText(">");
        next_week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekOfYear += 1;
                updateCalendar(weekOfYear);
            }
        });
        prev_week = findViewById(R.id.button_prev);
        prev_week.setText("<");
        prev_week.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                weekOfYear -= 1;
                updateCalendar(weekOfYear);
            }
        });
        year_month = findViewById(R.id.textView_year);
        textView_today = findViewById(R.id.textView_today);
        textView_today.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                weekOfYear = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
                updateCalendar(weekOfYear);
            }
        });
        weekOfYear = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        updateCalendar(weekOfYear);

        /* 시간표 UI 연결 작업 */
        monday[0] = findViewById(R.id.mon0);
        monday[1] = findViewById(R.id.mon_half0);
        monday[2] = findViewById(R.id.mon1);
        monday[3] = findViewById(R.id.mon_half1);
        monday[4] = findViewById(R.id.mon2);
        monday[5] = findViewById(R.id.mon_half2);
        monday[6] = findViewById(R.id.mon3);
        monday[7] = findViewById(R.id.mon_half3);
        monday[8] = findViewById(R.id.mon4);
        monday[9] = findViewById(R.id.mon_half4);
        monday[10] = findViewById(R.id.mon5);
        monday[11] = findViewById(R.id.mon_half5);
        monday[12] = findViewById(R.id.mon6);
        monday[13] = findViewById(R.id.mon_half6);
        monday[14] = findViewById(R.id.mon7);
        monday[15] = findViewById(R.id.mon_half7);
        monday[16] = findViewById(R.id.mon8);
        monday[17] = findViewById(R.id.mon_half8);
        monday[18] = findViewById(R.id.mon9);
        monday[19] = findViewById(R.id.mon_half9);
        monday[20] = findViewById(R.id.mon10);
        monday[21] = findViewById(R.id.mon_half10);

        monday[0].setOnClickListener(clickListener);
        monday[1].setOnClickListener(clickListener);
        monday[2].setOnClickListener(clickListener);
        monday[3].setOnClickListener(clickListener);
        monday[4].setOnClickListener(clickListener);
        monday[5].setOnClickListener(clickListener);
        monday[6].setOnClickListener(clickListener);
        monday[7].setOnClickListener(clickListener);
        monday[8].setOnClickListener(clickListener);
        monday[9].setOnClickListener(clickListener);
        monday[10].setOnClickListener(clickListener);
        monday[11].setOnClickListener(clickListener);
        monday[12].setOnClickListener(clickListener);
        monday[13].setOnClickListener(clickListener);
        monday[14].setOnClickListener(clickListener);
        monday[15].setOnClickListener(clickListener);
        monday[16].setOnClickListener(clickListener);
        monday[17].setOnClickListener(clickListener);
        monday[18].setOnClickListener(clickListener);
        monday[19].setOnClickListener(clickListener);
        monday[20].setOnClickListener(clickListener);
        monday[21].setOnClickListener(clickListener);

        tuesday[0] = findViewById(R.id.tue0);
        tuesday[1] = findViewById(R.id.tue_half0);
        tuesday[2] = findViewById(R.id.tue1);
        tuesday[3] = findViewById(R.id.tue_half1);
        tuesday[4] = findViewById(R.id.tue2);
        tuesday[5] = findViewById(R.id.tue_half2);
        tuesday[6] = findViewById(R.id.tue3);
        tuesday[7] = findViewById(R.id.tue_half3);
        tuesday[8] = findViewById(R.id.tue4);
        tuesday[9] = findViewById(R.id.tue_half4);
        tuesday[10] = findViewById(R.id.tue5);
        tuesday[11] = findViewById(R.id.tue_half5);
        tuesday[12] = findViewById(R.id.tue6);
        tuesday[13] = findViewById(R.id.tue_half6);
        tuesday[14] = findViewById(R.id.tue7);
        tuesday[15] = findViewById(R.id.tue_half7);
        tuesday[16] = findViewById(R.id.tue8);
        tuesday[17] = findViewById(R.id.tue_half8);
        tuesday[18] = findViewById(R.id.tue9);
        tuesday[19] = findViewById(R.id.tue_half9);
        tuesday[20] = findViewById(R.id.tue10);
        tuesday[21] = findViewById(R.id.tue_half10);

        tuesday[0].setOnClickListener(clickListener);
        tuesday[1].setOnClickListener(clickListener);
        tuesday[2].setOnClickListener(clickListener);
        tuesday[3].setOnClickListener(clickListener);
        tuesday[4].setOnClickListener(clickListener);
        tuesday[5].setOnClickListener(clickListener);
        tuesday[6].setOnClickListener(clickListener);
        tuesday[7].setOnClickListener(clickListener);
        tuesday[8].setOnClickListener(clickListener);
        tuesday[9].setOnClickListener(clickListener);
        tuesday[10].setOnClickListener(clickListener);
        tuesday[11].setOnClickListener(clickListener);
        tuesday[12].setOnClickListener(clickListener);
        tuesday[13].setOnClickListener(clickListener);
        tuesday[14].setOnClickListener(clickListener);
        tuesday[15].setOnClickListener(clickListener);
        tuesday[16].setOnClickListener(clickListener);
        tuesday[17].setOnClickListener(clickListener);
        tuesday[18].setOnClickListener(clickListener);
        tuesday[19].setOnClickListener(clickListener);
        tuesday[20].setOnClickListener(clickListener);
        tuesday[21].setOnClickListener(clickListener);

        wednesday[0] = findViewById(R.id.wed0);
        wednesday[1] = findViewById(R.id.wed_half0);
        wednesday[2] = findViewById(R.id.wed1);
        wednesday[3] = findViewById(R.id.wed_half1);
        wednesday[4] = findViewById(R.id.wed2);
        wednesday[5] = findViewById(R.id.wed_half2);
        wednesday[6] = findViewById(R.id.wed3);
        wednesday[7] = findViewById(R.id.wed_half3);
        wednesday[8] = findViewById(R.id.wed4);
        wednesday[9] = findViewById(R.id.wed_half4);
        wednesday[10] = findViewById(R.id.wed5);
        wednesday[11] = findViewById(R.id.wed_half5);
        wednesday[12] = findViewById(R.id.wed6);
        wednesday[13] = findViewById(R.id.wed_half6);
        wednesday[14] = findViewById(R.id.wed7);
        wednesday[15] = findViewById(R.id.wed_half7);
        wednesday[16] = findViewById(R.id.wed8);
        wednesday[17] = findViewById(R.id.wed_half8);
        wednesday[18] = findViewById(R.id.wed9);
        wednesday[19] = findViewById(R.id.wed_half9);
        wednesday[20] = findViewById(R.id.wed10);
        wednesday[21] = findViewById(R.id.wed_half10);

        wednesday[0].setOnClickListener(clickListener);
        wednesday[1].setOnClickListener(clickListener);
        wednesday[2].setOnClickListener(clickListener);
        wednesday[3].setOnClickListener(clickListener);
        wednesday[4].setOnClickListener(clickListener);
        wednesday[5].setOnClickListener(clickListener);
        wednesday[6].setOnClickListener(clickListener);
        wednesday[7].setOnClickListener(clickListener);
        wednesday[8].setOnClickListener(clickListener);
        wednesday[9].setOnClickListener(clickListener);
        wednesday[10].setOnClickListener(clickListener);
        wednesday[11].setOnClickListener(clickListener);
        wednesday[12].setOnClickListener(clickListener);
        wednesday[13].setOnClickListener(clickListener);
        wednesday[14].setOnClickListener(clickListener);
        wednesday[15].setOnClickListener(clickListener);
        wednesday[16].setOnClickListener(clickListener);
        wednesday[17].setOnClickListener(clickListener);
        wednesday[18].setOnClickListener(clickListener);
        wednesday[19].setOnClickListener(clickListener);
        wednesday[20].setOnClickListener(clickListener);
        wednesday[21].setOnClickListener(clickListener);

        thursday[0] = findViewById(R.id.thu0);
        thursday[1] = findViewById(R.id.thu_half0);
        thursday[2] = findViewById(R.id.thu1);
        thursday[3] = findViewById(R.id.thu_half1);
        thursday[4] = findViewById(R.id.thu2);
        thursday[5] = findViewById(R.id.thu_half2);
        thursday[6] = findViewById(R.id.thu3);
        thursday[7] = findViewById(R.id.thu_half3);
        thursday[8] = findViewById(R.id.thu4);
        thursday[9] = findViewById(R.id.thu_half4);
        thursday[10] = findViewById(R.id.thu5);
        thursday[11] = findViewById(R.id.thu_half5);
        thursday[12] = findViewById(R.id.thu6);
        thursday[13] = findViewById(R.id.thu_half6);
        thursday[14] = findViewById(R.id.thu7);
        thursday[15] = findViewById(R.id.thu_half7);
        thursday[16] = findViewById(R.id.thu8);
        thursday[17] = findViewById(R.id.thu_half8);
        thursday[18] = findViewById(R.id.thu9);
        thursday[19] = findViewById(R.id.thu_half9);
        thursday[20] = findViewById(R.id.thu10);
        thursday[21] = findViewById(R.id.thu_half10);

        thursday[0].setOnClickListener(clickListener);
        thursday[1].setOnClickListener(clickListener);
        thursday[2].setOnClickListener(clickListener);
        thursday[3].setOnClickListener(clickListener);
        thursday[4].setOnClickListener(clickListener);
        thursday[5].setOnClickListener(clickListener);
        thursday[6].setOnClickListener(clickListener);
        thursday[7].setOnClickListener(clickListener);
        thursday[8].setOnClickListener(clickListener);
        thursday[9].setOnClickListener(clickListener);
        thursday[10].setOnClickListener(clickListener);
        thursday[11].setOnClickListener(clickListener);
        thursday[12].setOnClickListener(clickListener);
        thursday[13].setOnClickListener(clickListener);
        thursday[14].setOnClickListener(clickListener);
        thursday[15].setOnClickListener(clickListener);
        thursday[16].setOnClickListener(clickListener);
        thursday[17].setOnClickListener(clickListener);
        thursday[18].setOnClickListener(clickListener);
        thursday[19].setOnClickListener(clickListener);
        thursday[20].setOnClickListener(clickListener);
        thursday[21].setOnClickListener(clickListener);

        friday[0] = findViewById(R.id.fri0);
        friday[1] = findViewById(R.id.fri_half0);
        friday[2] = findViewById(R.id.fri1);
        friday[3] = findViewById(R.id.fri_half1);
        friday[4] = findViewById(R.id.fri2);
        friday[5] = findViewById(R.id.fri_half2);
        friday[6] = findViewById(R.id.fri3);
        friday[7] = findViewById(R.id.fri_half3);
        friday[8] = findViewById(R.id.fri4);
        friday[9] = findViewById(R.id.fri_half4);
        friday[10] = findViewById(R.id.fri5);
        friday[11] = findViewById(R.id.fri_half5);
        friday[12] = findViewById(R.id.fri6);
        friday[13] = findViewById(R.id.fri_half6);
        friday[14] = findViewById(R.id.fri7);
        friday[15] = findViewById(R.id.fri_half7);
        friday[16] = findViewById(R.id.fri8);
        friday[17] = findViewById(R.id.fri_half8);
        friday[18] = findViewById(R.id.fri9);
        friday[19] = findViewById(R.id.fri_half9);
        friday[20] = findViewById(R.id.fri10);
        friday[21] = findViewById(R.id.fri_half10);

        friday[0].setOnClickListener(clickListener);
        friday[1].setOnClickListener(clickListener);
        friday[2].setOnClickListener(clickListener);
        friday[3].setOnClickListener(clickListener);
        friday[4].setOnClickListener(clickListener);
        friday[5].setOnClickListener(clickListener);
        friday[6].setOnClickListener(clickListener);
        friday[7].setOnClickListener(clickListener);
        friday[8].setOnClickListener(clickListener);
        friday[9].setOnClickListener(clickListener);
        friday[10].setOnClickListener(clickListener);
        friday[11].setOnClickListener(clickListener);
        friday[12].setOnClickListener(clickListener);
        friday[13].setOnClickListener(clickListener);
        friday[14].setOnClickListener(clickListener);
        friday[15].setOnClickListener(clickListener);
        friday[16].setOnClickListener(clickListener);
        friday[17].setOnClickListener(clickListener);
        friday[18].setOnClickListener(clickListener);
        friday[19].setOnClickListener(clickListener);
        friday[20].setOnClickListener(clickListener);
        friday[21].setOnClickListener(clickListener);

        /* 기존 유저 시간표 등록 */
        /* 서버에서 값 요청 */
        Thread _get = new Thread(){
            public void run(){
                try {
                    lectures = cm.get_user_timeline();
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        };
        _get.start();
        try {
            _get.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d("Main", "lecture size from server: " + lectures.size());
        for(LectureItem item: lectures){
            addViewNewLecture(item);
        }
    }

    public void mOnClick(View v){
        // 강의 검색 버튼 클릭
        Intent intent = new Intent(this, SearchLectureActivity.class);
        startActivityForResult(intent, ADD_NEW_LECTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch(requestCode){
                case ADD_MEMO:
                    int viewId = (int) data.getIntExtra("viewId", -1);
                    lectureViewMap.get(viewId).setMemos((ArrayList<Memo>) data.getSerializableExtra("memos"));
                    /* 후에 메모 제목 리스트를 갱신해야 할 필요성 있음. */
                    break;
                case ADD_NEW_LECTURE:
                    final LectureItem item = (LectureItem) data.getSerializableExtra("selectLecture");
                    if(addViewNewLecture(item)){
                        /* 요청 전송 */
                        new Thread(){
                            public void run() {
                                cm.request_post_timeline(item);
                            }
                        }.start();
                    }
                    else
                        Toast.makeText(this, "시간이 중복됩니다.", Toast.LENGTH_LONG).show();

            }

        }
    }

    private boolean addViewNewLecture(LectureItem item){
        // 시작점 찾기
        String[] start_time = item.getStart_time().split(":");
        int startIdx = 2*(Integer.parseInt(start_time[0])-8);
        if(Integer.parseInt(start_time[1]) >= 30){
            startIdx += 1;
        }
        // 끝점 찾기
        String[] end_time = item.getEnd_time().split(":");
        int endIdx = 2*(Integer.parseInt(start_time[0])-8);
        if(Integer.parseInt(end_time[1]) >= 30){
            endIdx += 1;
        }
        Log.d("timetable", "from: " + startIdx + ", to: " + endIdx);

        String str = item.getName() +"\n" + item.getlocation();
        for(String day: item.getDayofweek()){
            switch(day){
                case "월":
                    if(lectureViewMap.containsKey(monday[startIdx].getId()))
                        return false;
                    monday[startIdx].setText(str);
                    monday[startIdx].setTextColor(textColors[0]);
                    correctWidth(monday[startIdx], monday[startIdx].getWidth());
                    for(int i = startIdx; i <= endIdx; i++) {
                        monday[i].setBackgroundColor(bgColors[0]);
                        lectureViewMap.put(monday[i].getId(), new LectureItem(item));
                    }
                    break;
                case "화:":
                    if(lectureViewMap.containsKey(tuesday[startIdx].getId()))
                        return false;
                    tuesday[startIdx].setText(str);
                    tuesday[startIdx].setTextColor(textColors[1]);
                    correctWidth(tuesday[startIdx], tuesday[startIdx].getWidth());
                    for(int i = startIdx; i <= endIdx; i++) {
                        tuesday[i].setBackgroundColor(bgColors[1]);
                        lectureViewMap.put(tuesday[i].getId(), new LectureItem(item));
                    }
                    break;
                case "수":
                    if(lectureViewMap.containsKey(wednesday[startIdx].getId()))
                        return false;
                    wednesday[startIdx].setText(str);
                    wednesday[startIdx].setTextColor(textColors[2]);
                    correctWidth(wednesday[startIdx], wednesday[startIdx].getWidth());
                    for(int i = startIdx; i <= endIdx; i++) {
                        wednesday[i].setBackgroundColor(bgColors[2]);
                        lectureViewMap.put(wednesday[i].getId(), new LectureItem(item));
                    }
                    break;
                case "목":
                    if(lectureViewMap.containsKey(thursday[startIdx].getId()))
                        return false;
                    thursday[startIdx].setText(str);
                    thursday[startIdx].setTextColor(textColors[3]);
                    correctWidth(thursday[startIdx], thursday[startIdx].getWidth());
                    for(int i = startIdx; i <= endIdx; i++) {
                        thursday[i].setBackgroundColor(bgColors[3]);
                        lectureViewMap.put(thursday[i].getId(), new LectureItem(item));
                    }
                    break;
                case "금":
                    if(lectureViewMap.containsKey(friday[startIdx].getId()))
                        return false;
                    friday[startIdx].setText(str);
                    friday[startIdx].setTextColor(textColors[4]);
                    correctWidth(friday[startIdx], friday[startIdx].getWidth());
                    for(int i = startIdx; i <= endIdx; i++) {
                        friday[i].setBackgroundColor(bgColors[4]);
                        lectureViewMap.put(friday[i].getId(), new LectureItem(item));
                    }

            }
        }
        return true;
    }


    public void correctWidth(TextView textView, int desiredWidth)
    {
        Paint paint = new Paint();
        Rect bounds = new Rect();

        paint.setTypeface(textView.getTypeface());
        float textSize = textView.getTextSize();
        paint.setTextSize(textSize);
        String text = textView.getText().toString();
        paint.getTextBounds(text, 0, text.length(), bounds);

        while (bounds.width() > desiredWidth)
        {
            textSize--;
            paint.setTextSize(textSize);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }

        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    }

    private void updateCalendar(int _weekOfYear){
        Calendar today = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.WEEK_OF_YEAR, _weekOfYear);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("today: " + simpleDateFormat.format(cal.getTime()));

        int year = cal.get(Calendar.YEAR);
        int month = (cal.get(Calendar.MONTH)+1);

        /* 일주일 */
        String day = cal.get(Calendar.DAY_OF_MONTH) + "";
        date[0].setText(day);
        for(int i = 1; i < 5; i++){
            cal.add(Calendar.DAY_OF_MONTH, 1);
            day = cal.get(Calendar.DAY_OF_MONTH)+"";
            if(day.equals("1")){
                month += 1;
            }
            date[i].setText(day);
            date[i].setTypeface(null, Typeface.NORMAL);
            dayOfWeek[i].setTypeface(null, Typeface.NORMAL);

            /* 오늘은 색다르게 ~.~ */
            if(today.compareTo(cal) == 0){
                date[i].setTextColor(colorOfToday);
                date[i].setTypeface(null, Typeface.BOLD);
                dayOfWeek[i].setTextColor(colorOfToday);
                dayOfWeek[i].setTypeface(null, Typeface.BOLD);
            }
        }
        /* 연도 월 */
        String str = year + "년 " + month + "월";
        year_month.setText(str);
    }


}
