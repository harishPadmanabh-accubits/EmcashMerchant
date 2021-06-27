package com.app.emcashmerchant.ui.convert_to_cash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.app.emcashmerchant.R

class CashConvertedDialog : DialogFragment() {

    private  val KEY_TITLE = "KEY_TITLE"
    private  val KEY_SUBTITLE = "KEY_SUBTITLE"

    fun newInstance(title: String, subTitle: String): CashConvertedDialog {
        val args = Bundle()
        args.putString(KEY_TITLE, title)
        args.putString(KEY_SUBTITLE, subTitle)
        val fragment = CashConvertedDialog()
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.alert_converted_to_cash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupView(view: View) {
//        view.tvTitle.text = arguments?.getString(KEY_TITLE)
//        view.tvSubTitle.text = arguments?.getString(KEY_SUBTITLE)
    }

    private fun setupClickListeners(view: View) {
//        view.btnPositive.setOnClickListener {
//            // TODO: Do some task here
//            dismiss()
//        }
//        view.btnNegative.setOnClickListener {
//            // TODO: Do some task here
//            dismiss()
//        }
    }

}



//https://blog.mindorks.com/implementing-dialog-fragment-in-android