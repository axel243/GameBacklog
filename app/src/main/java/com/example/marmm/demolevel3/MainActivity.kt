package com.example.marmm.demolevel3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.support.v7.widget.helper.ItemTouchHelper
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import java.util.ArrayList

class MainActivity : AppCompatActivity(), GameAdapter.GameClickListener {
    //Local variables
    private var mGames: MutableList<Game>? = null
    private var mAdapter: GameAdapter? = null
    private var mRecyclerView: RecyclerView? = null
    private var mModifyPosition: Int = 0
    var db: AppDatabase? = null
    val TASK_GET_ALL_GAMES = 0
    val TASK_DELETE_GAME = 1
    val TASK_UPDATE_GAME = 2
    val TASK_INSERT_GAME = 3
    val EDIT_REQUEST_CODE = 1337

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        //Initialize the local variables
        mGames = ArrayList()
        mAdapter = GameAdapter(mGames!!, this, resources)
        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView!!.layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        db = AppDatabase.getInstance(this)
        GameAsyncTask(TASK_GET_ALL_GAMES).execute()
        mRecyclerView!!.adapter = mAdapter

        updateUI()
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            startActivityForResult(Intent(this@MainActivity, AddGameActivity::class.java), 1011)
        }

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            //Called when a user swipes left or right on a ViewHolder
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //Get the index corresponding to the selected position
                val position = viewHolder.adapterPosition
                val game = (mGames as ArrayList<Game>)[position]
                GameAsyncTask(TASK_DELETE_GAME).execute(game)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)

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
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    private fun updateUI() {
        if (mAdapter == null) {
            mAdapter = mGames?.let { GameAdapter(it, this, resources) }
            mRecyclerView!!.adapter = mAdapter
        } else {
            mAdapter!!.swapList(mGames!!)
        }
    }

    inner class GameAsyncTask(private val taskCode: Int) : AsyncTask<Game, Void, MutableList<Game>>() {
        override fun doInBackground(vararg games: Game): MutableList<Game> {
            when (taskCode) {

                TASK_DELETE_GAME ->
                    db!!.gameDao().deleteGames(games[0])
                TASK_UPDATE_GAME ->
                    db!!.gameDao().updateGames(games[0])
                TASK_INSERT_GAME ->
                    db!!.gameDao().insertGames(games[0])
            }
            //To return a new list with the updated data, we get all the data from the database again.
            return db!!.gameDao().allGames

        }

        override fun onPostExecute(list: MutableList<Game>) {
            super.onPostExecute(list)
            GameDbUpdated(list)
        }
    }

    fun GameDbUpdated(list: MutableList<Game>) {
        mGames = list

        updateUI()

    }

    override fun gameOnClick(i: Int) {
        val intent = Intent(this@MainActivity, AddGameActivity::class.java)
        mModifyPosition = i
        intent.putExtra(EXTRA_GAME, mGames!![i])
        startActivityForResult(intent, REQUESTCODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUESTCODE) {
            if (resultCode == Activity.RESULT_OK) {
                val addGame = data.getParcelableExtra<Game>(MainActivity.EXTRA_GAME)
                GameAsyncTask(TASK_INSERT_GAME).execute(addGame)
            }
        }else if (requestCode == EDIT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                val editGame = data.getParcelableExtra<Game>(MainActivity.EXTRA_GAME)
                GameAsyncTask(TASK_UPDATE_GAME).execute(editGame)
            }
        }
    }

    companion object {
        //Constants used when calling the update activity
        val EXTRA_GAME = "GAME"
        val REQUESTCODE = 1234
    }


}
