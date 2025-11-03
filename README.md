# MAD204 - Mobile Application Development Lab 3: Persistent Notes Application


## Student Details
- **Name:** Ramandeep Singh  
- **Student ID:** A00194321  
- **Course:** MAD204 - Mobile Application Development  
- **Date of Submission:** 02/11/2025

## Project Overview
Lab3NotesApp is an Android app developed in Kotlin allowing users to create, view, and delete notes. It employs a RecyclerView for displaying notes dynamically and persists data using SharedPreferences with GSON serialization. Notes remain saved even after app restarts. Users can delete notes via a long press with a Snackbar offering an Undo option.

## Features
- Dynamic note list display with RecyclerView
- Add notes using EditText input and button
- Delete notes with a long press and Undo via Snackbar
- Persistent storage with SharedPreferences and JSON (GSON)
- Restores notes on app launch

## Learning Objectives
- Understand key RecyclerView components: Adapter, ViewHolder, LayoutManager
- Implement adding/removing list items dynamically
- Handle UI events: button clicks, long press, Snackbar actions
- Use SharedPreferences for data persistence
- Serialize/deserialize objects using GSON
- Practice GitHub workflows (commits, branches, pull requests)

## Project Setup
- Project Name: Lab3NotesApp
- Language: Kotlin
- Minimum SDK: API 30 (Android 11)

## File Structure
- `MainActivity.kt`: Implements UI, RecyclerView setup, note handling, and persistence
- `MyAdapter.kt`: RecyclerView adapter class in a separate file
- `activity_main.xml`: Main layout with EditText, Button, RecyclerView
- `item_row.xml`: Layout for each note item in the list

## Running the Project
1. Clone or download the repository
2. Open in Android Studio
3. Add the Gson dependency to `build.gradle`:
    ```
    implementation 'com.google.code.gson:gson:2.10.1'
    ```
4. Sync Gradle and build
5. Run on an Android device or emulator (API 30+)

## GitHub Workflow
- Minimum 5 meaningful commits
- At least 3 Pull Requests with descriptive titles and summaries
- Well-documented code with file headers, comments, and inline explanations

## Challenges & Learning Reflection
- Mastered persistent storage with SharedPreferences and JSON
- Enhanced dynamic UI list handling via RecyclerView
- Improved UX with Snackbar Undo for deletions
- Clean coding by separating adapter in a different file

---

Thank you for reviewing this project. Feedback and suggestions are welcome.
