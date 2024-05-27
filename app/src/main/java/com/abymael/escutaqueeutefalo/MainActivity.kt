package com.abymael.escutaqueeutefalo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.abymael.escutaqueeutefalo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textToSpeech = TextToSpeech(this, this)
        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                runOnUiThread { binding.animacao.playAnimation() }
            }

            override fun onDone(utteranceId: String?) {
                runOnUiThread { binding.animacao.pauseAnimation() }
            }

            override fun onError(utteranceId: String?) {
                runOnUiThread { binding.animacao.pauseAnimation() }
            }
        })

        binding.falar.setOnClickListener {
            speakOut()
        }

        binding.criadores.setOnClickListener {
            startActivity(Intent(this, Criadores::class.java))
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale("pt", "BR"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Linguagem não suportada", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Inicialização do TTS falhou", Toast.LENGTH_SHORT).show()
        }
    }

    private fun speakOut() {
        val text = binding.editText.text.toString()
        if (text.isNotEmpty()) {
            val params = Bundle()
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "utteranceId")
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params, "utteranceId")
        } else {
            Toast.makeText(this, "Por favor, escreva algo para falar", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        if (this::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}
