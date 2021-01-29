package ise308.bekar.emirburak.bekar_emir_burak_footbaler

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLiteOH(var context : Context) :
    SQLiteOpenHelper(context,"FOOTBALLER_DB",null,1){
    companion object {
        const val TAG = "SQLiteOH "
    }

    //column names
    val id = "id"
    val _name = "name"
    val _surname = "surname"
    val _age = "age"
    val _position = "position"
    val _isAvailable = "is_available"
    val _teamName = "team_name"
    override fun onCreate(p0: SQLiteDatabase?){
        val sql = "CREATE TABLE footballer(" +
                "$id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$_name VARCHAR(50), $_surname VARCHAR(50)," +
                "$_age INTEGER,$_position VARCHAR(50)," +
                "$_isAvailable INTEGER,$_teamName VARCHAR(100))"
        p0!!.execSQL(sql)
        Log.i(TAG,"onCreate: Table footballer created!")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun insert(footballer: Footballer){
        val database = writableDatabase
        val isAvailable = if(footballer.isAvailableForPlaying!!){1}else{0}
        val sql = "INSERT INTO footballer ($_name,$_surname,$_age,$_position,$_isAvailable,$_teamName) " +
                "VALUES ('${footballer.name}','${footballer.surname}','${footballer.age}','${footballer.position}','${isAvailable}','${footballer.teamName}')"
        val id = database.execSQL(sql)
        Log.i(TAG,"insert: footballer with id: ${footballer.id} was inserted.")
    }

    fun readAll(): ArrayList<Footballer>{
        //read all datas, save to array list and return that array list
        val database = writableDatabase
        val list = ArrayList<Footballer>()
        val c = database.rawQuery("SELECT * FROM footballer", null)
        if(c.moveToFirst()){
            while(!c.isAfterLast){
                list.add(Footballer(c.getLong(c.getColumnIndex(id)),c.getString(c.getColumnIndex(_name)),c.getString(c.getColumnIndex(_surname)),
                    c.getInt(c.getColumnIndex(_age)),c.getString(c.getColumnIndex(_position)),
                    c.getInt(c.getColumnIndex(_isAvailable)),c.getString(c.getColumnIndex(_teamName)))
                )
                c.moveToNext()
            }
        }
        Log.i(TAG,"readAll: all footballer readed and added to arraylist!")
        return list
    }

    fun deleteFootballer(footballer : Footballer){
        val database = this.writableDatabase
        val query = "DELETE FROM footballer WHERE ID = ${footballer.id}"
        database.execSQL(query)
        Log.i(TAG,"deleteFootballer: footballer with id: ${footballer.id} was deleted.")
    }

    fun updateFootballer(footballer:Footballer){
        //update all the columns of parameter footballer
        val changeID = footballer.id
        val isAvailable = if(footballer.isAvailableForPlaying!!){1}else{0}
        val database = this.writableDatabase
        var sql = "UPDATE footballer SET $_name = '${footballer.name}' WHERE $id = $changeID"
        database.execSQL(sql)
        var sql2 = "UPDATE footballer SET $_surname = '${footballer.surname}' WHERE $id = $changeID"
        database.execSQL(sql2)
        var sql3 = "UPDATE footballer SET $_age = '${footballer.age}' WHERE $id = $changeID"
        database.execSQL(sql3)
        var sql4 = "UPDATE footballer SET $_teamName = '${footballer.teamName}' WHERE $id = $changeID"
        database.execSQL(sql4)
        var sql5 = "UPDATE footballer SET $_position = '${footballer.position}' WHERE $id = $changeID"
        database.execSQL(sql5)
        var sql6 = "UPDATE footballer SET $_isAvailable = '$isAvailable' WHERE $id = $changeID"
        database.execSQL(sql6)
        Log.i(TAG,"updateFootballer: footballer with id: ${footballer.id} was updated.")
    }


}