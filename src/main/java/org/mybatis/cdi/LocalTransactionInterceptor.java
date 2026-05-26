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

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.transaction.HeuristicMixedException;
import jakarta.transaction.HeuristicRollbackException;
import jakarta.transaction.NotSupportedException;
import jakarta.transaction.RollbackException;
import jakarta.transaction.SystemException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import org.apache.ibatis.session.SqlSessionManager;

/**
 * Best-effort interceptor for local transactions. It locates all the instances of {@code SqlSssionManager} and starts
 * transactions on all them. It cannot guarantee atomiticy if there is more than one {@code SqlSssionManager}. Use XA
 * drivers, a JTA container and the {@link JtaTransactionInterceptor} in that case.
 *
 * @see JtaTransactionInterceptor
 *
 * @author Frank David Martínez
 */
@Transactional
@Interceptor
public class LocalTransactionInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private transient SqlSessionManagerRegistry registry;

    /**
     * Invoke.
     *
     * @param ctx
     *          the ctx
     *
     * @return the object
     *
     * @throws Exception
     *           the exception
     */
    @AroundInvoke
    public Object invoke(InvocationContext ctx) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Checks if is transaction active.
     *
     * @return true, if is transaction active
     *
     * @throws SystemException
     *           used by jtaTransactionInterceptor
     */
    protected boolean isTransactionActive() throws SystemException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Begin jta.
     *
     * @throws NotSupportedException
     *           used by jtaTransactionInterceptor
     * @throws SystemException
     *           used by jtaTransactionInterceptor
     */
    protected void beginJta() throws NotSupportedException, SystemException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * End jta.
     *
     * @param isExternaTransaction
     *          the is externa transaction
     * @param commit
     *          the commit
     *
     * @throws SystemException
     *           used by jtaTransactionInterceptor
     * @throws RollbackException
     *           used by jtaTransactionInterceptor
     * @throws HeuristicMixedException
     *           used by jtaTransactionInterceptor
     * @throws HeuristicRollbackException
     *           used by jtaTransactionInterceptor
     */
    protected void endJta(boolean isExternaTransaction, boolean commit) throws SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean needsRollback(Transactional transactional, Throwable throwable) {
        if (RuntimeException.class.isAssignableFrom(throwable.getClass())) {
            return true;
        }
        for (Class<? extends Throwable> exceptionClass : transactional.rollbackFor()) {
            if (exceptionClass.isAssignableFrom(throwable.getClass())) {
                return true;
            }
        }
        return false;
    }

    protected Transactional getTransactionalAnnotation(InvocationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean start(Transactional transactional) {
        boolean started = false;
        for (SqlSessionManager manager : this.registry.getManagers()) {
            if (!manager.isManagedSessionStarted()) {
                manager.startManagedSession(transactional.executorType(), transactional.isolation().getTransactionIsolationLevel());
                started = true;
            }
        }
        return started;
    }

    private void commit(Transactional transactional) {
        for (SqlSessionManager manager : this.registry.getManagers()) {
            manager.commit(transactional.force());
        }
    }

    private void rollback(Transactional transactional) {
        for (SqlSessionManager manager : this.registry.getManagers()) {
            manager.rollback(transactional.force());
        }
    }

    private void close() {
        for (SqlSessionManager manager : this.registry.getManagers()) {
            manager.close();
        }
    }

    private Exception unwrapException(Exception wrapped) {
        Throwable unwrapped = wrapped;
        while (true) {
            if (unwrapped instanceof InvocationTargetException) {
                unwrapped = ((InvocationTargetException) unwrapped).getTargetException();
            } else if (unwrapped instanceof UndeclaredThrowableException) {
                unwrapped = ((UndeclaredThrowableException) unwrapped).getUndeclaredThrowable();
            } else if (!(unwrapped instanceof Exception)) {
                return new RuntimeException(unwrapped);
            } else {
                return (Exception) unwrapped;
            }
        }
    }
}
