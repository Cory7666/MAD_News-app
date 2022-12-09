package org.cory7666.newsapp.data.story

interface StoryProvider<T>
{
  fun get(): List<T>
}