package org.cory7666.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    binding.newsRecyclerView.layoutManager = LinearLayoutManager(context)
    binding.newsRecyclerView.adapter =
      HomeNewsListRecyclerAdapter(viewModel.newsList, this)

    binding.storiesRecyclerView.layoutManager =
      LinearLayoutManager(context).apply {
        orientation = RecyclerView.HORIZONTAL
      }
    binding.storiesRecyclerView.adapter =
      HomeStoriesRecyclerAdapter(viewModel, this)

    viewModel.toastMessageText.observe(viewLifecycleOwner) {
      if (it != 0)
      {
        val message = context?.getString(it)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
      }
    }

    binding.swipeRefreshLayout.setOnRefreshListener {
      viewModel.updateNewsList()
    }
    viewModel.isRefreshing.observe(viewLifecycleOwner) {
      binding.swipeRefreshLayout.isRefreshing = it
    }

    binding.newsRecyclerView.addItemDecoration(
      DividerItemDecoration(
        binding.newsRecyclerView.context, LinearLayout.VERTICAL
      )
    )

    binding.newsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener()
    {
      override fun onScrollStateChanged(
        recyclerView: RecyclerView, newState: Int
      )
      {
        super.onScrollStateChanged(recyclerView, newState)
        if (!recyclerView.canScrollVertically(RecyclerView.VERTICAL))
        {
          viewModel.updateNewsList()
        }
      }
    })

    setupActionBar()
    fullUpdate()

    return binding.root
  }

  private fun setupActionBar()
  {
    val actionBarViewModel =
      ViewModelProvider(requireActivity())[ActionBarViewModel::class.java]
    actionBarViewModel.setCustomBarAndShow(
      R.layout.home_screen_action_bar
    )

    (requireActivity() as AppCompatActivity).supportActionBar?.customView?.apply {
      findViewById<ImageButton>(R.id.buttonGoToSettings)?.setOnClickListener {
        findNavController().navigate(R.id.action_homeScreen_to_settingsScreen)
      }
      findViewById<ImageButton>(R.id.buttonUpdate).setOnClickListener {
        fullUpdate()
      }
    }
  }

  private fun fullUpdate()
  {
    viewModel.updateStoriesList()
    viewModel.updateNewsList()
  }
}