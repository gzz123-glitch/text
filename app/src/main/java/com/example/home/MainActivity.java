package com.example.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button login = findViewById(R.id.login);
        Button main  = findViewById(R.id.main);
        Button share  = findViewById(R.id.share);
        // 设置点击监听器
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建 Intent 对象，指定当前 Activity 和目标 Activity
                Intent intent = new Intent(MainActivity.this, loginActivity.class);
                // 启动目标 Activity
                startActivity(intent);
            }
        });
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建 Intent 对象，指定当前 Activity 和目标 Activity
                Intent intent = new Intent(MainActivity.this, MainActivity01.class);
                // 启动目标 Activity
                startActivity(intent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建 Intent 对象，指定当前 Activity 和目标 Activity
                Intent intent = new Intent(MainActivity.this, ShareEditActivity.class);
                // 启动目标 Activity
                startActivity(intent);
            }
        });

    }
}