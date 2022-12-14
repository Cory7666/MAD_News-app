package org.cory7666.newsapp.data.story

class StoryInfo(
  val title: String,
  val description: String,
  val sources: List<String>,
  val height: Long?
) : java.io.Serializable
{}