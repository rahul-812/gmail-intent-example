package com.example.unknownwalk

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get references fom resource
        val inputEmail: EditText = findViewById(R.id.emailEditText)
        val inputSubject: EditText = findViewById(R.id.subjectEditText)
        val inputCompose: EditText = findViewById(R.id.composeEditText)
        val button: Button = findViewById(R.id.button)

        val toastWarning =
            Toast.makeText(this, "Input field is empty", Toast.LENGTH_LONG)


        button.setOnClickListener {
            val emailId = inputEmail.text.toString()
            val subject = inputSubject.text.toString()
            val compose = inputCompose.text.toString()

            // if any of the edit text is empty, app should show a toast
            if (emailId.isEmpty() || subject.isEmpty() || compose.isEmpty()) {
                toastWarning.show();
                return@setOnClickListener
            }

            sendEmail(arrayOf(emailId.trimEnd()), subject, compose);
        }
    }

    private fun sendEmail(emailIds: Array<String>, subject: String, compose: String?) {
        val myIntent = Intent(Intent.ACTION_SEND).also {
            it.putExtra(Intent.EXTRA_EMAIL, emailIds)
            it.putExtra(Intent.EXTRA_SUBJECT, subject)
            it.putExtra(Intent.EXTRA_TEXT, compose)
            // filters appropriate app
            it.type = "message/rfc822"
        }

        if(myIntent.resolveActivity(packageManager) != null)
            startActivity(Intent.createChooser(myIntent, "chose an email client"))
        else
            Toast.makeText(this, "No suitable app found", Toast.LENGTH_SHORT).show()

    }
}