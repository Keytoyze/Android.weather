package com.example.key.com.weatherforcasting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.content.*;
import android.view.*;

public class ShareActivity extends AppCompatActivity {
    EditText edit;
    Intent I;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        I=getIntent();
        edit=(EditText)findViewById(R.id.editText);
        edit.setText(I.getStringExtra("ShareContent"));
    }

    public void Sharing(View view){
        //以下代码参考资料：http://blog.csdn.net/cuiran/article/details/8080259
        //可是不知道怎么回事，我的手机没法分享到QQ，虽然分享到微信和短信都成功了。
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, edit.getText().toString());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, getTitle()));
    }
}
