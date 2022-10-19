package org.cory7666.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import org.cory7666.newsapp.R
import org.cory7666.newsapp.viewmodel.ActionBarViewModel

class MainActivity : AppCompatActivity()
{
  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val actionBarViewModel =
      ViewModelProvider(this)[ActionBarViewModel::class.java]
    actionBarViewModel.hidden.observe(this) {
      if (it)
      {
        supportActionBar?.hide()
      }
      else
      {
        supportActionBar?.show()
      }
    }
  }
}