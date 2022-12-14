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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.cory7666.newsapp.R
import org.cory7666.newsapp.databinding.FragmentSettingsScreenBinding
import org.cory7666.newsapp.viewmodel.ActionBarViewModel
import org.cory7666.newsapp.viewmodel.MainActivityViewModel

class SettingsScreen : Fragment()
{
  private lateinit var binding: FragmentSettingsScreenBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View
  {
    binding = FragmentSettingsScreenBinding.inflate(inflater, container, false)
    binding.buttonSignOutAction.setOnClickListener {
      signout()
    }
    setupActionBar()
    return binding.root
  }

  private fun signout()
  {
    val activityViewModel =
      ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
    activityViewModel.repository.logout()
    ViewModelProvider(requireActivity())[ActionBarViewModel::class.java].hide()
    findNavController().navigate(R.id.action_settingsScreen_to_splashScreen)
  }

  private fun setupActionBar()
  {
    val actionBarViewModel =
      ViewModelProvider(requireActivity())[ActionBarViewModel::class.java]
    actionBarViewModel.setCustomBarAndShow(
      R.layout.settings_screen_action_bar
    )

    (requireActivity() as AppCompatActivity).supportActionBar?.customView
      ?.findViewById<ImageButton>(R.id.buttonGoToHomeScreen)
      ?.setOnClickListener {
        findNavController().popBackStack()
      }
  }
}