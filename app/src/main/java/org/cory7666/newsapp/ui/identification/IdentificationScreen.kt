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

package org.cory7666.newsapp.ui.identification

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.cory7666.newsapp.R
import org.cory7666.newsapp.databinding.FragmentIdentificationScreenBinding
import org.cory7666.newsapp.viewmodel.*

class IdentificationScreen : Fragment()
{
  private lateinit var binding: FragmentIdentificationScreenBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View
  {
    binding =
      FragmentIdentificationScreenBinding.inflate(inflater, container, false)

    val mainActivityViewModel = ViewModelProvider(
      requireActivity(), MainActivityViewModelFactory()
    )[MainActivityViewModel::class.java]
    val authViewModel = ViewModelProvider(
      this, AuthenticationViewModelFactory(
        repository = mainActivityViewModel.repository, context = context
      )
    )[AuthenticationViewModel::class.java]

    activity?.run {
      ViewModelProvider(this)[ActionBarViewModel::class.java].setCustomBarAndShow(
        0
      )
    }

    with(binding.viewPager) {
      adapter = AuthenticationScreenViewAdapter(
        childFragmentManager,
        lifecycle,
        listOf(RegistrationScreen(), LoginScreen())
      )
      currentItem = 0
    }

    authViewModel.isUserLoggedIn.observe(this.viewLifecycleOwner) { value ->
      if (value)
      {
        Log.d("his", "onCreateView: value is changed.")
        findNavController().navigate(R.id.action_identificationScreen_to_splashScreen)
      }
    }
    authViewModel.toastMessage.observe(this.viewLifecycleOwner) { toastMessage ->
      if (toastMessage != null && toastMessage.isNotEmpty())
      {
        Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
      }
    }

    return binding.root
  }
}