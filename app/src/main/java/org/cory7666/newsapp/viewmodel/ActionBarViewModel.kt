package org.cory7666.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActionBarViewModel : ViewModel()
{
  private val mHidden = MutableLiveData<Boolean>(true)
  val hidden: LiveData<Boolean>
    get() = mHidden

  fun show()
  {
    mHidden.value = false
  }

  fun hide()
  {
    mHidden.value = true
  }
}