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
import org.cory7666.newsapp.databinding.FragmentRegistrationScreenBinding
import org.cory7666.newsapp.viewmodel.AuthenticationViewModel

class RegistrationScreen : Fragment()
{
  private lateinit var binding: FragmentRegistrationScreenBinding
  private lateinit var viewModel: AuthenticationViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View
  {
    viewModel = ViewModelProvider(
      requireParentFragment()
    )[AuthenticationViewModel::class.java]
    binding =
      FragmentRegistrationScreenBinding.inflate(inflater, container, false)

    binding.buttonNextScreen.setOnClickListener {
      binding.tilNickname.editText?.text = null
      binding.tilNickname.error = null
      binding.tilEmail.editText?.text = null
      binding.tilEmail.error = null
      binding.tilPassword.editText?.text = null
      binding.tilPassword.error = null
      activity?.findViewById<ViewPager2>(R.id.viewPager)?.apply {
        setCurrentItem(currentItem + 1, false)
      }
    }

    binding.tilNickname.editText?.setOnFocusChangeListener { _, _ ->
      binding.tilNickname.error = null
    }
    viewModel.nicknameHint.observe(this.viewLifecycleOwner) { value ->
      binding.tilEmail.error = value
    }


    binding.tilEmail.editText?.setOnFocusChangeListener { _, _ ->
      binding.tilEmail.error = null
    }
    viewModel.emailHint.observe(this.viewLifecycleOwner) { value ->
      binding.tilEmail.error = value
    }


    binding.tilPassword.editText?.setOnFocusChangeListener { _, _ ->
      binding.tilPassword.error = null
    }
    viewModel.passwordHint.observe(this.viewLifecycleOwner) { value ->
      binding.tilPassword.error = value
    }


    binding.buttonAction.setOnClickListener {
      viewModel.validateNickname(binding.tilNickname.editText?.text.toString())
      viewModel.validateEmail(binding.tilEmail.editText?.text.toString())
      viewModel.validatePassword(binding.tilPassword.editText?.text.toString())
      viewModel.tryRegister(
        nickname = binding.tilNickname.editText?.text.toString(),
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