package org.cory7666.newsapp.ui.identification

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class AuthenticationScreenViewAdapter(
  manager: FragmentManager,
  lifecycle: Lifecycle,
  private val fragments: List<Fragment>
) : FragmentStateAdapter(manager, lifecycle)
{
  override fun getItemCount(): Int
  {
    return fragments.size
  }

  override fun createFragment(position: Int): Fragment
  {
    return fragments[position]
  }
}