package ise308.bekar.emirburak.bekar_emir_burak_footbaler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(){

    companion object {
        const val TAG = "MainActivity "
    }

    private lateinit var recyclerView : RecyclerView
    private lateinit var footballerAdapter : FootballerAdapter
    private lateinit var sqLiteOH : SQLiteOH
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        footballerAdapter = FootballerAdapter(applicationContext,this)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        recyclerView.adapter = footballerAdapter
        sqLiteOH = SQLiteOH(applicationContext)
    }
    //reads all the datas from sqlite database
    fun readAll(){
        Log.i(TAG,"readAll : all sqlite record reading...")
        footballerAdapter.deleteAll()
        sqLiteOH.readAll().also{
            for(item in it){
                footballerAdapter.addFootballer(item)
            }
        }
    }

    override fun onResume(){
        super.onResume()
        readAll()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            R.id.toolbarButton->{
                //start add activity
                Log.i(TAG,"onOptionsItemSelected : add button clicked.")
                val intent = Intent(applicationContext,NewFootballerActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    fun deleteFootballer(footballer: Footballer){
        //delete selected footballer record and read all sqlite datas.
        Log.i(TAG,"deleteFootballer : footballer with id: ${footballer.id} was deleted.")
        sqLiteOH.deleteFootballer(footballer)
        readAll()
    }
}