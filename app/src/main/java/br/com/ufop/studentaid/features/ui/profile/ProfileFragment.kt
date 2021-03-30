package br.com.ufop.studentaid.features.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.extensions.gone
import br.com.ufop.studentaid.core.extensions.setUpRecyclerView
import br.com.ufop.studentaid.core.extensions.visible
import br.com.ufop.studentaid.core.platform.BaseFragment
import br.com.ufop.studentaid.features.adapter.ServiceModelAdapter
import br.com.ufop.studentaid.features.models.FirestoreUser
import br.com.ufop.studentaid.features.ui.main.MainViewModel
import br.com.ufop.studentaid.features.ui.services.ServicesViewModel
import br.com.ufop.studentaid.features.util.ConstantsUtils
import br.com.ufop.studentaid.features.util.ConstantsUtils.KEY_EMAIL
import br.com.ufop.studentaid.features.util.ConstantsUtils.KEY_PHONE
import br.com.ufop.studentaid.features.util.ConstantsUtils.KEY_PHOTO
import br.com.ufop.studentaid.features.util.UtilKeyboard
import com.google.firebase.firestore.SetOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_fragment.*


class ProfileFragment : BaseFragment(R.layout.profile_fragment) {

    override fun toolbarTitle(): String = "Perfil"

    private val PICK_IMAGE: Int = 111
    private lateinit var viewModel: MainViewModel
    private lateinit var serviceViewModel: ServicesViewModel

    var profileClicked: FirestoreUser? = null
    var uploaded = false

    val adapter = ServiceModelAdapter(arrayListOf()) {

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileClicked = arguments?.getParcelable(getString(R.string.PROFILE_CLICKED))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        uploaded = false
        setUpViewModels()
        setClickListener()
        setUpLayout()
        observeUser()
    }


    private fun setUpLayout(firestoreUser: FirestoreUser? = null) {
        profileClicked?.apply {
            profilefragment_image?.isEnabled = false
            profilefragment_edit_profile_email_imageview.gone()
            profilefragment_edit_profile_phone_imageview.setImageResource(R.drawable.ic_phone)
            profilefragment_edit_profile_image_imageview.gone()
            profilefragment_textview_name?.text = name
            profile_email_text?.setText(email)
            profile_phone_text?.setText(phoneNumber)
            photoUrl?.let {
                if (it.isNotBlank())
                    Picasso.get().load(photoUrl).into(profilefragment_image)
            }
            profile_star?.rating = rating.toFloat()

            val phone = profile_phone_text?.text.toString()

            profilefragment_edit_profile_phone_imageview?.setOnClickListener {
                val uri = "tel:" + phone.trim()
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse(uri)
                startActivity(intent)

                if (uploaded.not()) {
                    uploadContractedService(null)
                }
            }
            if (phone.isNotBlank()) {
                profile_phone_text?.setOnClickListener {
                    val uri = "tel:" + phone.trim()
                    val intent = Intent(Intent.ACTION_DIAL)
                    intent.data = Uri.parse(uri)
                    startActivity(intent)
                }
            }
            providedServices?.let {
                if (it.isNotEmpty()) {
                    profile_provided_services_lyt?.visible()
                    provided_services_profile_recycler?.setUpRecyclerView(null, {
                        it.adapter = adapter
                    })
                    adapter.addAll(it)
                }
            }
        } ?: firestoreUser?.apply {

            profilefragment_textview_name?.text = name
            profile_email_text?.setText(email)
            profile_phone_text?.setText(phoneNumber)
//                profile_phone_cell  ?.visibility(phoneNumber.isNullOrBlank().not())
            photoUrl?.let {
                if (it.isNotBlank())
                    Picasso.get().load(photoUrl).into(profilefragment_image)
            }
            profile_star?.rating = rating.toFloat()

            providedServices?.let {
                if (it.isNotEmpty()) {
                    profile_provided_services_lyt?.visible()
                    provided_services_profile_recycler?.setUpRecyclerView(null, {
                        it.adapter = adapter
                    })
                    adapter.addAll(it)
                }
            }
        } ?: viewModel.getLoggedFirebaseUser()?.let { user ->
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
            UtilKeyboard.showKeyboard(requireContext(), profile_email_text)
            profile_email_text?.performClick()
        }
        save_email_iv?.setOnClickListener {
            profilefragment_edit_profile_email_imageview?.visible()
            save_email_iv?.gone()
            profile_email_text?.isEnabled = false
            UtilKeyboard.hideKeyboard(requireContext(), profile_email_text)

            val newEmail = profile_email_text?.text.toString()
            userLoginReference?.update(KEY_EMAIL, newEmail)
            showMessage("Email salvo com sucesso")
        }

        profilefragment_edit_profile_phone_imageview?.setOnClickListener {
            profilefragment_edit_profile_phone_imageview?.gone()
            save_phone_iv?.visible()
            profile_phone_text?.isEnabled = true
            profile_phone_text?.requestFocus()
            UtilKeyboard.showKeyboard(requireContext(), profile_phone_text)

        }
        save_phone_iv?.setOnClickListener {
            profilefragment_edit_profile_phone_imageview?.visible()
            save_phone_iv?.gone()
            UtilKeyboard.hideKeyboard(requireContext(), profile_phone_text)
            profile_phone_text?.isEnabled = false
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
            serviceViewModel = ViewModelProvider(it).get(ServicesViewModel::class.java)
            observe()
        }

    }

    private fun observe() {
        serviceViewModel.contractedServiceSelected.observe(viewLifecycleOwner, Observer {
            uploadContractedService(it)
        })
    }

    private fun uploadContractedService(it: String?) {
        var contractedServices = it?.let { arrayListOf(it) }
        if (contractedServices.isNullOrEmpty()) {
            if (adapter.getListContracted().isNullOrEmpty().not()) {
                contractedServices = adapter.getListContracted()
            }
        }
        val previousServices = viewModel.loggedFirestoreUser.value?.contractedServices
        previousServices?.let { it1 -> contractedServices?.addAll(it1) }
        contractedServices?.let{
            userLoginReference.set(
                mapOf(
                    ConstantsUtils.KEY_CONTRACTED_SERVICES to it.toSet()
                        .toList()
                ), SetOptions.merge()
            )
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        showMessage("Sucesso")
                        uploaded = true
//                                mainViewModel.getLoggedFirestoreUser()?.providedServices?.addAll(adapter.getServicesList())
//                                findNavController().popBackStack(R.id.contractedServicesFragment,true)
                    } else {
                        showMessage("Ocorreu um erro ao salvar o serviço , tente novamente")
                    }

                }
        }
    }

    private fun observeUser() {
        viewModel.loggedFirestoreUser.observe(viewLifecycleOwner, Observer {
            setUpLayout(it)
        })
    }


}