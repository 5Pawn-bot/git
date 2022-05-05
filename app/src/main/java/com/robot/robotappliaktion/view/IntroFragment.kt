package com.robot.robotappliaktion.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.robot.robotappliaktion.R
import com.robot.robotappliaktion.databinding.FragmentIntroBinding
import com.robot.robotappliaktion.util.RobotHelper
import com.robot.robotappliaktion.util.createSimplePhraseSet
import com.robot.robotappliaktion.viewmodel.QiContextViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IntroFragment : Fragment() {

    // Only necessary in first fragment accessed by navigation controller.
    // To make sure qiContext is there for actions on the robot.
    private var qiContextViewModel: QiContextViewModel? = null

    // For every fragment
    private lateinit var introFragmentBinding: FragmentIntroBinding
    private val robotHelper = RobotHelper()
    private var mainActivity: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(TAG, "On create View")

        qiContextViewModel =
            activity?.let { ViewModelProviders.of(it).get(QiContextViewModel::class.java) }

        introFragmentBinding = DataBindingUtil.inflate<FragmentIntroBinding>(
            inflater,
            R.layout.fragment_intro,
            container,
            false
        )

        mainActivity = (activity as MainActivity)

        return introFragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        qiContextViewModel?.let {
            if (it.qiContextValue() != null && it.qiContextValue()!!) {
                runIntroInit()
            } else {
                it.observeQiContext.observe(viewLifecycleOwner, qiContextObserver)
            }
        }
    }

    private val qiContextObserver = Observer<Boolean> {
        if (it) {
            runIntroInit()
        }
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

    private fun runIntroInit() {

        // Coroutine Sample to reach Main and IO thread in one Method...

        // To reach main/ui thread to do tablet/design/
        CoroutineScope(Dispatchers.Main).launch {
            Log.i(TAG, "runIntroInit")

            // Sample to use dataBinding...
            introFragmentBinding.introGreeting.text = "Hallo Leute!"

            //introFragmentBinding.introViewContainer.visibility = View.GONE

            // Get access to mainActivity and QiContext
            mainActivity?.getQiContext()?.let { qiContext ->

                // Switch to IO Thread for robot related Stuff...
                CoroutineScope(Dispatchers.IO).launch {

                    // Synchronous sample...
                    robotHelper.animate(qiContext, R.raw.hello_a010)

                    val answerSet = arrayListOf(
                        arrayOf("Antwort 1","eins","oans"),
                        arrayOf("Antwort 2", "zwei","zwo"),
                        arrayOf("Antwort 3"  )
                    )
//
//                   // val answerSet = createSimplePhraseSet()
//
//                    when(robotHelper.listen(qiContext, answerSet)){
//                        0 ->{
//                            // go for first option
//                        }
//                        1 ->{
//                            // go for second option
//
//                        }
//                        2 ->{
//                            // go for third option
//                        }
//                    }

                    robotHelper.listenAsync(qiContext,answerSet).andThenConsume {answerOption ->
                        when(answerOption){
                        0 ->{
                            // go for first option
                        }
                        1 ->{
                            // go for second option

                        }
                        2 ->{
                            // go for third option
                        }
                    }


                    }.let { listenFuture -> mainActivity?.setChatFuture(listenFuture) }

                    // Asynchronous with Future sample.
                    robotHelper.sayAsync(qiContext, "Hallo! Freut mich dich zu sehen.")
                        ?.andThenConsume { // sample of future chaining...

                            //Get here when future done (not cancelled).
                            navigateToMainCategoryFragment()
                        }
                        ?.let { sayFuture -> mainActivity?.setSayFuture(sayFuture) } // sample to set future in mainActivity
                }
            }
        }
    }

    private fun navigateToMainCategoryFragment() {
        CoroutineScope(Dispatchers.IO).launch {

            //possible place to check for futures to cancel.

            CoroutineScope(Dispatchers.Main).launch {

                Navigation.findNavController(introFragmentBinding.root)
                    .navigate(R.id.connectToMainActivityFragment)
            }
        }
    }

    companion object {

        private const val TAG = "IntroFragment"
    }
}