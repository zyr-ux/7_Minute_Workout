package com.myapps.a7minuteworkout

import android.os.Bundle
import android.util.Log
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.myapps.a7minuteworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishActivity : AppCompatActivity()
{
    private var binding:ActivityFinishBinding?=null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.root?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
                insets
            }
        }
        changeStatusBarIcons()

        val historyDao = (application as WorkoutApp).db.HistoryDao()
        addDatetoDB(historyDao)
    }

    private fun changeStatusBarIcons()
    {
        val insetsController = window.insetsController
        insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
    }

    private fun addDatetoDB(historyDao: HistoryDao)
    {
        val calendar=Calendar.getInstance()
        val dateTime=calendar.time
        Log.e("Date",""+dateTime)
        val sdf=SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date=sdf.format(dateTime)
        Log.e("Formatted Date",""+date)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
        }
    }
}