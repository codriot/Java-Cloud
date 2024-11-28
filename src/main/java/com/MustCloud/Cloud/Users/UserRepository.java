package com.MustCloud.Cloud.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    // Email ile kullanıcı bulma gibi ek metotlar tanımlayabilirsiniz.
    User findByEmail(String email);
}
