package org.cory7666.newsapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.cory7666.newsapp.R

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment()
{
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View?
  {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.fragment_splash_screen, container, false)
  }
}