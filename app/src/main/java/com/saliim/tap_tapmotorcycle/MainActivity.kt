package com.saliim.tap_tapmotorcycle

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null

    var score : Int = 0

    var imageArray = ArrayList<ImageView>()

    var handler: Handler = Handler()

    var runnable: Runnable = Runnable {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Tap Tap MotorCycle"

        score = 0

        imageArray = arrayListOf(honda1, honda2, honda3, honda4, honda5, honda6, honda7, honda8, honda9)

        for (image in imageArray){
            image.visibility = View.INVISIBLE
        }

        btn_play.setOnClickListener {
            btn_play.visibility = View.INVISIBLE
            black_bg.visibility = View.INVISIBLE
            time_bfr_play.visibility = View.VISIBLE

            object : CountDownTimer(3000, 1000){
                override fun onFinish() {
                    time_bfr_play.visibility = View.INVISIBLE
                    hideImages()
                    startGame()
                }

                override fun onTick(millisUntilFinished: Long) {
                    time_bfr_play.text = "" + millisUntilFinished / 1000
                }
            }.start()

        }

        btn_score.setOnClickListener {

            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Score")
            builder.setMessage("Your Final Score is $score.\nDo You Want to Play Again?")
            builder.setPositiveButton("Play Again"){dialog, which ->

                btn_score.visibility = View.INVISIBLE
                time_bfr_play.visibility = View.VISIBLE
                timeText.visibility = View.VISIBLE
                score = 0
                timeText.text = "Time: 10"

                object : CountDownTimer(3000, 1000){
                    override fun onFinish() {
                        time_bfr_play.visibility = View.INVISIBLE
                        hideImages()
                        startGame()
                    }

                    override fun onTick(millisUntilFinished: Long) {
                        time_bfr_play.text = "" + millisUntilFinished / 1000
                    }
                }.start()
            }
            builder.setNegativeButton("Quit"){dialog, which ->
                finish()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    }

    fun startGame(){
        object : CountDownTimer(10000, 1000){
            override fun onFinish() {
                timeText.visibility = View.INVISIBLE
                btn_score.visibility = View.VISIBLE
                handler.removeCallbacks(runnable)

                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle("Time's Up")
                builder.setMessage("Your Final Score is $score")
                builder.setPositiveButton("Play Again"){dialog, which ->

                    time_bfr_play.visibility = View.VISIBLE
                    timeText.visibility = View.VISIBLE
                    score = 0
                    timeText.text = "Time: 10"

                    object : CountDownTimer(3000, 1000){
                        override fun onFinish() {
                            time_bfr_play.visibility = View.INVISIBLE
                            hideImages()
                            startGame()
                        }

                        override fun onTick(millisUntilFinished: Long) {
                            time_bfr_play.text = "" + millisUntilFinished / 1000
                        }
                    }.start()
                }
                builder.setNegativeButton("Quit"){dialog, which ->
                    finish()
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()

            }

            override fun onTick(millisUntilFinished: Long) {
                timeText.text = "Time: " + millisUntilFinished / 1000
            }
        }.start()

    }

    fun hideImages(){

        runnable = object : Runnable{
            override fun run() {

                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                val random = Random()
                val index = random.nextInt(8 - 0)
                imageArray[index].visibility = View.VISIBLE

                handler.postDelayed(runnable, 500)

            }
        }

        handler.post(runnable)

    }

    fun increaseScore(view: View){
        score++

//        scoreText.text = "Score: $score"
    }

}
