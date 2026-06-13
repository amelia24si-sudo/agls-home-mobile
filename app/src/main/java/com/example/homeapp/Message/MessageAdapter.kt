package com.example.homeapp.Message

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homeapp.data.entity.MessageEntity
import com.example.homeapp.databinding.ItemMessageBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class MessageAdapter(
    private val messages: List<MessageEntity>,
    private val messageFragment: MessageFragment
) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    inner class MessageViewHolder(val binding: ItemMessageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val msg = messages[position]
        holder.binding.tvSender.text = msg.sender
        holder.binding.tvMessageContent.text = msg.message

        holder.itemView.setOnClickListener {
            Snackbar.make(holder.itemView, "Pesan dari: ${msg.sender}", Snackbar.LENGTH_SHORT).show()
        }

        holder.binding.btnDeleteMessage.setOnClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Hapus Pesan")
                .setMessage("Apakah kamu yakin ingin menghapus pesan ini?")
                .setPositiveButton("Ya") { dialog, _ ->
                    messageFragment.deleteMessage(msg)
                    dialog.dismiss()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun getItemCount(): Int = messages.size
}