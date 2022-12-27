package org.cory7666.newsapp.data.news

interface NewsProvider<T>
{
  fun getSomeNewsAsync(page: Int, count: Int)
}