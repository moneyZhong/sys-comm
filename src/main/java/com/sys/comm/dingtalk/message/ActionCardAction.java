package com.sys.comm.dingtalk.message;

/**
 * Created by dustin on 2017/3/19.
 */
public class ActionCardAction {
    private String title;
    private String actionURL;
    public ActionCardAction(){}
    public ActionCardAction(String text, String actionURL) {
        this.title = text;
        this.actionURL = actionURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getActionURL() {
        return actionURL;
    }

    public void setActionURL(String actionURL) {
        this.actionURL = actionURL;
    }
}
