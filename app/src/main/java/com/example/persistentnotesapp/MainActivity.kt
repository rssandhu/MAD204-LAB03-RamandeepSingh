package com.example.persistentnotesapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * MAD204 Lab3 - Persistent Notes App
 * Student: Ramandeep Singh, A00194321
 * Date: Submission Date
 * This app allows users to add, view, delete, and persist notes using RecyclerView and SharedPreferences.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var editTextNote: EditText
    private lateinit var buttonAddNote: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter

    private var notesList = mutableListOf<String>()
    private var recentlyDeletedNote: String? = null
    private var recentlyDeletedNotePosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNote = findViewById(R.id.editTextNote)
        buttonAddNote = findViewById(R.id.buttonAddNote)
        recyclerView = findViewById(R.id.recyclerViewNotes)

        // Load notes from SharedPreferences
        loadNotes()

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MyAdapter(notesList, object : MyAdapter.OnItemLongClickListener {
            override fun onItemLongClick(position: Int) {
                deleteNote(position)
            }
        })
        recyclerView.adapter = adapter

        buttonAddNote.setOnClickListener {
            val noteText = editTextNote.text.toString()
            if (noteText.isNotBlank()) {
                addNote(noteText)
                editTextNote.text.clear()
                Toast.makeText(this, "Note added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Adds a note to the list and notifies adapter.
     * @param note the note string to add
     */
    private fun addNote(note: String) {
        notesList.add(note)
        adapter.notifyItemInserted(notesList.size - 1)
    }

    /**
     * Deletes note at given position and shows Snackbar with undo option.
     * @param position position of the note to delete
     */
    private fun deleteNote(position: Int) {
        recentlyDeletedNote = notesList[position]
        recentlyDeletedNotePosition = position
        notesList.removeAt(position)
        adapter.notifyItemRemoved(position)
        showUndoSnackbar()
    }

    /**
     * Shows Snackbar with Undo option for deleted notes.
     */
    private fun showUndoSnackbar() {
        val view = findViewById<View>(android.R.id.content)
        Snackbar.make(view, "Note deleted", Snackbar.LENGTH_LONG)
            .setAction("UNDO") {
                undoDelete()
            }.show()
    }

    /**
     * Restores the recently deleted note.
     */
    private fun undoDelete() {
        recentlyDeletedNote?.let {
            notesList.add(recentlyDeletedNotePosition, it)
            adapter.notifyItemInserted(recentlyDeletedNotePosition)
        }
    }

    /**
     * Saves the notes list to SharedPreferences using GSON.
     */
    private fun saveNotes() {
        val sharedPreferences = getSharedPreferences("notes_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(notesList)
        editor.putString("notes_list", json)
        editor.apply()
    }

    /**
     * Loads notes list from SharedPreferences using GSON.
     */
    private fun loadNotes() {
        val sharedPreferences = getSharedPreferences("notes_prefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("notes_list", null)
        val type = object : TypeToken<MutableList<String>>() {}.type
        if (json != null) {
            notesList = gson.fromJson(json, type)
        }
    }

    override fun onPause() {
        super.onPause()
        saveNotes() // save notes when activity goes to background
    }
}

/**
 * RecyclerView Adapter for notes list
 */
class MyAdapter(
    private val notes: MutableList<String>,
    private val longClickListener: OnItemLongClickListener
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    /**
     * Interface for long click listener
     */
    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    /**
     * ViewHolder class to hold item views
     */
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNote: TextView = itemView.findViewById(R.id.textViewNote)

        init {
            itemView.setOnLongClickListener {
                longClickListener.onItemLongClick(adapterPosition)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewNote.text = notes[position]
    }
}
