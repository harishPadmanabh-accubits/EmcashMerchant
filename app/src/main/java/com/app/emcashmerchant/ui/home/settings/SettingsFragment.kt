package com.app.emcashmerchant.ui.home.settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.introScreen.IntroActivity
import com.app.emcashmerchant.ui.transfer_payment.TransferPaymentViewModel
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.REQUEST_CODE_PICK_IMAGE_PROFILE
import com.app.emcashmerchant.utils.extensions.*
import kotlinx.android.synthetic.main.fragment_transfer_contact_list.*
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.android.synthetic.main.settings_fragment.iv_back
import kotlinx.android.synthetic.main.signoutlay.view.*
import timber.log.Timber
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() =
            SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var sessionStorage: SessionStorage
    lateinit var dialog: AppDialog
    var termsConditions:String?=null
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 750
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 500
        }


        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())
        dialog = AppDialog(requireActivity())
        viewModel.getProfileDetails()
        viewModel.getTermsConditions()
        tv_name.text = sessionStorage.merchantName

        observe()

        iv_back.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }
        lay_notifications.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.goto_notifications_fragment)
        }


        lay_logout.setOnClickListener {
            if(checkNetwork(requireContext())){
                logOut()

            }else
            {
                sessionStorage.logoutUser()
            }
        }
        tv_updateprofileimage.setOnClickListener {
            selectProfileIMage()
        }
        lay_terms_conditions.setOnClickListener {
            if(termsConditions?.isNotEmpty()!!){
                val  bundle= bundleOf("terms_conditions" to termsConditions )
                Navigation.findNavController(view).navigate(R.id.goto_terms_fragment,bundle)
            }


        }

        val callback = object : OnBackPressedCallback(true ) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

    private fun logOut() {
        fl_logOut.visibility=View.VISIBLE

        fl_logOut.btn_logOut.setOnClickListener {
            viewModel.performLogout()

        }
        fl_logOut.iv_close.setOnClickListener {
            fl_logOut.visibility=View.GONE

        }
    }


//
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
//
//    }

    fun observe() {
        viewModel.apply {
            initialLogOutResponseStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        //show loading
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        sessionStorage.logoutUser()
                        activity?.openActivity(IntroActivity::class.java)
                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        activity?.showShortToast(it.errorMessage)
                    }
                }
            })
            profileDetails.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        sessionStorage.merchantName = it.data?.name
                        tv_name.text = sessionStorage.merchantName
                        iv_shop_profile_image.loadImageWithUrlUser(it.data?.profileImage)


                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        activity?.showShortToast(it.errorMessage)

                    }
                }
            })
            termsConditionsResponse.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        termsConditions=it.data?.data.toString()

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        activity?.showShortToast(it.errorMessage)

                    }
                }
            })
            updateStatus.observe(requireActivity(), Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        activity?.showShortToast(getString(R.string.profile_pic_updated))
                        viewModel.getProfileDetails()

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        activity?.showShortToast(it.errorMessage)

                    }
                }
            })
        }

    }

    private fun fetchFile(uri: Uri?): File {
        val parcelFileDescriptor = activity?.contentResolver?.openFileDescriptor(uri!!, "r", null)
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val file = File(activity?.cacheDir, activity?.contentResolver?.getFileName(uri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE_PROFILE -> {
                    selectedImageUri = data?.data
                    viewModel.performUpdateProfile(fetchFile(selectedImageUri))
                }

            }

        }
    }

    private fun selectProfileIMage() {
        Intent(Intent.ACTION_PICK).also {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                REQUEST_CODE_PICK_IMAGE_PROFILE
            )
        }
    }



}