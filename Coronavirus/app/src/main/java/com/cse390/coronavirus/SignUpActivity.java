package com.cse390.coronavirus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * The sign up activity is displayed when there is no current user or the current user chose to sign out
 * @author Khiem Phi (111667279) & Matthew Guidi (110794886)
 */
public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signUpButton;
    private Button loginButton;

    /**
     * Creates the view and initializes Firebase's Auth for user generation/login
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        initSignUpButton();
        if (currentUser != null){
            // Return to MainActivity With User-ID
        }

    }

    /**
     *  Pulls the user's sign in and verifies username and password are not empty
     *  If any issues come up from the creation of the user it informs the user registration failed
     *  Login is similar but says the sign in failed instead
     *  Transitions to the main activity upon a successful login/sign up with a message confirming completion 
     */
    private void initSignUpButton(){
        signUpButton = findViewById(R.id.signup_btn);
        emailEditText = findViewById(R.id.email_edt_text);
        passwordEditText = findViewById(R.id.pass_edt_text);
        loginButton = findViewById(R.id.login_btn);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param v
             */
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(SignUpActivity.this, "Please Fill In The Empty Fields", Toast.LENGTH_LONG).show();
                }else{
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                String currentUserID = mAuth.getCurrentUser().getUid();
                                intent.putExtra("userID", currentUserID);
                                setResult(RESULT_OK, intent);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(SignUpActivity.this, "Registration Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param v
             */
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(SignUpActivity.this, "Please Fill In The Empty Fields", Toast.LENGTH_LONG).show();
                }else{
                    System.out.println(email);
                    System.out.println(password);
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(SignUpActivity.this, "Successfully Signed-In", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                String currentUserID = mAuth.getCurrentUser().getUid();
                                intent.putExtra("userID", currentUserID);
                                setResult(RESULT_OK, intent);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(SignUpActivity.this, "Sign-In Failed", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }


}
