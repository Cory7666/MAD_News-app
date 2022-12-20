package org.cory7666.newsapp.data.story

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class FirestoreStoryProvider(
  private val x: MutableLiveData<List<StoryInfo>>
) : StoryProvider<Void>
{
  override fun get(): List<Void>
  {
    if (x.value.isNullOrEmpty())
    {
      Log.d(Tag, "get: update stories list.")
      Log.e(Tag, "get: size - ${storiesList.size}")

      if (storiesList.isNullOrEmpty())
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
            x.value = storiesList
          }
          else
          {
            result.exception?.printStackTrace()
          }
        }.addOnFailureListener {
          it.printStackTrace()
        }
      }
      else
      {
        x.value = storiesList
      }
    }
    return emptyList()
  }

  companion object
  {
    private val Tag: String = FirestoreStoryProvider::class.java.name;
    private val storiesList = LinkedList<StoryInfo>()
  }
}