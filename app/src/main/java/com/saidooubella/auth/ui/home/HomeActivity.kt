package com.saidooubella.auth.ui.home

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.shape.MaterialShapeDrawable
import com.saidooubella.auth.*
import com.saidooubella.auth.adapters.NotesAdapter
import com.saidooubella.auth.databinding.ActivityHomeBinding
import com.saidooubella.auth.db.local.NotesDatabase
import com.saidooubella.auth.ui.add.NewNoteActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private val binding by binding(ActivityHomeBinding::inflate)

    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModel.Factory(NotesDatabase.getInstance(this).notesDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingUpTheUI()

        val notesAdapter = NotesAdapter(object : NotesAdapter.OnItemListener {

            override fun onLongClick(id: Long) {
                MaterialAlertDialogBuilder(this@HomeActivity)
                    .setTitle(getString(R.string.delete_confirmation_title))
                    .setMessage(getString(R.string.delete_confirmation_message))
                    .setPositiveButton(getString(R.string.delete_option)) { dialog, _ ->
                        viewModel.removeNote(id)
                        dialog.cancel()
                    }
                    .setNegativeButton(getString(R.string.cancel_option)) { dialog, _ -> dialog.cancel() }
                    .show()
            }

            override fun onClick(id: Long) {
                startActivity(
                    Intent(this@HomeActivity, NewNoteActivity::class.java)
                        .putExtra(NewNoteActivity.EDITING_INDEX, id)
                )
            }
        })

        binding.noteList.adapter = notesAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.query.collectLatest { binding.clearQueryButton.isGone = it.isEmpty() }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.notes.collectLatest {
                    binding.emptyListIndicator.isGone = it.isNotEmpty()
                    notesAdapter.notes = it
                }
            }
        }

        binding.addNoteButton.setOnClickListener {
            startActivity(Intent(this, NewNoteActivity::class.java))
        }

        binding.searchField.doOnTextChanged { text, _, _, _ ->
            viewModel.setQuery(text.toString())
        }

        binding.clearQueryButton.setOnClickListener {
            binding.searchField.text?.clear()
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

        val addNoteMarginBottom = binding.root.paddingBottom
        val addNoteMarginRight = binding.root.paddingRight
        val addNoteMarginLeft = binding.root.paddingLeft

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime()
            )
            view.updatePadding(
                bottom = addNoteMarginBottom + systemBars.bottom,
                right = addNoteMarginRight + systemBars.right,
                left = addNoteMarginLeft + systemBars.left,
            )
            insets
        }
    }
}
