JBoss JSTL API Spec 1.2
=======================

What is this
-----------------------

This is fork of Apache's JSTL 1.2 implementation with few extra fixes to make it work better inside WildFly's modular classloader.

Upstream used: https://svn.apache.org/repos/asf/tomcat/taglibs/standard/


Building
-------------------

Ensure you have JDK 7 (or newer) installed

> java -version

On *nix-like system use the prepared script

> ./build.sh

On Windows use the corresponding batch script

> build.bat

If you already have Maven 3.1.0 (or newer) installed you can use it directly

> mvn install
