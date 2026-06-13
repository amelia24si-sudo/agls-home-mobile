package com.example.homeapp.Message

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.homeapp.R
import com.example.homeapp.data.AppDatabase
import com.example.homeapp.data.entity.MessageEntity
import com.example.homeapp.databinding.ActivityMessageFormBinding
import kotlinx.coroutines.launch

class MessageFormActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageFormBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMessageFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_message_form)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Kirim Pesan"
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.rounded_arrow_back_ios_24) // Pastikan drawable ini ada atau ganti bawaan
        }
        binding.toolbar.navigationIcon?.setTint(ContextCompat.getColor(this, R.color.black))

        db = AppDatabase.getInstance(this)

        binding.btnSendMessage.setOnClickListener {
            val sender = binding.etSender.text.toString()
            val messageText = binding.etMessage.text.toString()

            if (sender.isNotBlank() && messageText.isNotBlank()) {
                lifecycleScope.launch {
                    val messageEntity = MessageEntity(
                        sender = sender,
                        message = messageText,
                        timestamp = System.currentTimeMillis()
                    )
                    db.messageDao().insert(messageEntity)
                    finish()
                }
            } else {
                Toast.makeText(this, "Isi pengirim dan pesan!", Toast.LENGTH_SHORT).show()
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}