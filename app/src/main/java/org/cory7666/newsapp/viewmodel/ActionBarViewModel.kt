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

package org.cory7666.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActionBarViewModel : ViewModel()
{
  private val _hidden = MutableLiveData<Boolean>(true)
  private val _customActionBar = MutableLiveData<Int>(0)
  val hidden: LiveData<Boolean>
    get() = _hidden
  val customActionBar: LiveData<Int>
    get() = _customActionBar

  fun show()
  {
    _hidden.value = false
  }

  fun hide()
  {
    _hidden.value = true
  }

  fun setCustomBarAndShow(resource: Int)
  {
    _customActionBar.value = resource
    show()
  }
}