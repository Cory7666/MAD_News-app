package org.cory7666.newsapp.data.utils.validation

import org.cory7666.newsapp.data.utils.validation.exception.InvalidEmailException
import java.util.regex.Pattern

class EmailValidator(private val x: String) : Validating
{
  override fun validate()
  {
    if (!pattern.matcher(x).matches())
    {
      throw InvalidEmailException()
    }
  }

  companion object
  {
    private val pattern: Pattern = Pattern.compile("^[\\w.]*@.*\\..{2,10}\$")
  }
}