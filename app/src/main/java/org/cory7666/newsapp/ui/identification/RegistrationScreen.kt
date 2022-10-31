package org.cory7666.newsapp.ui.identification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.cory7666.newsapp.R
import org.cory7666.newsapp.ui.MainActivityViewModel
import org.cory7666.newsapp.ui.MainActivityViewModelFactory

class RegistrationScreen : Fragment()
{
  private lateinit var viewModel: AuthenticationViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View?
  {
    val activityViewModel = ViewModelProvider(
      requireActivity(), MainActivityViewModelFactory()
    )[MainActivityViewModel::class.java]

    viewModel = ViewModelProvider(
      this,
      AuthenticationViewModelFactory(activityViewModel.repository, context)
    )[AuthenticationViewModel::class.java]

    val view = inflater.inflate(
      R.layout.fragment_registration_screen, container, false
    )

    if (view != null)
    {
      val nicknameField = view.findViewById<TextInputLayout>(R.id.tilNickname)
      val emailField = view.findViewById<TextInputLayout>(R.id.tilEmail)
      val passwordField = view.findViewById<TextInputLayout>(R.id.tilPassword)
      val actionButton = view.findViewById<Button>(R.id.buttonAction)

      view.findViewById<Button>(R.id.buttonNextScreen)?.setOnClickListener {
        activity?.findViewById<ViewPager2>(R.id.viewPager)?.apply {
          currentItem += 1
        }
      }

      nicknameField.editText?.setOnFocusChangeListener { thisView, focused ->
        if (!focused && thisView is TextInputEditText)
        {
          viewModel.validateNickname(thisView.text.toString())
        }
      }
      viewModel.nicknameHint.observe(this.viewLifecycleOwner) { value ->
        nicknameField.error = value
      }


      emailField.editText?.setOnFocusChangeListener { thisView, focused ->
        if (!focused && thisView is TextInputEditText)
        {
          viewModel.validateEmail(thisView.text.toString())
        }
      }
      viewModel.emailHint.observe(this.viewLifecycleOwner) { value ->
        emailField.error = value
      }


      passwordField.editText?.setOnFocusChangeListener { thisView, focused ->
        if (!focused && thisView is TextInputEditText)
        {
          viewModel.validatePassword(thisView.text.toString())
        }
      }
      viewModel.passwordHint.observe(this.viewLifecycleOwner) { value ->
        passwordField.error = value
      }
    }

    return view
  }
}