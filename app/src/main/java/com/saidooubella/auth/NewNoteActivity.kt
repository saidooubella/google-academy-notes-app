package com.saidooubella.auth

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.core.view.*
import com.google.android.material.shape.MaterialShapeDrawable
import com.saidooubella.auth.databinding.ActivityNewNoteBinding

class NewNoteActivity : AppCompatActivity() {

    private val binding by binding(ActivityNewNoteBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingUpTheUI()

        val editingIndex = when (intent.hasExtra(EDITING_INDEX)) {
            true -> intent.getIntExtra(EDITING_INDEX, 0)
            else -> null
        }

        if (editingIndex != null) {
            val note = InMemoryNotesRepository.loadNote(editingIndex)
            binding.noteTitleField.setText(note.tile)
            binding.noteBodyField.setText(note.content)
        }

        binding.appbar.setNavigationOnClickListener {
            finish()
        }

        binding.saveNoteButton.setOnClickListener {

            val tile = binding.noteTitleField.text.toString()
            val content = binding.noteBodyField.text.toString()

            if (tile.isNotBlank() || content.isNotBlank()) {
                val note = Note(tile, content)
                if (editingIndex == null) {
                    InMemoryNotesRepository.insertNote(note)
                } else {
                    InMemoryNotesRepository.changeNote(editingIndex, note)
                }
            } else {
                toast("Empty notes will note be saved.")
            }

            finish()
        }
    }

    private fun settingUpTheUI() {

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controllerCompat = WindowInsetsControllerCompat(window, window.decorView)

        controllerCompat.isAppearanceLightStatusBars =
            (binding.appbarLayout.background as MaterialShapeDrawable).fillColor?.defaultColor?.isLight() == true

        controllerCompat.isAppearanceLightNavigationBars =
            (window.decorView.background as ColorDrawable).color.isLight()

        ViewCompat.setOnApplyWindowInsetsListener(binding.appbarLayout) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                top = systemBars.top,
                left = systemBars.left,
                right = systemBars.right
            )
            insets
        }

        val addNoteMarginBottom = binding.root.paddingBottom
        val addNoteMarginRight = binding.root.paddingRight
        val addNoteMarginLeft = binding.root.paddingLeft

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars =
                insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
            view.updatePadding(
                bottom = addNoteMarginBottom + systemBars.bottom,
                right = addNoteMarginRight + systemBars.right,
                left = addNoteMarginLeft + systemBars.left,
            )
            insets
        }
    }

    companion object {
        const val EDITING_INDEX = "EDITING_INDEX_EXTRA"
    }
}