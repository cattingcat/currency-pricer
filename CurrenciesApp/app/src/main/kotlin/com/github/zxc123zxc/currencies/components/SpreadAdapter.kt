package com.github.zxc123zxc.currencies.components

import android.content.Context
import android.view.*
import android.widget.ArrayAdapter

class SpreadAdapter(context: Context): ArrayAdapter<SpreadViewModel>(context, 0) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val model = this.getItem(position)

        if(convertView == null || convertView !is SpreadView) {
            return SpreadView(context, model)
        }

        convertView.viewModel = model

        return convertView
    }
}