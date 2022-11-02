package org.cory7666.newsapp.data

sealed class ExecutionResult
{
  class Success : ExecutionResult()
  class Error(val message: String? = null, val exception: Exception? = null) :
    ExecutionResult()

  class Task<T>(val task: com.google.android.gms.tasks.Task<T>) :
    ExecutionResult()
}