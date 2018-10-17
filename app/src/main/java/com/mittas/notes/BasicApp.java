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

import com.instabug.library.Instabug;
import com.instabug.library.invocation.InstabugInvocationEvent;
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
        new Instabug.Builder(this, "92e39ccbe16756616796f992a07028ec")
                .setInvocationEvents(InstabugInvocationEvent.SHAKE, InstabugInvocationEvent.SCREENSHOT)
                .build();
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
