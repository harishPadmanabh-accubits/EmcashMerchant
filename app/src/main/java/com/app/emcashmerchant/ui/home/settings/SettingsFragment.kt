package com.app.emcashmerchant.ui.home.settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.transition.ChangeBounds
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.introScreen.IntroActivity
import com.app.emcashmerchant.utils.AppDialog
import com.app.emcashmerchant.utils.REQUEST_CODE_PICK_IMAGE_PROFILE
import com.app.emcashmerchant.utils.extensions.getFileName
import com.app.emcashmerchant.utils.extensions.openActivity
import com.app.emcashmerchant.utils.extensions.showShortToast
import kotlinx.android.synthetic.main.settings_fragment.*
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

        iv_back.setOnClickListener {
            Navigation.findNavController(view).popBackStack()
        }

        lay_logout.setOnClickListener {
            viewModel.performLogout()
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        sessionStorage = SessionStorage(requireActivity())
        dialog = AppDialog(requireActivity())
        viewModel.getProfileDetails()
        viewModel.getTermsConditions()
        tv_name.text = sessionStorage.merchantName

        observe()


    }

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
                        termsConditions=it.data.toString()
                        dialog.dismiss_dialog()

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