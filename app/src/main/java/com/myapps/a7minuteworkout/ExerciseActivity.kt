package com.myapps.a7minuteworkout

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapps.a7minuteworkout.databinding.ActivityExerciseBinding
import com.myapps.a7minuteworkout.databinding.CustomDialogueBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener
{
    private var binding:ActivityExerciseBinding?=null
    private var CountDownTimer:CountDownTimer?=null
    private var Progress=0
    private val restTime:Long=5000
    private val exerciseTime:Long=30000
    private var exercise:ExerciseModel?=null
    private var exerciseList:ArrayList<ExerciseModel>?=null
    private var currentExcerisePos=0
    private var tts:TextToSpeech?=null
    private var player:MediaPlayer?= null
    private var exerciseStatusAdapter:ExerciseStatusAdapter?=null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.root?.let{
            ViewCompat.setOnApplyWindowInsetsListener(it) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
                insets
            }
        }

        changeStatusBarIcons()
        setSupportActionBar(binding?.actionBar)
        adjustToolbarPadding()
        if(supportActionBar!=null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        binding?.actionBar?.setNavigationOnClickListener{
            customDialogueForBackBtn()
        }

        exerciseList=Constants.defaultExceriseList()
        tts=TextToSpeech(this,this)
        setupProgressView()
        exerciseProgressRV()
    }

    @Deprecated("This method has been deprecated")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed()
    {
        customDialogueForBackBtn()
        //super.onBackPressed()
    }

    private fun customDialogueForBackBtn()
    {
        val customDialogue=Dialog(this)
        val dialogueBinding=CustomDialogueBinding.inflate(layoutInflater)
        customDialogue.setContentView(dialogueBinding.root)
        customDialogue.setCanceledOnTouchOutside(false)
        dialogueBinding.btnYes.setOnClickListener{
            this@ExerciseActivity.finish()
            customDialogue.dismiss()
        }
        dialogueBinding.btnNo.setOnClickListener {
            customDialogue.dismiss()
        }

        customDialogue.show()
    }

    private fun setupProgressView()
    {
        Log.d("ExerciseActivity", "setupProgressView called. currentExcerisePos: $currentExcerisePos")
        if (currentExcerisePos < exerciseList!!.size)
        {
            exercise=exerciseList!![currentExcerisePos]
            restProgressBar()
        }
        else
        {
            val intent=Intent(this,FinishActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun restProgressBar()
    {
        binding?.upcomingex?.visibility=View.VISIBLE
        binding?.upcomingex?.text="Upcoming : ${exercise!!.name}"
        if(currentExcerisePos>0)
            beepsound()
        binding?.exceriseimg?.visibility= View.GONE
        binding?.readyTitle?.text = "Rest and Get Ready"
        Progress = 0
        binding?.progressBar?.progress = Progress
        val restTimeinSec = restTime / 1000
        binding?.progressBar?.max = restTimeinSec.toInt()
        CountDownTimer = object : CountDownTimer(restTime, 1000)
        {
            override fun onTick(p0: Long)
            {
                Progress++
                binding?.progressBar?.progress = (restTimeinSec - Progress).toInt()
                binding?.tvtimer?.text = (restTimeinSec - Progress).toString()
            }
            override fun onFinish()
            {
                exerciseList!![currentExcerisePos].isSelected=true
                exerciseStatusAdapter!!.notifyDataSetChanged()
                exerciseProgressBar()
            }
        }.start()
    }

    private fun exerciseProgressBar()
    {
            binding?.upcomingex?.visibility = View.GONE
            binding?.exceriseimg?.visibility = View.VISIBLE
            exercise = exerciseList!![currentExcerisePos]
            binding?.readyTitle?.text = exercise?.name
            exercise?.name?.let { speakOut(it) }
            exercise?.let { binding?.exceriseimg?.setImageResource(it.pic) }
            Progress = 0
            binding?.progressBar?.progress = Progress
            val exceriseTimeinSec = exerciseTime / 1000
            binding?.progressBar?.max = exceriseTimeinSec.toInt()
            CountDownTimer = object : CountDownTimer(exerciseTime, 1000) {
                override fun onTick(p0: Long) {
                    Progress++
                    binding?.progressBar?.progress = (exceriseTimeinSec - Progress).toInt()
                    binding?.tvtimer?.text = (exceriseTimeinSec - Progress).toString()
                }

                override fun onFinish()
                {
                    exerciseList!![currentExcerisePos].isSelected=false
                    exerciseList!![currentExcerisePos].isCompleted=true
                    exerciseStatusAdapter!!.notifyDataSetChanged()
                    currentExcerisePos++
                    setupProgressView()
                }
            }.start()
    }

    private fun changeStatusBarIcons()
    {
        val insetsController = window.insetsController
        insetsController?.setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS, WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS)
    }

    private fun adjustToolbarPadding()
    {
        val statusBarHeight = resources.getDimensionPixelSize(resources.getIdentifier("status_bar_height", "dimen", "android"))
        binding?.actionBar?.setPadding(0, statusBarHeight, 0, 0)
    }

    private fun speakOut(text:String)
    {
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun beepsound()
    {
        try {
            val soundURI= Uri.parse("android.resource://com.myapps.a7minuteworkout/" + R.raw.beep)
            player=MediaPlayer.create(this@ExerciseActivity,soundURI)
            player?.isLooping=false
            player?.start()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun exerciseProgressRV()
    {
        binding?.exerciseProgressRV?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseStatusAdapter= ExerciseStatusAdapter(exerciseList!!)
        binding?.exerciseProgressRV?.adapter=exerciseStatusAdapter
    }

    override fun onInit(status: Int)
    {
        if(status==TextToSpeech.SUCCESS)
        {
            val result=tts?.setLanguage(Locale.US)

            if (result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS","The Language specified is not supported")
        }
        else
            Log.e("TTS","Initialization Failed")
    }

    override fun onDestroy()
    {
        super.onDestroy()
        if(CountDownTimer!=null)
        {
            CountDownTimer!!.cancel()
            Progress=0
        }
        if(player!=null){
            player!!.stop()
        }
        if (tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        binding=null
    }
    
}