package org.cory7666.newsapp.data.utils.validation

sealed class ValidationResult
{
  class Success : ValidationResult()
  data class Error(val messageResourceCode: Int) : ValidationResult()

  override fun toString(): String
  {
    return when (this)
    {
      is Error -> "Error"
      else     -> "Success"
    }
  }
}
