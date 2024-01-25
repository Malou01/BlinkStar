package com.example.bl_lib

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.*
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.bl_lib.data.DataStorage
import com.example.bl_lib.data.MainViewModel
import com.example.bl_lib.databinding.ActivityMainBinding
import com.example.bl_lib.fragments.Fragment1
import com.example.bl_lib.fragments.Fragment2
import com.example.bl_lib.json.JsonSendBuilder
import com.example.bt_def.BaseActivity
import com.example.bt_def.BluetoothConstants
import com.example.bt_def.bluetooth.BluetoothController
import kotlinx.coroutines.*
import org.json.JSONObject


class MainActivity : AppCompatActivity(), BluetoothController.Listener, Fragment2.MyFragmentListener {
    companion object {
        val itemWidget = DataStorage("itemWidget")
    }

    private val viewModel: MainViewModel by viewModels()

    //private val handler = Handler(Looper.getMainLooper())

    private lateinit var bluetoothController: BluetoothController
    private lateinit var btAdapter: BluetoothAdapter

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()
        initBtAdapter()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.selectedItemId = R.id.home
        supportFragmentManager.beginTransaction().replace(R.id.conteiner, Fragment1()).commit()
        binding.bottomNavigationView.itemBackground = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        binding.buttonOnOff.setOnClickListener{
            if (itemWidget.isConnect) {
                itemWidget.isStart = !itemWidget.isStart
                buttonOnOffState(itemWidget.isStart)
                bluetoothController.sendMessage(JsonSendBuilder.messageObj("start", itemWidget.isStart))
            } else Toast.makeText(this, "Подлючитесь к устройству", Toast.LENGTH_SHORT)
                .show()
        }

        binding.floatingBtnBl.setOnTouchListener { _: View?, event: MotionEvent ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val x = 0.8.toFloat()
                val y = 0.8.toFloat()
                binding.floatingBtnBl.scaleX = x
                binding.floatingBtnBl.scaleY = y
            } else if (event.action == MotionEvent.ACTION_UP) {
                val x = 1f
                val y = 1f
                binding.floatingBtnBl.scaleX = x
                binding.floatingBtnBl.scaleY = y

                if (!BluetoothController.BLUETOOTH_STATE) {
                    binding.pbSearch.visibility = View.VISIBLE
                }
                bluetoothController.connect(
                    this.getSharedPreferences(
                        BluetoothConstants.PREFERENCES, MODE_PRIVATE
                    )?.getString(BluetoothConstants.MAC, "") ?: "", this
                )

            }
            false
        }
        binding.floatingBtnBl.setOnLongClickListener { view: View ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
            }
            val x = 1f
            val y = 1f
            binding.floatingBtnBl.scaleX = x
            binding.floatingBtnBl.scaleY = y
            /*for (fragment in supportFragmentManager.fragments) {
                supportFragmentManager.beginTransaction().remove(fragment).commit()
            }*/
            if (!BluetoothController.BLUETOOTH_STATE) {
                startActivity(Intent(this, BaseActivity::class.java))
            }
            false
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item: android.view.MenuItem? ->
            when (item?.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.conteiner, Fragment1())
                        .commit()
                }
                R.id.search -> {
                    supportFragmentManager.beginTransaction().replace(R.id.conteiner, Fragment2())
                        .commit()
                }
            }
            true
        }
    }

    private fun buttonOnOffState(state: Boolean){
        if (state) {
            binding.buttonOnOff.setColorFilter(resources.getColor(R.color.white))
            binding.buttonOnOff.backgroundTintList = ColorStateList.valueOf(
                resources.getColor(
                    R.color.blue
                )
            )
        } else {
            binding.buttonOnOff.setColorFilter(resources.getColor(R.color.blue))
            binding.buttonOnOff.backgroundTintList = ColorStateList.valueOf(
                resources.getColor(
                    R.color.grey_100
                )
            )
        }
    }

    private fun initBtAdapter() {
        val bManager = this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        btAdapter = bManager.adapter
        bluetoothController = BluetoothController(btAdapter)
    }

    private fun enableViewState() {
        binding.floatingBtnBl.isActivated = true
        binding.floatingBtnWifi.isActivated = true
        binding.textBl.text = this.getSharedPreferences(
            BluetoothConstants.PREFERENCES, MODE_PRIVATE
        )?.getString(BluetoothConstants.MAC, "")
    }

    private fun disableViewState() {
        binding.floatingBtnBl.isActivated = false
        binding.floatingBtnWifi.isActivated = false
        binding.pbSearch.visibility = View.GONE
        buttonOnOffState(false)
    }

    override fun onReceive(message: String) {
        this.runOnUiThread {
            when (message) {
                BluetoothController.BLUETOOTH_CONNECTED -> {

                    Toast.makeText(this, "Подлючено!", Toast.LENGTH_SHORT).show()
                    binding.pbSearch.visibility = View.GONE
                    enableViewState()

                    //handler.post {
                        //val fragment = supportFragmentManager.findFragmentById(R.id.conteiner)
                        //if (fragment is Fragment1) {
                            //fragment.updateViewStyle(ENABLE)
                    sendViewStyle(true)
                       // }
                    //}

                }
                BluetoothController.BLUETOOTH_NO_CONNECTED -> {

                    Toast.makeText(this, "Разорвано", Toast.LENGTH_SHORT).show()
                    disableViewState()

                    //handler.post {
                        //val fragment = supportFragmentManager.findFragmentById(R.id.conteiner)
                        //if (fragment is Fragment1) {
                            //fragment.updateViewStyle(DISABLE)
                    sendViewStyle(false)
                        //}
                    //}

                }
                else -> {
                    println(message)
                    parseJsonData(message)
                    /*MainScope().launch {
                        coroutineScope {
                            launch {
                                val mainObject = JSONObject(message)
                                val item = JsonModel(
                                    mainObject.getJSONObject("main").getJSONObject("readings").getDouble("tempNow"),
                                    mainObject.getJSONObject("main").getJSONObject("readings").getDouble("tempMax")
                                )
                                viewModel.liveDaraCurrent.value = item
                            }
                        }
                    }*/
                }
            }
        }
    }

    private fun parseJsonData(message: String) {
        val mainObject = JSONObject(message)
        if (mainObject.has("main")) {
            itemWidget.paramMsgJson = message
            itemWidget.isStart = mainObject.getJSONObject("main").getBoolean("startStatus")
            itemWidget.dayNow = mainObject.getJSONObject("main").getJSONObject("readings").getInt("dayNow")
            itemWidget.workTime = mainObject.getJSONObject("main").getInt("workTime")

            itemWidget.tempNow = mainObject.getJSONObject("main").getJSONObject("readings").getDouble("tempNow")
            itemWidget.tempMax = mainObject.getJSONObject("main").getJSONObject("readings").getDouble("tempMax")
            itemWidget.switchState1 = mainObject.getJSONObject("main").getBoolean("startStatus") //!!! mainObject.getJSONObject("main").getJSONObject("load").getBoolean("heatStatus")
            itemWidget.humNow = mainObject.getJSONObject("main").getJSONObject("readings").getInt("humNow")
            itemWidget.humMax = mainObject.getJSONObject("main").getJSONObject("readings").getInt("humMax")
            itemWidget.switchState2 = mainObject.getJSONObject("main").getJSONObject("load").getBoolean("humStatus")
            itemWidget.tempEgg = mainObject.getJSONObject("main").getJSONObject("readings").getDouble("tempEgg")
            itemWidget.tempEggMax = mainObject.getJSONObject("main").getJSONObject("readings").getDouble("tempEggMax")
            itemWidget.switchState3 = mainObject.getJSONObject("main").getBoolean("startStatus") //!!! mainObject.getJSONObject("main").getJSONObject("load").getBoolean("heatStatus")

            itemWidget.airLast = mainObject.getJSONObject("main").getJSONObject("load").getInt("airLast")
            itemWidget.airNext = mainObject.getJSONObject("main").getJSONObject("load").getInt("airNext")
            itemWidget.switchState4 = mainObject.getJSONObject("main").getJSONObject("load").getBoolean("airStatus")

            itemWidget.lastRotation = mainObject.getJSONObject("main").getJSONObject("motor").getInt("lastRotation")
            itemWidget.nextRotation = mainObject.getJSONObject("main").getJSONObject("motor").getInt("nextRotation")
            itemWidget.switchState5 = mainObject.getJSONObject("main").getJSONObject("motor").getBoolean("status")

            itemWidget.coolLast = mainObject.getJSONObject("main").getJSONObject("load").getInt("coolLast")
            itemWidget.coolNext = mainObject.getJSONObject("main").getJSONObject("load").getInt("coolNext")
            itemWidget.switchState6 = mainObject.getJSONObject("main").getJSONObject("load").getBoolean("coolStatus")

            buttonOnOffState(itemWidget.isStart)
            viewModel.liveDataFragment1.value = itemWidget
        } else if (mainObject.has("bird")){
            itemWidget.birdMsgJson = message
            itemWidget.imageCounter = 0
            viewModel.liveDataFragment2.value = itemWidget
            saveData()
        }
    }
    private fun sendViewStyle(style: Boolean) {
        itemWidget.isConnect = style
        viewModel.liveDataFragment1.value = itemWidget
    }

    override fun onInputSent(input: String?) {
            bluetoothController.sendMessage(input!!)
            saveData()
    }

    private fun saveData() {
        val pref: SharedPreferences = this.getPreferences(MODE_PRIVATE)
        val preferences = pref.edit()
        preferences.putString("Fragment2Data", itemWidget.birdMsgJson)
        preferences.putInt("Fragment2Counter", itemWidget.imageCounter)
        preferences.apply()
    }

    private fun loadData() {
        val preferences: SharedPreferences = this.getPreferences(MODE_PRIVATE)
        itemWidget.birdMsgJson = preferences.getString("Fragment2Data",  itemWidget.kurStringJson)!!
        itemWidget.imageCounter = preferences.getInt("Fragment2Counter", 1)
    }
}