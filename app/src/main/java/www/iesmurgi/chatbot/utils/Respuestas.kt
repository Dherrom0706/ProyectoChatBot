package www.iesmurgi.chatbot.utils

import www.iesmurgi.chatbot.data.Message
import www.iesmurgi.chatbot.utils.Constants.OPEN_GOOGLE
import www.iesmurgi.chatbot.utils.Constants.OPEN_SEARCH
import java.util.*

object Respuestas {

    fun respuestas(message: String): String{

        val random = (0..2).random()
        val message = message.lowercase()

        return when{

            message.contains("hola") ->{

                when(random){
                    0-> "Hola"
                    1-> "Hey hey hey"
                    2-> "Hola, ¿qué tal?"
                    else-> "error"
                }
            }
            message.contains("como estas") ->{

                when(random){
                    0-> "Estoy bien, gracias por preguntar"
                    1-> "Pues la verdad, tengo algo de hambre"
                    2-> "Muy bien, ¿tu qué tal?"
                    else-> "error"
                }

            }

            message.contains("lanza") && message.contains("moneda")->{
                var r = (0..1).random()
                val resultado = if(r==0) "cara" else "cruz"

                "He lanzado una moneda, y el resultado ha sido $resultado"
            }

            message.contains("resuelve")->{
                val ecuacion: String? = message.substringAfter("resuelve")
                return try {
                    val respuesta = SolveMath.solveMath(ecuacion ?: "0")
                    respuesta.toString()
                }catch (e: java.lang.Exception){
                    "Lo siento no puedo resolver eso"
                }
            }

            message.contains("hora") && message.contains("?") ->{
                Time.timeStamp()
            }

            message.contains("abre") && message.contains("google") ->{
                OPEN_GOOGLE
            }

            message.contains("busca") ->{
                OPEN_SEARCH
            }

            else->{
                when(random){
                    0-> "No te entiendo..."
                    1-> "Lo siento no entiendo que dijiste"
                    2-> "Intenta preguntar algo diferente, ¡gracias!"
                    else-> "error"
                }
            }

        }
    }

}