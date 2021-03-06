package com.example.lost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashboardFragment : Fragment() {
    private lateinit var mDatabase: DatabaseReference
    private lateinit var feedList: ArrayList<Feed>
    private var listViewItems:ListView?=null
    lateinit var fab:FloatingActionButton
    lateinit var recyclerDashboard:RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    private var param1: String? = null
    private var param2: String? = null

    //lateinit var feedadapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vi= inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerDashboard=vi.findViewById(R.id.recyclerDashboard)
        recyclerDashboard.layoutManager=LinearLayoutManager(activity)
        recyclerDashboard.setHasFixedSize(true)

        feedList= arrayListOf<Feed>()
        getUserData()
        //feedAdapter= FeedAdapter(activity as Context,)

        return vi
    }

    private fun getUserData() {
        mDatabase = FirebaseDatabase.getInstance().getReference("Feed")
        mDatabase.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    for(feedSnapshot in snapshot.children){

                        val feed=feedSnapshot.getValue(Feed::class.java)
                        feedList.add(feed!!)

                    }
                    recyclerDashboard.adapter=FeedAdapter(feedList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}