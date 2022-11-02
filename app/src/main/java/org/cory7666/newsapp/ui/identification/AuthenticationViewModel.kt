package org.cory7666.newsapp.ui.identification

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.cory7666.newsapp.R
import org.cory7666.newsapp.data.ExecutionResult
import org.cory7666.newsapp.data.UserRepository
import org.cory7666.newsapp.data.model.LoginRequiredData
import org.cory7666.newsapp.data.model.RegistrationRequiredData
import org.cory7666.newsapp.data.utils.validation.exception.InvalidLengthException
import org.cory7666.newsapp.data.utils.validation.exception.InvalidSymbolsException

class AuthenticationViewModel(
  private val repository: UserRepository, private val context: Context?
) : ViewModel()
{
  private val _nicknameHint = MutableLiveData<String?>(null)
  private val _emailHint = MutableLiveData<String?>(null)
  private val _passwordHint = MutableLiveData<String?>(null)
  private val _toastMessage = MutableLiveData<String>("")
  private val _isUserLoggedIn = MutableLiveData<Boolean>(false)

  val nicknameHint: LiveData<String?>
    get() = _nicknameHint
  val emailHint: LiveData<String?>
    get() = _emailHint
  val passwordHint: LiveData<String?>
    get() = _passwordHint
  val toastMessage: LiveData<String>
    get() = _toastMessage
  val isUserLoggedIn: LiveData<Boolean>
    get() = _isUserLoggedIn

  fun validateNickname(x: String?)
  {
    _nicknameHint.value = if (x == null || x.isEmpty())
    {
      context?.getString(R.string.text_invalid_required_field)
    }
    else
    {
      when (repository.validateNickname(x))
      {
        is ExecutionResult.Error   -> context?.getString(R.string.text_invalid_symbols_in_nickname)
        is ExecutionResult.Success -> null
        else                       -> null
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
        else                       -> null
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
        else                       -> null
      }
    }
  }

  fun tryLogin(email: String?, password: String?)
  {
    if (email == null || password == null || email.isEmpty() || password.isEmpty())
    {
      _toastMessage.value =
        context?.getString(R.string.text_fill_required_fields)
    }
    else
    {
      when (val result =
        repository.login(LoginRequiredData(email = email, password = password)))
      {
        is ExecutionResult.Success ->
        {
          _toastMessage.value = "Logged in as ${email}."
          _isUserLoggedIn.value = true
        }
        is ExecutionResult.Error   -> _toastMessage.value =
          result.exception?.message ?: result.message ?: "Error!"
        is ExecutionResult.Task<*> ->
        {
          result.task.addOnSuccessListener {
            Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show()
            _isUserLoggedIn.value = true
          }.addOnFailureListener { ex ->
            Toast
              .makeText(context, "Error: ${ex.message}.", Toast.LENGTH_LONG)
              .show()
          }
        }
      }
    }
  }

  fun tryRegister(nickname: String?, email: String?, password: String?)
  {
    if (nickname == null || nickname.isEmpty() || email == null || email.isEmpty() || password == null || password.isEmpty())
    {
      _toastMessage.value =
        context?.getString(R.string.text_fill_required_fields)
    }
    else
    {
      when (val result = repository.register(
        RegistrationRequiredData(
          nickname = nickname, email = email, password = password
        )
      ))
      {
        is ExecutionResult.Success ->
        {
          _toastMessage.value = "Registered as ${email}."
          _isUserLoggedIn.value = true
        }
        is ExecutionResult.Error   -> _toastMessage.value =
          result.message ?: "Error!"
        is ExecutionResult.Task<*> ->
        {
          result.task.addOnSuccessListener {
            Toast.makeText(context, "Done!", Toast.LENGTH_SHORT).show()
            _isUserLoggedIn.value = true
          }.addOnFailureListener { ex ->
            Toast
              .makeText(context, "Error: ${ex.message}.", Toast.LENGTH_LONG)
              .show()
          }
        }
      }
    }
  }
}