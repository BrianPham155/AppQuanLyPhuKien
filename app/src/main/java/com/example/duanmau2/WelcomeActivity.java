package com.example.duanmau2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Khởi tạo và bắt đầu đếm ngược
        new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Không cần làm gì trong mỗi tick
            }

            @Override
            public void onFinish() {
                // Chuyển sang màn hình đăng nhập khi đếm ngược kết thúc
                navigateToLoginActivity();
            }
        }.start();
    }

    private void navigateToLoginActivity() {
        // Tạo Intent để chuyển sang LoginActivity
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(intent);
        // Kết thúc WelcomeActivity để ngăn người dùng quay lại bằng nút back
        finish();
    }
}
