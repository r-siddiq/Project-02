package com.example.project02;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);


    @Test
    public void testLoginActivityTransitionToMainActivity() {
        // Initialize Intents capturing
        Intents.init();

        // Enter valid username and password
        onView(withId(R.id.enterUsername)).perform(typeText("testUser1"));
        onView(withId(R.id.enterPassword)).perform(typeText("testUser1"));

        // Click the login button
        onView(withId(R.id.login)).perform(click());

        // Verify that an intent was sent to start MainActivity
        intended(hasComponent(MainActivity.class.getName()));

        // Release Intents capturing
        Intents.release();
    }
}
