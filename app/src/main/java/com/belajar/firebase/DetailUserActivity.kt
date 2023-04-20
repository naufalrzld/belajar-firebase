package com.belajar.firebase

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.belajar.firebase.databinding.ActivityDetailUserBinding

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private val userData by lazy { intent.getParcelableExtra<UserData>(USER_DATA) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.apply {
            title = if (userData == null) "Add New User" else "Edit User"
            setDisplayHomeAsUpEnabled(true)
        }

        userData?.let {
            binding.etName.setText(it.name)
            binding.etAge.setText(it.age.toString())
            binding.etGender.setText(it.gender)
        }

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString()
            val age = binding.etAge.text.toString().toInt()
            val gender = binding.etGender.text.toString()

            userData?.let { updateUser(it.id, name, age, gender) } ?: addNewUser(name, age, gender)
        }
    }

    private fun addNewUser(name: String, age: Int, gender: String) {
        /**
         * TODO 4: Buat variable HasMap,
         *  kemudian isi HasMap tersebut dengan data name, age dan gender.
         *
         *  Hint: Silakan periksa Assistant Firebase Cloud Firestore Point 4.
         **/
        // Tulis kode disini


        /**
         * TODO 5: Kirim data user berupa HasMap yang sudah dibuat pada TODO 4
         *  ke collection users. Jika add berhasil, kembali ke halaman sebelumnya.
         *
         *  Hint: Silakan periksa Assistant Firebase Cloud Firestore Point 4.
         *  Gunakan fungsi finish() atau onBackPressed() untuk kembali ke halaman sebelumnya
         **/
        // Tulis kode disini


    }

    private fun updateUser(id: String, name: String, age: Int, gender: String) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val USER_DATA = "user_data"
    }
}