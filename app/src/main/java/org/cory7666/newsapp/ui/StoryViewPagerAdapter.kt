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

package org.cory7666.newsapp.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import org.cory7666.newsapp.R
import kotlin.streams.toList

class StoryViewPagerAdapter(
  images: List<String>, private val context: Context?
) : PagerAdapter()
{
  private val imageViews: List<ImageView> = images.stream().map { image ->
    val iv = ImageView(context)
    Picasso
      .get()
      .load(image)
      .error(R.drawable.ic_baseline_error)
      .placeholder(R.drawable.downloading_animation)
      .into(iv)
    return@map iv
  }.toList()

  override fun getCount(): Int
  {
    return imageViews.size
  }

  override fun isViewFromObject(view: View, `object`: Any): Boolean
  {
    return view == (`object` as ImageView)
  }

  override fun instantiateItem(container: ViewGroup, position: Int): Any
  {
    container.addView(imageViews[position])
    return imageViews[position]
  }

  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any)
  {
    container.removeView(`object` as ImageView)
  }
}