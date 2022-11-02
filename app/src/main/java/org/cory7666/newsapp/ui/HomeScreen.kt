package org.cory7666.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.cory7666.newsapp.R

class HomeScreen : Fragment()
{
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View?
  {
    return inflater
      .inflate(R.layout.fragment_home_screen, container, false)
      ?.also {
        it.findViewById<Button>(R.id.button).setOnClickListener {
          Firebase.auth.signOut()
        }
      }
  }
}