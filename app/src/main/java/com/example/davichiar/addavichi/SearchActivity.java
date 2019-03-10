package com.example.davichiar.addavichi;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticedList;
    private AsyncTask<Void, Void, String> mTask;
    private Handler mHandler;
    private int timeTask = 0;
    private float addtest1 = 0, addtest2 = 0, acttest1 = 0, acttest2 = 0, sumtest = 0;
    private String nametest = "미판정";
    private long timeCheck = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        final Button button1 = (Button) findViewById(R.id.button1);
        final TextView button1_1 = (TextView) findViewById(R.id.button1_1);
        final TextView button1_2 = (TextView) findViewById(R.id.button1_2);
        final TextView button1_3 = (TextView) findViewById(R.id.button1_3);

        final Button button2 = (Button) findViewById(R.id.button2);
        final TextView button2_1 = (TextView) findViewById(R.id.button2_1);
        final TextView button2_2 = (TextView) findViewById(R.id.button2_2);
        final TextView button2_3 = (TextView) findViewById(R.id.button2_3);

        final Button button3 = (Button) findViewById(R.id.button3);
        final TextView button3_1 = (TextView) findViewById(R.id.button3_1);
        final TextView button3_2 = (TextView) findViewById(R.id.button3_2);
        final TextView button3_3 = (TextView) findViewById(R.id.button3_3);


        noticeListView = (ListView)findViewById(R.id.noticeListView);
        noticedList = new ArrayList<Notice>();

        adapter = new NoticeListAdapter(getApplicationContext(), noticedList);
        noticeListView.setAdapter(adapter);

        final TextView writeTextView = (TextView) findViewById(R.id.writeTextView);
        final TextView readTextView = (TextView) findViewById(R.id.readTextView);
        Intent intent = getIntent();
        String name = intent.getExtras().getString("name"); /*String형*/
        writeTextView.setText("검색어 : " + name);

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                mTask = new BackgroundTask();
                mTask.execute();

                readTextView.setText("검색 수 : " + Math.round(sumtest) + "개");

                if(addtest1 > 0 && addtest2 > 0) {
                    button1_1.setText("청정게시물 : " + Math.round(addtest1) + "개");
                    button1_2.setText("광고게시물 : " + Math.round(addtest2) + "개");
                    button1_3.setText("광고퍼센트 : " + Math.round(addtest2 / sumtest * 100) + "%");

                    if (Math.round(addtest2 / sumtest * 100) > 70) {
                        button1.setBackgroundColor(getResources().getColor(R.color.colorP3));
                    }
                    else if (Math.round(addtest2 / sumtest * 100) > 40) {
                        button1.setBackgroundColor(getResources().getColor(R.color.colorP2));
                    }
                    else {
                        button1.setBackgroundColor(getResources().getColor(R.color.colorP1));
                    }
                }
                else {
                    button1_1.setText("-");
                    button1_2.setText("-");
                    button1_3.setText("-");
                }

                if(acttest1 > 0 || acttest2 > 0) {
                    button2_1.setText("긍정게시물 : " + Math.round(acttest1) + "개");
                    button2_2.setText("부정게시물 : " + Math.round(acttest2) + "개");
                    button2_3.setText("종합퍼센트 : " + Math.round(acttest1 / addtest1 * 100) + "%");

                    if (Math.round(acttest1 / addtest1 * 100) > 70) {
                        button2.setBackgroundColor(getResources().getColor(R.color.colorP1));
                    }
                    else if (Math.round(acttest1 / addtest1 * 100) > 40) {
                        button2.setBackgroundColor(getResources().getColor(R.color.colorP2));
                    }
                    else {
                        button2.setBackgroundColor(getResources().getColor(R.color.colorP3));
                    }
                }
                else {
                    button2_1.setText("-");
                    button2_2.setText("-");
                    button2_3.setText("-");
                }

                if(!nametest.equals("미판정")) {
                    String temp[] = nametest.split("'");

                    button3_1.setText("" + temp[1] + "개");
                    button3_2.setText("" + temp[3] + "개");
                    button3_3.setText("" + temp[5] + "개");
                    button3.setBackgroundColor(getResources().getColor(R.color.colorP1));
                }
                else {
                    button3_1.setText("-");
                    button3_2.setText("-");
                    button3_3.setText("-");
                }

                mHandler.sendEmptyMessageDelayed(100, 1000);
            }
        };
        mHandler.sendEmptyMessage(100);
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://davichiar1.cafe24.com/NoticeList.php";
            noticedList.clear();
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;//결과 값을 여기에 저장함
                StringBuilder stringBuilder = new StringBuilder();

                while((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        public void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                String searchTitle, searchLink, searchImglink, searchContext, searchDate, searchNicname, searchAdd, searchActive, searchText;

                timeTask = jsonArray.length();
                int count = 0;
                int addcount1 = 0, addcount2 = 0, actcount1 = 0, actcount2 = 0;
                while(count < jsonArray.length()) {

                    JSONObject object = jsonArray.getJSONObject(count);
                    searchTitle = object.getString("TITLE");
                    searchLink = object.getString("LINK");
                    searchImglink = object.getString("IMGLINK");
                    searchContext = object.getString("CONTEXT1");
                    searchDate = object.getString("DATE");
                    searchNicname = object.getString("NICNAME");
                    searchAdd = object.getString("ADD_TEXT");
                    searchActive = object.getString("ACTIVE_TEXT");
                    searchText = object.getString("TEXT");

                    if(searchAdd.equals("청정"))
                        addcount1++;
                    if(searchAdd.equals("사진 광고") || searchAdd.equals("업체 광고") || searchAdd.equals("닉네임 광고") || searchAdd.equals("글 광고"))
                        addcount2++;

                    if(searchActive.equals("긍정"))
                        actcount1++;
                    if(searchActive.equals("부정"))
                        actcount2++;

                    Notice notice = new Notice(searchTitle, searchLink, searchImglink, searchContext, searchDate, searchNicname, searchAdd, searchActive, searchText);
                    noticedList.add(notice);
                    count++;
                }
                nametest = noticedList.get(0).getSearchText();
                addtest1 = addcount1;
                addtest2 = addcount2;
                acttest1 = actcount1;
                acttest2 = actcount2;
                sumtest = noticedList.size();
                adapter.notifyDataSetChanged();

                // mHandler.removeMessages(100);
                // mHandler.sendEmptyMessage(100);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //9강때 추가된부분 백버튼을 두번 누르면 앱이 종료되게 함.
    private long pressedTime;

    @Override
    public void onBackPressed() {
        //백버튼이 눌리고 1.5초안에 또 눌리면 종료가됨
        if (pressedTime == 0) {
            Toast.makeText(SearchActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }

        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if (seconds > 2000) {
                Toast.makeText(SearchActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                pressedTime = 0;
            }
            else {
                finishAffinity();
                System.runFinalization();
                System.exit(0);
            }
        }
    }

    @Override
    public void onStop() {
        Toast.makeText(getApplicationContext(), "기존 프로세서가 종료됩니다.", Toast.LENGTH_SHORT).show();
        finishAffinity();
        System.runFinalization();
        System.exit(0);
        super.onStop();
    }
}
