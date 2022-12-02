package org.cory7666.newsapp.data.news

interface NewsProvider<T>
{
  fun getNews(): List<T>
}