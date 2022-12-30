package org.cory7666.newsapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import org.cory7666.newsapp.R
import org.cory7666.newsapp.data.news.NewsInfo

@SuppressLint("NotifyDataSetChanged")
class HomeNewsListRecyclerAdapter(
  private val recyclerHolder: Fragment
) : RecyclerView.Adapter<ViewHolder>()
{
  inner class ArticleHolderView(view: View) : ViewHolder(view)
  {
    private val titleView: TextView = view.findViewById(R.id.title)
    private val descriptionView: TextView = view.findViewById(R.id.description)
    private val openInSourceButtonView: Button =
      view.findViewById(R.id.buttonOpenInSource)

    fun bind(article: NewsInfo, recyclerHolder: Fragment)
    {
      titleView.text = article.title
      descriptionView.text = article.description
      openInSourceButtonView.setOnClickListener {
        val action =
          HomeScreenDirections.actionHomeScreenToWebViewFragment(article.url)
        recyclerHolder.findNavController().navigate(action)
      }
    }
  }

  inner class LoaderViewHolder(view: View) : ViewHolder(view)

  var newsList: List<NewsInfo> = emptyList()
    set(value)
    {
      field = value
      notifyDataSetChanged()
    }

  init
  {
    this.newsList = emptyList()
  }

  override fun getItemViewType(position: Int): Int
  {
    return if (position < newsList.size) ArticleHolderViewId
    else LoadingViewHolderId
  }

  override fun onCreateViewHolder(
    parent: ViewGroup, viewType: Int
  ): ViewHolder
  {
    return when (viewType)
    {
      ArticleHolderViewId -> ArticleHolderView(
        LayoutInflater
          .from(parent.context)
          .inflate(R.layout.home_news_recyclerview_item, parent, false)
      )
      LoadingViewHolderId -> LoaderViewHolder(
        LayoutInflater
          .from(parent.context)
          .inflate(R.layout.loader_recyclerview_item, parent, false)
      )
      else                -> throw IllegalArgumentException("No View Holder of type ${viewType}.")
    }
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int)
  {
    when (holder)
    {
      is ArticleHolderView -> holder.bind(newsList[position], recyclerHolder)
    }
  }

  override fun getItemCount(): Int = newsList.size + 1

  companion object
  {
    private const val ArticleHolderViewId = 532621
    private const val LoadingViewHolderId = -25864
  }
}