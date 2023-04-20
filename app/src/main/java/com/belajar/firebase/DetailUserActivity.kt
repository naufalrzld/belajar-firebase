package com.belajar.firebase

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.belajar.firebase.databinding.ActivityDetailUserBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    private val userData by lazy { intent.getParcelableExtra<UserData>(USER_DATA) }

    /**
     * TODO 3: Buat variable global untuk menampung instance firestore
     *
     * Hint: Silakan periksa Assistant Firebase Cloud Firestore Point 3
     **/
    // Tulis kode disini
    private val firestore = Firebase.firestore

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

            userData?.let { user ->
                updateUser(user.id, name, age, gender)
            } ?: addNewUser(name, age, gender)
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
        val userHasMap = hashMapOf(
            "name" to name,
            "age" to age,
            "gender" to gender
        )

        /**
         * TODO 5: Kirim data user berupa HasMap yang sudah dibuat pada TODO 4
         *  ke collection users. Jika add berhasil, kembali ke halaman sebelumnya.
         *
         *  Hint: Silakan periksa Assistant Firebase Cloud Firestore Point 4.
         *  Gunakan fungsi finish() atau onBackPressed() untuk kembali ke halaman sebelumnya
         **/
        // Tulis kode disini
        firestore.collection("users").add(userHasMap)
            .addOnSuccessListener { finish() }
            .addOnFailureListener { exception ->
                Log.w("users", "Error adding documents.", exception)
                Toast.makeText(this, "Error updating documents.", Toast.LENGTH_LONG).show()
            }

    }

    private fun updateUser(id: String, name: String, age: Int, gender: String) {
        /**
         * TODO 6: Buat variable HasMap,
         *  kemudian isi HasMap tersebut dengan data name, age dan gender.
         **/
        // Tulis kode disini
        val userHasMap = hashMapOf<String, Any>(
            "name" to name,
            "age" to age,
            "gender" to gender
        )

        /**
         * TODO 7: Update data user berdasarkan document id.
         *  id didapatkan dari parameter id.
         *  Jika add berhasil, kembali ke halaman sebelumnya.
         *
         *  Hint: Untuk update data gunakan .document(id).update(userHasMap)
         **/
        // Tulis kode disini
        firestore.collection("users").document(id).update(userHasMap)
            .addOnSuccessListener { finish() }
            .addOnFailureListener { exception ->
                Log.w("users", "Error updating documents.", exception)
                Toast.makeText(this, "Error updating documents.", Toast.LENGTH_LONG).show()
            }

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