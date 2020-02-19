package com.phtl.itlab.smartdacha

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.nfc.Tag
import android.provider.CalendarContract.CalendarCache.URI
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.appcompat.app.ActionBar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.material.bottomnavigation.BottomNavigationView


class mainFragment : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_songs -> {
                //toolbar.title = "Stats"
                val intent = Intent(this, statFragment::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_albums -> {
                val intent = Intent(this, mainFragment::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.content, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_fragment)
        val TAG = "MyMessage"

        // toolbar = supportActionBar!!
        val database = FirebaseDatabase.getInstance()
        val my_led = database.getReference("led")
        val my_relay = database.getReference("relay")
        val my_window = database.getReference("window")
        val tempText = database.getReference("temp")
        val humText = database.getReference("hum")
        val humEarthText = database.getReference("humEarth")
        val pirSensor = database.getReference("pir")

        val btn_led =  findViewById(R.id.led) as Switch
        val btn_relay = findViewById(R.id.relay) as Switch
        val btn_window = findViewById(R.id.window) as Switch
        val temp = findViewById(R.id.textView) as TextView
        val hum = findViewById(R.id.textView2) as TextView
        val humEarth = findViewById(R.id.textView3) as TextView
        val pirSensor_text = findViewById(R.id.textView6) as TextView
       // val btn = findViewById(R.id.button) as Button
        val led_img = findViewById<ImageView>(R.id.imageView)
        val window_img = findViewById<ImageView>(R.id.imageView2)
        val cran_img = findViewById<ImageView>(R.id.imageView3)
        val bottomNavigation: BottomNavigationView = findViewById(R.id.navigationView)


        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        tempText.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue()
                Log.d(TAG, "Value is: " + value!!)
                temp.text = "Температура: " + value.toString() + "°C"
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())


            }
        })

        humText.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue()
                Log.d(TAG, "Value is: " + value!!)
                hum.text = "Влажность: " + value.toString() + "%"
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        humEarthText.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue()
                Log.d(TAG, "Value is: " + value!!)
                humEarth.text = "Влажность почвы : " + value.toString() + "%"
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        pirSensor.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue()
                Log.d(TAG, "Value is: " + value!!)
                pirSensor_text.text = "Датчик движения: " + value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        pirSensor.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue()

                    Log.d(TAG, "Value is: " + value!!)
                  /*  val intent = Intent()
                    val pendingIntent: PendingIntent = PendingIntent.getActivity(this@mainFragment, 0, intent, 0)
                    val builder = NotificationCompat.Builder(this@mainFragment)
                        .setSmallIcon(R.drawable.ic_dashboard_black_24dp)
                        .setContentTitle("Опаньки")
                        .setContentText("Кто-то дома!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    notificationManager.notify(0, builder.build())*/

            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

        btn_led.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                my_led.setValue(1);
                led_img.setImageResource(R.mipmap.lampon);

                // Change the app background color

            } else {
                // The switch is disabled
                my_led.setValue(0);
                led_img.setImageResource(R.mipmap.lampgray);

            }
        }
        btn_relay.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                my_relay.setValue(1);
                cran_img.setImageResource(R.mipmap.craneon);
                // Change the app background color

            } else {
                // The switch is disabled
                my_relay.setValue(0);
                cran_img.setImageResource(R.mipmap.cranegray);
            }
        }
        btn_window.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                // The switch is enabled/checked
                my_window.setValue(1);
                window_img.setImageResource(R.mipmap.windowon);
                // Change the app background color

            } else {
                // The switch is disabled
                my_window.setValue(0);
                window_img.setImageResource(R.mipmap.windowgray);
            }
        }
    }



}






