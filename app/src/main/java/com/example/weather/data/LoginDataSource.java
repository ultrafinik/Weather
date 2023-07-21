package com.example.weather.data;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.weather.data.model.LoggedInUser;
import com.example.weather.ui.login.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.concurrent.Executor;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {
    private FirebaseAuth mAuth;
    private  LoggedInUser fakeUser;
    private FirebaseUser user;
    public Result<LoggedInUser> login(String username, String password) {

        try {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        user = mAuth.getCurrentUser();
                    }
                }
            });
            // TODO: handle loggedInUser authentication
            if (user != null) {
                fakeUser =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                user.getDisplayName());

            }
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}