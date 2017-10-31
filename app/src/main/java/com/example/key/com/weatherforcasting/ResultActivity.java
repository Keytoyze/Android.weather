package com.example.key.com.weatherforcasting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.*;
import android.view.*;

import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {
    TextView TQ,JRQW,JRFX,DQFX,SD,CS,SJ,DQQW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        //接受数据
        TQ=(TextView)findViewById(R.id.textView4);
        JRQW=(TextView)findViewById(R.id.jrqw);
        DQQW=(TextView)findViewById(R.id.dqqw);
        JRFX=(TextView)findViewById(R.id.jrfx);
        DQFX=(TextView)findViewById(R.id.dqfx);
        SD=(TextView)findViewById(R.id.sd);
        CS=(TextView)findViewById(R.id.textView);
        SJ=(TextView)findViewById(R.id.textView17);
        Intent I=getIntent();
        CS.setText(I.getStringExtra("shengshi").toString());
        JRQW.setText(I.getStringExtra("jrqw").toString());
        DQQW.setText(I.getStringExtra("dqqw").toString());
        TQ.setText(I.getStringExtra("tq").toString());
        JRFX.setText(I.getStringExtra("jrfx").toString());
        DQFX.setText(I.getStringExtra("dqfx").toString());
        SD.setText(I.getStringExtra("sd").toString());
        SJ.setText(I.getStringExtra("gxsj").toString());
    }

    public void Share(View view){
        Intent S=new Intent(this,ShareActivity.class);
        //传递数据到分享页面的时候，先格式化一下文本。
        String Sh=getString(R.string.sharing).toString();
        Sh=Sh.replaceAll("#C",CS.getText().toString());
        Sh=Sh.replaceAll("#JRQW",JRQW.getText().toString());
        Sh=Sh.replaceAll("#DQQW",DQQW.getText().toString());
        Sh=Sh.replaceAll("#TQ",TQ.getText().toString());
        Sh=Sh.replaceAll("#JRFX",JRFX.getText().toString());
        Sh=Sh.replaceAll("#DQFX",DQFX.getText().toString());
        Sh=Sh.replaceAll("#SD",SD.getText().toString());
        Sh=Sh.replaceAll("#SJ",SJ.getText().toString());
        //传递数据
        S.putExtra("ShareContent",Sh);
        startActivity(S);
    }
}
