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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapps.a7minuteworkout.databinding.ActivityHistoryPageBinding
import kotlinx.coroutines.launch

class History_Page : AppCompatActivity()
{
    private var binding:ActivityHistoryPageBinding?=null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityHistoryPageBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.root?.let {
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
                insets
            }
        }
        setSupportActionBar(binding?.toolbarHistoryActivity)
        changeStatusBarIcons()
        adjustToolbarPadding()
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.toolbarHistoryActivity?.setNavigationOnClickListener {
            onBackPressed()
        }
        val historyDao = (application as WorkoutApp).db.HistoryDao()
        getAllDates(historyDao)
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
        binding?.toolbarHistoryActivity?.setPadding(0, statusBarHeight, 0, 0)
    }

    private fun getAllDates(historyDao: HistoryDao)
    {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect { alldateslist ->
                if(!alldateslist.isNullOrEmpty())
                {
                    binding?.tvHistory?.visibility= View.VISIBLE
                    binding?.rvHistory?.visibility= View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility=View.GONE
                    binding?.rvHistory?.layoutManager=LinearLayoutManager(this@History_Page)
                    val dates=ArrayList<String>()
                    for(date in alldateslist){
                        dates.add(date.date)
                    }
                    val historyAdapter=HistoryAdapter(dates)
                    binding?.rvHistory?.adapter=historyAdapter
                }
                else
                {
                    binding?.tvHistory?.visibility= View.GONE
                    binding?.rvHistory?.visibility= View.GONE
                    binding?.tvNoDataAvailable?.visibility=View.VISIBLE
                }

            }
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        binding=null
    }
}