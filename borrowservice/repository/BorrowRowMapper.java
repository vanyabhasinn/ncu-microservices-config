package com.ncu.library.borrowservice.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ncu.library.borrowservice.irepository.iBorrowRepository;
import com.ncu.library.borrowservice.model.Borrow;

@Repository
public class BorrowRepository implements iBorrowRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BorrowRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Borrow> getAllBorrows() {
        String sql = "SELECT * FROM borrow";
        return jdbcTemplate.query(sql, new BorrowRowMapper());
    }

    @Override
    public Borrow getBorrowById(Integer borrowId) {
        String sql = "SELECT * FROM borrow WHERE BorrowId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BorrowRowMapper(), borrowId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Borrow getBorrowByDate(String borrowDate) {
        String sql = "SELECT * FROM borrow WHERE BorrowDate = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new BorrowRowMapper(), borrowDate);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Borrow addBorrow(Borrow borrow) {
        String sql = "INSERT INTO borrow (BorrowId, BorrowDate, userId) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, borrow.getBorrowId(), borrow.getBorrowDate(), borrow.getUserId());
        return borrow;
    }

    @Override
    public List<Borrow> getBorrowsByPage(int page, int size) {
        int offset = page * size;
        String sql = "SELECT * FROM borrow LIMIT ? OFFSET ?";
        return jdbcTemplate.query(sql, new BorrowRowMapper(), size, offset);
    }

    @Override
    public Integer getBorrowCount() {
        String sql = "SELECT COUNT(*) FROM borrow";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public Borrow updateBorrow(Borrow borrow) {
        String sql = "UPDATE borrow SET BorrowDate = ?, userId = ? WHERE BorrowId = ?";
        jdbcTemplate.update(sql, borrow.getBorrowDate(), borrow.getUserId(), borrow.getBorrowId());
        return borrow;
    }

    @Override
    public void deleteBorrowById(Integer borrowId) {
        String sql = "DELETE FROM borrow WHERE BorrowId = ?";
        jdbcTemplate.update(sql, borrowId);
    }
}
