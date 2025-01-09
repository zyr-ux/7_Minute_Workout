package com.myapps.a7minuteworkout

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.myapps.a7minuteworkout.databinding.ActivityBmiCalculatorBinding
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.pow

class BMI_Calculator : AppCompatActivity()
{

    private var currentVisibleView: String = METRIC_UNITS_VIEW // A variable to hold a value to make a selected view visible
    private var binding: ActivityBmiCalculatorBinding? = null

    companion object
    {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW" // Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" // US Unit View
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBmiCalculatorBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.root?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
                insets
            }
        }
        changeStatusBarIcons()
        adjustToolbarPadding()
        setSupportActionBar(binding?.actionBar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.actionBar?.setNavigationOnClickListener {
            onBackPressed()
        }

        metricSystemView()
        binding?.unitsrg?.setOnCheckedChangeListener { _, checkedID:Int ->
            if (checkedID==R.id.metricrb)
                metricSystemView()
            else
                usSystemView()
        }
        binding?.btnBmiCalculate?.setOnClickListener{
            binding?.weightet?.clearFocus()
            binding?.heightet?.clearFocus()
            binding?.heightfeet?.clearFocus()
            binding?.heightinches?.clearFocus()
            bmicalculator()
        }
    }

    private fun changeStatusBarIcons()
    {
        val insetsController = window.insetsController
        insetsController?.setSystemBarsAppearance(
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
        )
    }

    @SuppressLint("InternalInsetResource")
    private fun adjustToolbarPadding()
    {
        val statusBarHeight = resources.getDimensionPixelSize(
            resources.getIdentifier("status_bar_height", "dimen", "android")
        )
        binding?.actionBar?.setPadding(0, statusBarHeight, 0, 0)
    }

    @SuppressLint("SetTextI18n")
    private fun bmicalculator()
    {
        var bmi:Double?=null
        var roundedBmi:Double?=null
        val weight= binding?.weightet?.text?.toString()?.toDoubleOrNull()
        val height=binding?.heightet?.text?.toString()?.toDoubleOrNull()
        val heightfeet=binding?.heightfeet?.text?.toString()?.toDoubleOrNull()
        val heightinches=binding?.heightinches?.text?.toString()?.toDoubleOrNull()

        if (currentVisibleView== METRIC_UNITS_VIEW && weight!= null && height != null && height > 0)
        {
            bmi = weight / (height / 100).pow(2.0)
        }
        else if(currentVisibleView== US_UNITS_VIEW && weight!=null && heightfeet!=null && heightfeet>0 && heightinches!=null)
        {
            bmi = (weight)/ (heightfeet*12+ heightinches).pow(2.0)*703
        }
        else
        {
            binding?.bmiResultTv?.text="Enter Something"
        }
        roundedBmi = bmi?.let { BigDecimal(it).setScale(2, RoundingMode.HALF_UP).toDouble() }

        if (roundedBmi!=null)
            binding?.bmiResultTv?.text=roundedBmi.toString()

        if (roundedBmi != null)
        {
            if(roundedBmi<18.5)
                binding?.bmiClassification?.setText("Underweight")
            else if(roundedBmi<25)
                binding?.bmiClassification?.setText("Healthy")
            else if(roundedBmi<30)
                binding?.bmiClassification?.setText("Overweight")
            else
                binding?.bmiClassification?.setText("Obese")
        }
    }

    private fun metricSystemView()
    {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.weighttil?.hint="WEIGHT (in kg)"
        binding?.heightinUSunitsLL?.visibility=View.GONE
        binding?.heighttil?.visibility=View.VISIBLE
        binding?.bmiResultTv?.text=""
        binding?.bmiClassification?.text=""
        binding?.weightet?.text!!.clear()
        binding?.heightet?.text!!.clear()
        binding?.weightet?.clearFocus()
        binding?.heightet?.clearFocus()
        binding?.heightfeet?.text!!.clear()
        binding?.heightinches?.text!!.clear()
        binding?.heightfeet?.clearFocus()
        binding?.heightinches?.clearFocus()
    }

    private fun usSystemView()
    {
        currentVisibleView = US_UNITS_VIEW
        binding?.weighttil?.hint="WEIGHT (in lbs)"
        binding?.heightinUSunitsLL?.visibility=View.VISIBLE
        binding?.heighttil?.visibility=View.GONE
        binding?.bmiResultTv?.text=""
        binding?.bmiClassification?.text=""
        binding?.weightet?.text!!.clear()
        binding?.heightet?.text!!.clear()
        binding?.weightet?.clearFocus()
        binding?.heightet?.clearFocus()
        binding?.heightfeet?.text!!.clear()
        binding?.heightinches?.text!!.clear()
        binding?.heightfeet?.clearFocus()
        binding?.heightinches?.clearFocus()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        binding=null
    }
}