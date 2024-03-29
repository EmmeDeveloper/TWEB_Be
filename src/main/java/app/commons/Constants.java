package app.commons;

public class Constants {

    public class Features {
        public static final String COURSE_ADD = "Course#Add";
        public static final String COURSE_DELETE = "Course#Delete";
        public static final String COURSE_GET_PROFESSORS = "Course#Getprofessors";
        public static final String COURSE_GET_ALL = "Course#Getall";

        public static final String PROFESSOR_ADD = "Professor#Add";
        public static final String PROFESSOR_DELETE = "Professor#Delete";
        public static final String PROFESSOR_GET_ALL = "Professor#Getall";

        public static final String TEACHING_ADD = "Professor#Addcourse";
        public static final String TEACHING_DELETE = "Professor#Deletecourse";
        public static final String SIGN_UP = "User#Signup";
        public static final String LOGOUT = "User#Logout";
        public static final String LOGIN = "User#Login";

        public static final String REPETITIONS_GET_AVAILABLE = "Repetitions#Getall";
        public static final String REPETITIONS_RESERVE = "Repetitions#Reserve";
        public static final String REPETITIONS_DELETE = "Repetitions#Delete";
        public static final String REPETITIONS_SET_DONE = "Repetitions#Setdone";
        public static final String REPETITIONS_UPDATE_NOTES = "Repetitions#Updatenotes";
        public static final String REPETITIONS_SET_DELETED = "Repetitions#Setdeleted";

        public static final String REPETITIONS_GET_FOR_USER = "Repetitions#Getallforuser";
        public static final String REPETITIONS_GET_FOR_ALL_USERS = "Repetitions#Getforallusers";

    }

    public class Roles {
        public static final String GUEST = "Guest";
        public static final String USER = "User";
        public static final String ADMIN = "Admin";
    }

    public class RepetitionStatus {
        public static final String PENDING = "pending";
        public static final String DONE = "done";
        public static final String DELETED = "deleted";
    }
}
