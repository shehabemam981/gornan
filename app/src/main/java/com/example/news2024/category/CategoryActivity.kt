package com.example.news2024.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.news2024.NewsHomeActivity
import com.example.news2024.R
import com.example.news2024.api.apiManager
import com.example.news2024.databinding.ActivityCategoryBinding
import com.example.news2024.model.Articles
import com.example.news2024.model.categoryModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class CategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryBinding
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    val categories : List<categoryModel> = listOf(
        categoryModel(1,"Sports", R.drawable.sports),
        categoryModel(2,"business",R.drawable.bussines),
        categoryModel(3,"environment",R.drawable.environment),
        categoryModel(4,"health",R.drawable.health),
        categoryModel(5,"science",R.drawable.science),
    )



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initDrawer()
        initRec()
    }

    private fun initRec() {
        val adapter = CategoryAdapter(categories)
        binding.recCategory.adapter = adapter
        adapter.onClickCategoryObject = CategoryAdapter.setOnClickOnCategory { categoryModel, position ->
            Toast.makeText(this@CategoryActivity, categoryModel.textCategory, Toast.LENGTH_SHORT).show()
            categoryModel.textCategory?.let { Log.e("dd", it) }
            apiManager.getSource().getArticlesFromCategory(categoryModel.textCategory?:"").enqueue(
                object :Callback<Articles>{
                    override fun onResponse(call: Call<Articles>, response: Response<Articles>) {

                    }

                    override fun onFailure(call: Call<Articles>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                }
            )
        }
    }

    private fun initDrawer() {
        drawerLayout = binding.drawerLayout
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.AllArticles -> {
                    val intent = Intent(this, NewsHomeActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.categoryId -> {
                    val intent = Intent(this, CategoryActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }


   
}