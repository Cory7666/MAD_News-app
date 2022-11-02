package org.cory7666.newsapp.data.utils.validation

import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.cory7666.newsapp.data.ExecutionResult
import org.cory7666.newsapp.data.UserRepository
import org.cory7666.newsapp.data.model.LoginRequiredData
import org.cory7666.newsapp.data.model.RegistrationRequiredData

class FirebaseUserRepository : UserRepository
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
    return try
    {
      EmailValidator(data.email).validate()
      PasswordValidator(data.password).validate()

      ExecutionResult.Task(
        Firebase.auth.signInWithEmailAndPassword(data.email, data.password)
      )
    }
    catch (ex: Exception)
    {
      ExecutionResult.Error(message = "Incorrect Login Data", exception = ex)
    }
  }

  override fun register(data: LoginRequiredData): ExecutionResult
  {
    return try
    {
      NicknameValidator((data as RegistrationRequiredData).nickname).validate()
      EmailValidator(data.email).validate()
      PasswordValidator(data.password).validate()

      ExecutionResult.Task(Firebase.auth
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
    catch (ex: Exception)
    {
      ExecutionResult.Error(message = "Incorrect Login Data", exception = ex)
    }
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