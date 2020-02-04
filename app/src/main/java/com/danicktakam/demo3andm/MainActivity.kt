package com.danicktakam.demo3andm

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.danicktakam.demo3andm.adapter.UserListAdapter
import com.danicktakam.demo3andm.db.ApplicationDatabase
import com.danicktakam.demo3andm.db.entity.Recipe
import com.danicktakam.demo3andm.db.entity.User
import com.danicktakam.demo3andm.db.utils.RoomConstants
import com.danicktakam.demo3andm.services.IRecipeService
import com.danicktakam.demo3andm.services.IUserService
import com.danicktakam.demo3andm.services.UserSevice
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_recipes_retrofit.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var userListAdapter: UserListAdapter
    private var db: ApplicationDatabase? = null
    private lateinit var users: MutableList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initView()
        fab.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            */
           /* val intent:Intent = Intent(applicationContext, AddUserActivity::class.java)
            intent.putExtra(RoomConstants.ACTION_USER, RoomConstants.INSERT_USER)
            startActivity(intent) */
            val impliciteIntentSendMail = Intent(Intent.ACTION_SENDTO);
            impliciteIntentSendMail.data = Uri.parse("mailto:213346@supinfo.com")
            startActivity(impliciteIntentSendMail)
        }

        Toast.makeText(this,  "On create", Toast.LENGTH_LONG).show()
    }



    fun loadingRoom(){
        users = db?.userDao()?.getAll()!!
        userListAdapter.setUserList(users)
        recyclerusers.adapter = userListAdapter
    }
    fun loadingRetrofit(){
        val retrofit = UserSevice().getRetrofitService()

        val service = retrofit.create(IUserService::class.java)

        service.getUser().enqueue(object: Callback<List<User>> {
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                loadingRoom()
                Log.e("error :",t.message)
            }

            override fun onResponse(
                call: Call<List<User>>,
                response: Response<List<User>>
            ) {
                users = response.body()!!.toList() as MutableList<User>
                /*
                    response.body()!!.forEach{
                        Log.i("Name ",it.Name)
                        students.add(it)
                    }
                */

                Log.i("Name ",users.toString())
                userListAdapter.setUserList(users)
                recyclerusers.adapter = userListAdapter
            }

        })
    }

    private fun initView() {
        db = ApplicationDatabase.getInstance(this)
        recyclerusers.layoutManager = LinearLayoutManager(this)
        userListAdapter = UserListAdapter()
        userListAdapter.onItemDetailClick = { position ->
            val intent = Intent(this, UserDetailActivity::class.java)
            intent.putExtra("user",Gson().toJson( this.users.get(position)))
            startActivity(intent)
            //DeleteUserAsync(db!!.userDao(), RoomConstants.DELETE_USER, this).execute(arrayUser[position])
        }

        var connect = true
        if(UserSevice().getIfInternetIsAvailable(this)){
            try {
                loadingRetrofit()
            } catch (ex: Exception){
                Toast.makeText(this,  "Can't connect to server", Toast.LENGTH_LONG).show()
                connect = false
            }
        }

        if(!UserSevice().getIfInternetIsAvailable(this) || !connect){
            try {
                loadingRoom()
            } catch (ex: Exception){
                Toast.makeText(this,  "Can't connect to sqlite", Toast.LENGTH_LONG).show()
            }
        }
    }




    override fun onStart() {
        super.onStart()
        Toast.makeText(this,  "On start", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this,  "On resume", Toast.LENGTH_LONG).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this,  "On pause", Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this,  "On stop", Toast.LENGTH_LONG).show()
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this,  "On restart", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this,  "On destroy", Toast.LENGTH_LONG).show()
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
        return when (item.itemId) {
            R.id.action_add_student -> {
                val intent:Intent = Intent(applicationContext, AddUserActivity::class.java)
                intent.putExtra(RoomConstants.ACTION_USER, RoomConstants.INSERT_USER)
                startActivity(intent)
                true
            }
            R.id.action_recipes -> {
                val intent:Intent = Intent(this@MainActivity, RecipeActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_recipes_retrofit -> {
                val intent:Intent = Intent(this@MainActivity, RecipesRetrofitActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
