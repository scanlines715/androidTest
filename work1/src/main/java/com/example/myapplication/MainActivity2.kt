package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity2 : AppCompatActivity() {
    private val tag = "MainActivity2"
    val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        Log.d(tag, "callback")
        it.data?.apply {
            Log.i(tag, "result ${this.getStringExtra("result")}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "onCreate")
        setContentView(R.layout.activity_main2)
        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener{
            Log.i(tag, "on click button")
            val intent = Intent(this,SecActivity::class.java)
            intent.putExtra("name","jak")
            getContent.launch(intent)
        }

    }
    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart")
    }
    override fun onResume() {
        super.onResume()
        Log.d(tag, "onResume")
    }
    override fun onPause() {
        super.onPause()
        Log.d(tag, "onPause")
    }
    override fun onStop() {
        super.onStop()
        Log.d(tag, "onStop")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "onDestroy")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d(tag, "onRestart")
    }
}
