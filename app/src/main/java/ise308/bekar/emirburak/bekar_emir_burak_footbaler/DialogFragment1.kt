package ise308.bekar.emirburak.bekar_emir_burak_footbaler

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.DialogFragment

class DialogFragment1(var footballer: Footballer): DialogFragment(), AdapterView.OnItemSelectedListener{
    companion object {
        const val TAG = "DialogFragment1"
    }

    private var selectedPositionOfFootballer : String ?=  null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.i(TAG,"onCreateDialog: Footballer with id: ${footballer.id} was opened for editing.")
        val builder = AlertDialog.Builder(requireActivity())

        val v = requireActivity().layoutInflater.inflate(R.layout.dialog_layout,null)
        builder.setView(v)
                .setMessage("Edit Footballer Record!")

        val nameEdit = v.findViewById<EditText>(R.id.nameEdit)
        val surnameEdit = v.findViewById<EditText>(R.id.surnameEdit)
        val ageEdit = v.findViewById<EditText>(R.id.ageEdit)
        val teamNameEdit = v.findViewById<EditText>(R.id.teamNameEdit)
        val positionSpinner = v.findViewById<Spinner>(R.id.positionSpinnerEdit)
        val isAvailable  = v.findViewById<CheckBox>(R.id.availableCheckBoxEdit)
        nameEdit.setText(footballer.name.toString())
        surnameEdit.setText(footballer.surname.toString())
        ageEdit.setText(footballer.age.toString())
        teamNameEdit.setText(footballer.teamName.toString())
        isAvailable.isChecked = footballer.isAvailableForPlaying!!
        val adapter1   =ArrayAdapter.createFromResource(requireContext(),R.array.positionsArray,android.R.layout.simple_spinner_item)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        positionSpinner.adapter = adapter1
        positionSpinner.onItemSelectedListener = this
        //insert footballer parameters position into spinner componentç
        val footballerPositionString = footballer.position
        val positionInAdapter = adapter1.getPosition(footballerPositionString)
        positionSpinner.setSelection(positionInAdapter)
        selectedPositionOfFootballer = footballer.position

        //check all edit texts filled and update the footballer in sqlite.
        v.findViewById<Button>(R.id.editFootballerButton).setOnClickListener{
            if(nameEdit.text.toString().isNotEmpty() && surnameEdit.text.toString().isNotEmpty()
                    && teamNameEdit.text.toString().isNotEmpty() && ageEdit.text.toString().isNotEmpty()
                    && selectedPositionOfFootballer != adapter1.getItem(0).toString()){
                val db = SQLiteOH(context!!)

                footballer.name = nameEdit.text.toString()
                footballer.surname = surnameEdit.text.toString()
                footballer.age = ageEdit.text.toString().toInt()
                footballer.teamName = teamNameEdit.text.toString()
                footballer.position = selectedPositionOfFootballer
                footballer.isAvailableForPlaying = isAvailable.isChecked
                db.updateFootballer(footballer)
                (activity as MainActivity).readAll()
                dismiss()
            }else{
                Toast.makeText(requireContext(),"Fill All Edit Texts!",Toast.LENGTH_LONG).show()
            }
        }
        return builder.create()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        selectedPositionOfFootballer = p0!!.getItemAtPosition(p2).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}