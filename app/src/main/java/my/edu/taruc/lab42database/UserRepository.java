package my.edu.taruc.lab42database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;

//TODO 6: Create a repository class to manage data query thread

public class UserRepository {
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    public UserRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);

        userDao = db.userDao();
        allUsers = userDao.loadAllUsers();
    }

    LiveData<List<User>> getAllUsers(){
        return allUsers;
    }

    public void deleteAll(User user){
        new deleteAllAsyncTask(userDao).execute(user);
    }

    //<Param, Progress, Results>
    private static class deleteAllAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        public deleteAllAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.deleteUser(users[0]);
            return null;
        }
    }
}

