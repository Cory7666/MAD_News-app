package org.cory7666.newsapp.data

import org.cory7666.newsapp.data.model.LoginRequiredData
import org.cory7666.newsapp.data.utils.validation.EmailValidator
import org.cory7666.newsapp.data.utils.validation.NicknameValidator
import org.cory7666.newsapp.data.utils.validation.PasswordValidator

class TemporaryUserRepository : UserRepository
{
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
    return ExecutionResult.Success()
  }

  override fun authenticate(loginRequiredData: LoginRequiredData): ExecutionResult
  {
    return ExecutionResult.Success()
  }
}