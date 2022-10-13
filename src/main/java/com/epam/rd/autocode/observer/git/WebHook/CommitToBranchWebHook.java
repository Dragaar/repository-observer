package com.epam.rd.autocode.observer.git.WebHook;

public class CommitToBranchWebHook extends AbstractWebHook{
    public CommitToBranchWebHook(String branchName) {
        super(branchName, Event.Type.COMMIT);
    }


}
