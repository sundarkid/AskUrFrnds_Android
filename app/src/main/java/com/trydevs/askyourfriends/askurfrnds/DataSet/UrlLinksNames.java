package com.trydevs.askyourfriends.askurfrnds.DataSet;


public class UrlLinksNames {
    public static final String URL_BASE = "http://askyourfriend.in/";
    public static final String URL_ADD_FRIENDS = "addFriends.php";
    public static final String URL_FRIEND_REQUESTS = "getFriendRequestList.php";
    public static final String URL_CONFIRM_FRIENDS = "confirmFriends.php";
    public static final String URL_REMOVE_FRIENDS = "removeFriends.php";
    public static final String URL_SIGNUP = "signup.php";
    public static final String URL_LOGIN = "login.php";
    public static final String URL_UPLOAD_QUIZ = "uploadQuiz.php";
    public static final String URL_DOWNLOAD_QUIZ = "getQuestions.php";
    public static final String LOGIN_FILE_NAME = "login_detail";
    public static final String URL_FORGOT_PASSWORD = "forgotPassword.php";
    public static final String URL_GET_PEOPLE_LIST = "getPeopleList.php";
    public static final String URL_CHECK = "checkSql.php";

    public static String getUrlDownloadQuiz() {
        return URL_BASE + URL_DOWNLOAD_QUIZ;
    }

    public static String getUrlAddFriends() {
        return URL_BASE + URL_ADD_FRIENDS;
    }

    public static String getUrlConfirmFriends() {
        return URL_BASE + URL_CONFIRM_FRIENDS;
    }

    public static String getUrlRemoveFriends() {
        return URL_BASE + URL_REMOVE_FRIENDS;
    }

    public static String getUrlSignup() {
        return URL_BASE + URL_SIGNUP;
    }

    public static String getUrlGetPeopleList() {
        return URL_BASE + URL_GET_PEOPLE_LIST;
    }

    public static String getUrlFriendRequests() {
        return URL_BASE + URL_FRIEND_REQUESTS;
    }

    public static String getUrlCheck() {
        return URL_BASE + URL_CHECK;
    }

    public static String getUrlLogin() {
        return URL_BASE + URL_LOGIN;
    }

    public static String getUrlUploadQuiz() {
        return URL_BASE + URL_UPLOAD_QUIZ;
    }

    public static String getUrlForgotPassword() {
        return URL_BASE + URL_FORGOT_PASSWORD;
    }

    public static String getLoginFileName() {
        return LOGIN_FILE_NAME;
    }
}
