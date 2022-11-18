package org.cory7666.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.cory7666.newsapp.data.FirebaseUserRepository

class MainActivityViewModelFactory : ViewModelProvider.Factory
{
  override fun <T : ViewModel> create(modelClass: Class<T>): T
  {
    if (modelClass.isAssignableFrom(MainActivityViewModel::class.java))
    {
      @Suppress("UNCHECKED_CAST") return MainActivityViewModel(repository = FirebaseUserRepository()) as T
    }
    else
    {
      throw IllegalArgumentException("Can't create ViewModel of type ${MainActivityViewModel::class.java.name}")
    }
  }
}