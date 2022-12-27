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
import org.cory7666.newsapp.R
import org.cory7666.newsapp.data.news.NewsInfo

@SuppressLint("NotifyDataSetChanged")
class HomeNewsListRecyclerAdapter(
  private val recyclerHolder: Fragment
) : RecyclerView.Adapter<HomeNewsListRecyclerAdapter.ArticleHolderView>()
{
  inner class ArticleHolderView(view: View) : RecyclerView.ViewHolder(view)
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

  override fun onCreateViewHolder(
    parent: ViewGroup, viewType: Int
  ): ArticleHolderView
  {
    val itemView =
      LayoutInflater
        .from(parent.context)
        .inflate(R.layout.home_news_recyclerview_item, parent, false)
    return ArticleHolderView(itemView)
  }

  override fun onBindViewHolder(holder: ArticleHolderView, position: Int)
  {
    holder.bind(newsList[position], recyclerHolder)
  }

  override fun getItemCount(): Int = newsList.size
}