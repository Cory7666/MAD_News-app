package org.cory7666.newsapp.data.story

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreStoryProvider(
  private val x: MutableLiveData<List<StoryInfo>>
) : StoryProvider<Void>
{
  override fun get(): List<Void>
  {
    val db = FirebaseFirestore.getInstance()
    db.collection("story").get().addOnCompleteListener { result ->
      Log.e("TAG", "get: ${result.isSuccessful}")
      if (result.isSuccessful)
      {
        val list = ArrayList<StoryInfo>(100)
        for (document in result.result)
        {
          val data = document.data
          list.add(
            StoryInfo(
              data["title"] as String, data["description"] as String
            )
          )
        }
        x.value = list
      }
      else
      {
        result.exception?.printStackTrace()
      }
    }.addOnFailureListener {
      it.printStackTrace()
    }
    return emptyList()
  }
}