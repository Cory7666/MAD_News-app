package org.cory7666.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import org.cory7666.newsapp.data.ExecutionResult
import org.cory7666.newsapp.data.UserRepository
import org.cory7666.newsapp.data.utils.validation.ValidationResult

class MainActivityViewModel(val repository: UserRepository<ValidationResult, ExecutionResult>) :
  ViewModel()
{

}