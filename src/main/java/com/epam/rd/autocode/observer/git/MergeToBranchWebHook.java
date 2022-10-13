package com.epam.rd.autocode.observer.git;

public class MergeToBranchWebHook extends AbstractWebHook {

    public MergeToBranchWebHook(String branchName) {
        super(branchName, Event.Type.MERGE);
    }
}
