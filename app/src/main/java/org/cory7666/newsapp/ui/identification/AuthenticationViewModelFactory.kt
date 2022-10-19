package org.cory7666.newsapp.ui.identification

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AuthenticationViewModelFactory(val context: Context?) : ViewModelProvider.Factory
{
  override fun <T : ViewModel> create(modelClass: Class<T>): T
  {
    if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java))
    {
      return AuthenticationViewModel(context = context) as T
    }
    else
    {
      throw IllegalArgumentException("Can't create ViewModel of type ${AuthenticationViewModel::class.java.name}")
    }
  }
}