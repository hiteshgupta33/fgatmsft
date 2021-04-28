package com.fgmsft.signmeup.signup.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.fgmsft.signmeup.R
import com.fgmsft.signmeup.databinding.SignUpConfirmationBinding
import com.fgmsft.signmeup.signup.model.SignUpForm

// the fragment initialization parameters
private const val ARG_SIGNUP_FORM = "signupform"

/**
 * Sign up confirmation view fragment. This class is responsible for showing the confirmation
 * screen and bind appropriate actions.
 */
class SignUpConfirmationFragment: Fragment() {
    // Remember to destroy binding when view is destroyed
    private var _binding: SignUpConfirmationBinding? = null
    private val binding get() = _binding!!

    private lateinit var signUpConfirmationViewModel: SignUpConfirmationViewModel

    private var signUpForm: SignUpForm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            signUpForm = it.getParcelable(ARG_SIGNUP_FORM)
        }

        signUpConfirmationViewModel = SignUpConfirmationViewModel(signUpForm)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignUpConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAvatar()
        binding.titleText.text = signUpConfirmationViewModel.getTitle(getString(R.string.confirmation_title_text))

        // Fill website information if available
        if (signUpConfirmationViewModel.shouldBeVisible(signUpForm?.website)) {
            binding.website.text = signUpForm?.website
            binding.website.visibility = View.VISIBLE
        }

        // Fill FirstName information if available
        if (signUpConfirmationViewModel.shouldBeVisible(signUpForm?.firstName)) {
            binding.firstName.text = signUpForm?.firstName
            binding.firstName.visibility = View.VISIBLE
        }

        // Fill Email information if available
        if (signUpConfirmationViewModel.shouldBeVisible(signUpForm?.email)) {
            binding.email.text = signUpForm?.email
            binding.email.visibility = View.VISIBLE
        }
    }

    /**
     * This function will request view model to create the Bitmap and observe to the changes.
     */
    private fun setupAvatar() {
        observeAvatarBitmap()
        signUpConfirmationViewModel.createBitmap(binding.avatarView.width, binding.avatarView.height, signUpForm?.avatarPath)
    }

    /**
     * Observe the changes to the [SignUpBaseViewModel._avatarBitmap].
     * Once avatar is created, show it on the view.
     *
     */
    private fun observeAvatarBitmap() {
        signUpConfirmationViewModel.avatarBitmap.observe(viewLifecycleOwner, { _bitmap ->
            _bitmap?.let {
                binding.avatarView.setImageBitmap(it)
            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param signUpForm [SignUpForm] data
         * @return A new instance of fragment SignUpConfirmationFragment.
         */
        fun newInstance(signUpForm: SignUpForm) =
            SignUpConfirmationFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SIGNUP_FORM, signUpForm)
                }
            }
    }
}