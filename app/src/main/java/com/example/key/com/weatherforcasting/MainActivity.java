package com.example.key.com.weatherforcasting;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.LinkedList;
import android.content.*;
public class MainActivity extends AppCompatActivity {
    LinkedList Province,City,City2;
    Spinner spinner1,spinner2,spinner3;
    Weather Now_pro,Now_city,Now_city2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //以下两行代码参考资料：http://www.cnblogs.com/sjrhero/articles/2606833.html
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().
                detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().
                detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        spinner1=(Spinner)findViewById(R.id.spinner);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        spinner3=(Spinner)findViewById(R.id.spinner3);
        renew();
    }
    public void renew(){
        Province=refresh("china",spinner1,Now_pro);//先刷新第一个spinner
        spinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Now_pro = (Weather) Province.get(arg2);
                //把第一个spinner选的内容对应的weather类传过去
                City=refresh(Now_pro.pyname,spinner2,Now_pro);
                spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        Now_city = (Weather) City.get(arg2);
                        //把第二个spinner选的内容对应的Weather类传过去
                        City2 = refresh(Now_city.pyname, spinner3,Now_city);
                        spinner3.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                       int arg2, long arg3) {
                                Now_city2 = (Weather) City2.get(arg2);
                                Toast toast = Toast.makeText(MainActivity.this, "获取城市数据成功！", Toast.LENGTH_SHORT);
                                toast.show();
                            }

                            public void onNothingSelected(AdapterView<?> arg0) {
                            }
                        });

                    }
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
    public void onSearch(View view) {
        //传递结果。
        Intent I=new Intent(this,ResultActivity.class);
        I.putExtra("shengshi", Now_pro.cityname+"-"+Now_city.cityname+"-"+Now_city2.cityname+"的天气情况");
        I.putExtra("jrqw",Now_city2.tem2 + "°C—" + Now_city2.tem1 + "°C");
        I.putExtra("tq",Now_city2.stateDetail);
        I.putExtra("dqqw",Now_city2.temNow + (Now_city2.temNow.equals("暂无实况") ? "" : "°C"));
        I.putExtra("jrfx",Now_city2.windState);
        I.putExtra("dqfx",(Now_city2.windDir.equals("暂无实况") ? "暂无实况" : Now_city2.windDir + Now_city2.windPower));
        I.putExtra("sd",Now_city2.sd);
        I.putExtra("gxsj","更新时间： "+Now_city2.time);
        startActivity(I);
    }

    public LinkedList refresh(String name, Spinner spinner,Weather NowWeather){
        //使用第三个参数的pyname属性作为城市拼音查询，把结果显示在spinner里，并且返回Weather类的集合。
        String city = name;
        LinkedList Weathers=new LinkedList();
        //C用来储存spinner的项。
        String[] C=new String[1];
        try {
            Connect Con=new Connect();
            //String data=GetHtml("http://flash.weather.com.cn/wmaps/xml/"+city+".xml");
            String data = Con.GetHtml("http://flash.weather.com.cn/wmaps/xml/" + city + ".xml");
            if (data.startsWith("<!DOCTYPE HTML>")) {
                //这一般是因为查询的城市或者省份没有下级（比如钓鱼岛），所以就把spinner置为只有原来选的这一项。
                Weathers.addLast(NowWeather);
                C[0]=NowWeather.cityname;
                //以下提示就没必要了
                //Toast toast2 = Toast.makeText(MainActivity.this, "哎呀，这个城市找不到了！", Toast.LENGTH_SHORT);
                //toast2.show();
            } else {
                //用>分割源代码
                String[] D = data.split(">");
                if (D.length >= 3) {
                    //Weathers.clear();
                    //更新C和weather类
                    C = new String[D.length - 2];
                    for (int i = 1; i <= D.length - 2; i++) {
                        Weather w = new Weather();
                        w.addweather(D[i].trim());
                        C[i - 1] = w.cityname;
                        Weathers.addLast(w);
                    }
                }
            }
            ArrayAdapter A = new ArrayAdapter(this, android.R.layout.simple_spinner_item, C);
            spinner.setAdapter(A);
        } catch (Exception e) {
            //网络异常
            Toast toast = Toast.makeText(MainActivity.this, "网络错误...请检查你的网络配置哦！", Toast.LENGTH_SHORT);
            toast.show();
        }
        return Weathers;
    }
}
