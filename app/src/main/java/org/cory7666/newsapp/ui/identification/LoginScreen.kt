package org.cory7666.newsapp.ui.identification

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.textfield.TextInputLayout
import org.cory7666.newsapp.R
import org.cory7666.newsapp.viewmodel.AuthenticationViewModel

class LoginScreen : Fragment()
{
  private lateinit var viewModel: AuthenticationViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View?
  {
    viewModel = ViewModelProvider(
      requireParentFragment()
    )[AuthenticationViewModel::class.java]

    val view =
      inflater.inflate(R.layout.fragment_login_screen, container, false)

    if (view != null)
    {
      val emailField = view.findViewById<TextInputLayout>(R.id.tilEmail)
      val passwordField = view.findViewById<TextInputLayout>(R.id.tilPassword)
      val actionButton = view.findViewById<Button>(R.id.buttonAction)

      view.findViewById<Button>(R.id.buttonPrevScreen)?.setOnClickListener {
        emailField.editText?.text = null
        passwordField.editText?.text = null
        activity?.findViewById<ViewPager2>(R.id.viewPager)?.apply {
          setCurrentItem(currentItem - 1, false)
        }
      }



      emailField.editText?.setOnFocusChangeListener { _, _ ->
        emailField.error = null
      }
      passwordField.editText?.setOnFocusChangeListener { _, _ ->
        passwordField.error = null
      }

      viewModel.emailHint.observe(this.viewLifecycleOwner) { value ->
        emailField.error = value
      }
      viewModel.passwordHint.observe(this.viewLifecycleOwner) { value ->
        passwordField.error = value
      }



      actionButton.setOnClickListener {
        viewModel.validateEmail(emailField.editText?.text.toString())
        viewModel.validatePassword(passwordField.editText?.text.toString())
        viewModel.tryLogin(
          email = emailField?.editText?.text.toString(),
          password = passwordField.editText?.text.toString()
        )
      }

      bindOnBackgroundClickAction(view)
    }

    return view
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