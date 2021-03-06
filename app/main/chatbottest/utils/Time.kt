package learn.codeacademy.chatbottest.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
//object class that returns time and is also called in the BotResponses.kt file
object Time {

    fun timeStamp() : String {
        val timeStamp = Timestamp(System.currentTimeMillis())
        val sdf = SimpleDateFormat("HH:mm")
        val time = sdf.format(Date(timeStamp.time))
        return time.toString()
    }
}
