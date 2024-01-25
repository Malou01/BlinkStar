package com.example.bl_lib.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.bl_lib.MainActivity
import com.example.bl_lib.R
import com.example.bl_lib.data.MainViewModel
import com.example.bl_lib.databinding.Fragment1Binding

class Fragment1 : Fragment() {
    private val model: MainViewModel by activityViewModels()

    private var _binding: Fragment1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        _binding = Fragment1Binding.inflate(inflater, container, false)
        updateCurrentCard()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val onClickListener = View.OnClickListener { myView: View ->
            when (myView.id) {
                R.id.switch1 -> {
                    MainActivity.itemWidget.switchState1 = binding.switch1.isChecked
                    switchStateUpdate(MainActivity.itemWidget.switchState1, binding.view1, binding.imageView11, binding.imageView12, binding.switch1)
                }
                R.id.switch2 -> {
                    MainActivity.itemWidget.switchState2 = binding.switch2.isChecked
                    switchStateUpdate(MainActivity.itemWidget.switchState2, binding.view2, binding.imageView21, binding.imageView22, binding.switch2)
                }
                R.id.switch3 -> {
                    MainActivity.itemWidget.switchState3 = binding.switch3.isChecked
                    switchStateUpdate(MainActivity.itemWidget.switchState3, binding.view3, binding.imageView31, binding.imageView32, binding.switch3)
                }
                R.id.switch4 -> {
                    MainActivity.itemWidget.switchState4 = binding.switch4.isChecked
                    switchStateUpdate( MainActivity.itemWidget.switchState4 , binding.view4, binding.imageView41, binding.imageView42, binding.switch4)
                }
                R.id.switch5 -> {
                    MainActivity.itemWidget.switchState5 = binding.switch5.isChecked
                    switchStateUpdate(MainActivity.itemWidget.switchState5, binding.view5, binding.imageView51, binding.imageView52, binding.switch5)
                }
                R.id.switch6 -> {
                    MainActivity.itemWidget.switchState6 = binding.switch6.isChecked
                    switchStateUpdate(MainActivity.itemWidget.switchState6, binding.view6, binding.imageView61, binding.imageView62, binding.switch6)
                }
            }
        }
        binding.switch1.setOnClickListener(onClickListener)
        binding.switch2.setOnClickListener(onClickListener)
        binding.switch3.setOnClickListener(onClickListener)
        binding.switch4.setOnClickListener(onClickListener)
        binding.switch5.setOnClickListener(onClickListener)
        binding.switch6.setOnClickListener(onClickListener)
    }

    private fun updateCurrentCard() = with(binding){
        model.liveDataFragment1.observe(viewLifecycleOwner){
            if (it.isConnect){
                textViewTopBar1.text = "День " + it.dayNow
                textViewTopBar2.text = java.lang.String.valueOf(it.workTime / 60) + ":" + strConstructor(it.workTime % 60)
                textView12.text = it.tempNow.toString()+"℃"
                textView14.text = it.tempMax.toString()+"℃"
                textView22.text = it.humNow.toString()+"%"
                textView24.text = it.humMax.toString()+"%"
                textView32.text = it.tempEgg.toString()+"℃"
                textView34.text = it.tempEggMax.toString()+"℃"
                textView42.text = (it.airLast / 60).toString() + ":" + strConstructor(it.airLast % 60)
                textView44.text = (it.airNext / 60).toString() + ":" + strConstructor(it.airNext % 60)
                textView52.text = (it.lastRotation / 60).toString() + ":" + strConstructor(it.lastRotation % 60)
                textView54.text = (it.nextRotation / 60).toString() + ":" + strConstructor(it.nextRotation % 60)
                textView62.text = (it.coolLast / 60).toString() + ":" + strConstructor(it.coolLast % 60)
                textView64.text = (it.coolNext / 60).toString() + ":" + strConstructor(it.coolNext % 60)

                constraintLayout2.isActivated = true
                switchStateUpdate(it.switchState1, view1, imageView11, imageView12, switch1)
                switchStateUpdate(it.switchState2, view2, imageView21, imageView22, switch2)
                switchStateUpdate(it.switchState3, view3, imageView31, imageView32, switch3)
                switchStateUpdate(it.switchState4, view4, imageView41, imageView42, switch4)
                switchStateUpdate(it.switchState5, view5, imageView51, imageView52, switch5)
                switchStateUpdate(it.switchState6, view6, imageView61, imageView62, switch6)
            } else {
                constraintLayout2.isActivated = false
                updateViewDisable(view1, imageView11, imageView12, switch1)
                updateViewDisable(view2, imageView21, imageView22, switch2)
                updateViewDisable(view3, imageView31, imageView32, switch3)
                updateViewDisable(view4, imageView41, imageView42, switch4)
                updateViewDisable(view5, imageView51, imageView52, switch5)
                updateViewDisable(view6, imageView61, imageView62, switch6)
            }
        }
    }

    private fun strConstructor(number: Int): String {
        return if (number < 10) "0$number" else number.toString()
    }

    private fun switchStateUpdate(state: Boolean, constraintLayout: ConstraintLayout, imageView1: ImageView, imageView2: ImageView, switch: Switch){
        if (state){
            updateViewActive(constraintLayout, imageView1, imageView2, switch)
        } else {
            updateViewEnable(constraintLayout, imageView1, imageView2, switch)
        }
    }

    private fun updateViewDisable(constraintLayout: ConstraintLayout, imageView1: ImageView, imageView2: ImageView, switch: Switch){
        constraintLayout.isActivated = false
        constraintLayout.isPressed = false
        imageView1.setColorFilter(resources.getColor(R.color.grey))
        imageView2.setColorFilter(resources.getColor(R.color.grey))
        switch.isEnabled = false
        switch.isActivated = false
        switch.isChecked = false
    }

    private fun updateViewEnable(constraintLayout: ConstraintLayout, imageView1: ImageView, imageView2: ImageView, switch: Switch){
        constraintLayout.isActivated = false
        constraintLayout.isPressed = true
        imageView1.setColorFilter(resources.getColor(R.color.blue))
        imageView2.setColorFilter(resources.getColor(R.color.blue))
        switch.isEnabled = true
        switch.isActivated = true
        switch.isChecked = false
    }


    private fun updateViewActive(constraintLayout: ConstraintLayout, imageView1: ImageView, imageView2: ImageView, switch: Switch){
        constraintLayout.isActivated = true
        constraintLayout.isPressed = false
        imageView1.setColorFilter(resources.getColor(R.color.white))
        imageView2.setColorFilter(resources.getColor(R.color.white))
        switch.isEnabled = true
        switch.isActivated = false
        switch.isChecked = true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}