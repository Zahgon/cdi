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

import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterBeanDiscovery;
import jakarta.enterprise.inject.spi.AnnotatedMember;
import jakarta.enterprise.inject.spi.AnnotatedType;
import jakarta.enterprise.inject.spi.Extension;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.enterprise.inject.spi.InjectionTarget;
import jakarta.enterprise.inject.spi.ProcessAnnotatedType;
import jakarta.enterprise.inject.spi.ProcessInjectionTarget;
import jakarta.enterprise.inject.spi.ProcessProducer;
import jakarta.enterprise.inject.spi.WithAnnotations;
import jakarta.inject.Named;
import jakarta.inject.Qualifier;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * MyBatis CDI extension.
 *
 * @author Frank D. Martinez [mnesarco]
 */
public class MybatisExtension implements Extension {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisExtension.class.getName());

    private final Set<BeanKey> sessionProducers = new HashSet<>();

    private final Set<Type> mapperTypes = new HashSet<>();

    private final Set<InjectionPoint> injectionPoints = new HashSet<>();

    /**
     * Collect types of all mappers annotated with Mapper.
     *
     * @param <T>
     *          the generic type
     * @param pat
     *          the pat
     */
    protected <T> void processAnnotatedType(@Observes @WithAnnotations({ Mapper.class }) final ProcessAnnotatedType<T> pat) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Collect all SqlSessionFactory producers annotated with SessionFactoryProvider.
     *
     * @param <T>
     *          the generic type
     * @param <X>
     *          the generic type
     * @param pp
     *          the pp
     */
    @SuppressWarnings("unchecked")
    protected <T, X> void processProducer(@Observes final ProcessProducer<T, X> pp) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Collect all targets to match Mappers and Session providers dependency.
     *
     * @param <X>
     *          the generic type
     * @param event
     *          the event
     */
    protected <X> void processInjectionTarget(@Observes ProcessInjectionTarget<X> event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Register all mybatis injectable beans.
     *
     * @param abd
     *          the abd
     */
    @SuppressWarnings("unchecked")
    protected void afterBeanDiscovery(@Observes final AfterBeanDiscovery abd) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Unique key for fully qualified Mappers and Sessions.
     */
    private static final class BeanKey implements Comparable<BeanKey> {

        private final String key;

        private final List<Annotation> qualifiers;

        private final Class<Type> type;

        private final String sqlSessionManagerName;

        public BeanKey(Class<Type> type, Set<Annotation> annotations) {
            this.type = type;
            this.qualifiers = sort(filterQualifiers(annotations));
            // Create key = type(.qualifier)*(.name)?
            final StringBuilder sb = new StringBuilder();
            String name = null;
            sb.append(type.getName());
            for (Annotation q : this.qualifiers) {
                if (q instanceof Named) {
                    name = ((Named) q).value();
                } else {
                    sb.append(".").append(q.annotationType().getSimpleName());
                }
            }
            if (name != null) {
                sb.append("_").append(name);
            }
            this.key = sb.toString();
            this.sqlSessionManagerName = name;
        }

        private Set<Annotation> filterQualifiers(Set<Annotation> annotations) {
            final Set<Annotation> set = new HashSet<>();
            for (Annotation a : annotations) {
                if (a.annotationType().isAnnotationPresent(Qualifier.class)) {
                    set.add(a);
                }
            }
            return set;
        }

        private List<Annotation> sort(Set<Annotation> annotations) {
            final List<Annotation> list = new ArrayList<>(annotations);
            Collections.sort(list, (a, b) -> a.getClass().getName().compareTo(b.getClass().getName()));
            return list;
        }

        @Override
        public int compareTo(BeanKey o) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public int hashCode() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public boolean equals(Object obj) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public MyBatisBean createBean() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public String getKey() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        @Override
        public String toString() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
