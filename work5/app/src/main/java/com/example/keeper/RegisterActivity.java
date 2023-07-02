package com.example.keeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.keeper.database.userDbOpHelper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private userDbOpHelper mDBOpenHelper;
    private Button mBtRegisteractivityRegister;
    private RelativeLayout mRlRegisteractivityTop;
    private ImageView mIvRegisteractivityBack;
    private LinearLayout mLlRegisteractivityBody;
    private EditText mEtRegisteractivityUsername;
    private EditText mEtRegisteractivityPassword1;
    private EditText mEtRegisteractivityPassword2;
    private EditText mEtRegisteractivityPhonecodes;
    private ImageView mIvRegisteractivityShowcode;
    private RelativeLayout mRlRegisteractivityBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initData();
    }

    private void initData() {
        mDBOpenHelper = new userDbOpHelper(this);
    }

    private void initView(){
        mBtRegisteractivityRegister = findViewById(R.id.bt_registeractivity_register);
        mRlRegisteractivityTop = findViewById(R.id.rl_registeractivity_top);
        mIvRegisteractivityBack = findViewById(R.id.iv_registeractivity_back);
        mLlRegisteractivityBody = findViewById(R.id.ll_registeractivity_body);
        mEtRegisteractivityUsername = findViewById(R.id.et_registeractivity_username);
        mEtRegisteractivityPassword1 = findViewById(R.id.et_registeractivity_password1);
        mEtRegisteractivityPassword2 = findViewById(R.id.et_registeractivity_password2);
        mIvRegisteractivityBack.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        // 根据点击的控件的id，执行不同的操作
        switch (view.getId()) {
            // 如果点击的是返回按钮，跳转到登录页面，并结束当前页面
            case R.id.iv_registeractivity_back:
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            // 如果点击的是注册按钮，获取用户输入的用户名和密码，并进行验证
            case R.id.bt_registeractivity_register:
                String username = mEtRegisteractivityUsername.getText().toString().trim();
                String password = mEtRegisteractivityPassword2.getText().toString().trim();
                // 判断用户名和密码是否为空，如果不为空，向数据库中添加一条用户数据
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)  ) {
                    mDBOpenHelper.add(username, password);
                    // 跳转到登录页面，并结束当前页面
                    Intent intent2 = new Intent(this, LoginActivity.class);
                    startActivity(intent2);
                    finish();
                    // 提示用户注册成功
                    Toast.makeText(this,  "注册成功:)", Toast.LENGTH_SHORT).show();
                } else {
                    // 提示用户注册失败
                    Toast.makeText(this, "注册失败:(", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
