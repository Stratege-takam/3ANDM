package com.danicktakam.demo3andm

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.danicktakam.demo3andm.db.ApplicationDatabase
import com.danicktakam.demo3andm.db.Dao.UserDAO
import com.danicktakam.demo3andm.db.entity.User
import com.danicktakam.demo3andm.db.utils.RoomConstants
import com.danicktakam.demo3andm.services.AsyncResponseCallback
import com.danicktakam.demo3andm.services.CountryService
import com.danicktakam.demo3andm.services.IUserService
import com.danicktakam.demo3andm.services.UserSevice
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_user.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddUserActivity : AppCompatActivity(), View.OnClickListener, AsyncResponseCallback {
    var genders: MutableList<String> = ArrayList();
    var countries: MutableList<String> = ArrayList();
    lateinit var currentAction: String;
     var currentuser: User? = null ;
    private var db: ApplicationDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)
        initView()
    }

    private fun initView() {
        db = ApplicationDatabase.getInstance(this)
        genders = getResources().getStringArray(R.array.gender_array).asList() as MutableList<String>
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, genders
        )
        spner_user_add_gender.setAdapter(adapter)

        countries = CountryService(this).getAllCountry().map { it.name } as MutableList<String>
        val adapterCountry: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item, countries
        )
        spner_user_add_country.setAdapter(adapterCountry)

        this.currentAction = intent.getStringExtra(RoomConstants.ACTION_USER)
       // Log.i("action", this.currentAction )
        if (intent.getStringExtra("user") != null){
            this.currentuser = Gson().fromJson(intent.getStringExtra("user"), User::class.java)
            edv_user_add_last_name.setText(this.currentuser!!.Lastname)
            edv_user_add_first_name.setText(this.currentuser!!.Firstname)
            spner_user_add_country.setSelection(countries.indexOf(this.currentuser!!.Country ))
            spner_user_add_gender.setSelection(genders.indexOf(this.currentuser!!.Gender ))
        }else{
            edv_user_add_last_name.text.clear()
            edv_user_add_first_name.text.clear()
        }

        btn_add_user_back.setOnClickListener(this)
        btn_add_user_save.setOnClickListener(this)

    }

    override fun onClick(clickView: View?) {
        when (clickView?.id) {
            R.id.btn_add_user_save -> {
                val user = User(Firstname = edv_user_add_first_name.text.toString(),
                    Lastname = edv_user_add_last_name.text.toString().trim(),
                    Country = spner_user_add_country.getSelectedItem().toString(),
                    Gender = spner_user_add_gender.getSelectedItem().toString()
                )
                if ( this.currentuser !=null){
                    user.Id = this.currentuser!!.Id
                   // Log.i("setId", user.Id.toString()  )
                }
                SaveUserAsync(this@AddUserActivity, db!!.userDao(), this.currentAction, this).execute(user)
            }
            R.id.btn_add_user_back -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }


    override fun onResponse(isSuccess: Boolean, call: String) {
        when (call) {
            RoomConstants.INSERT_USER ->   if (isSuccess) {
                initView()
                Toast.makeText(this@AddUserActivity, "Successfully added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@AddUserActivity, "Some error occur please try again later!!!", Toast.LENGTH_SHORT).show()
            }
            RoomConstants.UPDATE_USER -> if (isSuccess) {
                val intent = Intent(this@AddUserActivity, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@AddUserActivity, "Successfully updated", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@AddUserActivity, "Some error occur please try again later!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    fun SaveRetrofit(user: User, id: Int = 0){
        val retrofit = UserSevice().getRetrofitService()

        val service = retrofit.create(IUserService::class.java)

        val req = if(id == 0) service.createUser(user) else service.updateUser(id, user)

        req.enqueue( object :Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("error :",t.message)
            }

            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                val myuser = response.body()!!

                Log.i("User ",myuser.toString())
            }

        })
    }

    class SaveUserAsync(private val context: AddUserActivity,private val userDao: UserDAO, private val call: String, private val responseAsyncResponse: AsyncResponseCallback) : AsyncTask<User, Void, User>() {
        override fun doInBackground(vararg user: User?): User? {
            return try {
                when(context.currentAction){
                    RoomConstants.UPDATE_USER -> {
                        val myuser = user[0]!!
                        userDao.updateAll(myuser)

                        if(UserSevice().getIfInternetIsAvailable(context)){
                            try {
                                context.SaveRetrofit(myuser, myuser.Id)
                            } catch (ex: java.lang.Exception){
                                Toast.makeText(context,  "Can't edit user to server", Toast.LENGTH_LONG).show()
                            }
                        }
                        myuser
                    }
                    RoomConstants.INSERT_USER -> {
                        val myuser = user[0]!!
                        userDao.insertAll(myuser)
                        if(UserSevice().getIfInternetIsAvailable(context)){
                            try {
                                context.SaveRetrofit(myuser)
                            } catch (ex: java.lang.Exception){
                                Toast.makeText(context,  "Can't add user to server", Toast.LENGTH_LONG).show()
                            }
                        }
                        myuser
                    }
                    else -> {
                        user[0]!!
                    }
                }

            } catch (ex: Exception) {
                null
            }
        }

        override fun onPostExecute(result: User?) {
            super.onPostExecute(result)
            if (result != null) {
                responseAsyncResponse.onResponse(true, call)
            } else {
                responseAsyncResponse.onResponse(false, call)
            }
        }
    }
}
