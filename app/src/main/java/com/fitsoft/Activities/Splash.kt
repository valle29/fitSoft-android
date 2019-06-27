package com.fitsoft.Activities

import android.content.Intent
import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.view.View
import com.fitsoft.R
import com.fitsoft.Utils.Preferencias
import kotlinx.android.synthetic.main.splash.*

class Splash : AppCompatActivity() {

    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 2000 //2 seconds
    private val pref = Preferencias(this@Splash)

    internal val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            //Check if the user is login
            if(pref.sesion){
                startActivity(Intent(applicationContext, Login::class.java))
                finish()
            }else
            {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)
        supportActionBar?.hide()

        window.decorView.apply {
            // Hide both the navigation bar and the status bar.
            // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
            // a general rule, you should design your app to hide the status bar whenever you
            // hide the navigation bar.
            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        progressbar_splash.isIndeterminate = true
        progressbar_splash.indeterminateDrawable.setColorFilter(ContextCompat.getColor(this@Splash, R.color.white), PorterDuff.Mode.SRC_IN)

        //Initialize the Handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
    }

    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }
}
