package org.jboss.as.quickstarts.remote.mdb;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class RemoteBrokerBean {

    private List<String> messages = new ArrayList<>();

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getAllValues() {
        return messages;
    }
}
