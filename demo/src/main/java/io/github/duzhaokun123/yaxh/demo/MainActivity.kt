package io.github.duzhaokun123.yaxh.demo

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity: Activity() {
    companion object {
        private const val TAG = "YAXH_demo"
    }

    val str1: String? = null
    val str2: String = "something"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.tv1).apply {
            if (hookme()) {
                text = "hooked"
            }
        }
        findViewById<Button>(R.id.btn1).setOnClickListener {
            hookme2()
        }
        findViewById<Button>(R.id.btn2).setOnClickListener {
            hookme3()
        }
        findViewById<Button>(R.id.btn3).setOnClickListener {
            hookme4()
        }
        findViewById<Button>(R.id.btn4).setOnClickListener {
            val r = sum(100, 50)
            Toast.makeText(this, "sum(100, 50) = $r", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSomethingMaybeNull(): String? {
        return if (Random.nextBoolean()) "something" else null
    }

    private fun hookme(): Boolean {
        Log.d(TAG, "hookme: ")
        return false
    }

    private fun hookme2() {
        Log.d(TAG, "hookme2: ")
    }

    private fun hookme3() {
        Log.d(TAG, "hookme3: ")
    }

    private fun hookme4() {
        Log.d(TAG, "hookme4: ")
    }

    private fun sum(a: Int, b: Int): Int {
        return a + b
    }
}