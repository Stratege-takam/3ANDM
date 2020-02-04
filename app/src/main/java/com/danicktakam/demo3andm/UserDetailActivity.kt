package com.danicktakam.demo3andm

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.danicktakam.demo3andm.db.ApplicationDatabase
import com.danicktakam.demo3andm.db.Dao.UserDAO
import com.danicktakam.demo3andm.db.entity.User
import com.danicktakam.demo3andm.db.utils.RoomConstants
import com.danicktakam.demo3andm.services.AsyncResponseCallback
import com.danicktakam.demo3andm.services.IUserService
import com.danicktakam.demo3andm.services.UserSevice
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_user_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class UserDetailActivity : AppCompatActivity(), AsyncResponseCallback {
     private var db: ApplicationDatabase? = null
     lateinit var user: User;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        initView()
    }

   fun initView(){
       db = ApplicationDatabase.getInstance(this)
       //Log.i("users", intent.getStringExtra("user"))
       this.user = Gson().fromJson(intent.getStringExtra("user"), User::class.java)
      // Log.i("users cast", user.toString())
       tv_user_detail_country.text = this.user.Country
       tv_user_detail_first_name.text = this.user.Firstname
       tv_user_detail_gender.text = this.user.Gender
       tv_user_detail_last_name.text = this.user.Lastname

       btn_user_detail_delete.setOnClickListener{

           // Initialize a new instance of
           val builder = AlertDialog.Builder(this@UserDetailActivity)

           // Set the alert dialog title
           builder.setTitle("Confirm Delete")

           // Display a message on alert dialog
           builder.setMessage("Are you really want to deleted this?")

           // Set a positive button and its click listener on alert dialog
           builder.setPositiveButton("YES"){dialog, which ->
               deleteUserAsync(this@UserDetailActivity,db!!.userDao(), RoomConstants.DELETE_USER, this).execute(user)
           }


           // Display a negative button on alert dialog
           builder.setNegativeButton("No"){dialog,which ->
               Toast.makeText(applicationContext,"You are not agree.",Toast.LENGTH_SHORT).show()
           }


           // Finally, make the alert dialog using builder
           val dialog: AlertDialog = builder.create()

           // Display the alert dialog on app interface
           dialog.show()
       }

       btn_user_detail_edit.setOnClickListener{
           val intent = Intent(this, AddUserActivity::class.java)
           intent.putExtra(RoomConstants.ACTION_USER, RoomConstants.UPDATE_USER)
           intent.putExtra("user", Gson().toJson(user))
           startActivity(intent)
       }
   }


    override fun onResponse(isSuccess: Boolean, call: String) {
        if (call == RoomConstants.DELETE_USER) {
            if (isSuccess) {
                val intent = Intent(this@UserDetailActivity, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@UserDetailActivity, "Successfully deleted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@UserDetailActivity, "Some error occur please try again later!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteRetrofit(id: Int = 0){
        val retrofit = UserSevice().getRetrofitService()

        val service = retrofit.create(IUserService::class.java)


        service.deleteUser(id).enqueue( object : Callback<User> {
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


    class deleteUserAsync(private val context: UserDetailActivity,private val userDao: UserDAO, private val call: String, private val responseAsyncResponse: AsyncResponseCallback) : AsyncTask<User, Void, User>() {
        override fun doInBackground(vararg user: User?): User? {
            return try {
                val myuser = user[0]!!
                userDao.delete(myuser)
                if(UserSevice().getIfInternetIsAvailable(context)){
                    try {
                        context.deleteRetrofit(myuser.Id)
                    } catch (ex: java.lang.Exception){
                        Toast.makeText(context,  "Can't edit user to server", Toast.LENGTH_LONG).show()
                    }
                }
                myuser!!
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
