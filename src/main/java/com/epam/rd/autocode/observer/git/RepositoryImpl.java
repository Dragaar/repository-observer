package com.epam.rd.autocode.observer.git;

import java.util.*;

public class RepositoryImpl implements Repository{

    Map<Branch, List<Commit>> branches = new HashMap<>();

    ArrayList<WebHook> webHooks = new ArrayList<>();

    public RepositoryImpl() {
        //Commit firstCommit = new Commit("user", new String[]{"initial commit"});
        branches.put(new Branch("main"), new ArrayList<>());
    }

    @Override
    public Branch getBranch(String name) {
       Optional<Branch> res = branches
                .keySet()
                .stream()
                .filter(b -> b.toString().equals(name))
                .findFirst();
        if(res.isEmpty()) return null;
        else return res.get();
    }
    private Branch getBranchByHashCode(Branch branch)
    {
        Optional<Branch> res = branches
                .keySet()
                .stream()
                .filter(b -> b.hashCode() == branch.hashCode())
                .findFirst();
        if(res.isEmpty()) return null;
        else return res.get();
    }
   /* private Map<Branch, List<Commit>> getBranchByHashCode(Branch branch)
    {
        Map<Branch, List<Commit>> res = branches
                .entrySet()
                .stream()
                .filter(s -> s.getKey().hashCode() == branch.hashCode())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return res;
    }*/
    @Override
    public Branch newBranch(Branch sourceBranch, String name) {

        if(this.getBranch(name) != null                       //якщо гілка з вказаним ім'ям уже існує
                || getBranchByHashCode(sourceBranch) == null) // якщо батько в ієрархії не був знайдений
        {    throw new IllegalArgumentException();}

        Branch newBranch = new Branch(name);
        branches.put(newBranch, new ArrayList<>(branches.get(sourceBranch))); // створення нової гілки із копіюванням всіх комітів батька
        return newBranch;
    }

    @Override
    public Commit commit(Branch branch, String author, String[] changes) {
        Commit commit = new Commit(author, changes);
        branches.get(branch).add(commit);

        List<Commit> commitList = new ArrayList<>(); //затичка щоб передати лиш один коміт в notify
        commitList.add(commit);
        notifyWebHook(Event.Type.COMMIT, branch, commitList);

        return commit;
    }

    @Override
    public void merge(Branch sourceBranch, Branch targetBranch) {
        List<Commit> sourceCommits = branches.get(sourceBranch);
        List<Commit> targetCommits = branches.get(targetBranch);

        List<Commit> resultCommits = new ArrayList<>();
        boolean hasMerged = false;

        for(int i = 0; i < sourceCommits.size(); i++)
        {
            //якщо в targetBranch не має елементу з sourceCommits, тоді добавити його
            if(!targetCommits.contains(sourceCommits.get(i)))
            {
                resultCommits.add(sourceCommits.get(i)); // список скопійованих елементів для  notify
                targetCommits.add(sourceCommits.get(i));
                hasMerged = true;
            }
        }

        if(hasMerged){
            notifyWebHook(Event.Type.MERGE, targetBranch, resultCommits);
        }
    }

    @Override
    public void addWebHook(WebHook webHook) {
        webHooks.add(webHook);
    }

    private void notifyWebHook(Event.Type type, Branch branch, List<Commit> commits){
        webHooks.forEach(webHook -> {
                    if(webHook.type() == type && webHook.branch().equals(branch.toString()))
                    { webHook.onEvent(new Event(type, branch, commits)); }
                });
    }
}
