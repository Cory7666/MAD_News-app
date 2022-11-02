package org.cory7666.newsapp.data

import org.cory7666.newsapp.data.model.LoginRequiredData

interface UserRepository
{
  fun validateNickname(x: String): ExecutionResult
  fun validateEmail(x: String): ExecutionResult
  fun validatePassword(x: String): ExecutionResult
  fun login(data: LoginRequiredData): ExecutionResult
  fun register(data: LoginRequiredData): ExecutionResult
  fun isLoggedIn(): Boolean
  fun logout(): ExecutionResult
}