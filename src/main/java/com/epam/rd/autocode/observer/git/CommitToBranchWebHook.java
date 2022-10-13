package com.epam.rd.autocode.observer.git;

public class CommitToBranchWebHook extends AbstractWebHook{
    public CommitToBranchWebHook(String branchName) {
        super(branchName, Event.Type.COMMIT);
    }


}
