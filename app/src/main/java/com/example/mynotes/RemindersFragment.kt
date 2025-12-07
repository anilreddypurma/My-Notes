package com.example.mynotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.mynotes.databinding.FragmentRemindersBinding

class RemindersFragment : Fragment() {
    private var _binding: FragmentRemindersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRemindersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val db = AppDatabase.getDatabase(requireContext())

        binding.saveButton.setOnClickListener {
            val dayStr = binding.dayInput.text.toString().trim()
            val monthStr = binding.monthInput.text.toString().trim()
            val hourStr = binding.hourInput.text.toString().trim()
            val minuteStr = binding.minuteInput.text.toString().trim()
            val note = binding.noteInput.text.toString().trim()
            val isYearly = binding.yearlySwitch.isChecked

            if (dayStr.isEmpty() || monthStr.isEmpty() || note.isEmpty()) {
                Toast.makeText(context, "Fill Day, Month, and Note", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val day = dayStr.toIntOrNull()
            val month = monthStr.toIntOrNull()
            val hour = hourStr.toIntOrNull() ?: 9  // Default 9 AM
            val minute = minuteStr.toIntOrNull() ?: 0  // Default :00

            if (day !in 1..31 || month !in 1..12 || hour !in 0..23 || minute !in 0..59) {
                Toast.makeText(context, "Invalid date/time", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launchWhenStarted {
                db.remindersDao().insert(ReminderEntity(day, month, hour, minute, note, isYearly))
                binding.dayInput.text.clear()
                binding.monthInput.text.clear()
                binding.hourInput.text.clear()
                binding.minuteInput.text.clear()
                binding.noteInput.text.clear()
                Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
