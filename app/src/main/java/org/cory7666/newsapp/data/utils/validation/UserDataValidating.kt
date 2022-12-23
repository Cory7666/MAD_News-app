package org.cory7666.newsapp.data.utils.validation

interface UserDataValidating
{
  fun checkEmail(): ValidationResult
  fun checkPassword(): ValidationResult
  fun checkNickname(): ValidationResult
}