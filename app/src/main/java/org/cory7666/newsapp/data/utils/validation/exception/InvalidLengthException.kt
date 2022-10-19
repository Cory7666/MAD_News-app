package org.cory7666.newsapp.data.utils.validation.exception

sealed class InvalidLengthException : ValidationException()
{
  class Short : InvalidLengthException()
  class Long : InvalidLengthException()
}