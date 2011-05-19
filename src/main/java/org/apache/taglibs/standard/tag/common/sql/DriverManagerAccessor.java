/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.apache.taglibs.standard.tag.common.sql;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class that provides access to {@link java.sql.DriverManager}.
 * <p/>
 * DriverManager is not compatible with a modular environment, as it does no allow direct
 * access to driver classes that the callers class loader cannot load. This class allows access
 * to DriverManager by being forced into a cl that does have access and then invoking the DriverManager
 * methods.
 * <p/>
 * This is a horrible hack.
 *
 * @author Stuart Douglas
 */
public class DriverManagerAccessor {

    /**
     * Delegates to {@link java.sql.DriverManager#getConnection(String, String, String)}. If this fails it attempts to
     * load a class into the class loader cl and tries again.
     *
     * @param jdbcURL  The JDBC url
     * @param userName The username
     * @param password The password
     * @return A database connection
     */
    public static Connection getConnection(final String jdbcURL, final String userName, final String password) throws SQLException {
        try {
            return getRealConnection(jdbcURL, userName, password);
        } catch (final SQLException sqlException) {
            try {
                return (Connection) AccessController.doPrivileged(new PrivilegedExceptionAction() {
                    public Object run() throws Exception {
                        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
                        if (cl != null && cl != DriverManagerAccessor.class.getClassLoader()) {
                            final Class<?> definedClass = DriverManagerAccessorSupport.define(cl);
                            final Method method = definedClass.getDeclaredMethod("getRealConnection", String.class, String.class, String.class);
                            method.setAccessible(true);
                            return method.invoke(null, jdbcURL, userName, password);
                        } else {
                            throw sqlException;
                        }
                    }
                });
            } catch (PrivilegedActionException e) {
                throw sqlException;
            }
        }
    }

    private static Connection getRealConnection(final String jdbcURL, final String userName, final String password) throws SQLException {
        return DriverManager.getConnection(jdbcURL, userName, password);
    }


    /**
     * Delegates to {@link java.sql.DriverManager#getConnection(String)}. If this fails it attempts to
     * load a class into the class loader cl and tries again.
     *
     * @param jdbcURL The JDBC url
     * @return A database connection
     */
    public static Connection getConnection(final String jdbcURL) throws SQLException {
        try {
            return getRealConnection(jdbcURL);
        } catch (final SQLException sqlException) {
            try {
                return (Connection) AccessController.doPrivileged(new PrivilegedExceptionAction() {
                    public Object run() throws Exception {
                        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
                        if (cl != null && cl != DriverManagerAccessor.class.getClassLoader()) {
                            final Class<?> definedClass = DriverManagerAccessorSupport.define(cl);
                            final Method method = definedClass.getDeclaredMethod("getRealConnection", String.class);
                            method.setAccessible(true);
                            return (Connection) method.invoke(null, jdbcURL);

                        } else {
                            throw sqlException;
                        }
                    }
                });
            } catch (PrivilegedActionException e) {
                throw sqlException;
            }
        }
    }

    private static Connection getRealConnection(final String jdbcURL) throws SQLException {
        return DriverManager.getConnection(jdbcURL);
    }

}
