package com.eminesa.neartome.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eminesa.neartome.databinding.LayoutItemPharmacyBinding
import dailyofspace.eminesa.dailyofspace.network.Pharmacy

class PharmacyAdapter(
    val itemClickListener: ((view: View, item: Pharmacy) -> Unit),
) : ListAdapter<Pharmacy, PharmacyAdapter.PharmacyAdapterViewHolder>(PharmacyAdapterDiffUtil) {

    inner class PharmacyAdapterViewHolder(var itemBinding: LayoutItemPharmacyBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(userItem: Pharmacy) {
            initButtonText(userItem)
        }
    }

    private fun PharmacyAdapterViewHolder.initButtonText(userItem: Pharmacy) {
        itemBinding.apply {
            txtPharmacyName.text = userItem.EczaneAdi
            txtPharmacyAddress.text = userItem.Sehir?.plus("/" ).plus(userItem.ilce)
            txtPharmacyAddressExplain.text = userItem.Adresi
            cardViewPharmacy.setOnClickListener {
                itemClickListener(it, userItem)
            }
        }

    }

    companion object PharmacyAdapterDiffUtil : DiffUtil.ItemCallback<Pharmacy>() {
        override fun areItemsTheSame(oldItem: Pharmacy, newItem: Pharmacy): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Pharmacy, newItem: Pharmacy): Boolean {
            return oldItem.EczaneAdi == newItem.EczaneAdi
        }
    }

    override fun onBindViewHolder(holder: PharmacyAdapterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PharmacyAdapterViewHolder {
        return PharmacyAdapterViewHolder(
            LayoutItemPharmacyBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }
}