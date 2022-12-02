package org.cory7666.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.cory7666.newsapp.databinding.FragmentHomeScreenBinding
import org.cory7666.newsapp.viewmodel.HomeScreenViewModel

class HomeScreen : Fragment()
{
  private lateinit var binding: FragmentHomeScreenBinding
  private lateinit var viewModel: HomeScreenViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View
  {
    binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
    viewModel = ViewModelProvider(this)[HomeScreenViewModel::class.java]
    binding.recyclerView.layoutManager = LinearLayoutManager(context)
    binding.recyclerView.adapter = HomeRecyclerAdapter(viewModel.newsList, this)
    activity?.actionBar?.show()

    CoroutineScope(Dispatchers.Main).launch {
      viewModel.update()
    }

    return binding.root
  }
}