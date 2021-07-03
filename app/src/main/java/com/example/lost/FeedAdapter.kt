package com.example.lost
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import java.util.zip.Inflater

class FeedAdapter(context: Context, feedIn:MutableList<FeedModel>): BaseAdapter() {

    private val inflater:LayoutInflater= LayoutInflater.from(context)
    private var feedList=feedIn
    private var update:Update=context as Update

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val UID:String=feedList.get(position).UID as String
        val itemTextData=feedList.get(position).itemDataText as String
        val done: Boolean=feedList.get(position).done as Boolean

        val view:View
        val viewHolder:ListViewHolder

        if(convertView==null){
            view=inflater.inflate(R.layout.newfeed,convertView,false)
            viewHolder=ListViewHolder(view)
            view.tag=viewHolder
        }
        else
        {
            view=convertView
            viewHolder=view.tag as ListViewHolder
        }
        viewHolder.textLabel.text=itemTextData
        viewHolder.isDone.isChecked=done

        viewHolder.isDone.setOnClickListener{
            update.modifyItem(UID,!done)
        }
        return view
    }
    class ListViewHolder(row: View?) {

        val textLabel:TextView=row!!.findViewById(R.id.feed_textView) as TextView
        val isDone: CheckBox=row!!.findViewById(R.id.checkbox) as CheckBox


    }

    override fun getItem(position: Int): Any {
        return feedList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return feedList.size
    }

}