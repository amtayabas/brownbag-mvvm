package csv.brownbag.mvvm.basic.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import csv.brownbag.mvvm.basic.viewmodel.LoginViewModel
import csv.brownbag.mvvm.databinding.FragmentBasicBinding

class BasicFragment : Fragment() {

    private lateinit var binding: FragmentBasicBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasicBinding.inflate(inflater, container, false)
        val viewModel: LoginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.getUser()!!.observe(viewLifecycleOwner) { user ->
            if (user.email.isNotEmpty() && user.password.isNotEmpty()) {
                Toast.makeText(requireContext(), "email: ${user.email}" + "\n" +
                        "password: ${user.password}", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {

    }
}