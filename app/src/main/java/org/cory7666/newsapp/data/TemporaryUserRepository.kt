package org.cory7666.newsapp.data

import org.cory7666.newsapp.data.model.LoginRequiredData
import org.cory7666.newsapp.data.model.RegistrationRequiredData
import org.cory7666.newsapp.data.utils.validation.UserPersonalData
import org.cory7666.newsapp.data.utils.validation.ValidationResult

class TemporaryUserRepository : UserRepository<ValidationResult, ExecutionResult>
{
  private var isLoggedIn: Boolean = false

  override fun validateNickname(x: String): ValidationResult
  {
    return UserPersonalData(x).checkNickname()
  }

  override fun validateEmail(x: String): ValidationResult
  {
    return UserPersonalData(x).checkEmail()
  }

  override fun validatePassword(x: String): ValidationResult
  {
    return UserPersonalData(x).checkPassword()
  }

  override fun login(data: LoginRequiredData): ExecutionResult
  {
    return if (data.email == "alex@localhost.com" && data.password == "123456")
    {
      isLoggedIn = true
      ExecutionResult.Success()
    }
    else
    {
      ExecutionResult.Error(message = "Incorrect Login Data")
    }
  }

  override fun register(data: RegistrationRequiredData): ExecutionResult
  {
    isLoggedIn = true
    return ExecutionResult.Success()
  }

  override fun isLoggedIn(): Boolean
  {
    return isLoggedIn
  }

  override fun logout(): ExecutionResult
  {
    isLoggedIn = false
    return ExecutionResult.Success()
  }
}