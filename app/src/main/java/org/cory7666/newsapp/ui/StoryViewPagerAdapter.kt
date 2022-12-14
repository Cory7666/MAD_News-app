package org.cory7666.newsapp.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import org.cory7666.newsapp.R

class StoryViewPagerAdapter(
  private val images: List<String>, private val context: Context?
) : PagerAdapter()
{
  override fun getCount(): Int
  {
    return images.size
  }

  override fun isViewFromObject(view: View, `object`: Any): Boolean
  {
    return view == (`object` as ImageView)
  }

  override fun instantiateItem(container: ViewGroup, position: Int): Any
  {
    val view = ImageView(context)
    Picasso
      .get()
      .load(images[position])
      .error(R.drawable.ic_baseline_error)
      .placeholder(R.drawable.ic_baseline_downloading)
      .into(view)
    container.addView(view)
    return view
  }

  override fun destroyItem(container: ViewGroup, position: Int, `object`: Any)
  {
    container.removeView(`object` as ImageView)
  }
}