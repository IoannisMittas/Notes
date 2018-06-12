Notes, by Ioannis Mittas

Android notes application, based in Model-View-ViewModel architecture.

When the user opens the app for the first time, he asked to login with his Google account. He then
can create, view, edit and delete notes.

The app consists of three main activities. In NoteListActivity, every note is shown in a list. The user
can tap a fab button to open CreateNoteActivity, so he can create a new Note. The new note is then
shown back in the NoteListActivity. If a note is clicked in NoteListActivity, DetailActivity is opened
and the note is shown in a big screen. In there the user can edit the title or the bodytext of the note.
Also in NotelistActivity, if the user swipes a note then it's deleted.

The Views(Activities, Fragments) make data requests to the appropriate ViewModels, which serve as
middlemen between the Views and a Repository. The app broadly uses a reactive mechanism and an Observer-Observable
pattern by using LiveData. Also, in ViewModels Transformations are used to communicate with the repository.
We do that to  avoid leaking ViewModels, as we want our ViewModels to go away when their respected Views are gone, too.
A more detailed explanation can be found here: https://medium.com/google-developers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54

A Repository is used as a single point of entry for the data. The repository handles data
communication between the local persistance, which is a Room database, and the remote data source,
which is  a Firebase Realtime database. The local database serves as the single source of truth,
so even after the data are fetched remotely, they are being saved locally in the db before
they are exposed to the presentation layer.

The below  diagram from the official Android Guide to App Architecture shows the app architecture,
with the difference been that in this Notes app, the Remote Data Source is a Firebase Realtime
Database and the Firebase Android API is used to communicate with it.
https://developer.android.com/topic/libraries/architecture/images/final-architecture.png
