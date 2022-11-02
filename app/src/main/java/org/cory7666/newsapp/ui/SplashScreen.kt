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

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment()
{
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View?
  {
    ViewModelProvider(this)[ActionBarViewModel::class.java].hide()
    CoroutineScope(Dispatchers.Main).launch {
      delay(500)

      if (ViewModelProvider(
          this@SplashScreen.requireActivity(), MainActivityViewModelFactory()
        )[MainActivityViewModel::class.java].repository.isLoggedIn()
      )
      {
        findNavController().navigate(R.id.action_splashScreen_to_homeScreen2)
      }
      else
      {
        findNavController().navigate(
          R.id.action_splashScreen_to_identificationScreen2
        )
      }
    }
    return inflater.inflate(R.layout.fragment_splash_screen, container, false)
  }
}