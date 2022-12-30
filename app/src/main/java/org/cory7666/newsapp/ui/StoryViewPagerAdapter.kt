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