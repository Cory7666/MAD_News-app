package org.cory7666.newsapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.cory7666.newsapp.R

class IdentificationScreen : Fragment()
{
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View?
  {
    return inflater.inflate(
      R.layout.fragment_identification_screen, container, false
    )
  }
}