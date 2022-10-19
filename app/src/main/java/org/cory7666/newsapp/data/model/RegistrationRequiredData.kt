package org.cory7666.newsapp.data.model

class RegistrationRequiredData(
  val nickname: String, email: String, password: String
) : LoginRequiredData(email, password)
