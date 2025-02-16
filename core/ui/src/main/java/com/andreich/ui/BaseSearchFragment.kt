package com.andreich.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreich.ui.databinding.FragmentBaseSearchBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseSearchFragment : Fragment(R.layout.fragment_base_search) {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("MainActivity", throwable.message, throwable)
    }

    abstract val viewModel: BaseViewModel

    private var _binding: FragmentBaseSearchBinding? = null
    private val binding: FragmentBaseSearchBinding
        get() = _binding ?: throw RuntimeException("Binding is null!")

    private val debounce by lazy {
        Debounce(1000, lifecycleScope) {
            Log.d("LIST_FRAGMENT_SEARCH", it)
            viewModel.sendIntent(BaseUiIntent.SearchTrack(it))
        }
    }

    private val musicListAdapter: BaseMusicListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        BaseMusicListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeViewModel()
        observeNews()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaseSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerMusic.layoutManager = LinearLayoutManager(requireContext())
            recyclerMusic.adapter = musicListAdapter
            onSearchQuery()
            musicListAdapter.onMovieClick = object : BaseMusicListAdapter.OnMusicTrackClickListener {
                override fun onMusicClick(musicItem: MusicItem) {
                    viewModel.sendIntent(BaseUiIntent.ChooseTrack(musicItem))
                }
            }
        }
    }

    private fun onSearchQuery() {
        var previousText = ""
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("LIST_FRAGMENT_SEARCH", query.length.toString())
                viewModel.sendIntent(BaseUiIntent.SearchTrack(query))
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText != previousText) {
                    previousText = newText
                    debounce.offer(newText)
                }
                return true
            }
        })
    }

    private fun View.changeVisibility() {
        visibility = if (visibility == GONE) {
            VISIBLE
        } else GONE
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collect { state ->
                    state.audioList.let {
                        musicListAdapter.submitList(it)
                    }
                    withContext(Dispatchers.Main) {
                        binding.progressLoadMusic.visibility =
                            if (state.isLoading) VISIBLE else INVISIBLE
                    }
                }
            }
        }

    }

    private fun observeNews() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.news.collect { state ->
                    when(state) {
                        BaseUiNews.Initial -> {}
                        is BaseUiNews.ShowToast -> {
                            showToast(state.message)
                        }
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}