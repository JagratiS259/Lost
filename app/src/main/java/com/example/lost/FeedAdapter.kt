package com.example.lost
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FeedAdapter(private val userList: ArrayList<Feed>):RecyclerView.Adapter<FeedAdapter.MyViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem=userList[position]

        holder.condition.text= currentitem.description
        holder.landmark.text= currentitem.landmark

    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
 val condition:TextView=itemView.findViewById(R.id.txtcondition)
        val landmark:TextView=itemView.findViewById(R.id.txtlandmark)


    }
}
    /**(
}val context: Context, val feedList:MutableList<String>): RecyclerView.Adapter<FeedAdapter.DashboardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)

        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val text=feedList[position]
        holder.textView.text=text
            }

    override fun getItemCount(): Int {
        return feedList.size
    }
    class DashboardViewHolder(view:View):RecyclerView.ViewHolder(view){
        val textView:TextView=view.findViewById(R.id.txtRecyclerRowItem)
    }
}
class FeedAdapter(context: Context, feedList: MutableList<FeedModel>) : BaseAdapter() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = feedList
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val UID: String = itemList.get(position).UID as String
        val itemText: String = itemList.get(position).itemDataText as String
        val view: View
        val vh: ListRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.recycler_dashboard_single_row, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }
        vh.label.text = itemText
        return view
    }
    override fun getItem(index: Int): Any {
        return itemList.get(index)
    }
    override fun getItemId(index: Int): Long {
        return index.toLong()
    }
    override fun getCount(): Int {
        return itemList.size
    }
    private class ListRowHolder(row: View?) {
        val label: TextView = row!!.findViewById<TextView>(R.id.txtRecyclerRowItem) as TextView
    }
}**/