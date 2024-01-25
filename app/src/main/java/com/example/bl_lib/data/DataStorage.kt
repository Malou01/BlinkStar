package com.example.bl_lib.data

data class DataStorage(val name: String) {
    lateinit var  paramMsgJson: String
    var isConnect: Boolean = false
    var isStart: Boolean = false
    var dayNow: Int = 0
    var workTime: Int = 0

    var tempNow: Double = 0.0
    var tempMax: Double = 0.0
    var switchState1: Boolean = false//123

    var humNow: Int = 0
    var humMax: Int = 0
    var switchState2: Boolean = false

    var tempEgg: Double = 0.0
    var tempEggMax: Double = 0.0
    var switchState3: Boolean = false

    var airLast: Int = 0
    var airNext: Int = 0
    var switchState4: Boolean = false

    var lastRotation: Int = 0
    var nextRotation: Int = 0
    var switchState5: Boolean = false

    var coolLast: Int = 0
    var coolNext: Int = 0
    var switchState6: Boolean = false

    lateinit var birdMsgJson: String
    val kurStringJson =
        "{\"bird\":{\"param\":{\"1\":[1,3,38,60,38,false],\"2\":[4,5,37.9,55,38,false],\"3\":[6,7,37.8,55,38,false],\"4\":[8,10,37.7,55,38,false],\"5\":[11,15,37.6,50,38.1,true],\"6\":[16,18,37.5,45,38.3,true],\"7\":[19,21,37.2,80,38.3,true]},\"motor\":{\"type\":\"Reverse\",\"duration\":20,\"1\":[1,19,\"2:00\"]},\"air\":{\"1\":[1,10,\"1:00\",30],\"2\":[11,15,\"0:30\",30],\"3\":[16,19,\"0:20\",30],\"4\":[20,21,\"0:10\",30]},\"cool\":{\"1\":[11,15,\"24:00\",20,33],\"2\":[16,17,\"12:00\",30,33],\"3\":[18,21,\"12:00\",40,33]}}}"
    val duckStringJson =
        "{\"bird\":{\"param\":{\"1\":[1,2,38,65,37.8,false],\"2\":[3,7,37.8,55,37.8,false],\"3\":[8,13,37.6,50,37.8,false],\"4\":[14,15,37.5,50,38,false],\"5\":[16,20,37.4,47,38.2,false],\"6\":[21,23,37.3,43,38.2,false],\"7\":[24,25,37.2,40,38.2,false],\"8\":[26,28,37,70,38.3,false]},\"motor\":{\"type\":\"Reverse\",\"duration\":20,\"1\":[1,19,\"2:00\"]},\"air\":{\"1\":[1,12,\"0:30\",30],\"2\":[13,19,\"0:15\",30],\"3\":[20,23,\"0:10\",30],\"4\":[24,28,\"0:10\",40]},\"cool\":{\"1\":[11,13,\"12:00\",10,33],\"2\":[14,20,\"12:00\",30,33],\"3\":[21,25,\"12:00\",40,33],\"4\":[26,28,\"8:00\",40,33]}}}"
    val gooseStringJson =
        "{\"bird\":{\"param\":{\"1\":[1,2,38,65,37.8,false],\"2\":[3,4,37.6,60,37.8,false],\"3\":[5,6,37.6,55,37.8,false],\"4\":[7,12,37.5,55,37.8,false],\"5\":[13,16,37.5,57,38.2,false],\"6\":[17,23,37.4,57,38.2,false],\"7\":[24,27,37.2,50,38.2,false],\"8\":[28,29,37,70,38.3,false],\"9\":[30,31,37.9,80,38.3,false]},\"motor\":{\"type\":\"Reverse\",\"duration\":20,\"1\":[1,27,\"2:00\"]},\"air\":{\"1\":[1,12,\"0:30\",30],\"2\":[13,19,\"0:15\",30],\"3\":[20,27,\"0:10\",30],\"4\":[28,31,\"0:10\",20]},\"cool\":{\"1\":[5,10,\"24:00\",10,33],\"2\":[11,14,\"12:00\",20,33],\"3\":[15,20,\"12:00\",30,33],\"4\":[21,27,\"12:00\",40,33],\"5\":[28,31,\"8:00\",40,33]}}}"
    val indStringJson =
        "{\"bird\":{\"param\":{\"1\":[1,2,38,65,37.8,false],\"2\":[3,4,37.8,60,37.8,false],\"3\":[5,7,37.7,55,37.8,false],\"4\":[8,12,37.6,50,37.8,false],\"5\":[13,15,37.5,50,38,false],\"6\":[16,20,37.3,46,38.2,false],\"7\":[21,23,37.1,43,38.2,false],\"8\":[24,25,36.9,40,38.3,false],\"9\":[26,28,36.9,70,38.3,false]},\"motor\":{\"type\":\"Reverse\",\"duration\":20,\"1\":[1,25,\"2:00\"]},\"air\":{\"1\":[1,12,\"0:30\",30],\"2\":[13,19,\"0:15\",30],\"3\":[20,23,\"0:10\",30],\"4\":[24,28,\"0:10\",40]},\"cool\":{\"1\":[11,13,\"12:00\",10,33],\"2\":[14,20,\"12:00\",30,33],\"3\":[21,25,\"12:00\",40,33],\"4\":[26,28,\"8:00\",40,33]}}}"
    var imageCounter: Int = 0

    var motorType: String = "Reverse"
}
