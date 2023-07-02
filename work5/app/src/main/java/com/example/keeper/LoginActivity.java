package com.example.keeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keeper.bean.User;
import com.example.keeper.database.userDbOpHelper;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    private userDbOpHelper mDBOpenHelper;
    private TextView mTvLoginactivityRegister;
    private RelativeLayout mRlLoginactivityTop;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    private LinearLayout mLlLoginactivityTwo;
    private Button mBtLoginactivityLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initData();
        initView();
    }

    private void initData() {
        mDBOpenHelper = new userDbOpHelper(this);
    }

    private void initView() {
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.tv_loginactivity_register);
        mRlLoginactivityTop = findViewById(R.id.rl_loginactivity_top);
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        mLlLoginactivityTwo = findViewById(R.id.ll_loginactivity_two);
        mBtLoginactivityLogin.setOnClickListener(this::onClick);
        mTvLoginactivityRegister.setOnClickListener(this::onClick);
    }

    public void onClick(View view) {
        // 根据点击的控件的id，执行不同的操作
        switch (view.getId()) {
            // 如果点击的是注册按钮，跳转到注册页面，并结束当前页面
            case R.id.tv_loginactivity_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            // 如果点击的是登录按钮，获取用户输入的用户名和密码，并进行验证
            case R.id.bt_loginactivity_login:
                String name = mEtLoginactivityUsername.getText().toString().trim();
                String password = mEtLoginactivityPassword.getText().toString().trim();
                // 判断用户名和密码是否为空，如果为空，提示用户并返回
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                    // 从数据库中获取所有用户的数据
                    ArrayList<User> data = mDBOpenHelper.getAllData();
                    boolean match = false;
                    // 遍历用户数据，比较用户名和密码是否匹配
                    for (int i = 0; i < data.size(); i++) {
                        User user = data.get(i);
                        if (name.equals(user.getName()) && password.equals(user.getPassword())) {
                            // 如果匹配，设置标志为true，并跳出循环
                            match = true;
                            break;
                        } else {
                            // 如果不匹配，设置标志为false
                            match = false;
                        }
                    }
                    // 根据标志判断是否登录成功
                    if (match) {
                        // 如果成功，提示用户，并跳转到主页面，并结束当前页面
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // 如果失败，提示用户用户名或密码不正确
                        Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 如果用户名或密码为空，提示用户输入
                    Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}