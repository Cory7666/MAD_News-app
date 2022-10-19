package org.cory7666.newsapp.data

sealed class ExecutionResult
{
  class Success : ExecutionResult()
  class Error(val message: String? = null, val exception: Exception? = null) :
    ExecutionResult()
}