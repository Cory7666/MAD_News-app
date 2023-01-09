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

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.cory7666.newsapp.R
import org.cory7666.newsapp.viewmodel.ActionBarViewModel
import org.cory7666.newsapp.viewmodel.MainActivityViewModel
import org.cory7666.newsapp.viewmodel.MainActivityViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment()
{
  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    ViewModelProvider(this)[ActionBarViewModel::class.java].apply {
      setCustomBarAndShow(0)
      hide()
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View?
  {
    CoroutineScope(Dispatchers.Main).launch {
      delay(500)

      if (ViewModelProvider(
          this@SplashScreen.requireActivity(), MainActivityViewModelFactory()
        )[MainActivityViewModel::class.java].repository.isLoggedIn()
      )
      {
        findNavController().navigate(R.id.action_splashScreen_to_homeScreen)
      }
      else
      {
        findNavController().navigate(
          R.id.action_splashScreen_to_identificationScreen
        )
      }
    }
    return inflater.inflate(R.layout.fragment_splash_screen, container, false)
  }
}