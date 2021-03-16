package br.com.ufop.studentaid.features.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.gone
import br.com.ufop.studentaid.core.extensions.visible
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.models.FirestoreUser
import br.com.ufop.studentaid.features.ui.main.MainViewModel
import br.com.ufop.studentaid.features.util.ConstantsUtils.KEY_EMAIL
import br.com.ufop.studentaid.features.util.ConstantsUtils.KEY_PHONE
import br.com.ufop.studentaid.features.util.ConstantsUtils.KEY_PHOTO
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_fragment.*
import java.lang.Exception


class ProfileFragment : BaseFragment(R.layout.profile_fragment) {

    override fun toolbarTitle(): String = getString(R.string.text_profile)

    private val PICK_IMAGE: Int = 111
    private lateinit var viewModel: MainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        setUpViewModels()
        setClickListener()
        setUpLayout()
        observeUser()
    }

    private fun setUpLayout(firestoreUser: FirestoreUser? = null) {
        firestoreUser?.apply {

            profilefragment_textview_name?.text = name
            profile_email_text?.setText(email)
            profile_phone_text?.setText(phoneNumber)
//                profile_phone_cell  ?.visibility(phoneNumber.isNullOrBlank().not())
            Picasso.get().load(photoUrl).into(profilefragment_image)
            profile_star?.rating = rating.toFloat()
        }?: viewModel.getLoggedFirebaseUser()?.let {user->
            user.let {
                // Name, email address, and profile photo Url
                val name = user.displayName
                val email = user.email
                val photoUrl = user.photoUrl
                val phoneNumber = user.phoneNumber

                // Check if user's email is verified
                val emailVerified = user.isEmailVerified

                // The user's ID, unique to the Firebase project. Do NOT use this value to
                // authenticate with your backend server, if you have one. Use
                // FirebaseUser.getToken() instead.
                val uid = user.uid
                profilefragment_textview_name?.text = name
                profile_email_text?.setText(email)
                profile_phone_text?.setText(phoneNumber)
//                profile_phone_cell  ?.visibility(phoneNumber.isNullOrBlank().not())
                Picasso.get().load(photoUrl).into(profilefragment_image)
                profile_star?.rating = 1f
            }

        }
    }

    private fun setClickListener() {
        profilefragment_image?.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
        profilefragment_edit_profile_email_imageview?.setOnClickListener {
            profilefragment_edit_profile_email_imageview?.gone()
            save_email_iv?.visible()
            profile_email_text?.isEnabled = true
            profile_email_text?.requestFocus()
            profile_email_text?.performClick()
            view?.postDelayed({
                profile_email_text?.performClick()
            }, 500)
        }
        save_email_iv?.setOnClickListener {
            profilefragment_edit_profile_email_imageview?.visible()
            save_email_iv?.gone()
            profile_email_text?.isEnabled = false
            view?.postDelayed({
                profile_fragment?.performClick()
            }, 100)

            val newEmail = profile_email_text?.text.toString()
            userLoginReference?.update(KEY_EMAIL, newEmail)
            showMessage("Email salvo com sucesso")
        }

        profilefragment_edit_profile_phone_imageview?.setOnClickListener {
            profilefragment_edit_profile_phone_imageview?.gone()
            save_phone_iv?.visible()
            profile_phone_text?.requestFocus()
            profile_phone_text?.performClick()
            profile_phone_text?.isEnabled = true
            view?.postDelayed({
                profile_phone_text?.performClick()
            }, 500)

        }
        save_phone_iv?.setOnClickListener {
            profilefragment_edit_profile_phone_imageview?.visible()
            save_phone_iv?.gone()
            profile_phone_text?.isEnabled = false
            view?.postDelayed({
                profile_fragment?.performClick()
            }, 100)
            val newPhone = profile_phone_text?.text.toString()
            userLoginReference?.update(KEY_PHONE, newPhone)
            showMessage("Telefone salvo com sucesso")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            profilefragment_image?.setImageURI(data?.data)
//            val bitmap = (profilefragment_image.drawable as BitmapDrawable).bitmap
//            val baos = ByteArrayOutputStream()
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//            val data = baos.toByteArray()

            // Create a storage reference from our app
            val storageRef = storage.reference
            // Create a child reference
            // imagesRef now points to "images"
            // Child references can also take paths
            // spaceRef now points to "images/space.jpg
            // imagesRef still points to "images"
            val profileRef = storageRef.child("${currentUserUid}/images/profile.jpg")
            data?.data?.let {
                val uploadTask = profileRef.putFile(it)
                val urlTask = uploadTask.continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    profileRef.downloadUrl
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result

                        try {

                            userLoginReference?.update(KEY_PHOTO, downloadUri.toString())
                            showMessage("Imagem salva com sucesso")
                        } catch (exception: Exception) {
                            showMessage("Ocorreu um erro ao salvar o arquivo , tente novamente")
                        }
                    } else {
                        // Handle failures
                        // ...
                        showMessage("Ocorreu um erro ao salvar o arquivo , tente novamente")
                    }
                }
            }
        }
    }

    private fun setUpViewModels() {
        activity?.let {
            viewModel = ViewModelProvider(it).get(MainViewModel::class.java)
        }

    }

    private fun observeUser() {
        viewModel.loggedFirestoreUser?.observe(viewLifecycleOwner, Observer {
            setUpLayout(it)
        })
    }


}