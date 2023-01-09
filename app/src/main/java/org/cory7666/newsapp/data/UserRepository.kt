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

package org.cory7666.newsapp.data

import org.cory7666.newsapp.data.model.LoginRequiredData
import org.cory7666.newsapp.data.model.RegistrationRequiredData

interface UserRepository<T, K>
{
  fun validateNickname(x: String): T
  fun validateEmail(x: String): T
  fun validatePassword(x: String): T
  fun login(data: LoginRequiredData): K
  fun register(data: RegistrationRequiredData): K
  fun isLoggedIn(): Boolean
  fun logout(): K
}