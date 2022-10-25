package org.cory7666.newsapp.ui.identification

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.cory7666.newsapp.data.UserRepository

class AuthenticationViewModelFactory(
  private val repository: UserRepository, private val context: Context?
) : ViewModelProvider.Factory
{
  override fun <T : ViewModel> create(modelClass: Class<T>): T
  {
    if (modelClass.isAssignableFrom(AuthenticationViewModel::class.java))
    {
      @Suppress("UNCHECKED_CAST") return AuthenticationViewModel(
        repository = repository, context = context
      ) as T
    }
    else
    {
      throw IllegalArgumentException("Can't create ViewModel of type ${AuthenticationViewModel::class.java.name}")
    }
  }
}