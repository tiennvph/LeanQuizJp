package com.app.learnquizjp.fragment

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.learnquizjp.activity.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.content.Intent
import kotlinx.android.synthetic.main.fragment_user.view.*
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.app.learnquizjp.R
import kotlinx.android.synthetic.main.dialog_update_password.*
import android.widget.Toast
import com.app.learnquizjp.base.ConstantsPro.Companion.REQUEST_CODE_CAMERA_IMAGE
import com.app.learnquizjp.base.ConstantsPro.Companion.REQUEST_CODE_FOLDER_IMAGE
import com.app.learnquizjp.model.User
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class UserFragment : Fragment(){

    private lateinit var auth : FirebaseAuth
    private lateinit var authListener : FirebaseAuth.AuthStateListener
    private lateinit var storage : FirebaseStorage

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view : View = inflater.inflate(R.layout.fragment_user, container, false)
        //Init Fire base Component
        auth = FirebaseAuth.getInstance()
        val user : FirebaseUser? = auth.currentUser
        storage = FirebaseStorage.getInstance()
        authListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(Intent(activity, LoginActivity::class.java))
                activity!!.finish()
            }
        }
        val ref = FirebaseDatabase.getInstance().getReference("users").child(user!!.uid)
        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val user = dataSnapshot.getValue(User::class.java)
                if (user != null) {
                    view.tvEmail.text = user.username
                    if(user.profileImageUrl == "default"){
                        view.imgUserAva.setImageResource(R.drawable.anonymous_user)
                    }else{
                        context?.let { Glide.with(it).load(user.profileImageUrl).into(view.imgUserAva) }
                    }
                }

                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("result", "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        }
        ref.addValueEventListener(userListener)

        view.btnCamera.setOnClickListener {
            startActivityForResult(Intent(MediaStore.ACTION_IMAGE_CAPTURE),REQUEST_CODE_CAMERA_IMAGE)
        }

        view.btnFolder.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,REQUEST_CODE_FOLDER_IMAGE)
        }

        view.btnAdd.setOnClickListener {
//            var calendar = Calendar.getInstance()
            uploadImageToFirebaseStorage()
        }

        view.btnReset.setOnClickListener {
            val dialog = AlertDialog.Builder(context)
            val inflater = activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogView = inflater.inflate(R.layout.dialog_update_password, null)
            dialog.setView(dialogView)
            val dialog_update_password : Dialog = dialog.show()
            val edtPassword : EditText =  dialog_update_password.edtPassword
            val btnReset : Button = dialog_update_password.btnReset
            val btnCancel : Button = dialog_update_password.btnCancel
            btnReset.setOnClickListener {
                if (user != null && edtPassword.text.toString().trim() != "") {
                    if (edtPassword.text.toString().trim().length < 6) {
                        edtPassword.error = getString(R.string.notify_length_pass)
                    } else {
                        user.updatePassword(edtPassword.text.toString().trim()).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(activity, getString(R.string.notify_change_password_successful), Toast.LENGTH_SHORT).show()
                                auth.signOut()
                            } else {
                                Toast.makeText(activity, getString(R.string.notify_change_password_fail), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                } else if (edtPassword.text.toString().trim() == "") {
                    edtPassword.error = getString(R.string.notify_input_new_password)
                }
            }
            btnCancel.setOnClickListener {
                dialog_update_password.dismiss()
            }

        }

        return view
    }

    var selectedPhotoUri : Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_CAMERA_IMAGE && resultCode == RESULT_OK && data != null){
            val bitmap : Bitmap = data.extras.get("data") as Bitmap
            view!!.imgUserAva.setImageBitmap(bitmap)
        }
        if(requestCode == REQUEST_CODE_FOLDER_IMAGE && resultCode == RESULT_OK && data != null){
            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(activity!!.contentResolver,selectedPhotoUri)
            view!!.imgUserAva.setImageBitmap(bitmap)

        }
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener { authListener }
    }

    override fun onStop() {
        super.onStop()
        if(authListener != null){
            auth.removeAuthStateListener { authListener }
        }
    }

    private fun uploadImageToFirebaseStorage(){
        val storageRef = storage.getReferenceFromUrl("gs://learnquizjp.appspot.com")
        val mountainsRef = storageRef.child("avatar.png")
        // Get the data from an ImageView as bytes
        view!!.imgUserAva.isDrawingCacheEnabled = true
        view!!.imgUserAva.buildDrawingCache()
        val bitmap = (view!!.imgUserAva.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = mountainsRef.putBytes(data)
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
            Toast.makeText(activity,getString(R.string.notify_upload_avatar_fail),Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...

            mountainsRef.downloadUrl.addOnSuccessListener {
                Log.e("url",it.toString())
                saveUserToFirebaseDatabase(it.toString())
            }.addOnFailureListener{
                Toast.makeText(activity,"Error",Toast.LENGTH_SHORT).show()
            }
            Toast.makeText(activity,getString(R.string.notify_upload_avatar_successful),Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl : String){
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
        val user = User(uid, FirebaseAuth.getInstance().currentUser!!.email!!)
        ref.setValue(user)
        ref.child("profileImageUrl").setValue(profileImageUrl)
            .addOnSuccessListener {
                Toast.makeText(activity,getString(R.string.notify_save_user_successful),Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(activity,getString(R.string.notify_save_user_fail),Toast.LENGTH_SHORT).show()
            }
    }
}