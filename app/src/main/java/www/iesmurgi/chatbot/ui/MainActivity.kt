package www.iesmurgi.chatbot.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import www.iesmurgi.chatbot.R
import www.iesmurgi.chatbot.data.Message
import www.iesmurgi.chatbot.utils.Constants.OPEN_GOOGLE
import www.iesmurgi.chatbot.utils.Constants.OPEN_SEARCH
import www.iesmurgi.chatbot.utils.Constants.RECEIVE_ID
import www.iesmurgi.chatbot.utils.Constants.SEND_ID
import www.iesmurgi.chatbot.utils.Respuestas
import www.iesmurgi.chatbot.utils.Time

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MessegingAdapter
    private val botList = listOf("FranciscBot","DioniBot","ElChatGPT")
    private lateinit var recycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler =  findViewById(R.id.rv_messages)
        recyclerView()

        clickEvents()

        val random = (0..3).random()
        mensajeSaludo("Hola, hoy estas hablando con, ${botList[random]}, ¿en qué te puedo ayudar?")

    }

    private fun clickEvents() {
        findViewById<Button>(R.id.btn_send).setOnClickListener {
            enviarMensaje()
        }
        findViewById<EditText>(R.id.et_message).setOnClickListener {
            GlobalScope.launch {
                delay(1000)
                withContext(Dispatchers.Main){
                    recycler.scrollToPosition(adapter.itemCount-1)
                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessegingAdapter()
        val recycler = findViewById<RecyclerView>(R.id.rv_messages)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun enviarMensaje(){
        val campoTexto = findViewById<EditText>(R.id.et_message)
        val mensaje = campoTexto.text.toString()

        val time = Time.timeStamp()
        if (mensaje.isNotEmpty()){
            campoTexto.setText("")
            adapter.insertMessage(Message(mensaje, SEND_ID,time))
            recycler.scrollToPosition(adapter.itemCount-1)
            respuestaBot(mensaje)
        }
    }

    private fun respuestaBot(mensaje: String) {
        val time = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val respuesta = Respuestas.respuestas(mensaje)
                adapter.insertMessage(Message(respuesta, RECEIVE_ID,time))
                recycler.scrollToPosition(adapter.itemCount-1)

                when(respuesta){
                    OPEN_GOOGLE->{
                        val sitioWeb = Intent(Intent.ACTION_VIEW)
                        sitioWeb.data = Uri.parse("https://www.google.es/")
                        startActivity(sitioWeb)
                    }
                    OPEN_SEARCH->{
                        val sitioWeb = Intent(Intent.ACTION_VIEW)
                        val busqueda: String? = mensaje.substringAfter("busca")
                        sitioWeb.data = Uri.parse("https://www.google.es/search?&q=$busqueda")
                        startActivity(sitioWeb)
                    }
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                recycler.scrollToPosition(adapter.itemCount-1)
            }
        }
    }

    private fun mensajeSaludo(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message,RECEIVE_ID,timeStamp))

                findViewById<RecyclerView>(R.id.rv_messages).scrollToPosition(adapter.itemCount-1)
            }
        }
    }

}