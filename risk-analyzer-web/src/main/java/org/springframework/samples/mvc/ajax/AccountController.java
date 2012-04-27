package org.springframework.samples.mvc.ajax;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/account")
public class AccountController {

	private final static Logger logger = LoggerFactory.getLogger(AccountController.class);
	private Map<Long, Account> accounts = new ConcurrentHashMap<Long, Account>();
	
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	public @ResponseBody Account get(@PathVariable Long id) {
		logger.info("Requesting account with id {}", id);
		Account account = new Account();
		account.setBalance(new BigDecimal(10));
		account.setName("this mis my account");

		return account;
		//Account account = accounts.get(id);
		/*if (account == null) {
			throw new ResourceNotFoundException(id);
		}
		return account;*/
	}

}
