package com.example.filmdevelopingtimer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.isGone
import androidx.navigation.ui.AppBarConfiguration
import com.example.filmdevelopingtimer.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val buttonstop = findViewById<Button>(R.id.buttonstoptimer)
        buttonstop.visibility = View.GONE

        buttonstop.setOnClickListener {
            stopTimer()
        }

        val currenttext= findViewById<TextView>(R.id.textViewCurrenttext)
        currenttext.visibility = View.GONE

        val button = findViewById<Button>(R.id.buttonStartCounter)
        button.setOnClickListener {
            startTimer()
        }




        val spinnerminutes: Spinner = findViewById(R.id.spinnerminutes)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.minutes_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerminutes.adapter = adapter
        }


        val spinnerseconds: Spinner = findViewById(R.id.spinnerseconds)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.seconds_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerseconds.adapter = adapter
        }


        val spinnerringevery: Spinner = findViewById(R.id.spinnerringevery)
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.ringinterval_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerringevery.adapter = adapter
        }





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
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    lateinit var timer: CountDownTimer

    fun startTimer(){

        val buttonstop = findViewById<Button>(R.id.buttonstoptimer)
        buttonstop.visibility = View.VISIBLE

        val currenttext= findViewById<TextView>(R.id.textViewCurrenttext)
        currenttext.visibility = View.VISIBLE

        val spinnerminutes = findViewById<Spinner>(R.id.spinnerminutes) as Spinner
        val minutesInt = spinnerminutes.selectedItem.toString().toInt()

        val spinnerseconds = findViewById<Spinner>(R.id.spinnerminutes) as Spinner
        val secondsInt = spinnerseconds.selectedItem.toString().toInt()

        val spinnerringevery = findViewById<Spinner>(R.id.spinnerringevery) as Spinner
        val ringeveryInt = spinnerringevery.selectedItem.toString().toInt()

        val totalSeconds = minutesInt * 60 + secondsInt - 1


        val remainingseconds= findViewById<TextView>(R.id.textViewRemainingSeconds)
        remainingseconds.setText(totalSeconds.toString() + " sec")

        val totalMilliseconds = (totalSeconds * 1000).toLong()




        timer = object: CountDownTimer(totalMilliseconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeSeconds = (millisUntilFinished/1000)
                remainingseconds.setText(timeSeconds.toString())

                if (timeSeconds.toInt() % ringeveryInt == 0 && timeSeconds.toInt() != 0) {
                    playBgMusic()
                }


            }

            override fun onFinish() { // play sound
                playBgMusicFinal()
            }
        }
        timer.start()



    }




    fun playBgMusic(){
        val BgMusic = MediaPlayer.create(this, R.raw.timersound)
        BgMusic?.setOnPreparedListener{
            BgMusic?.start()
            val musicPlaying = true
        }

        BgMusic?.start()

    }




    fun playBgMusicFinal(){
        val BgMusic = MediaPlayer.create(this, R.raw.completionmusic)
        BgMusic?.setOnPreparedListener{
            BgMusic?.start()
            val musicPlaying = true
        }
        BgMusic?.start()

    }


    fun stopTimer(){
        timer.cancel()
        val buttonstop = findViewById<Button>(R.id.buttonstoptimer)
        buttonstop.visibility = View.GONE

        val currenttext= findViewById<TextView>(R.id.textViewCurrenttext)
        currenttext.visibility = View.GONE

        val remainingseconds= findViewById<TextView>(R.id.textViewRemainingSeconds)
        remainingseconds.setText("")


    }



}