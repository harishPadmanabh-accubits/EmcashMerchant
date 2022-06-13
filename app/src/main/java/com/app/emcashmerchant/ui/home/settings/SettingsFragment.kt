package com.app.emcashmerchant.ui.home.settings

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.transition.ChangeBounds
import com.app.emcashmerchant.R
import com.app.emcashmerchant.data.SessionStorage
import com.app.emcashmerchant.data.network.ApiCallStatus
import com.app.emcashmerchant.ui.introScreen.IntroActivity
import com.app.emcashmerchant.utils.*
import com.app.emcashmerchant.utils.extensions.*
import kotlinx.android.synthetic.main.settings_fragment.*
import kotlinx.android.synthetic.main.settings_fragment.iv_back
import kotlinx.android.synthetic.main.signoutlay.view.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class SettingsFragment : Fragment(R.layout.settings_fragment) {

    companion object {
        fun newInstance() =
            SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var sessionStorage: SessionStorage
    lateinit var dialog: AppDialog
    private var termsConditions: String? = null
    private var selectedImageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = ChangeBounds().apply {
            duration = 750
        }
        sharedElementReturnTransition = ChangeBounds().apply {
            duration = 500
        }

        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().navigate(R.id.homeFragment)


        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SettingsViewModel::class.java)
        sessionStorage = SessionStorage(requireContext())
        dialog = AppDialog(requireContext())

        tv_name.text = sessionStorage.merchantName

        /**calling viewmodels**/
        viewModels()

        /**observing livedata**/
        observe()

        iv_back.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }

        lay_notifications.setOnClickListener {
            val bundle = bundleOf(
                KEY_PAGE to SCREEN_PROFILE

            )
            Navigation.findNavController(view).navigate(R.id.goto_notifications_fragment, bundle)
        }

        cv_editBankDetails.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_addBankDetailsFragment)


        }

        lay_logout.setOnClickListener {
            if (checkNetwork(requireContext())) {

                /**method for api calling logout**/
                logOut()

            } else {
                sessionStorage.logoutUser()
            }
        }

        tv_updateprofileimage.setOnClickListener {
            selectProfileImage()
        }

        lay_terms_conditions.setOnClickListener {
            if (termsConditions?.isNotEmpty()!!) {
                val bundle = bundleOf("terms_conditions" to termsConditions)
                Navigation.findNavController(view).navigate(R.id.goto_terms_fragment, bundle)
            }

        }

        lay_editBank.setOnClickListener {

            findNavController().navigate(R.id.action_settingsFragment_to_editBankDetailsFragment)


        }


    }


    private fun logOut() {
        fl_logOut.visibility = View.VISIBLE

        fl_logOut.btn_logOut.setOnClickListener {
            viewModel.performLogout()

        }
        fl_logOut.iv_close.setOnClickListener {
            fl_logOut.visibility = View.GONE

        }
    }

    fun viewModels() {
        viewModel.getProfileDetails()
        viewModel.getTermsConditions()
    }

    fun observe() {
        viewModel.apply {
            initialLogOutResponseStatus.observe(viewLifecycleOwner, Observer {
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
            profileDetails.observe(viewLifecycleOwner, Observer { response ->
                when (response.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()

                        response.data?.let {
                            sessionStorage.merchantName = it.name
                            tv_name.text = sessionStorage.merchantName
                            iv_shop_profile_image.loadImageWithErrorCallback(it.profileImage, onError = {
                                tv_user_name_letter.text = generateDisplayPicText(sessionStorage.merchantName)
                            })

                            sessionStorage.profileImage = it.profileImage

                            isBankAccoutExists = it.isBankAccoutExists
                            if (isBankAccoutExists) {

                                lay_editBank.visibility = View.VISIBLE
                                cv_editBankDetails.visibility = View.GONE

                            } else {

                                tv_bankStatus.text = getString(R.string.addbankaccount)
                                lay_editBank.visibility = View.GONE
                                cv_editBankDetails.visibility = View.VISIBLE

                            }
                        }


                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        cl_main.visibility = View.GONE

                        activity?.showShortToast(response.errorMessage)

                    }
                }
            })
            termsConditionsResponse.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ApiCallStatus.LOADING -> {
                        dialog.show_dialog()
                    }
                    ApiCallStatus.SUCCESS -> {
                        dialog.dismiss_dialog()
                        it?.data?.data?.let {
                            termsConditions = it

                        }

                    }
                    ApiCallStatus.ERROR -> {
                        dialog.dismiss_dialog()
                        activity?.showShortToast(it.errorMessage)

                    }
                }
            })
            updateStatus.observe(viewLifecycleOwner, Observer {
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

    private fun selectProfileImage() {
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