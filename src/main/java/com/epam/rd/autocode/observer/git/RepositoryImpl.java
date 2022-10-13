package com.epam.rd.autocode.observer.git;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositoryImpl implements Repository{

    List<Branch> branches = new ArrayList<>();

    public RepositoryImpl() {

        branches.add(new Branch("main"));
    }

    @Override
    public Branch getBranch(String name) {
       Optional<Branch> res = branches
                .stream()
                .filter(b -> b.toString().equals(name))
                .findFirst();
        if(res.isEmpty()) return null;
        else return res.get();
    }
    private Branch getBranchByHashCode(Branch branch)
    {
        Optional<Branch> res = branches
                .stream()
                .filter(b -> b.hashCode() == branch.hashCode())
                .findFirst();
        if(res.isEmpty()) return null;
        else return res.get();
    }
    @Override
    public Branch newBranch(Branch sourceBranch, String name) {
        if(this.getBranch(name) != null                             //якщо гілка з вказаним ім'ям уже існує
                || this.getBranchByHashCode(sourceBranch) == null) // якщо батько в ієрархії не був знайдений
        {    throw new IllegalArgumentException();}


        Branch newBranch = new Branch(name);
        branches.add(newBranch);
        return newBranch;
    }

    @Override
    public Commit commit(Branch branch, String author, String[] changes) {
        return null;
    }

    @Override
    public void merge(Branch sourceBranch, Branch targetBranch) {

    }

    @Override
    public void addWebHook(WebHook webHook) {

    }
}
