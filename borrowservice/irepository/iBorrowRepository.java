package com.ncu.library.borrowservice.irepository;

import java.util.List;
import com.ncu.library.borrowservice.model.Borrow;

public interface iBorrowRepository {

    List<Borrow> getAllBorrows();
    Borrow getBorrowById(Integer borrowId);
    Borrow getBorrowByDate(String borrowDate);
    Borrow addBorrow(Borrow borrow);
    Borrow updateBorrow(Borrow borrow);
    void deleteBorrowById(Integer borrowId);

    // Pagination support
    List<Borrow> getBorrowsByPage(int page, int size);
    Integer getBorrowCount();
}
