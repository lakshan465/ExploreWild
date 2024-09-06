package lk.ruhcs.explorewild;

import javafx.event.ActionEvent;

public abstract class User {

    //public abstract void reload();
    public abstract void close();
    public abstract void logout();
    public abstract void switchForm(ActionEvent event);
}
