/*
 *   Copyright 2022 Alex Filozop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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