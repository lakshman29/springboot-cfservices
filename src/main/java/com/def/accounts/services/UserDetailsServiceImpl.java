package com.def.accounts.services;
/*package io.pivotal.springtrader.accounts.services;

import io.pivotal.springtrader.accounts.domain.Account;
import io.pivotal.springtrader.accounts.exceptions.NoRecordsFoundException;
import io.pivotal.springtrader.accounts.repositories.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	AccountRepository accountRepository;
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
	Account account=accountRepository.findByUserid(username);
		if (account == null) {
			
			throw new NoRecordsFoundException();
		}
		 User user =new User(account.getEmail(), account.getPasswd(), AuthorityUtils.createAuthorityList(new String[]{"USER", "ADMIN"}));
		return user;
	}

}
*/