package org.cory7666.newsapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import org.cory7666.newsapp.R
import org.cory7666.newsapp.data.ExecutionResult
import org.cory7666.newsapp.data.UserRepository
import org.cory7666.newsapp.data.model.LoginRequiredData
import org.cory7666.newsapp.data.model.RegistrationRequiredData
import org.cory7666.newsapp.data.utils.validation.ValidationResult

class AuthenticationViewModel(
  private val repository: UserRepository<ValidationResult, ExecutionResult>,
  private val context: Context?
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
        ValidationResult.Success -> null
        else                     -> context?.getString(R.string.text_invalid_symbols_in_nickname)
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
        ValidationResult.Success -> null
        else                     -> context?.getString(R.string.text_invalid_email)
      }
    }
  }

  fun validatePassword(x: String?)
  {
    _passwordHint.value = if (x.isNullOrEmpty())
    {
      context?.getString(R.string.text_invalid_required_field)
    }
    else
    {
      when (repository.validatePassword(x))
      {
        ValidationResult.Success           -> null
        ValidationResult.TooShort          -> context?.getString(R.string.text_invalid_length_short)
        ValidationResult.UnresolvedSymbols -> context?.getString(R.string.text_invalid_symbols_in_password)
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
    else if (repository.validateEmail(email) != ValidationResult.Success || repository.validatePassword(
        password
      ) != ValidationResult.Success
    )
    {
      _toastMessage.value =
        context?.getString(R.string.text_incorrect_sign_in_data)
    }
    else
    {
      when (val result =
        repository.login(LoginRequiredData(email = email, password = password)))
      {
        is ExecutionResult.Success ->
        {
          _toastMessage.value =
            "${context?.getString(R.string.text_signed_in_as)} ${email}."
          _isUserLoggedIn.value = true
        }
        is ExecutionResult.Error   ->
        {
          result.exception?.printStackTrace()
          _toastMessage.value =
            getUserFriendlyExecutionErrorReasonMessage(result)
        }
        is ExecutionResult.Task<*> ->
        {
          result.task.addOnSuccessListener {
            _toastMessage.value = "Done!"
            _isUserLoggedIn.value = true
          }.addOnFailureListener { ex ->
            ex.printStackTrace()
            _toastMessage.value = getUserFriendlyExceptionReasonMessage(ex)
          }
        }
      }
    }
  }

  fun tryRegister(nickname: String?, email: String?, password: String?)
  {
    if (nickname.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty())
    {
      _toastMessage.value =
        context?.getString(R.string.text_fill_required_fields)
    }
    else if (repository.validateEmail(email) != ValidationResult.Success || repository.validatePassword(
        password
      ) != ValidationResult.Success || repository.validateNickname(nickname) != ValidationResult.Success
    )
    {
      _toastMessage.value =
        context?.getString(R.string.text_incorrect_registration_data)
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
          _toastMessage.value =
            "${context?.getString(R.string.text_registered_as)} ${email}."
          _isUserLoggedIn.value = true
        }
        is ExecutionResult.Error   ->
        {
          result.exception?.printStackTrace()
          _toastMessage.value =
            getUserFriendlyExecutionErrorReasonMessage(result)
        }

        is ExecutionResult.Task<*> ->
        {
          result.task.addOnSuccessListener {
            _toastMessage.value = "Done!"
            _isUserLoggedIn.value = true
          }.addOnFailureListener { ex ->
            ex.printStackTrace()
            _toastMessage.value = getUserFriendlyExceptionReasonMessage(ex)
          }
        }
      }
    }
  }

  private fun getUserFriendlyExecutionErrorReasonMessage(result: ExecutionResult.Error): String
  {
    return getUserFriendlyExceptionReasonMessage(result.exception)
      ?: result.message ?: result.exception?.message
      ?: "${context?.getString(R.string.text_error)}"
  }

  private fun getUserFriendlyExceptionReasonMessage(ex: Exception?): String?
  {
    return when (ex)
    {
      is FirebaseAuthInvalidUserException -> "${context?.getString(R.string.text_invalid_user_data)}"
      is FirebaseException                -> "${context?.getString(R.string.text_connection_with_server_error)}"
      else                                -> null
    }
  }
}