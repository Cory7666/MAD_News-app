package org.cory7666.newsapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import org.cory7666.newsapp.R
import org.cory7666.newsapp.data.news.NewsInfo

@SuppressLint("NotifyDataSetChanged")
class HomeNewsListRecyclerAdapter(
  newsListLiveData: LiveData<List<NewsInfo>>, parent: Fragment
) : RecyclerView.Adapter<HomeNewsListRecyclerAdapter.ViewHolder>()
{
  class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
  {
    val titleView: TextView = view.findViewById(R.id.title)
    val descriptionView: TextView = view.findViewById(R.id.description)
    val openInSourceButtonView: Button =
      view.findViewById(R.id.buttonOpenInSource)
  }

  private val context: Context
  var newsList: List<NewsInfo>

  init
  {
    this.context = parent.requireContext()
    this.newsList = newsListLiveData.value ?: emptyList()
    newsListLiveData.observe(parent) { newValue ->
      this@HomeNewsListRecyclerAdapter.newsList = newValue
      notifyDataSetChanged()
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
  {
    val itemView =
      LayoutInflater
        .from(parent.context)
        .inflate(R.layout.home_news_recyclerview_item, parent, false)
    return ViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int)
  {
    val containerItem = newsList[position]
    holder.titleView.text = containerItem.title
    holder.descriptionView.text = containerItem.description
    holder.openInSourceButtonView.setOnClickListener {
      ContextCompat.startActivity(
        context, Intent(Intent.ACTION_VIEW, Uri.parse(containerItem.url)), null
      )
    }
  }

  override fun getItemCount(): Int = newsList.size
}