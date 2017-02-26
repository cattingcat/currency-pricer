package com.github.zxc123zxc.currencies.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.github.zxc123zxc.currencies.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onInstrumentChoise(v: View) {
        val i = Intent(this, InstrumentSpreadsActivity::class.java)
        i.putExtra("InstrumentIso", (v as TextView).text.toString())
        i.putExtra("CurrentInstrument", "RUR")
        startActivity(i)
    }
}
