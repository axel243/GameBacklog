package com.example.marmm.demolevel3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import java.text.DateFormat;
import android.view.View
import android.widget.EditText
import android.widget.Toast
import android.widget.Spinner
import java.util.*

class AddGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_game_card)
        val titleInput = findViewById<EditText>(R.id.titleInput)
        val platformInput = findViewById<EditText>(R.id.platformInput)
        val notesInput = findViewById<EditText>(R.id.notesInput)
        val statusSpinner = findViewById<Spinner>(R.id.statusSpinner)
        var gameUpdate: Game? = intent.getParcelableExtra(MainActivity.EXTRA_GAME)
        if (gameUpdate != null) {
            titleInput.setText(gameUpdate.getTitleText())
            platformInput.setText(gameUpdate.getPlatform())
            notesInput.setText(gameUpdate.getNote())
            statusSpinner.setSelection(gameUpdate.getStatus()!!)
        } else {
            gameUpdate = Game("", "", "", -1, "")
        }
        val id = gameUpdate.getGameId()
        val fab = findViewById<FloatingActionButton>(R.id.saveFab)

        fab.setOnClickListener {
            val titleText = titleInput.text.toString()
            val platformText = platformInput.text.toString()
            val notesText = notesInput.text.toString()
            val status:Int = statusSpinner.selectedItemPosition

                val resultIntent = Intent()
                val dateFormat = DateFormat.getDateInstance()
                val currentDate = Date(System.currentTimeMillis())
                val game = Game(titleText, platformText, notesText, status, dateFormat.format(currentDate))
                game.setGameId(id)
                resultIntent.putExtra(MainActivity.EXTRA_GAME, game)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

