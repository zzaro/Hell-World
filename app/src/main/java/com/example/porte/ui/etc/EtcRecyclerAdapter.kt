package com.example.porte.ui.etc

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.porte.R
import kotlinx.android.synthetic.main.etc_list_item_banner.view.*
import kotlinx.android.synthetic.main.etc_list_item_shop.view.*
import java.lang.IllegalArgumentException

class EtcRecyclerAdapter(val parentContext: Context) : RecyclerView.Adapter<EtcRecyclerAdapter.ItemViewHolder>() {

    override fun getItemCount() = EtcData.values().size

    override fun getItemViewType(position: Int): Int {
        return EtcData.values()[position].etcType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EtcRecyclerAdapter.ItemViewHolder {
        val view = when(viewType){
            BANNER -> LayoutInflater.from(parent.context).inflate(R.layout.etc_list_item_banner, parent, false)
            ITEM -> LayoutInflater.from(parent.context).inflate(R.layout.etc_list_item_shop, parent, false)
            else-> throw IllegalArgumentException(Error("매칭되는 뷰타입이 없습니다."))
        }
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        when(holder.itemViewType){
            BANNER->{
                holder.bindBanner(EtcData.values()[position].etcContent as EtcBanner)
            }
            ITEM->{
                holder.bindItem(EtcData.values()[position].etcContent as EtcItem)
                holder.itemView.img_item_link.setOnClickListener {
                    val selectedEtcItem = EtcData.values()[position].etcContent as EtcItem
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(selectedEtcItem.link))
                    parentContext.startActivity(intent)
                }
            }
        }
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindBanner(etcBanner: EtcBanner){
            itemView.img_item_banner.setImageResource(etcBanner.imageId)
        }
        fun bindItem(etcItem: EtcItem){
            itemView.img_etc_item.setImageResource(etcItem.imageId)
            itemView.txt_etc_item_subject.text = etcItem.itemName
            itemView.txt_shop_etc_info.text = etcItem.itemInfo
        }
    }

    companion object{
        val BANNER =0
        val ITEM =1
    }
}