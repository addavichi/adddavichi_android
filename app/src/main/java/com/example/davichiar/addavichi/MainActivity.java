package com.example.davichiar.addavichi;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;

    private AlertDialog dialog;
    private String searchText;
    private TextView writeText;
    private ImageButton loginButton;

    private String jsonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText searchEditText = (EditText) findViewById(R.id.searchEditText);
        TextView homeButton = (TextView) findViewById(R.id.homeButton);
        writeText = (TextView) findViewById(R.id.writeText);
        ImageButton searchButton = (ImageButton) findViewById(R.id.searchButton);
        loginButton = (ImageButton) findViewById(R.id.loginButton);

        writeText.setVisibility(View.GONE);

        writeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                dialog = builder.setMessage("로그아웃 하시겠습니까?")
                        .setNegativeButton("취소", null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Response.Listener<String> checkListener = new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        writeText.setVisibility(View.GONE);
                                        loginButton.setVisibility(View.VISIBLE);
                                    }
                                };

                                RegisterCheck registerCheck = new RegisterCheck(jsonText, String.valueOf(0), checkListener);
                                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                                queue.add(registerCheck);
                            }
                        })
                        .create();
                dialog.show();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEditText.setText("");
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(loginIntent);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText = searchEditText.getText().toString();
                searchEditText.setText("");

                if(searchText.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    dialog = builder.setMessage("빈 검색어는 입력이 불가능합니다.")
                            .setNegativeButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) {
                                // Toast.makeText(getApplicationContext(), searchText, Toast.LENGTH_SHORT).show();
                                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                                searchIntent.putExtra("name",searchText); /*송신*/
                                MainActivity.this.startActivity(searchIntent);
                            }
                            else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                dialog = builder.setMessage("검색을 다시 시도해주세요.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                MainRequest mainRequest = new MainRequest(searchText, String.valueOf(0), responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(mainRequest);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Response.Listener<String> checkListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    jsonText = jsonResponse.getString("userID");
                    boolean success = jsonResponse.getBoolean("success");
                    if(success) {
                        writeText.setVisibility(View.VISIBLE);
                        writeText.setText(jsonText + "님 안녕하세요!");
                        loginButton.setVisibility(View.GONE);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        LoginCheck loginCheck = new LoginCheck(checkListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(loginCheck);
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
