package com.khoffylabs.comptecqrses.queries.repositories;

import com.khoffylabs.comptecqrses.queries.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
