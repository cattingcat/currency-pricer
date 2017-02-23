package com.currency_pricer.myapplication

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.currency_pricer.SpreadDataSource
import com.currency_pricer.SpreadItem
import com.currency_pricer.components.SpreadView
import java.util.*


class MainActivity : AppCompatActivity() {
    val list: LinearLayout by lazy {findViewById(R.id.spreadCollectionView) as LinearLayout}
    val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) as Toolbar }
    val fab: FloatingActionButton by lazy { findViewById(R.id.fab) as FloatingActionButton }
    val dataSource: SpreadDataSource = SpreadDataSource()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { onFabClick(it) }

        val task = MyAsyncTask(this)
        task.execute()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onFabClick(view: View) {
        val task = MyAsyncTask(this)
        task.execute()
        Snackbar.make(view, "Reloading", Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    private fun setupStub() {
        for(i in 1.rangeTo(5)) {
            val tmp = SpreadView(this)
            with(tmp) {
                setMaxPrice(90.0)
                brokerName = "Broker"
                updateDate = "Today"
                bid = 66.77 + i
                ask = 55.66 - i

            }
            list.addView(tmp)
        }
    }

    inner class MyAsyncTask(val ctx: Context) : AsyncTask<String, Int, Collection<SpreadItem>>() {
        override fun onPostExecute(result: Collection<SpreadItem>) {
            list.removeAllViews()

            val max = result.flatMap { listOf(it.ask, it.bid) }.max()

            for (i in result) {
                val spreadView = SpreadView(ctx)
                with(spreadView) {
                    setMaxPrice(max ?: 100.0)
                    brokerName = i.brokerName
                    updateDate = Date(i.date).toString()
                    ask = i.ask
                    bid = i.bid
                }

                list.addView(spreadView)
            }

            //setupStub()
            super.onPostExecute(result)
        }

        override fun doInBackground(vararg params: String?): Collection<SpreadItem> {
            val usdList = dataSource.loadData()["USD"]
            return usdList ?: listOf()
        }
    }
}
