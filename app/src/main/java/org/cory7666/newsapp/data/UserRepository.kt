package org.cory7666.newsapp.data

import org.cory7666.newsapp.data.model.LoginRequiredData
import org.cory7666.newsapp.data.model.RegistrationRequiredData

interface UserRepository<T, K>
{
  fun validateNickname(x: String): T
  fun validateEmail(x: String): T
  fun validatePassword(x: String): T
  fun login(data: LoginRequiredData): K
  fun register(data: RegistrationRequiredData): K
  fun isLoggedIn(): Boolean
  fun logout(): K
}