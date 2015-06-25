package com.trydevs.askyourfriends.askurfrnds.DataSet;


public class UrlLinksNames {
    public static final String URL_BASE = "http://askyourfriend.in/";
    public static final String URL_ADD_FRIENDS = "addFriends.php";
    public static final String URL_FRIEND_REQUESTS = "getFriendRequestList.php";
    public static final String URL_FRIEND_LIST = "getFriendsList.php";
    public static final String URL_CONFIRM_FRIENDS = "confirmFriends.php";
    public static final String URL_REMOVE_FRIENDS = "removeFriends.php";
    public static final String URL_SIGNUP = "signup.php";
    public static final String URL_LOGIN = "login.php";
    public static final String URL_UPLOAD_QUIZ = "uploadQuiz.php";
    public static final String URL_DOWNLOAD_QUIZ = "getQuestions.php";
    public static final String URL_GET_NEW_INFO = "getNewInfo.php";
    public static final String URL_QUIZ_RESULT = "getResult.php";
    public static final String LOGIN_FILE_NAME = "login_detail";
    public static final String URL_FORGOT_PASSWORD = "forgotPassword.php";
    public static final String URL_GET_PEOPLE_LIST = "getPeopleList.php";
    public static final String URL_CHECK = "checkSql.php";

    public static final String JSON_USER_ID = "user_id";
    public static final String JSON_QUESTION_ID = "q_id";
    public static final String JSON_GROUP_ID = "group_no";
    public static final String JSON_QUESTION = "question";
    public static final String JSON_OPTION_A = "optionA";
    public static final String JSON_OPTION_B = "optionB";
    public static final String JSON_OPTION_C = "optionC";
    public static final String JSON_OPTION_D = "optionD";
    public static final String JSON_ANSWER = "answer";
    public static final String JSON_DATE = "date";

    public static final String JSON_NAME = "name";
    public static final String JSON_INSTITUTION = "institution";
    public static final String JSON_MAIL = "mail";
    public static final String JSON_PHONE = "phone";
    public static final String JSON_REQUEST_ID = "request_id";

    public static String getJsonUserId() {
        return JSON_USER_ID;
    }

    public static String getJsonName() {
        return JSON_NAME;
    }

    public static String getJsonMail() {
        return JSON_MAIL;
    }

    public static String getJsonPhone() {
        return JSON_PHONE;
    }

    public static String getJsonRequestId() {
        return JSON_REQUEST_ID;
    }

    public static String getJsonInstitution() {
        return JSON_INSTITUTION;
    }

    public static String getJsonQuestionId() {
        return JSON_QUESTION_ID;
    }

    public static String getJsonGroupId() {
        return JSON_GROUP_ID;
    }

    public static String getJsonQuestion() {
        return JSON_QUESTION;
    }

    public static String getJsonOptionA() {
        return JSON_OPTION_A;
    }

    public static String getJsonOptionB() {
        return JSON_OPTION_B;
    }

    public static String getJsonOptionC() {
        return JSON_OPTION_C;
    }

    public static String getJsonOptionD() {
        return JSON_OPTION_D;
    }

    public static String getJsonAnswer() {
        return JSON_ANSWER;
    }

    public static String getJsonDate() {
        return JSON_DATE;
    }

    public static String getUrlBase() {
        return URL_BASE;
    }

    public static String getUrlFriendList() {
        return URL_BASE + URL_FRIEND_LIST;
    }

    public static String getUrlGetNewInfo() {
        return URL_BASE + URL_GET_NEW_INFO;
    }

    public static String getUrlDownloadQuiz() {
        return URL_BASE + URL_DOWNLOAD_QUIZ;
    }

    public static String getUrlAddFriends() {
        return URL_BASE + URL_ADD_FRIENDS;
    }

    public static String getUrlQuizResult() {
        return URL_BASE + URL_QUIZ_RESULT;
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
