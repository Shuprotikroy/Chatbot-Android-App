package learn.codeacademy.chatbottest.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.message_item.view.*
import learn.codeacademy.chatbottest.R
import learn.codeacademy.chatbottest.data.Message
import learn.codeacademy.chatbottest.utils.Constants.RECIEVE_ID
import learn.codeacademy.chatbottest.utils.Constants.SEND_ID

class MessagingAdapter: RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {
//messgeslist stores the Message dataclass in mutable list
    var messagesList = mutableListOf<Message>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            //enables on removing of messages when a message is long pressed
            itemView.setOnClickListener {
                messagesList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false) //inflates the layout that has bot and user response
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
       val currentMessage = messagesList[position]       //tracks and returns current position of a message

        when(currentMessage.id){
            //when currentMessage id is SEND_ID,it shows the user types messages
            SEND_ID -> {
                holder.itemView.tv_message.apply{
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_bot_message.visibility = View.GONE
            }
            //when currentMessge id is RECIEVE_ID,it shows the bot responses
            RECIEVE_ID -> {
                holder.itemView.tv_bot_message.apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.tv_message.visibility = View.GONE
            }



        }
    }

    override fun getItemCount(): Int {
return messagesList.size
    }
    fun insertMessage(message: Message){ //this will be used to insert messages in mainactivity
        this.messagesList.add(message)
        notifyItemInserted(messagesList.size)
    }


}
