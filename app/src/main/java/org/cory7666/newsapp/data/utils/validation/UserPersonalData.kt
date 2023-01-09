/*
 *   Copyright 2022 Alex Filozop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.cory7666.newsapp.data.utils.validation

import java.util.regex.Pattern

class UserPersonalData(private val data: String) : UserDataValidating
{
  override fun checkEmail(): ValidationResult
  {
    return if (emailPattern.matcher(data).matches())
    {
      ValidationResult.Success
    }
    else
    {
      ValidationResult.UnresolvedSymbols
    }
  }

  override fun checkPassword(): ValidationResult
  {
    return if (data.length < minimalPasswordLength)
    {
      ValidationResult.TooShort
    }
    else if (!passwordSymbolSequencePattern.matcher(data).matches())
    {
      ValidationResult.UnresolvedSymbols
    }
    else
    {
      ValidationResult.Success
    }
  }

  override fun checkNickname(): ValidationResult
  {
    return if (nicknamePattern.matcher(data).matches())
    {
      ValidationResult.Success
    }
    else
    {
      ValidationResult.UnresolvedSymbols
    }
  }

  companion object
  {
    private val nicknamePattern: Pattern = Pattern.compile("^[\\w]*$")
    private val emailPattern: Pattern =
      Pattern.compile("^[\\w.]*@.*\\..{2,10}\$")
    private val passwordSymbolSequencePattern: Pattern =
      Pattern.compile("^[\\w]*$")
    private const val minimalPasswordLength: Int = 6
  }
}