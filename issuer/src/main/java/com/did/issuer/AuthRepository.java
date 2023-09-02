package com.did.issuer;

import com.did.issuer.domain.Identity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Identity, Long> {
    Optional<Identity> findByPhone(String phone);
}
