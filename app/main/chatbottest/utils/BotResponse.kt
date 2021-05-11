package learn.codeacademy.chatbottest.utils


import learn.codeacademy.chatbottest.utils.Constants.CRICKET_MATCH
import learn.codeacademy.chatbottest.utils.Constants.OPEN_GOOGLE
import learn.codeacademy.chatbottest.utils.Constants.OPEN_SEARCH
/*Handles Bot Responses*/
object BotResponse {
    fun basicResponses(_message: String): String { //Basic Responses function handles all the bot responses and returns it to mainactivity
        val random = (0..2).random()
        val message = _message.toLowerCase() //handles user's message and returns bot response

        return when{
            //Hello
            message.contains("hello") -> {
                when(random) {
                    0 -> "Hello there!"
                    1 -> "Sup"
                    2 -> "Bonjour"
                    else -> "error"
                }
            }

            //How are you
            message.contains("how are you") -> {
                when(random) {
                    0 -> "I'm doing fine, thanks for asking!"
                    1 -> "I'm hungry"
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }
            //flip a coin
            message.contains("flip") && message.contains("coin") -> {
                var  r = (0..1).random()
                val result = if(r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }
            //Solves maths
            message.contains("solve") -> {
                val equation: String? = message.substringAfter("solve")
                return try {
                    val answer = SolveMath.solveMath(equation?:"0")
                    answer.toString()
                } catch (e: Exception){
                    "Sorry, I can't solve that!"
                }
            }
             //Gets current time
            message.contains("time") && message.contains("?") -> {
                Time.timeStamp()
            }
          //Opens Google
            message.contains("open") && message.contains("google") -> {
                OPEN_GOOGLE
            }

            //Opens Search
            message.contains("search")  -> {
                OPEN_SEARCH
            }
            message.contains("upcoming") && message.contains("cricket")&& message.contains("matches")&& message.contains("?") -> {
            CRICKET_MATCH
            }


            else -> {
                when(random) {
                    0 -> "I don't understand"
                    1 -> "Idk"
                    2 -> "Ask something different"
                    else -> "error"
                }


            }

        }
    }
}
