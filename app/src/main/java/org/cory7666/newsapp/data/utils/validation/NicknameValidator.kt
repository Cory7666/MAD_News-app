package org.cory7666.newsapp.data.utils.validation

import org.cory7666.newsapp.data.utils.validation.exception.InvalidSymbolsException
import java.util.regex.Pattern

class NicknameValidator(private val x: String) : Validating
{
  override fun validate()
  {
    if (!pattern.matcher(x).matches())
    {
      throw InvalidSymbolsException()
    }
  }

  companion object
  {
    private val pattern: Pattern = Pattern.compile("^[\\w]*$")
  }
}