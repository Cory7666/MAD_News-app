package org.cory7666.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActionBarViewModel : ViewModel()
{
  private val _hidden = MutableLiveData<Boolean>(true)
  private val _customActionBar = MutableLiveData<Int>(0)
  val hidden: LiveData<Boolean>
    get() = _hidden
  val customActionBar: LiveData<Int>
    get() = _customActionBar

  fun show()
  {
    _hidden.value = false
  }

  fun hide()
  {
    _hidden.value = true
  }

  fun setCustomBarAndShow(resource: Int)
  {
    _customActionBar.value = resource
    show()
  }
}