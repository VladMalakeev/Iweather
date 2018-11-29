package vlad.iweather2.DataBase;

import android.arch.persistence.room.Room;
import android.content.Context;


public class DataBaseSingleton{

        public static DataBaseSingleton instance;

        private AppDataBase database;

        private DataBaseSingleton(Context context){
            database = Room.databaseBuilder(context, AppDataBase.class, "database")
                    .build();
        }

        public static DataBaseSingleton getInstance(Context context) {
            if(instance == null){
                instance = new DataBaseSingleton(context);
            }
            return instance;
        }

        public AppDataBase getDatabase() {
            return database;
        }
    }