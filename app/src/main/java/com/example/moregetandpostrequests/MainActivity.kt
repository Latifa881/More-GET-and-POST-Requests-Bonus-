package com.example.moregetandpostrequests

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var etName:EditText
    lateinit var btSave:Button
    lateinit var btGet:Button
    lateinit var rvMain: RecyclerView
    val detailsArray=ArrayList<UsersItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etName=findViewById(R.id.etName)
        btSave=findViewById(R.id.btSave)
        btGet=findViewById(R.id.btGet)
        rvMain=findViewById(R.id.rvMain)
        btSave.setOnClickListener {
            val name=etName.text.toString()
            if(name.isNotEmpty()){
                addData(name)
                etName.text.clear()
            }else{
                Toast.makeText(
                    this@MainActivity,
                    "Enter a name",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        btGet.setOnClickListener {
            getAllData()
        }
        rvMain.adapter = RecyclerViewAdapter(detailsArray)
        rvMain.layoutManager = LinearLayoutManager(this)


    }
    fun addData(name: String) {
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        if (apiInterface != null) {
            apiInterface.addDetails(UsersItem("KSA",name,0))
                .enqueue(object : Callback<UsersItem> {
                    override fun onResponse(
                        call: Call<UsersItem>,
                        response: Response<UsersItem>
                    ) {
                        Toast.makeText(
                            this@MainActivity,
                            "$name is added successfully",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    override fun onFailure(call: Call<UsersItem>, t: Throwable) {
                        Toast.makeText(
                            this@MainActivity,
                            "failed to add",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("Failed oops", t.toString())

                        call.cancel()
                    }
                })

        }
    }
    fun getAllData(){
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()
        if (apiInterface != null) {

            apiInterface.getDetails().enqueue(object : Callback<Users> {
                override fun onResponse(
                    call: Call<Users>,
                    response: Response<Users>
                ) {
                    progressDialog.dismiss()
                    Log.d("TAG", response.code().toString() + "")
                    val resultArray=response.body()!!
                    for (data in resultArray) {
                        detailsArray.add(UsersItem("",data.name,0))
                    }
                    rvMain.smoothScrollToPosition(rvMain.getBottom())
                    rvMain.adapter!!.notifyDataSetChanged()


                }

                override fun onFailure(call: Call<Users>, t: Throwable) {
                    progressDialog.dismiss()
                    call.cancel()
                }
            })
        }

    }
}