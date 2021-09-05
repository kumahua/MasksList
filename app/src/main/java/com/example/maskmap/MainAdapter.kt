package com.example.maskmap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maskmap.data.Feature
import com.example.maskmap.databinding.ItemViewBinding

//RecyclerView.Adapter<加入定義畫面的view，也就是MyViewHolder>()
class MainAdapter : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    var pharmacylist :List<Feature> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    //讓view來自item_view
    //MyViewHolder必須繼承RecyclerView的ViewHolder
    class MyViewHolder(val itemViewBinding: ItemViewBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    //用於決定資料型態。
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemViewBinding =
            ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        //onCreateViewHolder要傳回給MyViewHolder
        return MyViewHolder(itemViewBinding)
    }

    //決定元件上要顯示的資料
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemViewBinding.tvName.text = ""
    }
    //決定資料大小
    override fun getItemCount(): Int {
        return pharmacylist.size
    }
}