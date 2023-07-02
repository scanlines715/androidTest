package com.example.keeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.keeper.bean.Note;
import com.example.keeper.database.noteDbOpHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {
    private Note note;
    private EditText etTitle;
    private EditText etContent;
    private noteDbOpHelper mNoteDbOpHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局文件
        setContentView(R.layout.activity_edit);

        // 获取标题和内容的输入框
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);

        // 初始化数据
        initData();
    }

    // 初始化数据，从上一个页面获取传递过来的Note对象，并显示在输入框中
    private void initData() {
        Intent intent = getIntent();
        note = (Note) intent.getSerializableExtra("note");
        if (note != null) {
            etTitle.setText(note.getTitle());
            etContent.setText(note.getContent());
        }
        // 创建数据库操作对象
        mNoteDbOpHelper = new noteDbOpHelper(this);
    }

    // 保存按钮的点击事件，将用户修改的数据更新到数据库中
    public void save(View view) {
        // 获取标题和内容
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        // 判断标题是否为空，如果为空，提示用户并返回
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(this,"内容不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        // 设置Note对象的标题，内容和创建时间
        note.setTitle(title);
        note.setContent(content);
        note.setCreatedTime(getCurrentTimeFormat());
        // 调用数据库操作对象的更新方法，返回更新的行数
        long rowId = mNoteDbOpHelper.updateData(note);
        // 判断更新是否成功，如果成功，提示用户并结束当前页面，如果失败，提示用户
        if (rowId != -1) {
            Toast.makeText(this,"修改成功:)",Toast.LENGTH_SHORT).show();
            this.finish();
        }else{
            Toast.makeText(this,"修改失败:(",Toast.LENGTH_SHORT).show();
        }
    }

    // 获取当前时间的格式化字符串
    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

}