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

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.ImageView
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
  ): View
  {
    binding = FragmentWebViewBinding.inflate(inflater, container, false)
    binding.parentWebView.loadUrl(loadUrl)
    binding.parentWebView.webViewClient = object : WebViewClient()
    {
      override fun shouldOverrideUrlLoading(
        view: WebView?, request: WebResourceRequest?
      ): Boolean
      {
        return false
      }

      override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?)
      {
        super.onPageStarted(view, url, favicon)
        binding.loadingProgressImageView.visibility = ImageView.VISIBLE
        binding.loadingProgressImageView.translationX =
          binding.parentWebView.width / 2 - binding.loadingProgressImageView.width / 2 + 0f
        binding.loadingProgressImageView.translationY =
          binding.parentWebView.height / 2 - binding.loadingProgressImageView.height / 2 + 0f
      }

      override fun onPageFinished(view: WebView?, url: String?)
      {
        super.onPageFinished(view, url)
        binding.loadingProgressImageView.visibility = ImageView.INVISIBLE
      }
    }
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
        findNavController().popBackStack()
      }
    }
  }
}