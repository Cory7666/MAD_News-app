package org.cory7666.newsapp.data.news

interface NewsProvider<T>
{
  fun getFew(page: Int, count: Int): List<T>
}