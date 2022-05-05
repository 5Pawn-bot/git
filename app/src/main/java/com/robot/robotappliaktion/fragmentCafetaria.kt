package com.robot.robotappliaktion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.robot.robotappliaktion.util.RobotHelper
import com.robot.robotappliaktion.view.MainActivity
import kotlinx.android.synthetic.main.fragment_blank5.view.*



/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment5.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment5 : Fragment() {

    private val robotHelper = RobotHelper()
    private var mainActivity: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_blank5, container, false)

        mainActivity = (activity as MainActivity)


        view.buttonCafeteriaBack.setOnClickListener {

            mainActivity?.getChatFuture()?.requestCancellation()

            Navigation.findNavController(view).navigate(R.id.cafeteriaToMainIT)
        }

        return view;
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

    companion object {

        private const val TAG = "FragmentCafeteria"
    }
}