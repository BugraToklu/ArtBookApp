package com.example.artbooktesting

import android.app.AlarmManager
import android.app.Notification
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.navigation.fragment.findNavController
import com.example.artbooktesting.databinding.FragmentLoginBinding
import com.example.artbooktesting.databinding.FragmentSignupBinding
import com.example.artbooktesting.model.User
import com.example.artbooktesting.roomdb.DatabaseHelper
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private var fragmentBinding: FragmentSignupBinding? = null
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSignupBinding.bind(view)
        fragmentBinding = binding

        databaseHelper = DatabaseHelper(requireContext())


        auth = FirebaseAuth.getInstance()

        binding.kayTOl.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            findNavController().navigate(R.id.action_signupFragment_to_artFragment)
                            Toast.makeText(requireContext(), "User created successfully", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "User creation failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
