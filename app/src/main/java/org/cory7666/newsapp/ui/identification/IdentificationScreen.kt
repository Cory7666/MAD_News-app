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
import androidx.viewpager2.widget.ViewPager2
import org.cory7666.newsapp.R
import org.cory7666.newsapp.ui.MainActivityViewModel
import org.cory7666.newsapp.ui.MainActivityViewModelFactory
import org.cory7666.newsapp.viewmodel.ActionBarViewModel

class IdentificationScreen : Fragment()
{
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View?
  {
    val viewFragment = inflater.inflate(
      R.layout.fragment_identification_screen, container, false
    )
    val viewPager = viewFragment.findViewById<ViewPager2>(R.id.viewPager)

    val mainActivityViewModel = ViewModelProvider(
      requireActivity(), MainActivityViewModelFactory()
    )[MainActivityViewModel::class.java]
    val authViewModel = ViewModelProvider(
      this, AuthenticationViewModelFactory(
        repository = mainActivityViewModel.repository, context = context
      )
    )[AuthenticationViewModel::class.java]

    activity?.run {
      ViewModelProvider(this)[ActionBarViewModel::class.java].show()
    }

    viewPager.adapter = AuthenticationScreenViewAdapter(
      childFragmentManager,
      lifecycle,
      listOf(RegistrationScreen(), LoginScreen())
    )
    viewPager.currentItem = 0

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

    return viewFragment
  }
}