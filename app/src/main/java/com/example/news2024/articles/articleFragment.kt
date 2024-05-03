package com.example.news2024.articles

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.news2024.NewsHomeActivity
import com.example.news2024.R
import com.example.news2024.api.apiManager
import com.example.news2024.articles.viewModel.ArticleViewModel
import com.example.news2024.constant.constant
import com.example.news2024.databinding.FragmentArticleBinding
import com.example.news2024.model.Articles
import com.example.news2024.model.ArticlesItem
import com.example.news2024.model.SourcesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class articleFragment : Fragment() {
   lateinit var binding: FragmentArticleBinding
   var source : SourcesItem? = null
    lateinit var articleViewModel:ArticleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        articleViewModel = ViewModelProvider(this)[ArticleViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindLiveData()
        source?.let {
            changeSource(it)
            binding.ErrorButton.isVisible=false
            binding.errorText.isVisible=false
        }
       showSearch()

    }

    private fun bindLiveData() {
        articleViewModel.initRec.observe(viewLifecycleOwner,{
           initRec(it)
        })
        articleViewModel.showError.observe(viewLifecycleOwner,{
            showError(it)
        })

    }

    private fun showSearch() {
        (activity as NewsHomeActivity).search{ query->
            apiManager.getSource().getArticlesFromQuery(query,constant.apiKey).enqueue(object :Callback<Articles>{
                override fun onResponse(call: Call<Articles>, response: Response<Articles>) {
                    if (response.isSuccessful){
                        response.body().let {
                            initRec(response.body())

                        }
                    }
                }




                override fun onFailure(call: Call<Articles>, t: Throwable) {
                    showError(t.message)
                }

        })
        }
    }


    fun changeSource(sourcesItem: SourcesItem) {
            this.source = sourcesItem
         articleViewModel.initApi(source!!)
    }


     fun initRec(body: Articles?) {
        val adapter = ArticleAdapter(body!!.articles)
        binding.articleRv.adapter =adapter
        adapter.setOnClickDetails =ArticleAdapter.onClickDetails {
            val intent = Intent(this@articleFragment.context,itemArticleActivity::class.java)
            intent.putExtra(constant.itemKey,it)
            startActivity(intent)
//                        val argument = Bundle()
//                          argument.putParcelable(constant.itemKey,it)
//                        val articleDetailsitem = ItemArticleFragment()
//                        articleDetailsitem.arguments = argument
//                        parentFragmentManager.beginTransaction()
//                            .addToBackStack(null)
//                            .replace(R.id.itemFragment,articleDetailsitem).commit()
        }
    }

    private fun showError(message: String?) {
        binding.ErrorButton.isVisible=true

        binding.errorText.isVisible=true
        binding.errorText.text=message
    }

}