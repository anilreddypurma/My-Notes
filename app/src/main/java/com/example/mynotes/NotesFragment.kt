package com.example.mynotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mynotes.databinding.FragmentNotesBinding
import kotlinx.coroutines.flow.collect

class NotesFragment : Fragment() {
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = AppDatabase.getDatabase(requireContext())
        val adapter = NoteAdapter { note ->
            lifecycleScope.launchWhenStarted {
                db.notesDao().delete(note)
            }
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launchWhenStarted {
            db.notesDao().getAllNotes().collect { notes ->
                adapter.submitList(notes)
            }
        }

        binding.fab.setOnClickListener {
            val text = binding.editText.text.toString().trim()
            if (text.isNotEmpty()) {
                lifecycleScope.launchWhenStarted {
                    db.notesDao().insert(NoteEntity(text = text))
                    binding.editText.text.clear()
                }
            }
        }
    }
}
