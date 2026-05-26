/*
 *    Copyright 2013-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.cdi;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptor;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.Status;
import jakarta.transaction.SystemException;
import jakarta.transaction.UserTransaction;

/**
 * Interceptor for JTA transactions. MyBatis should be configured to use the {@code MANAGED} transaction manager.
 *
 * @author Eduardo Macarrón
 */
@Transactional
@Interceptor
public class JtaTransactionInterceptor extends LocalTransactionInterceptor {

    private static final long serialVersionUID = 1L;

    @Inject
    private transient Instance<UserTransaction> userTransaction;

    @Override
    protected boolean isTransactionActive() throws SystemException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void beginJta() throws NotSupportedException, SystemException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    protected void endJta(boolean isExternaTransaction, boolean needsRollback) throws SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
