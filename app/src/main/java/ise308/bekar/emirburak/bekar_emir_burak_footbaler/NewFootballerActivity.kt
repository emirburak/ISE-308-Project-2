package ise308.bekar.emirburak.bekar_emir_burak_footbaler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class NewFootballerActivity() : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    companion object {
        const val TAG = "NewFootballerActivity "
    }
    private var selectedPosition = ""
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_footballer)
        Log.i(TAG,"onCreate: new footballer activity opened...")
        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val surnameEditText = findViewById<EditText>(R.id.surnameEditText)
        val ageEditText = findViewById<EditText>(R.id.ageEditText)
        val teamNameEditText = findViewById<EditText>(R.id.teamNameEditText)
        val positionSpinner = findViewById<Spinner>(R.id.positionSpinner)
        val availableCheckBox = findViewById<CheckBox>(R.id.availableCheckBox)
        val newFootballerButton = findViewById<Button>(R.id.newFootballerButton)

        availableCheckBox.isChecked = false
        //array adapter for positionSpinner comnponent in new footballer activity.
        //array of string is in the path of values/strings/positionsArray.
        ArrayAdapter.createFromResource(applicationContext,R.array.positionsArray,android.R.layout.simple_spinner_item)
            .also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                positionSpinner.adapter = it
            }
        //this means that NewFootballerActivity inherited from AdapterView and overrided 2 functions.
        positionSpinner.onItemSelectedListener = this

        //check all edit texts filled and insert the footballer in sqlite.
        newFootballerButton.setOnClickListener {
            if(nameEditText.text.toString().isNotEmpty() && surnameEditText.text.toString().isNotEmpty()
                && teamNameEditText.text.toString().isNotEmpty() && ageEditText.text.toString().isNotEmpty()
                && selectedPosition != "Select One"){
                val name = nameEditText.text.toString()
                val surname = surnameEditText.text.toString()
                val age =  ageEditText.text.toString().toInt()
                val position = selectedPosition
                val isAvailable = if(availableCheckBox.isChecked){1}else{0}
                val teamName = teamNameEditText.text.toString()
                val sqlite = SQLiteOH(applicationContext)
                sqlite.insert(Footballer(1,name,surname,age,position,isAvailable,teamName))
                Log.i(TAG,"newFootballerButton.onClick: new footballer added.")
                finish()
            }else{
                Toast.makeText(applicationContext,"Fill All Edit Texts!",Toast.LENGTH_LONG).show()
            }
        }
    }

    //item selected listener for spinner.
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectedPosition = p0!!.getItemAtPosition(p2).toString()
    }
    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}