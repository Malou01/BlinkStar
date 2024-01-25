package com.example.bl_lib.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.bl_lib.*
import com.example.bl_lib.data.MainViewModel
import com.example.bl_lib.databinding.Fragment2Binding
import com.example.bl_lib.json.JsonSendBuilder
import com.example.bl_lib.json.JsonViewSet


class Fragment2 : Fragment() {
    private val model: MainViewModel by activityViewModels()

    private var listener: MyFragmentListener? = null
    interface MyFragmentListener {
        fun onInputSent(input: String?)
    }

    private var _binding: Fragment2Binding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View {
        _binding = Fragment2Binding.inflate(inflater, container, false)
        updateCurrentCard()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listMotor: MutableList<String> = ArrayList()
        listMotor.clear()
        listMotor.add("Reverse")
        listMotor.add("Classic")
        binding.spinnerMotor.adapter = ArrayAdapter<Any?>(activity!!, android.R.layout.simple_spinner_item, listMotor as List<Any?>)

        setView(MainActivity.itemWidget.imageCounter)
        binding.buttonAdd.setOnClickListener {
            updateLine(layoutInflater.inflate(R.layout.row_cricketer_1, null, false), binding.layoutList, binding.buttonAdd, 12
            )
        }
        binding.buttonAdd2.setOnClickListener {
            updateLine(layoutInflater.inflate(R.layout.row_cricketer_2, null, false), binding.layoutList2, binding.buttonAdd2, 3)
        }
        binding.buttonAdd3.setOnClickListener {
            updateLine(layoutInflater.inflate(R.layout.row_cricketer_3, null, false), binding.layoutList3, binding.buttonAdd3, 5)
        }
        binding.buttonAdd4.setOnClickListener {
            updateLine(layoutInflater.inflate(R.layout.row_cricketer_4, null, false), binding.layoutList4, binding.buttonAdd4, 5)
        }

        binding.image.setOnClickListener {
            MainActivity.itemWidget.imageCounter++
            if (MainActivity.itemWidget.imageCounter > 4) MainActivity.itemWidget.imageCounter = 1
            setView(MainActivity.itemWidget.imageCounter)
        }

        binding.buttonPush.setOnClickListener {
            if (checkIfValidSend()) Toast.makeText(activity, "Успешно", Toast.LENGTH_SHORT).show()
            else Toast.makeText(activity, "Не все поля заполнены!", Toast.LENGTH_SHORT).show()
        }

        binding.getParam.setOnClickListener {
            listener!!.onInputSent(
                JsonSendBuilder.messageObj("message", "getParam")
            )
        }

        binding.buttonClean.setOnClickListener {
            binding.layoutList.removeAllViewsInLayout()
            binding.layoutList2.removeAllViewsInLayout()
            binding.layoutList3.removeAllViewsInLayout()
            binding.layoutList4.removeAllViewsInLayout()
            updateLine(layoutInflater.inflate(R.layout.row_cricketer_1, null, false), binding.layoutList, binding.buttonAdd, 12)
            updateLine(layoutInflater.inflate(R.layout.row_cricketer_2, null, false), binding.layoutList2, binding.buttonAdd2, 3)
            updateLine(layoutInflater.inflate(R.layout.row_cricketer_3, null, false), binding.layoutList3, binding.buttonAdd3, 5)
            updateLine(layoutInflater.inflate(R.layout.row_cricketer_4, null, false), binding.layoutList4, binding.buttonAdd4, 5)
            binding.motorDuration.setText("")
            binding.image.setImageResource(R.drawable.ic_number_foreground)
            MainActivity.itemWidget.imageCounter = 0
        }

        binding.spinnerMotor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                MainActivity.itemWidget.motorType = adapterView.getItemAtPosition(i).toString()
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun updateCurrentCard() {
        model.liveDataFragment2.observe(viewLifecycleOwner) {
            setView(it.imageCounter)
        }
    }

    private fun updateLine(cricketerView: View, layoutList: LinearLayout, buttonAdd: Button, maxLine: Int){
        val imageClose = cricketerView.findViewById<View>(R.id.image_remove) as ImageView

        imageClose.setOnClickListener {
            layoutList.removeView(cricketerView)
            val imgRemove = layoutList.getChildAt(layoutList.childCount - 1).findViewById<View>(R.id.image_remove) as ImageView
            if (layoutList.childCount != 1) imgRemove.visibility = View.VISIBLE
            if (layoutList.childCount < maxLine) buttonAdd.visibility = View.VISIBLE
        }

        layoutList.addView(cricketerView)
        if (layoutList.childCount == 1) {
            val imgRemove = layoutList.getChildAt(0).findViewById<View>(R.id.image_remove) as ImageView
            imgRemove.visibility = View.INVISIBLE
        }
        if (layoutList.childCount > maxLine - 1) buttonAdd.visibility = View.GONE

        if (layoutList.childCount > 1) {
            val imgRemove = layoutList.getChildAt(layoutList.childCount - 2).findViewById<View>(R.id.image_remove) as ImageView
            imgRemove.visibility = View.INVISIBLE
        }
    }

    private fun setView(number: Int) {
        when (number) {
            0 -> {
                binding.image.setImageResource(R.drawable.ic_number_foreground)
                setText(MainActivity.itemWidget.birdMsgJson)
            }
            1 -> {
                binding.image.setImageResource(R.drawable.img_kur)
                setText(MainActivity.itemWidget.kurStringJson)
            }
            2 -> {
                binding.image.setImageResource(R.drawable.img_duck)
                setText(MainActivity.itemWidget.duckStringJson)
            }
            3 -> {
                binding.image.setImageResource(R.drawable.img_gosse)
                setText(MainActivity.itemWidget.gooseStringJson)
            }
            4 -> {
                binding.image.setImageResource(R.drawable.img_ind)
                setText(MainActivity.itemWidget.indStringJson)
            }
        }
    }

    private fun setText(message: String) {
        binding.layoutList.removeAllViewsInLayout()
        binding.layoutList2.removeAllViewsInLayout()
        binding.layoutList3.removeAllViewsInLayout()
        binding.layoutList4.removeAllViewsInLayout()
        for (i in 0..12) {
            if (JsonViewSet.jsonLineIsChecker(message, "param", i + 1)) {
                if (binding.layoutList.childCount - 1 < i) {
                    updateLine(layoutInflater.inflate(R.layout.row_cricketer_1, null, false), binding.layoutList, binding.buttonAdd, 12
                    )
                }
                val cricketerView: View = binding.layoutList.getChildAt(i)
                val editTextName1 = cricketerView.findViewById<View>(R.id.edit_day1) as EditText
                val editTextName2 = cricketerView.findViewById<View>(R.id.edit_day2) as EditText
                val editTextName3 = cricketerView.findViewById<View>(R.id.edit_temp1) as EditText
                val editTextName4 = cricketerView.findViewById<View>(R.id.edit_hum) as EditText
                val editTextName5 = cricketerView.findViewById<View>(R.id.edit_temp2) as EditText
                val checkBox = cricketerView.findViewById<View>(R.id.checkBox) as CheckBox
                JsonViewSet.jsonLineSet(message,"param", i, editTextName1, editTextName2, editTextName3, editTextName4, editTextName5, checkBox)
            } else break
        }
        for (i in 0..3) {
            if (JsonViewSet.jsonLineIsChecker(message, "motor", i + 1)) {
                if (binding.layoutList2.childCount - 1 < i) {
                    updateLine(layoutInflater.inflate(R.layout.row_cricketer_2, null, false), binding.layoutList2, binding.buttonAdd2, 3)
                }
                val cricketerView: View = binding.layoutList2.getChildAt(i)
                val editTextName1 = cricketerView.findViewById<View>(R.id.edit_motor1) as EditText
                val editTextName2 = cricketerView.findViewById<View>(R.id.edit_motor2) as EditText
                val editTextName3 = cricketerView.findViewById<View>(R.id.edit_time1) as EditText
                JsonViewSet.jsonLineSet(message, "motor", i, editTextName1, editTextName2, editTextName3, binding.motorDuration, binding.spinnerMotor)
            } else break
        }
        for (i in 0..5) {
            if (JsonViewSet.jsonLineIsChecker(message, "air", i + 1)) {
                if (binding.layoutList3.childCount - 1 < i) {
                    updateLine(layoutInflater.inflate(R.layout.row_cricketer_3, null, false), binding.layoutList3, binding.buttonAdd3, 5)
                }
                val cricketerView: View = binding.layoutList3.getChildAt(i)
                val editTextName1 = cricketerView.findViewById<View>(R.id.edit_air1) as EditText
                val editTextName2 = cricketerView.findViewById<View>(R.id.edit_air2) as EditText
                val editTextName3 = cricketerView.findViewById<View>(R.id.edit_air3) as EditText
                val editTextName4 = cricketerView.findViewById<View>(R.id.edit_air4) as EditText
                JsonViewSet.jsonLineSet(message, "air", i, editTextName1, editTextName2, editTextName3, editTextName4)
            } else break
        }
        for (i in 0..5) {
            if (JsonViewSet.jsonLineIsChecker(message, "cool", i + 1)) {
                if (binding.layoutList4.childCount - 1 < i) {
                    updateLine(layoutInflater.inflate(R.layout.row_cricketer_4, null, false),  binding.layoutList4, binding.buttonAdd4, 5)
                }
                val cricketerView: View = binding.layoutList4.getChildAt(i)
                val editTextName1 = cricketerView.findViewById<View>(R.id.edit_cool1) as EditText
                val editTextName2 = cricketerView.findViewById<View>(R.id.edit_cool2) as EditText
                val editTextName3 = cricketerView.findViewById<View>(R.id.edit_cool3) as EditText
                val editTextName4 = cricketerView.findViewById<View>(R.id.edit_cool4) as EditText
                val editTextName5 = cricketerView.findViewById<View>(R.id.edit_cool5) as EditText
                JsonViewSet.jsonLineSet(message, "cool", i, editTextName1, editTextName2, editTextName3, editTextName4, editTextName5)
            } else break
        }
    }

    private fun checkIfValidSend(): Boolean {
        for (i in 0 until binding.layoutList.childCount) {
            val cricketerView: View = binding.layoutList.getChildAt(i)
            val editTextName1 = cricketerView.findViewById<View>(R.id.edit_day1) as EditText
            val editTextName2 = cricketerView.findViewById<View>(R.id.edit_day2) as EditText
            val editTextName3 = cricketerView.findViewById<View>(R.id.edit_temp1) as EditText
            val editTextName4 = cricketerView.findViewById<View>(R.id.edit_hum) as EditText
            val editTextName5 = cricketerView.findViewById<View>(R.id.edit_temp2) as EditText
            val checkBox = cricketerView.findViewById<View>(R.id.checkBox) as CheckBox
            if (editTextName1.text.toString() == "") return false
            if (editTextName2.text.toString() == "") return false
            if (editTextName3.text.toString() == "") return false
            if (editTextName3.text.toString() == ".") return false
            if (editTextName4.text.toString() == "") return false
            if (editTextName5.text.toString() == "") return false
            if (editTextName5.text.toString() == ".") return false
            if (editTextName2.text.toString().toInt() < editTextName1.text.toString().toInt()) return false
            JsonSendBuilder.messageParamSum(editTextName1.text.toString().toInt(), editTextName2.text.toString().toInt(), editTextName3.text.toString().toDouble(), editTextName4.text.toString().toInt(), editTextName5.text.toString().toDouble(), checkBox.isChecked, i + 1)
        }
        JsonSendBuilder.messageSetMotorState(MainActivity.itemWidget.motorType)
        if (binding.motorDuration.text.toString() == "") return false
        JsonSendBuilder.messageSetMotorDuration(binding.motorDuration.text.toString().toInt())
        for (i in 0 until binding.layoutList2.childCount) {
            val cricketerView: View = binding.layoutList2.getChildAt(i)
            val editTextName1 = cricketerView.findViewById<View>(R.id.edit_motor1) as EditText
            val editTextName2 = cricketerView.findViewById<View>(R.id.edit_motor2) as EditText
            val editTextName3 = cricketerView.findViewById<View>(R.id.edit_time1) as EditText
            if (editTextName1.text.toString() == "") return false
            if (editTextName2.text.toString() == "") return false
            if (editTextName3.text.toString() == "") return false
            if (editTextName2.text.toString().toInt() < editTextName1.text.toString().toInt()) return false
            JsonSendBuilder.messageParamSum(editTextName1.text.toString().toInt(), editTextName2.text.toString().toInt(), editTextName3.text.toString(), i + 1)
        }
        for (i in 0 until binding.layoutList3.childCount) {
            val cricketerView: View = binding.layoutList3.getChildAt(i)
            val editTextName1 = cricketerView.findViewById<View>(R.id.edit_air1) as EditText
            val editTextName2 = cricketerView.findViewById<View>(R.id.edit_air2) as EditText
            val editTextName3 = cricketerView.findViewById<View>(R.id.edit_air3) as EditText
            val editTextName4 = cricketerView.findViewById<View>(R.id.edit_air4) as EditText
            if (editTextName1.text.toString() == "") return false
            if (editTextName2.text.toString() == "") return false
            if (editTextName3.text.toString() == "") return false
            if (editTextName4.text.toString() == "") return false
            if (editTextName2.text.toString().toInt() < editTextName1.text.toString().toInt()) return false
            JsonSendBuilder.messageParamSum(editTextName1.text.toString().toInt(), editTextName2.text.toString().toInt(), editTextName3.text.toString(), editTextName4.text.toString().toInt(), i + 1)
        }
        for (i in 0 until binding.layoutList4.childCount) {
            val cricketerView: View = binding.layoutList4.getChildAt(i)
            val editTextName1 = cricketerView.findViewById<View>(R.id.edit_cool1) as EditText
            val editTextName2 = cricketerView.findViewById<View>(R.id.edit_cool2) as EditText
            val editTextName3 = cricketerView.findViewById<View>(R.id.edit_cool3) as EditText
            val editTextName4 = cricketerView.findViewById<View>(R.id.edit_cool4) as EditText
            val editTextName5 = cricketerView.findViewById<View>(R.id.edit_cool5) as EditText
            if (editTextName1.text.toString() == "") return false
            if (editTextName2.text.toString() == "") return false
            if (editTextName3.text.toString() == "") return false
            if (editTextName4.text.toString() == "") return false
            if (editTextName5.text.toString() == "") return false
            if (editTextName5.text.toString() == ".") return false
            if (editTextName2.text.toString().toInt() < editTextName1.text.toString().toInt()) return false
            JsonSendBuilder.messageParamSum(editTextName1.text.toString().toInt(), editTextName2.text.toString().toInt(), editTextName3.text.toString(), editTextName4.text.toString().toInt(), editTextName5.text.toString().toDouble(), i + 1)
        }
        val stringJson: String? = JsonSendBuilder.messageObj("param", "motor", "air", "cool")
        MainActivity.itemWidget.birdMsgJson = stringJson!!
        listener!!.onInputSent(stringJson)
        JsonSendBuilder.clean()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        listener = null
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = if (context is MyFragmentListener) {
            context
        } else {
            throw RuntimeException(
                context.toString()
                        + " must implement FragmentListener"
            )
        }
    }
}