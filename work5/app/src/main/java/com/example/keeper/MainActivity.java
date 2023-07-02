package com.example.keeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.keeper.adapter.MyAdapter;
import com.example.keeper.bean.Note;
import com.example.keeper.database.noteDbOpHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FloatingActionButton mBtnAdd;
    private List<Note> mNotes;
    private MyAdapter mMyAdapter;
    private noteDbOpHelper mNoteDbOpHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
        initEvent();
    }

    private void initEvent() {
        mMyAdapter = new MyAdapter(this,mNotes);
        mRecyclerView.setAdapter(mMyAdapter);
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    private void initData() {
        mNotes = new ArrayList<>();
        mNoteDbOpHelper = new noteDbOpHelper(this);
        mNotes = getDataFromDb();

    }

    private List<Note> getDataFromDb() {
        List<Note> queryAll = mNoteDbOpHelper.queryAllFromDb();
        return queryAll;
    }

    private String getCurrentTimeFormat() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    public void add(View view) {
        Intent intent = new Intent(this,AddActivity.class);
        startActivity(intent);
    }

    private void initView(){
        mRecyclerView = findViewById(R.id.recview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDataFromDb();
    }

    private void refreshDataFromDb() {
        mNotes = getDataFromDb();
        mMyAdapter.refreshData(mNotes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mNotes = mNoteDbOpHelper.queryFromDbByTitle(newText);
                mMyAdapter.refreshData(mNotes);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}