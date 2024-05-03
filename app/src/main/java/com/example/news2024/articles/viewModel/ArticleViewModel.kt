package com.example.news2024.articles.viewModel

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news2024.api.apiManager
import com.example.news2024.articles.ArticleAdapter
import com.example.news2024.articles.itemArticleActivity
import com.example.news2024.constant.constant
import com.example.news2024.model.Articles
import com.example.news2024.model.SourcesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleViewModel: ViewModel() {
    var initRec = MutableLiveData<Articles>()
    var showError = MutableLiveData<String>()
     fun initApi(sourceItem:SourcesItem) {
        apiManager.getSource().getArticles(constant.apiKey, sourceItem.id.toString()).enqueue(
            object : Callback<Articles> {
                override fun onResponse(call: Call<Articles>, response: Response<Articles>) {
                    if (response.isSuccessful){
                        response.body().let {
                            initRec.value = response.body()
                        }
                    }
                }



                override fun onFailure(call: Call<Articles>, t: Throwable) {
                    showError.value = t.message
                }


            }
        )
    }

}