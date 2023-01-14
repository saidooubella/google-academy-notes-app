package com.saidooubella.auth

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup.MarginLayoutParams
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.shape.MaterialShapeDrawable
import com.saidooubella.auth.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val binding by binding(ActivityHomeBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingUpTheUI()

        val notesAdapter = NotesAdapter(object : NotesAdapter.OnItemLongClicked {

            override fun onLongClick(index: Int) {
                MaterialAlertDialogBuilder(this@HomeActivity)
                    .setTitle(getString(R.string.delete_confirmation_title))
                    .setMessage(getString(R.string.delete_confirmation_message))
                    .setPositiveButton(getString(R.string.delete_option)) { dialog, _ ->
                        InMemoryNotesRepository.removeNote(index)
                        dialog.cancel()
                    }
                    .setNegativeButton(getString(R.string.cancel_option)) { dialog, _ -> dialog.cancel() }
                    .show()
            }

            override fun onClick(index: Int) {
                startActivity(
                    Intent(this@HomeActivity, NewNoteActivity::class.java)
                        .putExtra(NewNoteActivity.EDITING_INDEX, index)
                )
            }
        })

        binding.noteList.adapter = notesAdapter

        InMemoryNotesRepository.loadNotes().observe(this) {
            binding.emptyListIndicator.isGone = it.isNotEmpty()
            notesAdapter.notes = it
        }

        binding.addNoteButton.setOnClickListener {
            startActivity(Intent(this, NewNoteActivity::class.java))
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

        val notesListPaddingBottom = binding.noteList.paddingBottom
        val notesListPaddingLeft = binding.noteList.paddingLeft
        val notesListPaddingRight = binding.noteList.paddingRight
        ViewCompat.setOnApplyWindowInsetsListener(binding.noteList) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updatePadding(
                bottom = notesListPaddingBottom + systemBars.bottom,
                left = notesListPaddingLeft + systemBars.left,
                right = notesListPaddingRight + systemBars.right
            )
            insets
        }

        val addNoteMarginBottom = binding.addNoteButton.marginBottom
        val addNoteMarginRight = binding.addNoteButton.marginRight
        val addNoteMarginLeft = binding.addNoteButton.marginLeft

        ViewCompat.setOnApplyWindowInsetsListener(binding.addNoteButton) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.updateLayoutParams<MarginLayoutParams> {
                bottomMargin = addNoteMarginBottom + systemBars.bottom
                rightMargin = addNoteMarginRight + systemBars.right
                leftMargin = addNoteMarginLeft + systemBars.left
            }
            insets
        }
    }
}
