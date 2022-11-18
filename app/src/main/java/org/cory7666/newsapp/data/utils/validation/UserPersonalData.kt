package org.cory7666.newsapp.data.utils.validation

import java.util.regex.Pattern

class UserPersonalData(private val data: String) : UserDataValidating
{
  override fun checkEmail(): ValidationResult
  {
    return if (emailPattern.matcher(data).matches())
    {
      ValidationResult.Success
    }
    else
    {
      ValidationResult.UnresolvedSymbols
    }
  }

  override fun checkPassword(): ValidationResult
  {
    return if (data.length < minimalPasswordLength)
    {
      ValidationResult.TooShort
    }
    else if (!passwordSymbolSequencePattern.matcher(data).matches())
    {
      ValidationResult.UnresolvedSymbols
    }
    else
    {
      ValidationResult.Success
    }
  }

  override fun checkNickname(): ValidationResult
  {
    return if (nicknamePattern.matcher(data).matches())
    {
      ValidationResult.Success
    }
    else
    {
      ValidationResult.UnresolvedSymbols
    }
  }

  companion object
  {
    private val nicknamePattern: Pattern = Pattern.compile("^[\\w]*$")
    private val emailPattern: Pattern =
      Pattern.compile("^[\\w.]*@.*\\..{2,10}\$")
    private val passwordSymbolSequencePattern: Pattern =
      Pattern.compile("^[\\d]*$")
    private const val minimalPasswordLength: Int = 5
  }
}