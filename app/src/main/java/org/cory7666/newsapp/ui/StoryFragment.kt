package org.cory7666.newsapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.cory7666.newsapp.R
import org.cory7666.newsapp.data.story.StoryInfo
import org.cory7666.newsapp.databinding.FragmentStoryBinding
import org.cory7666.newsapp.viewmodel.ActionBarViewModel

class StoryFragment : Fragment()
{
  private val args: StoryFragmentArgs by navArgs()
  private lateinit var binding: FragmentStoryBinding
  private lateinit var story: StoryInfo

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    story = args.story
  }

  @SuppressLint("SetTextI18n")
  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View
  {
    binding = FragmentStoryBinding.inflate(inflater, container, false)
    binding.descriptionTextView.text = story.description
    binding.imageViewPager.apply {
      layoutParams.height = story.height?.toInt() ?: 100
      adapter = StoryViewPagerAdapter(story.sources, context)
    }

    setupActionBar()
    return binding.root
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
        findNavController().popBackStack()
      }
    }
  }
}