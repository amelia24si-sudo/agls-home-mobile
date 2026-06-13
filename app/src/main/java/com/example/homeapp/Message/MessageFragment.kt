package com.example.homeapp.Message

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homeapp.data.AppDatabase
import com.example.homeapp.data.entity.MessageEntity
import com.example.homeapp.databinding.FragmentMessageBinding
import kotlinx.coroutines.launch

class MessageFragment : Fragment() {
    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: AppDatabase
    private lateinit var adapter: MessageAdapter
    private val messages = mutableListOf<MessageEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = AppDatabase.getInstance(requireContext())
        adapter = MessageAdapter(messages, this)

        binding.rvMessages.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMessages.adapter = adapter

        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvMessages.addItemDecoration(divider)

        fetchMessages()

        binding.fabAddMessage.setOnClickListener {
            startActivity(Intent(requireContext(), MessageFormActivity::class.java))
        }
    }

    private fun fetchMessages() {
        lifecycleScope.launch {
            val data = db.messageDao().getAll()
            messages.clear()
            messages.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        fetchMessages()
    }

    fun deleteMessage(message: MessageEntity) {
        lifecycleScope.launch {
            db.messageDao().delete(message)
            fetchMessages()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}