package com.example.news2024

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import com.example.news2024.Source.NewsFragment
import com.example.news2024.databinding.ActivityHomeNewsBinding

class NewsHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityHomeNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityHomeNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showButton()
        callSearch()
        initFragment()

        closeSearch()
        displayArrow()
    }

    private fun displayArrow() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun closeSearch() {
        binding.searchCloseBtn.setOnClickListener {
            binding.searchInput.isVisible =false
            binding.searchButton.isVisible = true
            binding.searchCloseBtn.isVisible =false
        }
    }




    private fun callSearch() {
        binding.searchButton.setOnClickListener {
            showSearch(true)
        }
        binding.searchEdit.setOnEditorActionListener { v, _, _ ->
            onClicksearch?.onClickSearch(v.text.toString())
            true
        }
    }
   fun showButton(){
       binding.searchButton.isVisible=true

   }
    private fun showSearch(show: Boolean) {
     binding.searchInput.isVisible = show
        binding.searchButton.isVisible =! show

        binding.searchCloseBtn.isVisible =show
    }

    private fun initFragment() {
      supportFragmentManager.beginTransaction().replace(
          R.id.MainFragment,NewsFragment()
      ).commit()
    }
    var onClicksearch:setOnClickSearch? = null
    fun search(listener:setOnClickSearch){
        onClicksearch =listener
    }
   fun interface setOnClickSearch{
      fun  onClickSearch(query:String)
    }

}