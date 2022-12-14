package org.cory7666.newsapp.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import org.cory7666.newsapp.R
import org.cory7666.newsapp.data.story.StoryInfo
import org.cory7666.newsapp.viewmodel.HomeScreenViewModel

class HomeStoriesRecyclerAdapter(
  viewModel: HomeScreenViewModel, private val parent: Fragment
) : RecyclerView.Adapter<HomeStoriesRecyclerAdapter.ViewHolder>()
{
  var storiesContainer: List<StoryInfo> = emptyList()

  init
  {
    viewModel.storiesList.observe(parent) {
      storiesContainer = it ?: emptyList<StoryInfo>()
      notifyDataSetChanged()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
  {
    return ViewHolder(
      LayoutInflater
        .from(parent.context)
        .inflate(R.layout.home_story_recyclerview_item, parent, false)
    )
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int)
  {
    holder.imageView.text = storiesContainer[position].title.subSequence(0, 2)

    holder.imageView.setOnClickListener {
      Log.e("TAG", "onBindViewHolder: go to ${storiesContainer[position]}")
      val action =
        HomeScreenDirections.actionHomeScreenToStoryFragment(storiesContainer[position])
      parent.findNavController().navigate(action)
    }
  }

  override fun getItemCount(): Int
  {
    return storiesContainer.size
  }

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
  {
    val imageView: TextView

    init
    {
      imageView = view.findViewById(R.id.image)
    }
  }
}