package br.com.ufop.studentaid.features.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import br.com.ufop.studentaid.R
import br.com.ufop.studentaid.core.platform.BaseFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : BaseFragment(R.layout.login_fragment), View.OnClickListener {

    override fun toolbarTitle(): String = getString(R.string.title_login)

    private val RC_SIGN_IN: Int = 10
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var viewModel: LoginViewModel
    private var auth: FirebaseAuth? = null
    val gso by lazy {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle()
        activity?.let { viewModel = ViewModelProvider(it).get(LoginViewModel::class.java) }
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) };
        auth = Firebase.auth
        checkGoogleSignIn()
        // TODO: Use the ViewModel

        setClickListener()
    }

    private fun checkGoogleSignIn() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        val account = auth?.currentUser
        handleUserLoggedIn(account)

    }

    private fun setClickListener() {
        view?.findViewById<SignInButton>(R.id.google_sign_in_button)?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.google_sign_in_button -> signInGoogle()
        }
    }

    private fun handleUserLoggedIn(account: FirebaseUser?) {
        account?.let {
            viewModel.setLoggedUser(it)
            findNavController().navigate(R.id.mainFragmentDest)
        }
    }

    private fun signInGoogle() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            // Google Sign In was successful, authenticate with Firebase
            val account = completedTask.getResult(ApiException::class.java)

            firebaseAuthWithGoogle(account?.idToken)
        } catch (e: ApiException) {
            // Google Sign In failed, update UI appropriately

            showMessage(getString(R.string.message_error))
            // ...
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        activity?.let {
            auth?.signInWithCredential(credential)
                    ?.addOnCompleteListener(it) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth?.currentUser
                            handleUserLoggedIn(user)
                        } else {
                            // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                            // ...
//                        Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                            showMessage(getString(R.string.message_error))
//                        updateUI(null)
                        }

                        // ...
                    }
        }
    }
}