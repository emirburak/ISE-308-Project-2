package ise308.bekar.emirburak.bekar_emir_burak_footbaler

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FootballerAdapter internal constructor(var context : Context,var activity: Activity) : RecyclerView.Adapter<FootballerAdapter.ViewHolder>(){
    companion object {
        const val TAG = "FootballerAdapter "
    }

    private val footballerList: ArrayList<Footballer>
    private val mInflater: LayoutInflater

    init{
        mInflater = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        footballerList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        holder.addNewFootballer(footballerList[position])
    }

    override fun getItemCount(): Int{
        return footballerList.size
    }

    fun addFootballer(footballer: Footballer){
        footballerList.add(footballer)
        this.notifyDataSetChanged()
    }
    fun deleteAll(){
        this.footballerList.clear()
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(var itemLayout: View) : RecyclerView.ViewHolder(itemLayout){
        private lateinit var footballer : Footballer
        private var nameSurnameTextView : TextView
        private var ageTextView : TextView
        private var positionTextView : TextView
        private var editButton : ImageButton
        private var deleteButton : ImageButton
        //delete button
        val deleteButtonOnClickListener = View.OnClickListener {
            val alertDialogBuilder: AlertDialog.Builder = AlertDialog.Builder(activity)
            alertDialogBuilder.setTitle("Delete Footballer")
            alertDialogBuilder.setMessage("Are you sure to delete?")
            alertDialogBuilder.setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    Log.i(TAG,"deleteButtonOnClickListener: footballer delete confirmed.")
                    (activity as MainActivity).deleteFootballer(this.footballer)
            })
            alertDialogBuilder.setNegativeButton("No",
                DialogInterface.OnClickListener  { dialog, which ->
                dialog.dismiss()
            })
            alertDialogBuilder.create().show()
        }
        //edit button
        val editButtonOnClickListener = View.OnClickListener {
            Log.i(TAG,"editButtonOnClickListener: footballer edit button pressed.")
            val dialogFragment1 = DialogFragment1(this.footballer)
            dialogFragment1.show((activity as MainActivity).supportFragmentManager,null)
        }

        init{
            nameSurnameTextView = itemLayout.findViewById(R.id.nameSurnameTextView)
            ageTextView = itemLayout.findViewById(R.id.ageTextView)
            positionTextView = itemLayout.findViewById(R.id.positionTextView)
            editButton = itemLayout.findViewById(R.id.editButton)
            editButton.setOnClickListener(editButtonOnClickListener)
            deleteButton = itemLayout.findViewById(R.id.deleteButton)
            deleteButton.setOnClickListener(deleteButtonOnClickListener)
        }

        fun addNewFootballer(newFootballer:Footballer){
            Log.i(TAG,"setData: footballer informations setted.")
            this.footballer = newFootballer
            nameSurnameTextView.text = newFootballer.name+" "+newFootballer.surname
            ageTextView.text = newFootballer.age.toString()
            positionTextView.text = newFootballer.position
            //if footballer is available background color will be green otherwise gray.
            if(newFootballer.isAvailableForPlaying!!){
                this.itemLayout.setBackgroundColor(Color.GREEN)
            }else{
                this.itemLayout.setBackgroundColor(Color.GRAY)
            }
        }

    }


}