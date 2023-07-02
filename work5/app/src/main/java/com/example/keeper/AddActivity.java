package com.example.keeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keeper.bean.Note;
import com.example.keeper.database.noteDbOpHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    private EditText etTitle;
    private EditText etContent;
    private noteDbOpHelper mNoteDbOpHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局文件
        setContentView(R.layout.activity_add);

        // 获取标题和内容的输入框
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        // 创建数据库操作对象
        mNoteDbOpHelper = new noteDbOpHelper(this);
    }

    // 添加按钮的点击事件，将用户输入的数据插入到数据库中
    public void add(View view) {
        // 获取标题和内容
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        // 判断标题是否为空，如果为空，提示用户并返回
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this,"内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        // 创建一个Note对象，设置标题，内容和创建时间
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedTime(getCurrentTimeFormat());
        // 调用数据库操作对象的插入方法，返回插入的行数
        long row = mNoteDbOpHelper.insertData(note);
        // 判断插入是否成功，如果成功，提示用户并结束当前页面，如果失败，提示用户
        if(row!= -1){
            Toast.makeText(this,"添加成功:)",Toast.LENGTH_SHORT).show();
            this.finish();
        }else {
            Toast.makeText(this,"添加失败:(",Toast.LENGTH_SHORT).show();
        }
    }

    // 获取当前时间的格式化字符串
    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

}