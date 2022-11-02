package org.cory7666.newsapp.data

import org.cory7666.newsapp.data.model.LoginRequiredData
import org.cory7666.newsapp.data.utils.validation.EmailValidator
import org.cory7666.newsapp.data.utils.validation.NicknameValidator
import org.cory7666.newsapp.data.utils.validation.PasswordValidator

class TemporaryUserRepository : UserRepository
{
  private var isLoggedIn: Boolean = false

  override fun validateNickname(x: String): ExecutionResult
  {
    return try
    {
      NicknameValidator(x).validate()
      ExecutionResult.Success()
    }
    catch (ex: Exception)
    {
      ExecutionResult.Error(message = "Invalid Nickname", exception = ex)
    }
  }

  override fun validateEmail(x: String): ExecutionResult
  {
    return try
    {
      EmailValidator(x).validate()
      ExecutionResult.Success()
    }
    catch (ex: Exception)
    {
      ExecutionResult.Error(message = "Invalid Email", exception = ex)
    }
  }

  override fun validatePassword(x: String): ExecutionResult
  {
    return try
    {
      PasswordValidator(x).validate()
      ExecutionResult.Success()
    }
    catch (ex: Exception)
    {
      ExecutionResult.Error(message = "Invalid Password", exception = ex)
    }
  }

  override fun login(data: LoginRequiredData): ExecutionResult
  {
    return try
    {
      EmailValidator(data.email).validate()
      PasswordValidator(data.password).validate()

      if (data.email == "alex@localhost.com" && data.password == "123456")
      {
        isLoggedIn = true
        ExecutionResult.Success()
      }
      else
      {
        ExecutionResult.Error(message = "Incorrect Login Data")
      }
    }
    catch (ex: Exception)
    {
      ExecutionResult.Error(message = "Incorrect Login Data", exception = ex)
    }
  }

  override fun register(data: LoginRequiredData): ExecutionResult
  {
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