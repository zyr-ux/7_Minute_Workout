package com.myapps.a7minuteworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.myapps.a7minuteworkout.databinding.ItemHistoryRowBinding


class HistoryAdapter(private val items:ArrayList<String>): RecyclerView.Adapter<HistoryAdapter.Viewholder>()
{

    class Viewholder(binding: ItemHistoryRowBinding):RecyclerView.ViewHolder(binding.root)
    {
        val llhistoryitemmain=binding.llHistoryItemMain
        val tvposition=binding.tvPosition
        val tvDate=binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(ItemHistoryRowBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val date:String=items.get(position)
        holder.tvposition.text= (position+1).toString()
        holder.tvDate.text=date
        if (position % 2 == 0)
            holder.llhistoryitemmain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.gray))
        else
            holder.llhistoryitemmain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
    }
}