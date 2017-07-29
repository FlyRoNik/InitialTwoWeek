package com.cleveroad.nikita_frolov_cr.initialtwoweek.dao;

public interface Subject {
    void registerObserver(RepositoryObserver repositoryObserver);
    void removeObserver(RepositoryObserver repositoryObserver);
    void notifyObservers();
}
