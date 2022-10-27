package com.arvind.astreamchatapp.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.arvind.astreamchatapp.data.local.ChatUser
import com.arvind.astreamchatapp.databinding.FragmentLoginBinding
import com.arvind.astreamchatapp.view.base.BaseFragment
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = with(binding) {
        buttonGo.setOnClickListener {
            authenticateTheUser()
        }
    }

    private fun authenticateTheUser() {
        val firstName = binding.firstNameEditText.text.toString()
        val username = binding.usernameEditText.text.toString()
        if (validateInput(firstName, binding.firstNameInputLayout) &&
            validateInput(username, binding.usernameInputLayout)
        ) {
            val chatUser = ChatUser(firstName, username)
            val action = LoginFragmentDirections.actionLoginFragmentToChannelFragment(chatUser)
            findNavController().navigate(action)
        }
    }

    private fun validateInput(inputText: String, textInputLayout: TextInputLayout): Boolean {
        return if (inputText.length <= 3) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error = "* Minimum 4 Characters Allowed"
            false
        } else {
            textInputLayout.isErrorEnabled = false
            textInputLayout.error = null
            true
        }

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)
}