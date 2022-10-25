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

class LoginScreen : Fragment()
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
      this, AuthenticationViewModelFactory(
        repository = activityViewModel.repository, context = context
      )
    )[AuthenticationViewModel::class.java]

    val view =
      inflater.inflate(R.layout.fragment_login_screen, container, false)

    if (view != null)
    {
      view.findViewById<Button>(R.id.buttonPrevScreen).setOnClickListener {
        activity?.findViewById<ViewPager2>(R.id.viewPager)?.apply {
          currentItem -= 1
        }
      }

      view.findViewById<TextInputLayout>(R.id.tilEmail)?.let {
        it.editText?.setOnFocusChangeListener { view, focused ->
          if (!focused && view is TextInputEditText)
          {
            viewModel.validateEmail(view.text.toString())
          }
        }
        viewModel.emailHint.observe(this.viewLifecycleOwner) { value ->
          it.error = value
        }
      }

      view.findViewById<TextInputLayout>(R.id.tilPassword)?.let {
        it.editText?.setOnFocusChangeListener { view, focused ->
          if (!focused && view is TextInputEditText)
          {
            viewModel.validatePassword(view.text.toString())
          }
        }
        viewModel.passwordHint.observe(this.viewLifecycleOwner) { value ->
          it.error = value
        }
      }
    }

    return view
  }
}