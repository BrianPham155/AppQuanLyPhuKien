package com.example.duanmau2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau2.DAO.NguoiDungDAO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {

    private NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nguoiDungDAO = new NguoiDungDAO(this);


        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPass = findViewById(R.id.edtPass);
        EditText edtRePass = findViewById(R.id.edtRePass);
        EditText edtEmail = findViewById(R.id.edtEmail);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView txtLogin = findViewById(R.id.txtLogin);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                String rePass= edtRePass.getText().toString();
                String email = edtEmail.getText().toString();

                if (!pass.equals(rePass)) {
                    Toast.makeText(RegisterActivity.this, " Hai mật khẩu không trùng nhau. Kiểm tra lại", Toast.LENGTH_SHORT).show();
                } else {
                    boolean check = nguoiDungDAO.Register(user, pass, email);
                    if (check){
                        Toast.makeText(RegisterActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(RegisterActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
}