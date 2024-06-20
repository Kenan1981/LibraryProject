package com.tpe.entity.concretes.business;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardStatistics {

    private long books;
    private long authors;
    private long publishers;
    private long categories;
    private long loans;
    private long unReturnedBooks;
    private long expiredBooks;
    private long members;
}