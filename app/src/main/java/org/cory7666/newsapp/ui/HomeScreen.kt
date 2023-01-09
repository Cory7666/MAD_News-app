/*
 *   Copyright 2022 Alex Filozop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    setupNewsRecyclerView()
    setupStoriesRecyclerView()
    setupListeners()
    setObservers()
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
    }
  }

  private fun fullUpdate()
  {
    viewModel.updateStoriesList()
    viewModel.clearAndGetNews()
  }

  private fun setupNewsRecyclerView()
  {
    binding.newsRecyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = HomeNewsListRecyclerAdapter(this@HomeScreen).apply {
        viewModel.newsList.observe(viewLifecycleOwner) {
          this.newsList = it ?: emptyList()
        }
      }

      addItemDecoration(
        DividerItemDecoration(
          this.context, LinearLayout.VERTICAL
        )
      )

      addOnScrollListener(object : RecyclerView.OnScrollListener()
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
    }
  }

  private fun setupStoriesRecyclerView()
  {
    binding.storiesRecyclerView.apply {
      layoutManager = LinearLayoutManager(context).apply {
        orientation = RecyclerView.HORIZONTAL
      }
      adapter = HomeStoriesRecyclerAdapter(this@HomeScreen).apply {
        viewModel.storiesList.observe(viewLifecycleOwner) {
          storiesContainer = it
        }
      }
    }
  }

  private fun setupListeners()
  {
    binding.swipeRefreshLayout.setOnRefreshListener {
      viewModel.updateNewsList()
    }
  }

  private fun setObservers()
  {
    viewModel.toastMessageText.observe(viewLifecycleOwner) {
      if (it != 0)
      {
        val message = context?.getString(it)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
      }
    }

    viewModel.isRefreshing.observe(viewLifecycleOwner) {
      binding.swipeRefreshLayout.isRefreshing = it
    }
  }
}