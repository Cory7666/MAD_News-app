package org.cory7666.newsapp.data.story

class TemporaryStoryProvider : StoryProvider<StoryInfo>
{
  override fun get(): List<StoryInfo>
  {
    return listOf(
      StoryInfo("T1", "Description1"),
      StoryInfo("T2", "Description2"),
      StoryInfo("T3", "Description3"),
      StoryInfo("T4", "Description4"),
      StoryInfo("T5", "Description5"),
      StoryInfo("T6", "Description6")
    )
  }
}