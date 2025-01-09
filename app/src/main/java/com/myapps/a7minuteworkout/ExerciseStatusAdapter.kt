package com.myapps.a7minuteworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.myapps.a7minuteworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items:ArrayList<ExerciseModel>):RecyclerView.Adapter<ExerciseStatusAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemExerciseStatusBinding):RecyclerView.ViewHolder(binding.root){
        val exerciseCount=binding.exercisCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemExerciseStatusBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model:ExerciseModel=items[position]
        holder.exerciseCount.text=model.id.toString()
        when{
            model.isSelected->{
                holder.exerciseCount.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_yellow)
                holder.exerciseCount.setTextColor(Color.parseColor("#FFFFFF"))
            }
            model.isCompleted->{
                holder.exerciseCount.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_color_accent_background)
                holder.exerciseCount.setTextColor(Color.parseColor("#FFFFFF"))
            }
            else ->{
                holder.exerciseCount.background=ContextCompat.getDrawable(holder.itemView.context,R.drawable.item_circular_color_grey)
                holder.exerciseCount.setTextColor(Color.parseColor("#000000"))
            }
        }
    }
}