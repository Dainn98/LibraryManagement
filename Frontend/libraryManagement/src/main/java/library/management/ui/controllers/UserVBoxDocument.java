package library.management.ui.controllers;

import library.management.data.entity.Document;
import library.management.data.entity.Loan;

import java.util.ArrayList;
import java.util.List;

public interface UserVBoxDocument {
    List<Loan> loanList = new ArrayList<>();
    List<Document> documentList = new ArrayList<>();
    List<UserDocContainerController> docContainerControllerList = new ArrayList<>();
}
