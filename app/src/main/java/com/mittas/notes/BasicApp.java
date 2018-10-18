/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mittas.notes;

import android.app.Application;

import com.instabug.bug.BugReporting;
import com.instabug.bug.PromptOption;
import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;
import com.instabug.library.ui.onboarding.WelcomeMessage;
import com.mittas.notes.data.LocalDatabase;
import com.mittas.notes.data.RemoteDatabase;


/**
 * Android Application class. Used for accessing singletons.
 */
public class BasicApp extends Application {

    private AppExecutors appExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        appExecutors = new AppExecutors();

        initInstabug();
    }

    private void initInstabug() {
        // Instabug dialog can be invoked with events like shake of the phone, screen gestures, etc.
        // In order to use it with the click of a menu button, like Qixxit for example, we need to set
        // InstabugInvocationEvent.NONE in the builder
        new Instabug.Builder(this, "92e39ccbe16756616796f992a07028ec")
                .setInvocationEvents(InstabugInvocationEvent.NONE)
                .build();


        // By default, instabug provides some onboarding information, for example
        // prompts the user to shake the phone in order to provide feedback.We disable
        // onboarding here.
        Instabug.setWelcomeMessageState(WelcomeMessage.State.DISABLED);

        // Change tint color here. We can use home24 orange :)
        Instabug.setPrimaryColor(getResources().getColor(R.color.colorPrimary));

        // By default, dialog can show 3 options: Report a problem, send improvement(feedback) and chat with us.
        // If we don't want in app chat, we provide only options for bug and feedback
        BugReporting.setPromptOptionsEnabled(PromptOption.BUG, PromptOption.FEEDBACK);

    }

    // This will be invoked on the click of the menu button
    public static void invokeInstabug() {
        BugReporting.invoke();
    }


    public LocalDatabase getLocalDatabase() {
        return LocalDatabase.getInstance(this);
    }

    public RemoteDatabase getRemoteDatabase() {
        return RemoteDatabase.getInstance();
    }

    public NoteRepository getRepository() {
        return NoteRepository.getInstance(getLocalDatabase(), getRemoteDatabase(), appExecutors);
    }
}
