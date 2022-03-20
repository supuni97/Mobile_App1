package com.example.myapp1_kotlin

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController

import com.example.myapp1_kotlin.api.UserApiService
import com.example.myapp1_kotlin.databinding.FragmentFirstBinding
import com.example.myapp1_kotlin.model.User

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val userApiService = UserApiService.create()

    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
           val editText = binding.inputtextid.editableText
           val user = userApiService.getUser(editText.toString());


            user.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {

                    val body = response.body()
                    body?.let {
                        Log.i("FirstFragment", it.name)

                        binding.textviewUsername.text=it.username;

                        binding.textviewId.text= it.id.toString()
                        binding.textviewName.text = it.name
                        binding.textviewEmail.text = it.email

                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.i("FirstFragment", "error")
                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

