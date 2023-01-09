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

package org.cory7666.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.cory7666.newsapp.R
import org.cory7666.newsapp.viewmodel.ActionBarViewModel
import org.cory7666.newsapp.viewmodel.MainActivityViewModelFactory

class MainActivity : AppCompatActivity()
{
  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val actionBarViewModel =
      ViewModelProvider(this)[ActionBarViewModel::class.java]
    ViewModelProvider(this, MainActivityViewModelFactory())
    actionBarViewModel.hidden.observe(this) {
      if (it)
      {
        supportActionBar?.hide()
      }
      else
      {
        supportActionBar?.show()
      }
    }

    actionBarViewModel.customActionBar.observe(this) {
      if (it == 0)
      {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_TITLE
      }
      else
      {
        supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar?.setCustomView(it)
      }
    }
  }
}