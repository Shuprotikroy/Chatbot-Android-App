package learn.codeacademy.chatbottest.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import learn.codeacademy.chatbottest.R

import learn.codeacademy.chatbottest.data.Message
import learn.codeacademy.chatbottest.utils.BotResponse
import learn.codeacademy.chatbottest.utils.Constants.CRICKET_MATCH
import learn.codeacademy.chatbottest.utils.Constants.OPEN_GOOGLE
import learn.codeacademy.chatbottest.utils.Constants.OPEN_SEARCH
import learn.codeacademy.chatbottest.utils.Time
import learn.codeacademy.chatbottest.utils.Constants.RECIEVE_ID
import learn.codeacademy.chatbottest.utils.Constants.SEND_ID
import org.json.JSONArray

class MainActivity : AppCompatActivity(){

    private lateinit var textView: TextView
    private var requestQueue: RequestQueue?=null
    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("Peter","Francesca","Luigi","Igor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestQueue = Volley.newRequestQueue(this)
        recyclerView()
        clickEvents()
        val random = (0..3).random()
        customMessage("Hello! Today you are speaking with ${botList[random]}, how may I help?") //custom bot message on start of app
    }
    private fun clickEvents() { //handles click events
        //response on send button
        btn_send.setOnClickListener{
            sendMessage()
        }
        et_message.setOnClickListener{
        //scrolls to bottom
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount-1)
                }

            }

        }
    }
    private fun recyclerView() {  //handles recyclerview and adapter
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }
    //function used for sending messages
    private fun sendMessage() {
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if(message.isNotEmpty()){
            et_message.setText("")

            adapter.insertMessage(Message(message, SEND_ID,timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount-1)
            botResponse(message)
        }
    }

    private fun botResponse(message: String) { //this function reads responses and according to the constant i.e responses returns value
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)

            withContext(Dispatchers.Main){
                val response = BotResponse.basicResponses(message)
                adapter.insertMessage(Message(response, RECIEVE_ID,timeStamp))

                rv_messages.scrollToPosition(adapter.itemCount-1)
                when(response){
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)                 //opens google on open google
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfter("search")                        //opens browser on search+search term
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }
                    //calling json data when user asks upcoming cricket matches?
                    CRICKET_MATCH -> {

                         jsonParse()
                    }
                }
            }
        }

    }





    override fun onStart() {
        super.onStart()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                rv_messages.scrollToPosition(adapter.itemCount -1)
            }
        }
    }

    private fun customMessage(message: String) {             //handles bot message on chatbot start and is called in OnCreate()
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message, RECIEVE_ID,timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount-1)

            }
        }
    }
    //function for parsing json data and showing upcoming cricket matches
    private fun jsonParse(){
        val url = "https://cricapi.com/api/matchCalendar?apikey=x4xpfgwxkLcN0vhULFsSLpOmPft1"
        //JsonObjectRequest is used to fetch data from the given url,parse it and display list of cricket matches
        val request: JsonObjectRequest = JsonObjectRequest(Request.Method.GET,url,null,
                Response.Listener {
                    response -> try {
                        val jsonArray: JSONArray = response.getJSONArray("data")
                    val timeStamp = Time.timeStamp()
                        for (i in 0 until jsonArray.length()) {
                            val data = jsonArray.getJSONObject(i)
                            val date = data.getString("date")
                            val name = data.getString("name")
                            val unique_id = data.getString("unique_id")
                            val message = "Upcoming matches are $name on $date\n\n"
                            val timeStamp = Time.timeStamp()
                            adapter.insertMessage(Message(message, RECIEVE_ID,timeStamp))
                            rv_messages.scrollToPosition(adapter.itemCount-1)


                            Log.d("TAG","$name")

                        }
                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }, Response.ErrorListener { error -> error.printStackTrace()  })
                   requestQueue?.add(request)


    }

}
