package org.cory7666.newsapp.data

import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.cory7666.newsapp.data.model.LoginRequiredData
import org.cory7666.newsapp.data.model.RegistrationRequiredData
import org.cory7666.newsapp.data.utils.validation.UserPersonalData
import org.cory7666.newsapp.data.utils.validation.ValidationResult

class FirebaseUserRepository : UserRepository<ValidationResult, ExecutionResult>
{
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
    return ExecutionResult.Task(
      Firebase.auth.signInWithEmailAndPassword(data.email, data.password)
    )
  }

  override fun register(data: RegistrationRequiredData): ExecutionResult
  {
    return ExecutionResult.Task(Firebase.auth
      .createUserWithEmailAndPassword(data.email, data.password)
      .addOnSuccessListener {
        val profileChangeRequest =
          UserProfileChangeRequest
            .Builder()
            .setDisplayName(data.nickname)
            .build()
        Firebase.auth.currentUser?.updateProfile(profileChangeRequest)
      })
  }

  override fun isLoggedIn(): Boolean
  {
    return Firebase.auth.currentUser != null
  }

  override fun logout(): ExecutionResult
  {
    Firebase.auth.signOut()
    return ExecutionResult.Success()
  }
}