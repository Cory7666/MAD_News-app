package org.cory7666.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.cory7666.newsapp.R
import org.cory7666.newsapp.databinding.FragmentHomeScreenBinding

class HomeScreen : Fragment()
{
  private lateinit var binding: FragmentHomeScreenBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View
  {
    binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
    binding.button.setOnClickListener {
      Firebase.auth.signOut()
      findNavController().navigate(R.id.action_homeScreen_to_splashScreen)
    }
    return binding.root
  }
}