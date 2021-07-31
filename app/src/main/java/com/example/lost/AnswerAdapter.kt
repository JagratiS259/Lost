package com.example.lost

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AnswerAdapter(val answerList:List<Answers>):
    RecyclerView.Adapter<AnswerAdapter.MyViewHolder>() {
    class MyViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var headingText:TextView=itemView.findViewById(R.id.HeadingText)
        var answerText:TextView=itemView.findViewById(R.id.answerText)
        var linearLayout:LinearLayout=itemView.findViewById(R.id.faqLinearLayout)
        var expandableLayout:RelativeLayout=itemView.findViewById(R.id.expandable_layout)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view:View=LayoutInflater.from(parent.context).inflate(R.layout.answer_item,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return answerList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem=answerList[position]
        holder.answerText.text=currentItem.Answer
        holder.headingText.text=currentItem.Heading

        val isExpandable:Boolean=answerList[position].expandable
        holder.expandableLayout.visibility=if(isExpandable)View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener{
            val answers=answerList[position]
            answers.expandable=!answers.expandable
            notifyItemChanged(position)
        }
    }


}