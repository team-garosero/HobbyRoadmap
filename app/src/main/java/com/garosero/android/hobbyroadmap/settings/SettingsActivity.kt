package com.garosero.android.hobbyroadmap.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.garosero.android.hobbyroadmap.AppApplication
import com.garosero.android.hobbyroadmap.R
import com.garosero.android.hobbyroadmap.databinding.ActivitySettingsBinding
import com.garosero.android.hobbyroadmap.main.MainActivity

class SettingsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // tool bar
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // feedback
        binding.itemFeedback.setOnItemClickListener(object : CustomSettingView.OnItemClickListener {
            override fun onItemClick(view: View) {
                try {
                    val email = Intent(Intent.ACTION_SEND)
                    val address = getString(R.string.feedback_email)
                    val title = getString(R.string.feedback_title)
                    email.type = "plain/Text"
                    email.putExtra(Intent.EXTRA_EMAIL, address)
                    email.putExtra(Intent.EXTRA_SUBJECT, title)
                    startActivity(email)
                } catch (e: ActivityNotFoundException){
                    Toast.makeText(baseContext,
                        "이메일을 보낼 수 있는 앱이 없습니다.", Toast.LENGTH_LONG).show()
                } catch (e: Exception){

                }
            }
        })

        var intent = Intent(this, MainActivity::class.java)
        binding.customLogout.setOnItemClickListener(object : CustomSettingView.OnItemClickListener {
            override fun onItemClick(view: View) {
                // todo logout
                /*finishAffinity() // 모든 백스택 비우기
                startActivity(intent)
                finish()*/
            }
        })

        // user name
        AppApplication.requestSubscribeUser()
        binding.layoutProfile.tvUserName.text = AppApplication.userData.value?.nickname ?: "tree"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}