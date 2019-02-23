package com.znk.architecutrec;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes();
    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public void update(int id,String  title,String  description,int priority) {

        new UpdateNoteAsyncTask(id,title,description,priority);
    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

//    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
//        private NoteDao noteDao;
//
//        private UpdateNoteAsyncTask(NoteDao noteDao) {
//            this.noteDao = noteDao;
//        }
//
//        @Override
//        protected Void doInBackground(Note... notes) {
//            noteDao.update(notes[0]);
//            return null;
//        }
//    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        private DeleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao noteDao;

        private DeleteAllNotesAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }

    private class UpdateNoteAsyncTask  extends  AsyncTask<Void,Void,Void>{
       String  Title,D_description;
       int Id,Priority;
        public UpdateNoteAsyncTask(int id, String title,String description,int priority) {
            this.Id=id;
            this.Title=title;
            this.D_description=description;
            this.Priority=priority;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.update(Id,Title,D_description,Priority);
            return null;
        }
    }
}

