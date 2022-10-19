package org.cory7666.newsapp.ui.identification

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.cory7666.newsapp.R
import org.cory7666.newsapp.data.ExecutionResult
import org.cory7666.newsapp.data.TemporaryUserRepository
import org.cory7666.newsapp.data.UserRepository
import org.cory7666.newsapp.data.utils.validation.exception.InvalidLengthException
import org.cory7666.newsapp.data.utils.validation.exception.InvalidSymbolsException

class AuthenticationViewModel(private val context: Context?) : ViewModel()
{
  private val repository: UserRepository = TemporaryUserRepository()

  private val _nicknameHint = MutableLiveData<String?>(null)
  private val _emailHint = MutableLiveData<String?>(null)
  private val _passwordHint = MutableLiveData<String?>(null)

  val nicknameHint: LiveData<String?>
    get() = _nicknameHint
  val emailHint: LiveData<String?>
    get() = _emailHint
  val passwordHint: LiveData<String?>
    get() = _passwordHint

  fun validateNickname(x: String?)
  {
    _nicknameHint.value = if (x == null || x.isEmpty())
    {
      context?.getString(R.string.text_invalid_required_field)
    }
    else
    {
      when (val result = repository.validateNickname(x))
      {
        is ExecutionResult.Error   -> context?.getString(R.string.text_invalid_symbols_in_nickname)
        is ExecutionResult.Success -> null
      }
    }
  }

  fun validateEmail(x: String?)
  {
    _emailHint.value = if (x == null || x.isEmpty())
    {
      context?.getString(R.string.text_invalid_required_field)
    }
    else
    {
      when (repository.validateEmail(x))
      {
        is ExecutionResult.Success -> null
        is ExecutionResult.Error   -> context?.getString(R.string.text_invalid_email)
      }
    }
  }

  fun validatePassword(x: String?)
  {
    _passwordHint.value = if (x == null || x.isEmpty())
    {
      context?.getString(R.string.text_invalid_required_field)
    }
    else
    {
      when (val result = repository.validatePassword(x))
      {
        is ExecutionResult.Success -> null
        is ExecutionResult.Error   -> when (result.exception)
        {
          is InvalidLengthException  -> context?.getString(R.string.text_invalid_length_short)
          is InvalidSymbolsException -> context?.getString(R.string.text_invalid_symbols_in_password)
          else                       -> context?.getString(R.string.text_invalid_symbols_in_password)
        }
      }
    }
  }
}