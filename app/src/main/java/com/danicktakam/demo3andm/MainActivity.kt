package com.danicktakam.demo3andm

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.danicktakam.demo3andm.adapter.UserListAdapter
import com.danicktakam.demo3andm.db.ApplicationDatabase
import com.danicktakam.demo3andm.db.entity.User
import com.danicktakam.demo3andm.db.utils.RoomConstants
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var userListAdapter: UserListAdapter
    private var db: ApplicationDatabase? = null
    private lateinit var users: List<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        initView()
        fab.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
            */
            val intent:Intent = Intent(applicationContext, AddUserActivity::class.java)
            intent.putExtra(RoomConstants.ACTION_USER, RoomConstants.INSERT_USER)
            startActivity(intent)
        }

        Toast.makeText(this,  "On create", Toast.LENGTH_LONG).show()
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
        users = db?.userDao()?.getAll()!!
        userListAdapter.setUserList(users.toMutableList())
        recyclerusers.adapter = userListAdapter
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
            R.id.action_recipes -> {
                val intent:Intent = Intent(this@MainActivity, RecipesRetrofitActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
