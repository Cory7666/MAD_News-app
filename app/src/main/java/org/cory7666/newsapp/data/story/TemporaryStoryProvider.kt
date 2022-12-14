package org.cory7666.newsapp.data.story

class TemporaryStoryProvider : StoryProvider<StoryInfo>
{
  override fun get(): List<StoryInfo>
  {
    return listOf(
      StoryInfo("T1", "Description1", "", 100),
      StoryInfo("T2", "Description2", "", 100),
      StoryInfo("T3", "Description3", "", 100),
      StoryInfo("T4", "Description4", "", 100),
      StoryInfo("T5", "Description5", "", 100),
      StoryInfo("T6", "Description6", "", 100)
    )
  }
}