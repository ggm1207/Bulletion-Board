package com.example.administrator.myapplication;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseInstanceIDS";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        // 토큰이 바뀌면 여기에서 이벤트를 받는다.
        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token) {
        // 그러면, 여기서 바뀐토큰을 저장해줘야 됨.
        // db에 저장해서 필요할때 불러서 쓰거나.
        // 앱자체 SharedPreference 저장해서 불러쓰면 된다.
    }
}
