package com.fgmsft.signmeup.signup.form.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fgmsft.signmeup.databinding.SignUpFormBinding
import com.fgmsft.signmeup.signup.form.SignUpFormViewModel
import com.fgmsft.signmeup.signup.SignUpBaseViewModel
import com.fgmsft.signmeup.signup.model.SignUpForm

/**
 * View class for Sign up form.
 *
 * SignUp fragment handles the sign up form and validation of the form.
 * Any design changes to the sign up form should be done here. See [sign_up_form.xml]
 *
 * Use the [SignUpFormFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SignUpFormFragment: SignUpFormAvatarFragment() {
    // Using view binding as this is a simple case but it provides null and type safety.
    // Remember to destroy binding when view is destroyed
    private var _binding: SignUpFormBinding? = null
    private val binding get() = _binding!!

    /**
     * View Model for handling signup flow.
     */
    private lateinit var signUpFormViewModel: SignUpFormViewModel

    companion object {
        /**
         * Use this factory method to create a new instance of this fragment.
         *
         * @return A new instance of fragment SignUpFormFragment.
         */
        fun newInstance() = SignUpFormFragment()

        private val TAG = SignUpFormFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpFormViewModel = ViewModelProvider(requireActivity()).get(SignUpFormViewModel::class.java)

        observeSignUpFormState()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SignUpFormBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup views and listeners
        setupTextWatchers()
        setupSignUpBtn()
        setupAvatarCapture()
    }

    /**
     * Function which observes the changes to the sign up state and updates UI.
     */
    private fun observeSignUpFormState() {
        signUpFormViewModel.signupState.observe(this, Observer { _signUpState ->
            // return if state is null
            val signUpState = _signUpState ?: return@Observer

            // Enable submit button if form is valid.
            binding.submit.isEnabled = signUpState.isDataValid

            // If email doesn't match the expected pattern, show error to user
            if (signUpState.emailError != null) {
                binding.email.error = getString(signUpState.emailError!!)
            } else {
                binding.email.error = null
            }

            // if password doesn't meet the criteria, show error to user
            if (signUpState.passwordError != null) {
                binding.password.error = getString(signUpState.passwordError!!)
            } else {
                binding.password.error = null
            }
        })
    }

    /**
     * Function to set up the text change watchers on the email and password views.
     *
     * Currently only email and password are required fields. Extend the function to add
     * watchers for more fields in the future.
     *
     * (This could also be changed to focus listeners if the requirements are to show errors after user moves away
     * from the field.)
     */
    private fun setupTextWatchers() {
        binding.email.afterTextChanged {
            // Ask view model to validate email
            signUpFormViewModel.validateEmail(binding.email.text.toString())
        }

        binding.password.afterTextChanged {
            // Ask view model to validate password
            signUpFormViewModel.validatePassword(binding.password.text.toString())
        }
    }

    /**
     * Function to set up on click listener on the sign up button.
     *
     * when user taps on sign in, [SignUpForm] is created with the user inputs.
     *
     */
    private fun setupSignUpBtn() {
        binding.submit.setOnClickListener {
            val signUpForm = SignUpForm(
                firstName = binding.firstName.text.toString(),
                email = binding.email.text.toString(),
                password = binding.password.text.toString(),
                website = binding.website.text.toString()
            )

            // Ask view model to sign up the user with the given information.
            // TODO: Ideally the data should be stored in the data store by the view model.
            signUpFormViewModel.signUpUser(signUpForm)
        }
    }

    /**
     * Setup capture of the avatar. This function sets OnClick listener on the avatarBtn.
     * When user clicks on the button, it calls to handle the permissions
     *
     */
    private fun setupAvatarCapture() {
        binding.avatarBtn.setOnClickListener {
            handleAvatarCaptureRequest()
        }
    }

    /**
     * This function will request view model to create the Bitmap and observe to the changes.
     */
    override fun loadAvatar(avatarPath: String?) {
        observeAvatarBitmap()
        signUpFormViewModel.createBitmap(binding.imageContainer.width, binding.imageContainer.height, avatarPath)
    }

    /**
     * Observe the changes to the [SignUpBaseViewModel._avatarBitmap].
     * Once avatar is created, show it on the view.
     *
     */
    private fun observeAvatarBitmap() {
        signUpFormViewModel.avatarBitmap.observe(viewLifecycleOwner, { _bitmap ->
            if (_bitmap == null) {
                binding.avatarBtn.visibility = View.VISIBLE
                binding.avatarView.visibility = View.GONE
            } else {
                binding.avatarView.setImageBitmap(_bitmap)
                binding.avatarBtn.visibility = View.GONE
                binding.avatarView.visibility = View.VISIBLE
            }
        })
    }

    /**
     * Extension function for Edit Text watcher
     */
    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                /* Don't do anything */
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                /* Don't do anything */
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}