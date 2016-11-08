package com.def.accounts.repositories;


import org.springframework.data.repository.CrudRepository;

import com.def.accounts.domain.Account;

public interface AccountRepository extends CrudRepository<Account,Integer> {

	Account findByUseridAndPasswd(String userId, String passwd);

	Account findByUserid(String userId);

	Account findByAuthtoken(String authtoken);
}
