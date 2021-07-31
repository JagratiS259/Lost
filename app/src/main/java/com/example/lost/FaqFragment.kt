package com.example.lost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FaqFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FaqFragment : Fragment() {
    // TODO: Rename and change types of parameters
    val answerList=ArrayList<Answers>()
    lateinit var recyclerFaq:RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    private var param1: String? = null
    private var param2: String? = null

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
        val view=inflater.inflate(R.layout.fragment_faq, container, false)
        recyclerFaq=view.findViewById(R.id.recyclerfaq)
        layoutManager= LinearLayoutManager(activity)
        getUserData()
        setRecyclerView()
        // Inflate the layout for this fragment
        return view
    }
    private fun setRecyclerView() {
        val answerAdapter= AnswerAdapter(answerList)
        recyclerFaq.adapter=answerAdapter
        recyclerFaq.setHasFixedSize(true)


    }

    private fun getUserData() {
        answerList.add(
            Answers(
                "Are there adoption services available?",
                "As of now adoption services are not available but you will be notified of any further changes that might take place.",

                ))
        answerList.add(Answers(
            "How can we reach out to the shelter home provider?",
            "You can directly reach out to the shelter home provider through his contact details provided."
        ))
        answerList.add(Answers(
            "How will I know whether the animal has safely reached",
            "You can track the animal through Map tracking."
        ))
        answerList.add(Answers(
            "Will my contact details be safe",
            "Your contact details will not be shared without your permission in any case."
        ))
        answerList.add(Answers(
            "Still have questions?",
            "You can directly reach out to our help and support services."
        ))
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FaqFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FaqFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}