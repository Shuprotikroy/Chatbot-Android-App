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
            //Returns a response to hello message
            message.contains("hello") -> {
                when(random) {
                    0 -> "Hello there!"
                    1 -> "Sup"
                    2 -> "Bonjour"
                    else -> "error"
                }
            }

            //Returns a response to how are you messge
            message.contains("how are you") -> {
                when(random) {
                    0 -> "I'm doing fine, thanks for asking!"
                    1 -> "I'm hungry"
                    2 -> "Pretty good! How about you?"
                    else -> "error"
                }
            }
            //Returns a response to flip coin message
            message.contains("flip") && message.contains("coin") -> {
                var  r = (0..1).random()
                val result = if(r == 0) "heads" else "tails"

                "I flipped a coin and it landed on $result"
            }
            //Solves basic mathematical calculations
            message.contains("solve") -> {
                val equation: String? = message.substringAfter("solve")
                return try {
                    val answer = SolveMath.solveMath(equation?:"0")
                    answer.toString()
                } catch (e: Exception){
                    "Sorry, I can't solve that!"
                }
            }
             //Gets current time through Time.timeStamp()
            message.contains("time") && message.contains("?") -> {
                Time.timeStamp()
            }
          //Refers to the OPEN_GOOGLE constants in Constants.kt data class
            message.contains("open") && message.contains("google") -> {
                OPEN_GOOGLE
            }

            //Refers to the OPEN_SEARCH constant in Constants.kt data class
            message.contains("search")  -> {
                OPEN_SEARCH
            }
            //Refers to the CRICKET_MATCH constants in Constants.kt data class
            message.contains("upcoming") && message.contains("cricket")&& message.contains("matches")&& message.contains("?") -> {
            CRICKET_MATCH
            }

          //If the bot doesn't get a response from the predefined list of responses,it reverts from one of the below responses
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
