package org.cory7666.newsapp.ui

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
import org.cory7666.newsapp.databinding.FragmentWebViewBinding
import org.cory7666.newsapp.viewmodel.ActionBarViewModel

class WebViewFragment : Fragment()
{
  private val args: WebViewFragmentArgs by navArgs()
  lateinit var binding: FragmentWebViewBinding

  private lateinit var loadUrl: String

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    loadUrl = args.loadUrl
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
  ): View?
  {
    binding = FragmentWebViewBinding.inflate(inflater, container, false)
    binding.parentWebView.loadUrl(loadUrl)
    setupActionBar()
    return binding.root
  }

  private fun setupActionBar()
  {
    val actionBarViewModel =
      ViewModelProvider(requireActivity())[ActionBarViewModel::class.java]
    actionBarViewModel.setCustomBarAndShow(
      R.layout.webview_action_bar
    )

    (requireActivity() as AppCompatActivity).supportActionBar?.customView?.apply {
      findViewById<TextView>(R.id.titleTextView)?.text = "Web"
      findViewById<ImageButton>(R.id.buttonGoToHomeScreen)?.setOnClickListener {
        findNavController().navigate(R.id.action_webViewFragment_to_homeScreen)
      }
    }
  }
}