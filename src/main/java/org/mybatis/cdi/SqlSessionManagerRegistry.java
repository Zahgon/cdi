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

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Any;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionManager;

/**
 * @author Frank D. Martinez [mnesarco]
 */
@ApplicationScoped
public class SqlSessionManagerRegistry {

    private Map<SqlSessionFactory, SqlSessionManager> managers;

    @Inject
    @Any
    private Instance<SqlSessionFactory> factories;

    /**
     * Inits the SqlSessionManagerRegistry.
     */
    @PostConstruct
    public void init() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public SqlSessionManager getManager(SqlSessionFactory factory) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Collection<SqlSessionManager> getManagers() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
