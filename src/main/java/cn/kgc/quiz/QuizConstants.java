package cn.kgc.quiz;

public class QuizConstants {


    //feedback mode
    public static final int QUIZ_FEEDBACK_MODE_FREE = 0;
    public static final int QUIZ_FEEDBACK_MODE_INTERACTIVE = 1;
    public static final int QUIZ_FEEDBACK_MODE_CBM = 2;
    public static final int QUIZ_FEEDBACK_MODE_IDEA = 3;

    //user type
    public static final int USER_TYPE_TEACHER = 0;
    public static final int USER_TYPE_STUDENT = 1;

    //whether the question type is set by default
    public static final int QUESTION_TYPE_DEFAULT = 0;
    public static final int QUESTION_TYPE_NON_DEFAULT = 1;

    //the flag whether the user's answer id right, partly right ,wrong
    public static final int USER_ANSWER_RIGHT = 0;
    public static final int USER_ANSWER_PARTLY_RIGHT = 1;
    public static final int USER_ANSWER_WRONG = 2;

    //the question finished
    public static final int QUESTION_ATTEMPT_FINISHED = 0;
    public static final int QUESTION_ATTEMPT_UNFINISHED = 1;

    //exam type
    public static final int QUIZ_EXAM_TYPE_QUIZ = 0;
    public static final int QUIZ_EXAM_TYPE_EXAM = 1;


}
