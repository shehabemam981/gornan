package com.example.news2024.Source

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.marginStart
import com.example.news2024.R
import com.example.news2024.api.apiManager
import com.example.news2024.articles.articleFragment
import com.example.news2024.constant.constant
import com.example.news2024.databinding.FragmentNewsBinding
import com.example.news2024.model.Source
import com.example.news2024.model.SourcesItem
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {

   lateinit var binding: FragmentNewsBinding

    var articlesFragment = articleFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar(true)
        initApi()
       initArticlesFragment()
        binding.ErrorButton.setOnClickListener {
            initApi()
        }

    }

    private fun initArticlesFragment() {
        childFragmentManager.beginTransaction().replace(R.id.articleFragment,articlesFragment).commit()
    }

    private fun initApi() {
    apiManager.getSource().getSource(constant.apiKey).enqueue(object :Callback<Source>{
        override fun onResponse(call: Call<Source>, response: Response<Source>) {
           if(response.isSuccessful){
               progressBar(false)
               response.body()?.sources.let { sources->
                  sources?.forEach {sourceItem->
                   val tab = binding.tabSource.newTab()
                   tab.text =sourceItem?.name
                   binding.tabSource.addTab(tab)
                      tab.tag = sourceItem
               }

               binding.tabSource.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
                   override fun onTabSelected(tab: TabLayout.Tab?) {

                       val sourcesItem:SourcesItem = tab?.tag as SourcesItem
                      articlesFragment.changeSource(sourcesItem)
                   }

                   override fun onTabReselected(tab: TabLayout.Tab?) {
                      val sourcesItem:SourcesItem = tab?.tag as SourcesItem
                       articlesFragment.changeSource(sourcesItem)
                   }

                   override fun onTabUnselected(tab: TabLayout.Tab?) {

                   }

               })
                   binding.tabSource.getTabAt(0)!!.select()

               }
           }
        }



        override fun onFailure(call: Call<Source>, t: Throwable) {
            progressBar(false)
         showError(t.message)
        }


    })
    }
    private fun progressBar(isVisible:Boolean) {
        binding.brogressBar.isVisible=isVisible
    }
    private fun showError(message: String?) {
        binding.ErrorButton.isVisible=true
        binding.errorText.text=message
        binding.errorText.isVisible=true
    }

}