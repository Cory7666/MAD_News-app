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

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import org.cory7666.newsapp.R
import org.cory7666.newsapp.databinding.FragmentLoginScreenBinding
import org.cory7666.newsapp.viewmodel.AuthenticationViewModel

class LoginScreen : Fragment()
{
  private lateinit var binding: FragmentLoginScreenBinding
  private lateinit var viewModel: AuthenticationViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View
  {
    viewModel = ViewModelProvider(
      requireParentFragment()
    )[AuthenticationViewModel::class.java]
    binding = FragmentLoginScreenBinding.inflate(inflater, container, false)

    binding.buttonPrevScreen.setOnClickListener {
      binding.tilEmail.editText?.text = null
      binding.tilEmail.error = null
      binding.tilPassword.editText?.text = null
      binding.tilPassword.error = null
      activity?.findViewById<ViewPager2>(R.id.viewPager)?.apply {
        setCurrentItem(currentItem - 1, false)
      }
    }



    binding.tilEmail.editText?.setOnFocusChangeListener { _, _ ->
      binding.tilEmail.error = null
    }
    binding.tilPassword.editText?.setOnFocusChangeListener { _, _ ->
      binding.tilPassword.error = null
    }

    viewModel.emailHint.observe(this.viewLifecycleOwner) { value ->
      binding.tilEmail.error = value
    }
    viewModel.passwordHint.observe(this.viewLifecycleOwner) { value ->
      binding.tilPassword.error = value
    }



    binding.buttonAction.setOnClickListener {
      viewModel.validateEmail(binding.tilEmail.editText?.text.toString())
      viewModel.validatePassword(binding.tilPassword.editText?.text.toString())
      viewModel.tryLogin(
        email = binding.tilEmail.editText?.text.toString(),
        password = binding.tilPassword.editText?.text.toString()
      )
    }

    bindOnBackgroundClickAction(binding.root)
    return binding.root
  }

  private fun hideKeyboard()
  {
    (requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
      requireView().windowToken, 0
    )
  }

  private fun bindOnBackgroundClickAction(view: View)
  {
    view.setOnClickListener {
      hideKeyboard()
    }
  }
}