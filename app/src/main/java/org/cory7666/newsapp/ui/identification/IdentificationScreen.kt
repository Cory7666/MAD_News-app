package org.cory7666.newsapp.ui.identification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import org.cory7666.newsapp.R
import org.cory7666.newsapp.viewmodel.ActionBarViewModel

class IdentificationScreen : Fragment()
{
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View?
  {
    activity?.run {
      ViewModelProvider(this)[ActionBarViewModel::class.java].show()
    }

    return inflater.inflate(
      R.layout.fragment_identification_screen, container, false
    )?.apply {
      val viewPager = findViewById<ViewPager2>(R.id.viewPager)
      viewPager.adapter = AuthenticationScreenViewAdapter(
        childFragmentManager,
        lifecycle,
        listOf(RegistrationScreen(), LoginScreen())
      )
      viewPager.currentItem = 0
    }
  }
}