package com.robot.robotappliaktion.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.robot.robotappliaktion.R
import com.robot.robotappliaktion.databinding.FragmentMainCategoryBinding
import com.robot.robotappliaktion.util.RobotHelper

class MainCategoryFragment : Fragment() {

    // For every fragment
    private lateinit var mainCategoryFragmentBinding: FragmentMainCategoryBinding
    private val robotHelper = RobotHelper()
    private var mainActivity: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "On create View")

        mainCategoryFragmentBinding = DataBindingUtil.inflate<FragmentMainCategoryBinding>(
            inflater,
            R.layout.fragment_main_category,
            container,
            false
        )

        mainActivity = (activity as MainActivity)

        return mainCategoryFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runMainCategoryInit()
    }

    override fun onPause() {
        super.onPause()

        // Cancel all futures before switch fragment.
        mainActivity?.let { act ->

            if (act.getChatFuture() != null)
                act.getChatFuture()?.requestCancellation()

            if (act.getSayFuture() != null)
                act.getSayFuture()?.requestCancellation()

            if (act.getAnimateFuture() != null)
                act.getAnimateFuture()?.requestCancellation()
        }
    }

    private fun runMainCategoryInit() {
        Log.i(TAG, "runMainCategoryInit")


    }


    companion object {
        private const val TAG = "MainCategoryFragment"
    }
}