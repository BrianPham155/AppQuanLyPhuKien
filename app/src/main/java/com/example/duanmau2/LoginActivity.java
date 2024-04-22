package com.example.duanmau2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duanmau2.DAO.NguoiDungDAO;
import com.example.duanmau2.util.SendMail;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity {

    private NguoiDungDAO nguoiDungDAO;
    private SendMail sendMail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText edtUser = findViewById(R.id.edtUser);
        EditText edtPass = findViewById(R.id.edtPass);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView txtForgot = findViewById(R.id.txtForgot);
        TextView txtSignUp = findViewById(R.id.txtSignUp);

        nguoiDungDAO = new NguoiDungDAO(this);
        sendMail = new SendMail();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();

                boolean check = nguoiDungDAO.CheckLogin(user, pass);
                if (check){
                    Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại. Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogForgot();
            }
        });
    }

    private void ShowDialogForgot(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_forgot, null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        EditText edtEmail = view.findViewById(R.id.edtEmail);
        Button btnSend = view.findViewById(R.id.btnSend);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String matkhau = nguoiDungDAO.ForgotPassword(email);

                if (matkhau.equals("")){
                    Toast.makeText(LoginActivity.this, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show();
                }else {
                    sendMail.Send(LoginActivity.this, email, "LẤY LẠI MẬT KHẨU", "Mật khẩu của bạn là: " + matkhau);
                    alertDialog.dismiss();
                }
            }
        });
    }
}