package com.likky.json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView text;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        button=findViewById(R.id.button);
        String url = "https://www.1pig.xyz/data.json";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                Log.d(TAG, "onResponse: " + response.body().string());
                String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //方法一：Gson可以直接解析成一个List
                        parseJSONWithGSON(responseData);
                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.1pig.xyz/data.json";
                OkHttpClient okHttpClient = new OkHttpClient();
                final Request request = new Request.Builder()
                        .url(url)
                        .get()//默认就是GET请求，可以不写
                        .build();
                Call call = okHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
//                Log.d(TAG, "onFailure: ");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
//                Log.d(TAG, "onResponse: " + response.body().string());
                        String responseData = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //方法一：Gson可以直接解析成一个List
                                parseJSONWithGSONTraditional(responseData);
                                Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

            }
        });


    }

    private  void parseJSONWithGSON(String jsonData)
    {
        Gson gson=new Gson();
        List<JsonBean> applist=gson.fromJson(jsonData,new TypeToken<List<JsonBean>>(){}.getType());
        text.setText("");
        for (JsonBean json:applist)
        {
            //text.setText("id is"+json.getId()+"name is"+json.getName()+"version is"+json.getVersion());
            text.append("\n 使用简单方法解析"+"\n id is "+json.getId()+"\n name is "+json.getName()+"\n version is "+json.getVersion());
        }
    }

    private  void parseJSONWithGSONTraditional(String jsonData)
    {
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(jsonData).getAsJsonArray();

        Gson gson = new Gson();
        ArrayList<JsonBean> jsonBeanList = new ArrayList<>();

        //加强for循环遍历JsonArray
        for (JsonElement user : jsonArray) {
            //使用GSON，直接转成Bean对象
            JsonBean userBean = gson.fromJson(user, JsonBean.class);
            jsonBeanList.add(userBean);
        }
        text.setText("");
        for (JsonBean json:jsonBeanList)
        {
            //text.setText("id is"+json.getId()+"name is"+json.getName()+"version is"+json.getVersion());
            text.append("\n 使用传统方法解析"+"\n id is "+json.getId()+"\n name is "+json.getName()+"\n version is "+json.getVersion());
        }
    }
}
