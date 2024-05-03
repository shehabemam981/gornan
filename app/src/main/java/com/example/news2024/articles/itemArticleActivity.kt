package com.example.news2024.articles

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.news2024.R
import com.example.news2024.constant.constant
import com.example.news2024.databinding.ActivityItemArticleBinding
import com.example.news2024.model.ArticlesItem
import com.google.android.material.imageview.ShapeableImageView

class itemArticleActivity : AppCompatActivity() {
    lateinit var binding: ActivityItemArticleBinding
    lateinit var article:ArticlesItem
    lateinit var image: ShapeableImageView
    lateinit var title: TextView
    lateinit var button: Button
    lateinit var description : TextView
    lateinit var urlSource: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
     getData()
        bindData()
     showArrowBack()
    }

    private fun showArrowBack() {
        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home){
            onBackPressed()
           return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun bindData() {
        image = binding.imageDetails
        title = binding.articletitle
        description = binding.articletext
        urlSource = binding.articleUrl
        title.text = article.title
        description.text = article.description
        Glide.with(this).load(article.urlToImage).into(image)
         urlSource.setOnClickListener {
             viewUrl(article.url)
         }

    }

    private fun viewUrl(url: String?) {
     val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }

    private fun getData(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            article = intent.getParcelableExtra(constant.itemKey, ArticlesItem::class.java)!!
        }else{
            article = intent.getParcelableExtra<ArticlesItem>(constant.itemKey)!!
        }
    }


}