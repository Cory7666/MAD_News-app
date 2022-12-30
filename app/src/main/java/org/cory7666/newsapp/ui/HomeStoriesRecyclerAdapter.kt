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

class HomeStoriesRecyclerAdapter(
  private val parent: Fragment
) : RecyclerView.Adapter<HomeStoriesRecyclerAdapter.ViewHolder>()
{
  var storiesContainer: List<StoryInfo> = emptyList()
    set(value)
    {
      field = value
      notifyDataSetChanged()
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
    holder.bind(storiesContainer[position])
  }

  override fun getItemCount(): Int
  {
    return storiesContainer.size
  }

  inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
  {
    val imageView: TextView = view.findViewById(R.id.image)

    fun bind(story: StoryInfo)
    {
      imageView.text = story.title.subSequence(0, 2)
      imageView.setOnClickListener {
        Log.d("TAG", "onBindViewHolder: go to ${story}")
        val action = HomeScreenDirections.actionHomeScreenToStoryFragment(story)
        parent.findNavController().navigate(action)
      }
    }
  }
}