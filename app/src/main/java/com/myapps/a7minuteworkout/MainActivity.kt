package com.myapps.a7minuteworkout

import android.content.Intent
import android.os.Bundle
import android.view.WindowInsetsController
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.myapps.a7minuteworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private var binding:ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.root?.let{
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
                insets
            }
        }
        changeStatusBarIcons()

        binding?.startBtn?.setOnClickListener{
            val intent=Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }
        binding?.BMIBtn?.setOnClickListener{
            val intent=Intent(this,BMI_Calculator::class.java)
            startActivity(intent)
        }
        binding?.HistoryBtn?.setOnClickListener{
            val intent=Intent(this,History_Page::class.java)
            startActivity(intent)
        }
    }

    private fun changeStatusBarIcons()
    {
        val insetsController = window.insetsController
        insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
    }

    override fun onDestroy()
    {
        super.onDestroy()
        binding=null
    }

}