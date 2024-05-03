package com.example.news2024.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.news2024.R
import com.example.news2024.model.categoryModel
import com.google.android.material.imageview.ShapeableImageView

class CategoryAdapter(var categoryModel: List<categoryModel>) : RecyclerView.Adapter<CategoryAdapter.myViewHolderCategory>() {
  class myViewHolderCategory(itemView: View):RecyclerView.ViewHolder(itemView){
    var image = itemView.findViewById<ShapeableImageView>(R.id.imageCategory)
    fun bind (categoryModel: categoryModel){
      Glide.with(itemView).load(categoryModel.imageView).into(image)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolderCategory {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.content_category,parent,false)
    return myViewHolderCategory(itemView)
  }

  override fun getItemCount(): Int {
    return categoryModel.size
  }

  override fun onBindViewHolder(holder: myViewHolderCategory, position: Int) {
   val itemView = categoryModel[position]
    holder.bind(itemView)
  holder.itemView.setOnClickListener {
    onClickCategoryObject?.OnClickOnCategory(itemView,position)
  }

  }
  var onClickCategoryObject:setOnClickOnCategory? = null
  fun interface setOnClickOnCategory{
    fun OnClickOnCategory(categoryModel: categoryModel ,position: Int)
  }

}
