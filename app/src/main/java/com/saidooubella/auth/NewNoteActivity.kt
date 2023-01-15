package com.saidooubella.auth

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.*
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.*
import com.google.android.material.shape.MaterialShapeDrawable
import com.saidooubella.auth.databinding.ActivityNewNoteBinding
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

internal class NewNoteViewModel(
    private val notesDao: NotesDao,
    private val editingIndex: Long?,
) : ViewModel() {

    private val mutableState = MutableStateFlow(Note())

    val state = mutableState.asStateFlow()

    init {
        if (editingIndex != null) {
            viewModelScope.launch {
                mutableState.update { notesDao.load(editingIndex) }
            }
        }
    }

    fun updateContent(
        title: String = mutableState.value.title,
        content: String = mutableState.value.content
    ) = mutableState.update {
        it.copy(title = title, content = content)
    }

    fun saveNote() {
        viewModelScope.launch { notesDao.insert(mutableState.value) }
    }

    class Factory(
        private val notesDao: NotesDao,
        private val editingIndex: Long?,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewNoteViewModel(notesDao, editingIndex) as T
        }
    }
}

class NewNoteActivity : AppCompatActivity() {

    private val binding by binding(ActivityNewNoteBinding::inflate)
    private val viewModel by viewModels<NewNoteViewModel> {
        NewNoteViewModel.Factory(
            NotesDatabase.getInstance(this).notesDao, when (intent.hasExtra(EDITING_INDEX)) {
                true -> intent.getLongExtra(EDITING_INDEX, 0)
                else -> null
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        settingUpTheUI()

        if (intent.hasExtra(EDITING_INDEX)) {
            binding.appbar.title = "Editing Note"
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.distinctUntilChanged { _, new ->
                    new.title == binding.noteTitleField.text.toString() &&
                            new.content == binding.noteBodyField.text.toString()
                }.collectLatest {
                    binding.noteTitleField.setText(it.title)
                    binding.noteBodyField.setText(it.content)
                }
            }
        }

        binding.noteTitleField.doOnTextChanged { text, _, _, _ ->
            viewModel.updateContent(title = text.toString())
        }

        binding.noteBodyField.doOnTextChanged { text, _, _, _ ->
            viewModel.updateContent(content = text.toString())
        }

        binding.appbar.setNavigationOnClickListener {
            finish()
        }

        binding.saveNoteButton.setOnClickListener {

            val tile = viewModel.state.value.title
            val content = viewModel.state.value.content

            if (tile.isNotBlank() || content.isNotBlank()) {
                viewModel.saveNote()
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

    companion object {
        const val EDITING_INDEX = "EDITING_INDEX_EXTRA"
    }
}