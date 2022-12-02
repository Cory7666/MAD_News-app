package org.cory7666.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.cory7666.newsapp.R
import org.cory7666.newsapp.databinding.FragmentHomeScreenBinding
import org.cory7666.newsapp.viewmodel.ActionBarViewModel
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

    setupActionBar()

    CoroutineScope(Dispatchers.Main).launch {
      viewModel.update()
    }

    return binding.root
  }

  private fun setupActionBar()
  {
    ViewModelProvider(requireActivity())[ActionBarViewModel::class.java].setCustomBarAndShow(
      R.layout.home_screen_action_bar
    )
    requireActivity().actionBar?.customView
      ?.findViewById<Button>(R.id.buttonGoToSettings)
      ?.setOnClickListener {
        findNavController().navigate(R.id.action_homeScreen_to_settingsScreen)
      }
  }
}