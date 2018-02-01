package com.samples.services.foreground;

/**
 * Created by Anukool Srivastav on 01/02/18.
 */

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "com.samples.services.foreground.action.main";
        public static String INIT_ACTION = "com.samples.services.foreground.action.init";
        public static String PREV_ACTION = "com.samples.services.foreground.action.prev";
        public static String PLAY_ACTION = "com.samples.services.foreground.action.play";
        public static String NEXT_ACTION = "com.samples.services.foreground.action.next";
        public static String STARTFOREGROUND_ACTION = "com.samples.services.foreground.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "com.samples.services.foreground.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }

}
