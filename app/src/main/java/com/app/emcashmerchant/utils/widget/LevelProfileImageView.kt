package com.app.emcashmerchant.utils.widget

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.emcashmerchant.R
import com.app.emcashmerchant.utils.extensions.hide
import com.app.emcashmerchant.utils.extensions.loadImageWithUrl
import kotlinx.android.synthetic.main.layout_item_recent_payment.view.*
import java.util.*

class LevelProfileImageView(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    init {
        inflate(context, R.layout.layout_item_recent_payment, this)
    }

    enum class UserProfileLevel{
        RED,GREEN,YELLOW,NONE
    }

    fun setLevel(level : UserProfileLevel){
        when(level){
            UserProfileLevel.RED->{
                fl_user_level.setBackgroundResource(R.drawable.red_round)
            }
            UserProfileLevel.GREEN->{
                fl_user_level.setBackgroundResource(R.drawable.green_round)
            }
            UserProfileLevel.YELLOW->{
                fl_user_level.setBackgroundResource((R.drawable.yellow_round))
            }
            UserProfileLevel.NONE->{
                fl_user_level.hide()
            }
        }
    }

    fun setProfileImage(imageUrl:String?){
        if(!imageUrl.isNullOrEmpty()){
            iv_user_image.loadImageWithUrl(imageUrl,onError = {
                when(it){
                    //set bg to imageview
                }
            })
        }else{
            //set bg to imageview
        }
    }

    fun setProfileName(name:String){
        tv_user_name_.text =name
    }

    fun setFirstLetter(name:String){
        tv_user_name_letter.text = name.first().toString().toUpperCase(Locale.getDefault())
    }
}