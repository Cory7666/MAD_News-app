package org.cory7666.newsapp.data.story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class FirestoreStoryProvider() : StoryProvider
{
  private val _stories = MutableLiveData<List<StoryInfo>>()
  private val _error = MutableLiveData<Throwable>()
  val stories: LiveData<List<StoryInfo>> = _stories
  val error: LiveData<Throwable> = _error

  override fun getAsync()
  {
    Log.d(Tag, "get: update stories list.")
    Log.e(Tag, "get: size - ${storiesList.size}")

    if (storiesList.isEmpty())
    {
      val db = FirebaseFirestore.getInstance()
      db.collection("story").get().addOnCompleteListener { result ->
        if (result.isSuccessful)
        {
          for (document in result.result)
          {
            val data = document.data
            storiesList.add(
              StoryInfo(
                data["title"] as String,
                data["description"] as String,
                data["source"] as List<String>,
                data["height"] as Long?
              )
            )
            Log.d(
              FirestoreStoryProvider::class.java.name,
              "got story: ${data["title"]}|${data["description"]}|${data["source"]}",
            )
          }
          _stories.value = storiesList
        }
        else
        {
          result.exception?.printStackTrace()
          _error.value = result.exception
        }
      }.addOnFailureListener {
        it.printStackTrace()
        _error.value = it
      }
    }
    else
    {
      _stories.value = storiesList
    }
  }

  companion object
  {
    private val Tag: String = FirestoreStoryProvider::class.java.name
    private val storiesList = LinkedList<StoryInfo>()
  }
}