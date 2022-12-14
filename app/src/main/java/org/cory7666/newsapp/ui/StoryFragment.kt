package org.cory7666.newsapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import org.cory7666.newsapp.R
import org.cory7666.newsapp.data.story.StoryInfo
import org.cory7666.newsapp.viewmodel.ActionBarViewModel

class StoryFragment : Fragment()
{
  private val args: StoryFragmentArgs by navArgs()
  private lateinit var story: StoryInfo

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    story = args.story
  }

  @SuppressLint("SetTextI18n")
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View?
  {
    val view = inflater.inflate(R.layout.fragment_story, container, false)
    view.findViewById<TextView>(R.id.descriptionTextView)?.text =
      "${story.description}"

    val imageView = view.findViewById<ImageView>(R.id.imageView)
    imageView.layoutParams.height = story.height?.toInt() ?: 100
    Picasso
      .get()
      .load(story.source)
      .error(R.drawable.ic_baseline_error)
      .placeholder(R.drawable.ic_baseline_downloading)
      .into(imageView)

    setupActionBar()
    return view
  }

  private fun setupActionBar()
  {
    val actionBarViewModel =
      ViewModelProvider(requireActivity())[ActionBarViewModel::class.java]
    actionBarViewModel.setCustomBarAndShow(
      R.layout.story_action_bar
    )

    (requireActivity() as AppCompatActivity).supportActionBar?.customView?.apply {
      findViewById<TextView>(R.id.titleTextView)?.text = story.title.toString()
      findViewById<ImageButton>(R.id.buttonGoToHomeScreen)?.setOnClickListener {
        findNavController().navigate(R.id.action_storyFragment_to_homeScreen)
      }
    }
  }
}