package com.example.nwokoye.adurevideos;

/**
 * Created by NWOKOYE on 9/17/2017.
 */
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cloudrail.si.exceptions.AuthenticationException;
import com.cloudrail.si.interfaces.Profile;
import com.cloudrail.si.services.Facebook;
import com.cloudrail.si.services.Twitter;
import com.cloudrail.si.types.DateOfBirth;

public class LoginService  extends IntentService {

    public static final String EXTRA_PROFILE = "profile";
    public static final String EXTRA_SOCIAL_ACCOUNT = "account";
    private static final String TAG = "VIVZ";

    public LoginService() {
        super("Social Login Service");
    }

    private Profile init(SocialMedia provider) throws AuthenticationException {
        //Superclass reference variable = subclass object
        Profile profile = null;
        switch (provider) {
            case FACEBOOK:
                profile = new Facebook(this, getString(R.string.facebook_app_key), getString(R.string.facebook_app_secret));
                break;
            case TWITTER:
                profile = new Twitter(this, getString(R.string.twitter_app_key), getString(R.string.twitter_app_secret));
                break;
        }
        //At run time, profile will contain one of the 8 objects above and will fetch details from that particular provider
        return profile;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                Bundle extras = intent.getExtras();
                //Guess what I sent from the Activity which calls this Service! Our Enumeration variable!
                //It contains FACEBOOK or TWITTER or ... and is Serializable!
                SocialMedia provider = (SocialMedia) extras.getSerializable(EXTRA_PROFILE);

                //You already know which social account the user will use, just create an object of it.
                Profile profile = init(provider);

                //Create a User Account object with all the details
                UserAccount account = init(profile);

                //Start another Activity to show the user details
                Intent detailsIntent = new Intent(getApplicationContext(), Homepage.class);

                //To directly start activities from an IntentService, we need to add this flag, Google if curious!
                detailsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //Send the User Account object with all details
                detailsIntent.putExtra(EXTRA_SOCIAL_ACCOUNT, account);
                startActivity(detailsIntent);
            } catch (AuthenticationException e) {

                //When you cancel in the middle of a login, cloud rail was throwing this exception, hence!
                Log.e(TAG, "onHandleIntent: You cancelled", e);

            }
        }
    }

    private UserAccount init(Profile profile) {
        //The usual get-set stuff
        UserAccount account = new UserAccount();
        account.setIdentifier(profile.getIdentifier());
        account.setFullName(profile.getFullName());
        account.setEmail(profile.getEmail());
        account.setDescription(profile.getDescription());
        DateOfBirth dob = profile.getDateOfBirth();
        if (dob != null) {
            Long day = dob.getDay();
            Long month = dob.getMonth();
            Long year = dob.getYear();
            if (day != null) {
                account.setDay(day);
            }
            if (month != null) {
                account.setMonth(month);
            }
            if (year != null) {
                account.setYear(year);
            }
        }
        account.setGender(profile.getGender());
        account.setPictureURL(profile.getPictureURL());
        account.setLocale(profile.getLocale());
        return account;
    }
}