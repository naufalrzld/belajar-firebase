package com.belajar.firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.belajar.firebase.databinding.ActivityMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/**
 * TODO 0: Lakukan integrasi project ke firebase.
 *  Silakan manfaatkan tools Firebase Assistant
 *  yang sudah disediakan oleh Android Studio.
 *  Caranya buka Tools -> Firebase.
 *  Assistant akan muncul di layar bagian kanan.
 **/

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: UserAdapter

    /**
     * TODO 1: Buat variable global untuk menampung instance firestore
     *
     * Hint: Silakan periksa Assistant Firebase Cloud Firestore Point 3
     **/
    // Tulis kode disini
    private val firestore = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userAdapter = UserAdapter(
            onItemClickListener = {
                val intent = Intent(this, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.USER_DATA, it)
                startActivity(intent)
            },
            onItemDeleteClickListener = { deleteUser(it) }
        )

        binding.rvUsers.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = userAdapter
        }

        binding.swRefresh.setOnRefreshListener { getUsers() }

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, DetailUserActivity::class.java))
        }
    }

    private fun getUsers() {
        val users = mutableListOf<UserData>()

        /**
         * TODO 2: Ambil collection users.
         *  Jika proses pengambilan data collection users berhasil,
         *  buat object UserData dengan paramter id, name, age dan gender
         *  berasal dari document collection, kemudian tambahkan ke dalam array users.
         *  Kemudian Kirim array users ke UserAdapter
         *  dengan memanfaatkan kode adapter.setUsers(users)
         *  agar data users tampil di layar.
         *
         * Hint: Silakan periksa Assistant Firebase Cloud Firestore Point 5
         **/
        // Tulis kode disini
        binding.swRefresh.isRefreshing = true
        firestore.collection("users").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val id = document.id
                    val data = document.data
                    val name = data["name"] as String
                    val age = data["age"] as Long
                    val gender = data["gender"] as String

                    val userData = UserData(id, name, age.toInt(), gender)
                    users.add(userData)
                }

                binding.swRefresh.isRefreshing = false
                userAdapter.setUsers(users)
            }
            .addOnFailureListener { exception ->
                Log.w("users", "Error getting documents.", exception)
                binding.swRefresh.isRefreshing = false
                Toast.makeText(this, "Error updating documents.", Toast.LENGTH_LONG).show()
            }

    }

    private fun deleteUser(id: String) {
        /**
         * TODO 8: Lakukan delete user bedasarkan document id.
         *  id didapatkan dari paramter id.
         *  Jika proses delete berhasil, hapus juga item tersebut dari RecyclerView.
         *
         *  Hint: Untuk delete data gunakan .document(id).delete().
         *  Untuk menghapus item dari RecyclerView, gunakan fungsi adapter.deleteUser(id)
         **/
        // Tulis kode disini
        binding.swRefresh.isRefreshing = true
        firestore.collection("users").document(id).delete()
            .addOnSuccessListener {
                binding.swRefresh.isRefreshing = false
                userAdapter.deleteUser(id)
            }
            .addOnFailureListener { exception ->
                Log.w("users", "Error deleting documents.", exception)
                binding.swRefresh.isRefreshing = false
                Toast.makeText(this, "Error updating documents.", Toast.LENGTH_LONG).show()
            }
    }

    override fun onResume() {
        super.onResume()
        getUsers()
    }
}