package com.epam.rd.autocode.observer.git.WebHook;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWebHook implements WebHook{
    String branchName;
    Event.Type type;

    List<Event> eventList = new ArrayList<>();

    public AbstractWebHook(String branchName, Event.Type type) {
        this.branchName = branchName;
        this.type = type;
    }

    @Override
    public String branch() {
        return branchName;
    }

    @Override
    public Event.Type type() {
        return type;
    }

    @Override
    public List<Event> caughtEvents() { //список подій що відбулися
        return eventList;
    }

    @Override
    public void onEvent(Event event) {
        eventList.add(event);
    }
}
