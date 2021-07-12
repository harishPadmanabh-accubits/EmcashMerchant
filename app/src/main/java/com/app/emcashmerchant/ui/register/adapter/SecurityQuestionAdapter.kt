package com.app.emcashmerchant.ui.register.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.models.SecurityQuestionsResponse

class SecurityQuestionAdapter (private val context: Context, private val arrayList: ArrayList<SecurityQuestionsResponse.Data>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var tvSecurityQues: TextView
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.layout_item_security_question, parent, false)
        tvSecurityQues=convertView.findViewById(R.id.tv_security_question)
        tvSecurityQues.text= arrayList[position].question
        return  convertView
    }

    override fun getItem(position: Int): Any {
     return  position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }

    override fun getCount(): Int {
        return arrayList.size

    }


}