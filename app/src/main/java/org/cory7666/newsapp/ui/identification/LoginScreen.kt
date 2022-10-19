package org.cory7666.newsapp.ui.identification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import org.cory7666.newsapp.R

class LoginScreen : Fragment()
{
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View?
  {

    return inflater
      .inflate(R.layout.fragment_login_screen, container, false)
      ?.apply {
        findViewById<Button>(R.id.buttonPrevScreen).setOnClickListener {
          activity?.findViewById<ViewPager2>(R.id.viewPager)?.apply {
            currentItem -= 1
          }
        }
      }
  }
}