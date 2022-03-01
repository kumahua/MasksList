package com.example.maskmap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.maskmap.data.Feature
import com.example.maskmap.databinding.ItemViewBinding

//RecyclerView.Adapter<加入定義畫面的view，也就是MyViewHolder>()
class MainAdapter(mainActivity: MainActivity) :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    var pharmacyList :List<Feature> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    //用於決定資料型態。
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemViewBinding =
            ItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        //onCreateViewHolder要傳回給MyViewHolder
        return MyViewHolder(itemViewBinding)
    }

    //決定元件上要顯示的資料
    //position即{0,1,2,3,...}
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemViewBinding.tvName.text = pharmacyList[position].properties.name

        holder.itemViewBinding.tvAdultAmount.text = pharmacyList[position].properties.mask_adult.toString()
        holder.itemViewBinding.tvChildAmount.text = pharmacyList[position].properties.mask_child.toString()

//        holder.itemViewBinding.layoutItem.setOnClickListener {
//            itemClickListener.onItemClickListener(pharmacyList[position])
//        }
    }

    //決定資料大小
    override fun getItemCount(): Int {
        return pharmacyList.size
    }

    //讓view來自item_view
    //MyViewHolder必須繼承RecyclerView的ViewHolder
    class MyViewHolder(val itemViewBinding: ItemViewBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root)

    interface IItemClickListener {
        fun onItemClickListener(data: Feature)
    }
}