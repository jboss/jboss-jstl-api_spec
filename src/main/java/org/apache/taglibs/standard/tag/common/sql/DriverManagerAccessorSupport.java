package org.apache.taglibs.standard.tag.common.sql;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

/**
 * Support routines for {@link DriverManagerAccessor}
 *
 * @author Stuart Douglas
 */
public class DriverManagerAccessorSupport {


    private static java.lang.reflect.Method defineClass;

    static {
        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Object>() {
                public Object run() throws Exception {
                    Class<?> cl = Class.forName("java.lang.ClassLoader", false, null);
                    Method m1 = cl.getDeclaredMethod("defineClass", new Class[]{String.class, byte[].class, int.class,
                            int.class});
                    m1.setAccessible(true);
                    defineClass = m1;
                    return null;
                }
            });
        } catch (PrivilegedActionException pae) {
            throw new RuntimeException("cannot initialize ClassFile", pae.getException());
        }
    }

    /**
     * Definines the class using the given ClassLoader and ProtectionDomain
     */
    public static Class<?> define(ClassLoader loader) {

        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                String packageName = DriverManagerAccessor.class.getPackage().getName();
                RuntimePermission permission = new RuntimePermission("defineClassInPackage." + packageName);
                sm.checkPermission(permission);
            }

            byte[] b = loadBytes();
            Object[] args = new Object[]{DriverManagerAccessor.class.getName().replace('/', '.'), b, new Integer(0), new Integer(b.length)};
            Class<?> clazz = (Class<?>) defineClass.invoke(loader, args);
            return clazz;
        } catch (RuntimeException e) {
            //we have to attempt to define the class first
            //otherwise it may be returned by a different class loader
            try {
                return loader.loadClass(DriverManagerAccessor.class.getName());
            } catch (ClassNotFoundException ex) {
                //ignore
            }
            throw e;
        } catch (Exception e) {
            try {
                return loader.loadClass(DriverManagerAccessor.class.getName());
            } catch (ClassNotFoundException ex) {
                //ignore
            }
            throw new RuntimeException(e);
        }
    }

    private static byte[] loadBytes() {
        final InputStream inputStream = DriverManagerAccessor.class.getClassLoader().getResourceAsStream(DriverManagerAccessor.class.getName().replace('.', '/') + ".class");
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        byte[] buffer = new byte[512];
        int read = 0;
        try {
            while ((read = inputStream.read(buffer)) != -1) {
                bytes.write(buffer, 0, read);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes.toByteArray();
    }

}
