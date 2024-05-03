package com.example.news2024.articles

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.news2024.constant.constant
import com.example.news2024.databinding.FragmentItemArticleBinding
import com.example.news2024.model.ArticlesItem
import com.google.android.material.imageview.ShapeableImageView


class ItemArticleFragment : Fragment() {
    lateinit var  binding : FragmentItemArticleBinding
    lateinit var image:ShapeableImageView
    lateinit var title:TextView
    lateinit var description :TextView
    lateinit var urlSource:Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      binding = FragmentItemArticleBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments.let {
          bindArticles(it?.let { it1 -> getData(it1) })
        }

    }

    private fun bindArticles(data: ArticlesItem?) {
    image = binding.imageDetails
        title = binding.articletitle
        description = binding.articletext
        urlSource = binding.articleUrl
        Glide.with(this@ItemArticleFragment).load(data?.urlToImage).into(image)
        title.text = data?.title
        description.text = data?.description
        urlSource.setOnClickListener {
            goToWebPage(data?.url)
        }

    }

    private fun getData(argument:Bundle): ArticlesItem? {
     return   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            argument.getParcelable(constant.itemKey,ArticlesItem::class.java)
        }else{
            argument.getParcelable(constant.itemKey)
        }
    }
    fun goToWebPage(yourUrl: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(yourUrl))
        startActivity(intent)
    }


}