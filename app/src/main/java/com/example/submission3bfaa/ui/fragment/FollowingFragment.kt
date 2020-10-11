package com.example.submission3bfaa.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission3bfaa.R
import com.example.submission3bfaa.ui.adapter.UserAdapter
import com.example.submission3bfaa.utils.User
import kotlinx.android.synthetic.main.fragment_following.view.*

class FollowingFragment : Fragment() {
    private var arrayList: ArrayList<User>? = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_following, container, false)
        arguments.let {
            arrayList = it?.getParcelableArrayList(LIST_PARAMS)
        }

        view.rv_following.layoutManager = LinearLayoutManager(requireContext())
        view.rv_following.adapter = UserAdapter(arrayList)
        return view
    }

    companion object {
        fun newInstance(arrayList: ArrayList<User>): FollowingFragment {
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(LIST_PARAMS, arrayList)
                }
            }
        }

        const val LIST_PARAMS = "params_list"
    }
}