package org.cory7666.newsapp.data.utils.validation

import org.cory7666.newsapp.data.utils.validation.exception.InvalidLengthException
import org.cory7666.newsapp.data.utils.validation.exception.InvalidSymbolsException
import java.util.regex.Pattern

class PasswordValidator(private val x: String) : Validating
{
  override fun validate()
  {
    if (x.length < minPasswordLength)
    {
      throw InvalidLengthException.Short()
    }
    else if (!symbolSequencePattern.matcher(x).matches())
    {
      throw InvalidSymbolsException()
    }
  }

  companion object
  {
    private val symbolSequencePattern: Pattern = Pattern.compile("^[\\d]*$")
    private const val minPasswordLength: Int = 5
  }
}