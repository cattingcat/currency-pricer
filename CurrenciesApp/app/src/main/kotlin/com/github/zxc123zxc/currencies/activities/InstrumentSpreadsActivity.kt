package com.github.zxc123zxc.currencies.activities

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ListView

import com.github.zxc123zxc.currencies.R
import com.github.zxc123zxc.currencies.components.FillAdapterTask
import com.github.zxc123zxc.currencies.components.SpreadAdapter

class InstrumentSpreadsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrument_spreads)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            Snackbar.make(it, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val instr = this.intent.extras.getString("InstrumentIso")
        val currInstr = this.intent.extras.get("CurrentInstrument")

        val spreadList = findViewById(R.id.spreadList) as ListView
        val adapter = SpreadAdapter(this)
        spreadList.adapter = adapter

        val task = FillAdapterTask(adapter)
        task.execute(instr)

        title = "$instr / $currInstr"
    }
}
