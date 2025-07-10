package com.kenryhraval.banking.security;

import org.springframework.security.access.prepost.PreAuthorize;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasRole('ADMIN') or @accountSecurity.isAccountOwner(#accountId, authentication)")
public @interface IsAccountOwnerOrAdmin { }
