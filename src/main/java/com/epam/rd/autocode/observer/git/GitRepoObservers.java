package com.epam.rd.autocode.observer.git;

import com.epam.rd.autocode.observer.git.Repository.Repository;
import com.epam.rd.autocode.observer.git.Repository.RepositoryImpl;
import com.epam.rd.autocode.observer.git.WebHook.CommitToBranchWebHook;
import com.epam.rd.autocode.observer.git.WebHook.MergeToBranchWebHook;
import com.epam.rd.autocode.observer.git.WebHook.WebHook;

public class GitRepoObservers {
    public static Repository newRepository() {
        return new RepositoryImpl();
    }

    public static WebHook mergeToBranchWebHook(String branchName) {
        return new MergeToBranchWebHook(branchName);
    }

    public static WebHook commitToBranchWebHook(String branchName) {
        return new CommitToBranchWebHook(branchName);
    }
}
